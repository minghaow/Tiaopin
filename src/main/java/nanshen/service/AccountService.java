package nanshen.service;

import nanshen.data.AdminUserInfo;
import nanshen.data.LookInfo;
import nanshen.data.Sku.SkuItem;
import nanshen.data.SystemUtil.ExecInfo;
import nanshen.data.SystemUtil.ExecResult;
import nanshen.data.User.UserAddress;
import nanshen.data.User.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * 账户的相关操作
 *
 * @author WANG Minghao
 */
public interface AccountService {

    /**
     * 根据管理员Id获得管理员信息
     *
     * @param adminUserId
     * @return
     */
    AdminUserInfo getAdminUserInfoByUserId(Long adminUserId);

    /**
     * 根据搭配信息表得到管理员信息
     *
     * @param lookInfoList
     * @return
     */
    Map<Long, AdminUserInfo> getAdminUserInfoByLookInfoList(List<LookInfo> lookInfoList);

    /**
     * 根据单品信息表得到管理员信息
     *
     * @param skuItemList
     * @return
     */
    Map<Long, AdminUserInfo> getAdminUserInfoBySkuInfoList(List<SkuItem> skuItemList);

    /**
     * 根据管理员名称获得管理员信息
     *
     * @param username
     * @return
     */
    AdminUserInfo getAdminUserInfoByUsername(String username);

    /**
     * According to userId to get userInfo in cache
     *
     * @param userId userId
     * @return UserInfo
     */
    UserInfo getUserInfo(long userId);

    /**
     * Create new user
     *
     * @param phone user's phone number
     * @param passwordOrigin origin password
     * @return ExecResult<UserInfo>
     */
    ExecResult<UserInfo> createNewUser(String phone, String passwordOrigin);

    /**
     * set new user's username
     *
     * @param userId user's id number
     * @param username origin password
     * @return ExecInfo
     */
    ExecInfo setUsername(long userId, String username);

    /**
     * get user address list by userId
     *
     * @param userId user's id number
     * @return List<UserAddress>
     */
    List<UserAddress> getUserAddressListByUserId(long userId);

    /**
     * get user address by addressId
     *
     * @param addressId addressId
     * @return UserAddress
     */
    UserAddress getUserAddress(long addressId);

    /**
     * check if the phone number is not registered
     *
     * @param phone
     * @return
     */
    ExecResult<UserInfo> checkIsNotRegistered(String phone);

    /**
     * check if the phone number is registered
     *
     * @param phone
     * @return
     */
    ExecResult<UserInfo> checkIsRegistered(String phone);

    /**
     * clear admin user info cache
     *
     * @return boolean
     */
    boolean clearAdminUserInfoCache();

    ExecResult<UserInfo> checkIsNotRegisteredByWx(String openid);

    ExecResult<UserInfo> createNewUser(String openid, String phone, String password, String imgUrl, String country, String province, String city, String gender, String nickName);

}
