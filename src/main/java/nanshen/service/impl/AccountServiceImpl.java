package nanshen.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import nanshen.constant.TimeConstants;
import nanshen.dao.AdminUserInfoDao;
import nanshen.dao.User.UserAddressDao;
import nanshen.dao.User.UserInfoDao;
import nanshen.dao.User.UserMessageDao;
import nanshen.data.AdminUserInfo;
import nanshen.data.LookInfo;
import nanshen.data.Sku.SkuItem;
import nanshen.data.SystemUtil.ExecInfo;
import nanshen.data.SystemUtil.ExecResult;
import nanshen.data.User.UserAddress;
import nanshen.data.User.UserInfo;
import nanshen.data.User.UserMessage;
import nanshen.data.User.UserMessageType;
import nanshen.service.AccountService;
import nanshen.service.PeopleService;
import nanshen.service.common.ScheduledService;
import nanshen.utils.EncryptUtils;
import nanshen.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Account Service Implementation
 * NOTE: Mainly provide id and account information map according to all kinds of list
 *
 * @Author WANG Minghao
 */
@Service
public class AccountServiceImpl extends ScheduledService implements AccountService {

    @Autowired
    private AdminUserInfoDao adminUserInfoDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserAddressDao userAddressDao;

    @Autowired
    private UserMessageDao userMessageDao;

    @Autowired
    private PeopleService peopleService;

    /** 买手ID到买手信息的缓存 */
    private final LoadingCache<Long, UserInfo> userCache = CacheBuilder.newBuilder()
            .softValues()
            .expireAfterWrite(TimeConstants.HALF_HOUR_IN_SECONDS, TimeUnit.SECONDS)
            .build(
                    new CacheLoader<Long, UserInfo>() {
                        @Override
                        public UserInfo load(Long id) throws Exception {
                            return userInfoDao.getUserInfo(id);
                        }
                    });

    Map<Long, AdminUserInfo> adminIdUserInfoMap = new HashMap<Long, AdminUserInfo>();
    Map<String, AdminUserInfo> adminNameUserInfoMap = new HashMap<String, AdminUserInfo>();

    public void update() {
        adminIdUserInfoMap = new HashMap<Long, AdminUserInfo>();
        adminNameUserInfoMap = new HashMap<String, AdminUserInfo>();
        List<AdminUserInfo> adminUserInfoList = adminUserInfoDao.getAll();
        for (AdminUserInfo adminUserInfo : adminUserInfoList) {
            adminIdUserInfoMap.put(adminUserInfo.getId(), adminUserInfo);
            adminNameUserInfoMap.put(adminUserInfo.getUsername(), adminUserInfo);
        }
    }

    public int updatePeriod() {
        return TimeConstants.HOUR_IN_SECONDS;
    }

    @Override
    public Map<Long, AdminUserInfo> getAdminUserInfoByLookInfoList(List<LookInfo> lookInfoList) {
        Map<Long, AdminUserInfo> idAndAdminUserInfoMap = new HashMap<Long, AdminUserInfo>();
        for (LookInfo lookInfo : lookInfoList) {
            idAndAdminUserInfoMap.put(lookInfo.getUploadUserId(), getAdminUserInfoByUserId(lookInfo.getUploadUserId()));
        }
        return idAndAdminUserInfoMap;
    }

    @Override
    public Map<Long, AdminUserInfo> getAdminUserInfoBySkuInfoList(List<SkuItem> skuItemList) {
        Map<Long, AdminUserInfo> idAndAdminUserInfoMap = new HashMap<Long, AdminUserInfo>();
        for (SkuItem skuItem : skuItemList) {
            idAndAdminUserInfoMap.put(skuItem.getUploadUserId(), getAdminUserInfoByUserId(skuItem.getUploadUserId()));
        }
        return idAndAdminUserInfoMap;
    }

    @Override
    public AdminUserInfo getAdminUserInfoByUsername(String username) {
        return adminNameUserInfoMap.get(username);
    }

    @Override
    public UserInfo getUserInfo(long userId) {
        try {
            return userCache.get(userId);
        } catch (ExecutionException e) {
            LogUtils.warning("AccountService: get user info error!", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public ExecResult<UserInfo> createNewUser(String phone, String passwordOrigin) {
        UserInfo userInfo = new UserInfo(phone, EncryptUtils.encodePassword(passwordOrigin));
        userInfo = userInfoDao.addNewUser(userInfo);
        if (userInfo == null) {
            return ExecResult.fail("手机号已注册，请找回密码或换新的手机号~");
        }
        return ExecResult.succ(userInfo);
    }

    @Override
    public ExecInfo setUsername(long userId, String username) {
        if (!userInfoDao.setUsername(userId, username)) {
            return ExecInfo.fail("用户名设置失败，请联系客服！");
        }
        userCache.invalidate(userId);
        return ExecInfo.succ();
    }

    @Override
    public List<UserAddress> getUserAddressListByUserId(long userId) {
        return userAddressDao.getUserAddressListByUserId(userId);
    }

    @Override
    public UserAddress getUserAddress(long addressId) {
        return userAddressDao.getUserAddress(addressId);
    }

    @Override
    public ExecResult<UserInfo> checkIsNotRegistered(String phone) {
        UserInfo userInfo = userInfoDao.getUserInfoByPhone(phone);
        if (userInfo != null) {
            return ExecResult.fail("该手机号已经注册过，请直接登录");
        }
        return ExecResult.succ(null);
    }

    @Override
    public ExecResult<UserInfo> checkIsRegistered(String phone) {
        UserInfo userInfo = userInfoDao.getUserInfoByPhone(phone);
        if (userInfo == null) {
            return ExecResult.fail("该手机号未注册过");
        }
        return ExecResult.succ(userInfo);
    }

    @Override
    public boolean clearAdminUserInfoCache() {
        userCache.invalidateAll();
        return true;
    }

    @Override
    public boolean clearUserInfoCache(long uid) {
        userCache.invalidate(uid);
        return true;
    }

    @Override
    public ExecResult<UserInfo> checkIsNotRegisteredByWx(String openid) {
        UserInfo userInfo = userInfoDao.getUserInfoByOpenid(openid);
        if (userInfo == null) {
            return ExecResult.fail("该微信号未注册过");
        }
        return ExecResult.succ(userInfo);
    }

    @Override
    public ExecResult<UserInfo> createNewUser(String openid, String phone, String password, String imgUrl, String country, String province, String city, String gender, String nickName) {
        UserInfo userInfo = new UserInfo(openid, phone, EncryptUtils.encodePassword(password), imgUrl, country, province, city, gender, nickName);
        userInfo = userInfoDao.addNewUser(userInfo);
        if (userInfo == null) {
            return ExecResult.fail("用户已注册，请找回密码~", userInfoDao.getUserInfoByPhone(phone));
        }
        userMessageDao.insert(new UserMessage(0, 0, UserMessageType.TEXT, userInfo.getId(), "嗨，" + nickName + "！欢迎加" +
                "入挑品购物问答大家庭！挑品作为中立的购物问答社区，在这里你可以分享你的购物经验和行业见解，晒你买到的好物，吐槽你踩的深坑。" +
                "快去分享吧！", 0));
        return ExecResult.succ(userInfo);
    }

    @Override
    public ExecInfo setUserDesc(UserInfo userInfo, String desc) {
        userInfo.setUserDesc(desc);
        if (userInfoDao.updateBuyer(userInfo)) {
            userCache.invalidate(userInfo.getId());
            peopleService.invalidateUserCache(userInfo.getId());
            return ExecInfo.succ();
        }
        return ExecInfo.fail("更新失败，请联系客服");
    }

    @Override
    public AdminUserInfo getAdminUserInfoByUserId(Long adminUserId) {
        return adminIdUserInfoMap.get(adminUserId);
    }

}
