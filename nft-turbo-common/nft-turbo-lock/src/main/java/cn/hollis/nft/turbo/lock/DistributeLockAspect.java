package cn.hollis.nft.turbo.lock;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;


//分布式锁切面
@Aspect
@Component
@Order(Integer.MIN_VALUE + 1)
@Slf4j
public class DistributeLockAspect {

    private RedissonClient redissonClient;

    public DistributeLockAspect(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    //基于 Redisson 实现分布式锁，保证方法在分布式环境下的并发安全。
    @Around("@annotation(cn.hollis.nft.turbo.lock.DistributeLock)")
    public Object process(ProceedingJoinPoint pjp) throws Exception {
        Object response = null;

        // 获取被拦截的方法对象
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        // 获取方法上的 @DistributeLock 注解
        DistributeLock distributeLock = method.getAnnotation(DistributeLock.class);

        // 解析锁的 key
        String key = distributeLock.key();
        if (DistributeLockConstant.NONE_KEY.equals(key)) {
            // 如果注解上没有直接配置 key，则使用 SpEL 表达式解析 key
            if (DistributeLockConstant.NONE_KEY.equals(distributeLock.keyExpression())) {
                throw new DistributeLockException("no lock key found...");
            }

            // 创建 SpEL 解析器
            SpelExpressionParser parser = new SpelExpressionParser();
            Expression expression = parser.parseExpression(distributeLock.keyExpression());

            // 设置表达式的上下文
            EvaluationContext context = new StandardEvaluationContext();
            Object[] args = pjp.getArgs(); // 方法入参
            StandardReflectionParameterNameDiscoverer discoverer = new StandardReflectionParameterNameDiscoverer();
            String[] parameterNames = discoverer.getParameterNames(method); // 参数名

            // 将方法参数绑定到 SpEL 上下文中
            if (parameterNames != null) {
                for (int i = 0; i < parameterNames.length; i++) {
                    context.setVariable(parameterNames[i], args[i]);
                }
            }

            // 执行表达式，得到最终的锁 key
            key = String.valueOf(expression.getValue(context));
        }

        // 分组场景，区分不同业务的锁
        String scene = distributeLock.scene();
        // 最终锁 key 格式：scene#key
        String lockKey = scene + "#" + key;

        // 获取注解上的配置
        int expireTime = distributeLock.expireTime(); // 锁过期时间
        int waitTime = distributeLock.waitTime();     // 等待时间

        // 获取 Redisson 分布式锁对象
        RLock rLock = redissonClient.getLock(lockKey);

        try {
            boolean lockResult = false;

            // 如果没有配置等待时间，直接上锁
            if (waitTime == DistributeLockConstant.DEFAULT_WAIT_TIME) {
                if (expireTime == DistributeLockConstant.DEFAULT_EXPIRE_TIME) {
                    // 无过期时间，阻塞锁
                    log.info(String.format("lock for key : %s", lockKey));
                    rLock.lock();
                } else {
                    // 带过期时间的锁
                    log.info(String.format("lock for key : %s , expire : %s", lockKey, expireTime));
                    rLock.lock(expireTime, TimeUnit.MILLISECONDS);
                }
                lockResult = true;
            } else {
                // 配置了等待时间 -> 使用 tryLock
                if (expireTime == DistributeLockConstant.DEFAULT_EXPIRE_TIME) {
                    log.info(String.format("try lock for key : %s , wait : %s", lockKey, waitTime));
                    lockResult = rLock.tryLock(waitTime, TimeUnit.MILLISECONDS);
                } else {
                    log.info(String.format("try lock for key : %s , expire : %s , wait : %s",
                            lockKey, expireTime, waitTime));
                    lockResult = rLock.tryLock(waitTime, expireTime, TimeUnit.MILLISECONDS);
                }
            }

            // 如果加锁失败，抛出异常
            if (!lockResult) {
                log.warn(String.format("lock failed for key : %s , expire : %s", lockKey, expireTime));
                throw new DistributeLockException("acquire lock failed... key : " + lockKey);
            }

            log.info(String.format("lock success for key : %s , expire : %s", lockKey, expireTime));

            // 执行目标方法
            response = pjp.proceed();

        } catch (Throwable e) {
            // 捕获异常，向外抛出
            throw new Exception(e);
        } finally {
            // 确保只有持有锁的线程才释放锁，避免误解锁
            if (rLock.isHeldByCurrentThread()) {
                rLock.unlock();
                log.info(String.format("unlock for key : %s , expire : %s", lockKey, expireTime));
            }
        }

        return response;
    }

}
