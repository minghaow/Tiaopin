package nanshen.dao;

import nanshen.data.UserInfo;

import java.util.Date;
import java.util.List;

/**
 * 买手信息数据库操作
 *
 * @author WANG Minghao
 */
public interface UserInfoDao {

    /**
     * 获取指定用户名的买手信息
     *
     * @param username
     * @return
     */
    public UserInfo getBuyerInfoByUsername(String username);

    /**
     * 获取指定ID的买手信息
     *
     * @param id
     * @return
     */
    public UserInfo getBuyerInfo(long id);

    /**
     * 添加一个新买手
     *
     * @param info
     * @return
     */
    public UserInfo addNewBuyer(UserInfo info);

    /**
     * 更新买手的信息
     *
     * @param info
     * @return
     */
    public boolean updateBuyer(UserInfo info);

    /**
     * 获取指定用户名的买手信息
     *
     * @param email)
     * @return
     */
    UserInfo getBuyerInfoByEmail(String email);

    /**
     * 根据买手类型获取所有该类型买手的数据
     *
     * @param buyerTypeList
     * @return
     */
    List<UserInfo> getAllBuyerInfos(List<UserInfo> buyerTypeList);

    /**
     * 记录登陆时的一些信息
     *
     * @param username
     * @param ip
     * @param loginTime
     * @return
     */
    boolean login(String username, String ip, Date loginTime);

    /**
     * 获取买手ID在指定ID列表中的买手信息
     *
     * @param buyerIds
     * @return
     */
    List<UserInfo> getBuyerInfo(List<Long> buyerIds);
}
