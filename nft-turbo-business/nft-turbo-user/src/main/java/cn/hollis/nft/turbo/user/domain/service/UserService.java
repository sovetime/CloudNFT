package cn.hollis.nft.turbo.user.domain.service;

import cn.hollis.nft.turbo.api.user.constant.UserOperateTypeEnum;
import cn.hollis.nft.turbo.api.user.constant.UserStateEnum;
import cn.hollis.nft.turbo.api.user.request.UserActiveRequest;
import cn.hollis.nft.turbo.api.user.request.UserAuthRequest;
import cn.hollis.nft.turbo.api.user.request.UserModifyRequest;
import cn.hollis.nft.turbo.api.user.response.UserOperatorResponse;
import cn.hollis.nft.turbo.api.user.response.data.InviteRankInfo;
import cn.hollis.nft.turbo.base.exception.BizException;
import cn.hollis.nft.turbo.base.exception.RepoErrorCode;
import cn.hollis.nft.turbo.base.response.PageResponse;
import cn.hollis.nft.turbo.lock.DistributeLock;
import cn.hollis.nft.turbo.user.domain.entity.User;
import cn.hollis.nft.turbo.user.domain.entity.convertor.UserConvertor;
import cn.hollis.nft.turbo.user.infrastructure.exception.UserErrorCode;
import cn.hollis.nft.turbo.user.infrastructure.exception.UserException;
import cn.hollis.nft.turbo.user.infrastructure.mapper.UserMapper;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.CacheManager;
import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.alicp.jetcache.template.QuickConfig;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.protocol.ScoredEntry;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static cn.hollis.nft.turbo.user.infrastructure.exception.UserErrorCode.*;


//用户服务
@Service
public class UserService extends ServiceImpl<UserMapper, User> implements InitializingBean {

    private static final String DEFAULT_NICK_NAME_PREFIX = "藏家_";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserOperateStreamService userOperateStreamService;

    @Autowired
    private AuthService authService;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private UserCacheDelayDeleteService userCacheDelayDeleteService;

    //用户名布隆过滤器
    private RBloomFilter<String> nickNameBloomFilter;

    //邀请码布隆过滤器
    private RBloomFilter<String> inviteCodeBloomFilter;

    //邀请排行榜
    private RScoredSortedSet<String> inviteRank;

    //通过用户ID对用户信息做的缓存
    private Cache<String, User> idUserCache;

    //@PostConstruct 作用，确保依赖注入完成、避免重复创建
    @PostConstruct
    public void init() {
        QuickConfig idQc = QuickConfig.newBuilder(":user:cache:id:")
                .cacheType(CacheType.BOTH)
                .expire(Duration.ofHours(2))
                .syncLocal(true)
                .build();
        idUserCache = cacheManager.getOrCreateCache(idQc);
    }

    // 注册
    @DistributeLock(keyExpression = "#telephone", scene = "USER_REGISTER")
    @Transactional(rollbackFor = Exception.class)
    public UserOperatorResponse register(String telephone, String inviteCode) {
        String defaultNickName;
        String randomString;
        do {
            //将随机数转换成大写
            randomString = RandomUtil.randomString(6).toUpperCase();
            //前缀 + 6位随机数 + 手机号后四位
            defaultNickName = DEFAULT_NICK_NAME_PREFIX + randomString + telephone.substring(7, 11);
        } while (nickNameExist(defaultNickName) || inviteCodeExist(randomString));

        //邀请码逻辑
        String inviterId = null;
        if (StringUtils.isNotBlank(inviteCode)) {
            User inviter = userMapper.findByInviteCode(inviteCode);
            if (inviter != null) {
                inviterId = inviter.getId().toString();
            }
        }

        User user = register(telephone, defaultNickName, telephone, randomString, inviterId);
        Assert.notNull(user, UserErrorCode.USER_OPERATE_FAILED.getCode());

        //添加对应信息到缓存中，缓存中有BloomFilter维护用户信息缓存以及邀请排行榜
        addNickName(defaultNickName);
        addInviteCode(randomString);
        updateInviteRank(inviterId);
        updateUserCache(user.getId().toString(), user);

        //加入流水
        long streamResult = userOperateStreamService.insertStream(user, UserOperateTypeEnum.REGISTER);
        Assert.notNull(streamResult, () -> new BizException(RepoErrorCode.UPDATE_FAILED));

        UserOperatorResponse userOperatorResponse = new UserOperatorResponse();
        userOperatorResponse.setSuccess(true);

        return userOperatorResponse;
    }

    //管理员注册
    @DistributeLock(keyExpression = "#telephone", scene = "USER_REGISTER")
    @Transactional(rollbackFor = Exception.class)
    public UserOperatorResponse registerAdmin(String telephone, String password) {
        User user = registerAdmin(telephone, telephone, password);
        Assert.notNull(user, UserErrorCode.USER_OPERATE_FAILED.getCode());
        idUserCache.put(user.getId().toString(), user);

        //加入流水
        long streamResult = userOperateStreamService.insertStream(user, UserOperateTypeEnum.REGISTER);
        Assert.notNull(streamResult, () -> new BizException(RepoErrorCode.UPDATE_FAILED));

        UserOperatorResponse userOperatorResponse = new UserOperatorResponse();
        userOperatorResponse.setSuccess(true);

        return userOperatorResponse;
    }

    //注册
    private User register(String telephone, String nickName, String password, String inviteCode, String inviterId) {
        if (userMapper.findByTelephone(telephone) != null) {
            throw new UserException(DUPLICATE_TELEPHONE_NUMBER);
        }

        User user = new User();
        user.register(telephone, nickName, password, inviteCode, inviterId);
        return save(user) ? user : null;
    }

    //管理员注册
    private User registerAdmin(String telephone, String nickName, String password) {
        if (userMapper.findByTelephone(telephone) != null) {
            throw new UserException(DUPLICATE_TELEPHONE_NUMBER);
        }

        User user = new User();
        user.registerAdmin(telephone, nickName, password);
        return save(user) ? user : null;
    }


    //实名认证
    @CacheInvalidate(name = ":user:cache:id:", key = "#userAuthRequest.userId")
    @Transactional(rollbackFor = Exception.class)
    public UserOperatorResponse auth(UserAuthRequest userAuthRequest) {
        UserOperatorResponse userOperatorResponse = new UserOperatorResponse();
        //根据id查询用户
        User user = userMapper.findById(userAuthRequest.getUserId());
        Assert.notNull(user, () -> new UserException(USER_NOT_EXIST));

        if (user.getState() == UserStateEnum.AUTH || user.getState() == UserStateEnum.ACTIVE) {
            userOperatorResponse.setSuccess(true);
            userOperatorResponse.setUser(UserConvertor.INSTANCE.mapToVo(user));
            return userOperatorResponse;
        }

        Assert.isTrue(user.getState() == UserStateEnum.INIT, () -> new UserException(USER_STATUS_IS_NOT_INIT));
        Assert.isTrue(authService.checkAuth(userAuthRequest.getRealName(), userAuthRequest.getIdCard()), () -> new UserException(USER_AUTH_FAIL));
        user.auth(userAuthRequest.getRealName(), userAuthRequest.getIdCard());
        boolean result = updateById(user);
        if (result) {
            //加入流水
            long streamResult = userOperateStreamService.insertStream(user, UserOperateTypeEnum.AUTH);
            Assert.notNull(streamResult, () -> new BizException(RepoErrorCode.UPDATE_FAILED));
            userOperatorResponse.setSuccess(true);
            userOperatorResponse.setUser(UserConvertor.INSTANCE.mapToVo(user));
        } else {
            userOperatorResponse.setSuccess(false);
            userOperatorResponse.setResponseCode(UserErrorCode.USER_OPERATE_FAILED.getCode());
            userOperatorResponse.setResponseMessage(UserErrorCode.USER_OPERATE_FAILED.getMessage());
        }
        return userOperatorResponse;
    }


    //用户激活
    //@CacheInvalidate，标识这个方法被调用时需要移除用户缓存
    @CacheInvalidate(name = ":user:cache:id:", key = "#userActiveRequest.userId")
    @Transactional(rollbackFor = Exception.class)
    public UserOperatorResponse active(UserActiveRequest userActiveRequest) {
        UserOperatorResponse userOperatorResponse = new UserOperatorResponse();
        //根据id查询用户
        User user = userMapper.findById(userActiveRequest.getUserId());
        Assert.notNull(user, () -> new UserException(USER_NOT_EXIST));
        Assert.isTrue(user.getState() == UserStateEnum.AUTH, () -> new UserException(USER_STATUS_IS_NOT_AUTH));

        user.active(userActiveRequest.getBlockChainUrl(), userActiveRequest.getBlockChainPlatform());
        //更新用户
        boolean result = updateById(user);
        if (result) {
            //加入流水
            long streamResult = userOperateStreamService.insertStream(user, UserOperateTypeEnum.ACTIVE);
            Assert.notNull(streamResult, () -> new BizException(RepoErrorCode.UPDATE_FAILED));
            userOperatorResponse.setSuccess(true);
        } else {
            userOperatorResponse.setSuccess(false);
            userOperatorResponse.setResponseCode(UserErrorCode.USER_OPERATE_FAILED.getCode());
            userOperatorResponse.setResponseMessage(UserErrorCode.USER_OPERATE_FAILED.getMessage());
        }
        return userOperatorResponse;
    }


    //冻结，
    @Transactional(rollbackFor = Exception.class)
    public UserOperatorResponse freeze(Long userId) {
        UserOperatorResponse userOperatorResponse = new UserOperatorResponse();
        User user = userMapper.findById(userId);
        Assert.notNull(user, () -> new UserException(USER_NOT_EXIST));
        Assert.isTrue(user.getState() == UserStateEnum.ACTIVE, () -> new UserException(USER_STATUS_IS_NOT_ACTIVE));

        //第一次删除缓存
        idUserCache.remove(user.getId().toString());

        if (user.getState() == UserStateEnum.FROZEN) {
            userOperatorResponse.setSuccess(true);
            return userOperatorResponse;
        }
        user.setState(UserStateEnum.FROZEN);
        boolean updateResult = updateById(user);
        Assert.isTrue(updateResult, () -> new BizException(RepoErrorCode.UPDATE_FAILED));
        //加入流水
        long result = userOperateStreamService.insertStream(user, UserOperateTypeEnum.FREEZE);
        Assert.notNull(result, () -> new BizException(RepoErrorCode.UPDATE_FAILED));

        //使用延时任务第二次删除缓存
        userCacheDelayDeleteService.delayedCacheDelete(idUserCache, user);

        userOperatorResponse.setSuccess(true);
        return userOperatorResponse;
    }


    //解冻
    @Transactional(rollbackFor = Exception.class)
    public UserOperatorResponse unfreeze(Long userId) {
        UserOperatorResponse userOperatorResponse = new UserOperatorResponse();
        User user = userMapper.findById(userId);
        Assert.notNull(user, () -> new UserException(USER_NOT_EXIST));

        //第一次删除缓存
        idUserCache.remove(user.getId().toString());

        if (user.getState() == UserStateEnum.ACTIVE) {
            userOperatorResponse.setSuccess(true);
            return userOperatorResponse;
        }
        user.setState(UserStateEnum.ACTIVE);
        //更新数据库
        boolean updateResult = updateById(user);
        Assert.isTrue(updateResult, () -> new BizException(RepoErrorCode.UPDATE_FAILED));
        //加入流水
        long result = userOperateStreamService.insertStream(user, UserOperateTypeEnum.UNFREEZE);
        Assert.notNull(result, () -> new BizException(RepoErrorCode.UPDATE_FAILED));

        //使用延时任务第二次删除缓存
        userCacheDelayDeleteService.delayedCacheDelete(idUserCache, user);

        userOperatorResponse.setSuccess(true);
        return userOperatorResponse;
    }


    //分页查询用户信息
    public PageResponse<User> pageQueryByState(String keyWord, String state, int currentPage, int pageSize) {
        Page<User> page = new Page<>(currentPage, pageSize);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("state", state);

        if (keyWord != null) {
            wrapper.like("telephone", keyWord);
        }
        wrapper.orderBy(true, true, "gmt_create");

        Page<User> userPage = this.page(page, wrapper);

        return PageResponse.of(userPage.getRecords(), (int) userPage.getTotal(), pageSize, currentPage);
    }

    //通过手机号和密码查询用户信息
    public User findByTelephoneAndPass(String telephone, String password) {
        return userMapper.findByTelephoneAndPass(telephone, DigestUtil.md5Hex(password));
    }

    //通过手机号查询用户信息
    public User findByTelephone(String telephone) {
        return userMapper.findByTelephone(telephone);
    }

    //通过用户ID查询用户信息
    @Cached(name = ":user:cache:id:", cacheType = CacheType.BOTH, key = "#userId", cacheNullValue = true)
    @CacheRefresh(refresh = 60, timeUnit = TimeUnit.MINUTES)
    public User findById(Long userId) {
        return userMapper.findById(userId);
    }

    //更新用户信息，缓存中删除
    @CacheInvalidate(name = ":user:cache:id:", key = "#userModifyRequest.userId")
    @Transactional(rollbackFor = Exception.class)
    public UserOperatorResponse modify(UserModifyRequest userModifyRequest) {
        UserOperatorResponse userOperatorResponse = new UserOperatorResponse();
        //数据库中进行查询
        User user = userMapper.findById(userModifyRequest.getUserId());
        Assert.notNull(user, () -> new UserException(USER_NOT_EXIST));
        Assert.isTrue(user.canModifyInfo(), () -> new UserException(USER_STATUS_CANT_OPERATE));

        //从布隆过滤器中查询用户名称
        if (StringUtils.isNotBlank(userModifyRequest.getNickName()) && nickNameExist(userModifyRequest.getNickName())) {
            throw new UserException(NICK_NAME_EXIST);
        }
        BeanUtils.copyProperties(userModifyRequest, user);

        //密码更新
        if (StringUtils.isNotBlank(userModifyRequest.getPassword())) {
            user.setPasswordHash(DigestUtil.md5Hex(userModifyRequest.getPassword()));
        }

        //更新数据库
        if (updateById(user)) {
            //加入流水
            long streamResult = userOperateStreamService.insertStream(user, UserOperateTypeEnum.MODIFY);
            Assert.notNull(streamResult, () -> new BizException(RepoErrorCode.UPDATE_FAILED));
            //添加到BloomFilter列表中（缓存中）
            addNickName(userModifyRequest.getNickName());
            userOperatorResponse.setSuccess(true);

            return userOperatorResponse;
        }
        userOperatorResponse.setSuccess(false);
        userOperatorResponse.setResponseCode(UserErrorCode.USER_OPERATE_FAILED.getCode());
        userOperatorResponse.setResponseMessage(UserErrorCode.USER_OPERATE_FAILED.getMessage());

        return userOperatorResponse;
    }

    //获取我的排名
    public Integer getInviteRank(String userId) {
        Integer rank = inviteRank.revRank(userId);
        if (rank != null) {
            return rank + 1;
        }
        return null;
    }

    //获取邀请列表
    public PageResponse<User> getUsersByInviterId(String inviterId, int currentPage, int pageSize) {
        Page<User> page = new Page<>(currentPage, pageSize);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("nick_name", "gmt_create");
        wrapper.eq("inviter_id", inviterId);

        wrapper.orderBy(true, false, "gmt_create");

        Page<User> userPage = this.page(page, wrapper);
        return PageResponse.of(userPage.getRecords(), (int) userPage.getTotal(), pageSize, currentPage);
    }

    //获取邀请排行
    public List<InviteRankInfo> getTopN(Integer topN) {
        Collection<ScoredEntry<String>> rankInfos = inviteRank.entryRangeReversed(0, topN - 1);

        List<InviteRankInfo> inviteRankInfos = new ArrayList<>();

        if (rankInfos != null) {
            for (ScoredEntry<String> rankInfo : rankInfos) {
                InviteRankInfo inviteRankInfo = new InviteRankInfo();
                String userId = rankInfo.getValue();
                if (StringUtils.isNotBlank(userId)) {
                    User user = findById(Long.valueOf(userId));
                    if (user != null) {
                        inviteRankInfo.setNickName(user.getNickName());
                        inviteRankInfo.setInviteCode(user.getInviteCode());
                        inviteRankInfo.setInviteScore(rankInfo.getScore().intValue());
                        inviteRankInfos.add(inviteRankInfo);
                    }
                }
            }
        }

        return inviteRankInfos;
    }

    //如果布隆过滤器中存在，再进行数据库二次判断
    public boolean nickNameExist(String nickName) {
        if (this.nickNameBloomFilter != null && this.nickNameBloomFilter.contains(nickName)) {
            return userMapper.findByNickname(nickName) != null;
        }

        return false;
    }

    //如果布隆过滤器中存在，再进行数据库二次判断
    public boolean inviteCodeExist(String inviteCode) {
        if (this.inviteCodeBloomFilter != null && this.inviteCodeBloomFilter.contains(inviteCode)) {
            return userMapper.findByInviteCode(inviteCode) != null;
        }

        return false;
    }

    private boolean addNickName(String nickName) {
        return this.nickNameBloomFilter != null && this.nickNameBloomFilter.add(nickName);
    }

    private boolean addInviteCode(String inviteCode) {
        return this.inviteCodeBloomFilter != null && this.inviteCodeBloomFilter.add(inviteCode);
    }


    //更新排名，排名规则：
    //1、优先按照分数排，分数越大的，排名越靠前
    //2、分数相同，则按照上榜时间排，上榜越早的排名越靠前
    private void updateInviteRank(String inviterId) {
        if (inviterId == null) {
            return;
        }

        //1、这里因为是一个私有方法，无法通过注解方式实现分布式锁。
        //2、register方法已经加了锁，这里需要二次加锁的原因是register锁的是注册人，这里锁的是邀请人
        RLock rLock = redissonClient.getLock(inviterId);
        rLock.lock();
        try {
            //获取当前用户的积分
            Double score = inviteRank.getScore(inviterId);
            if (score == null) {
                score = 0.0;
            }

            //获取最近一次上榜时间
            long currentTimeStamp = System.currentTimeMillis();
            //把上榜时间转成小数(时间戳13位，所以除以10000000000000能转成小数)，并且倒序排列（用1减），即上榜时间越早，分数越大（时间越晚，时间戳越大，用1减一下，就反过来了）
            double timePartScore = 1 - (double) currentTimeStamp / 10000000000000L;

            //1、当前积分保留整数，即移除上一次的小数位
            //2、当前积分加100，表示新邀请了一个用户
            //3、加上“最近一次上榜时间的倒序小数位“作为score
            inviteRank.add(score.intValue() + 100.0 + timePartScore, inviterId);
        } finally {
            rLock.unlock();
        }
    }

    private void updateUserCache(String userId, User user) {
        idUserCache.put(userId, user);
    }

    //初始化布隆过滤器
    @Override
    public void afterPropertiesSet() throws Exception {
        this.nickNameBloomFilter = redissonClient.getBloomFilter("nickName");
        if (nickNameBloomFilter != null && !nickNameBloomFilter.isExists()) {
            this.nickNameBloomFilter.tryInit(100000L, 0.01);
        }

        this.inviteCodeBloomFilter = redissonClient.getBloomFilter("inviteCode");
        if (inviteCodeBloomFilter != null && !inviteCodeBloomFilter.isExists()) {
            this.inviteCodeBloomFilter.tryInit(100000L, 0.01);
        }

        this.inviteRank = redissonClient.getScoredSortedSet("inviteRank");
    }
}
