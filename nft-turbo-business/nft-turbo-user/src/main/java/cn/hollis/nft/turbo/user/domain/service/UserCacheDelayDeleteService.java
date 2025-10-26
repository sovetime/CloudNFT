package cn.hollis.nft.turbo.user.domain.service;

import cn.hollis.nft.turbo.user.domain.entity.User;
import com.alicp.jetcache.Cache;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;


//用户缓存延迟删除服务
//使用定时任务第二次删除缓存 ，避免出现不一致的情况
@Service
@Slf4j
public class UserCacheDelayDeleteService {

    //自定义线程工厂
    private static ThreadFactory userCacheDelayProcessFactory = new ThreadFactoryBuilder()
            //设置线程名称格式
            .setNameFormat("user-cache-delay-delete-pool-%d").build();

    //定时任务线程池
    private ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(10, userCacheDelayProcessFactory);

    public void delayedCacheDelete(Cache idUserCache, User user) {
        //调度延迟任务，在2秒后执行缓存删除操作
        scheduler.schedule(() -> {
            boolean idDeleteResult = idUserCache.remove(user.getId().toString());
            log.info("idUserCache removed, key = {} , result  = {}", user.getId(), idDeleteResult);
        }, 2, TimeUnit.SECONDS);
    }
}
