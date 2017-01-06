package nanshen.dao.impl;

import nanshen.dao.UserInfoDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.User.UserInfo;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 买手信息数据库操作实现
 *
 * @author KONG Xiangxin
 */
@Repository
public class UserInfoDaoImpl extends BaseDao implements UserInfoDao {

    @Override
    public UserInfo getBuyerInfoByUsername(String username) {
        return dao.fetch(UserInfo.class, Cnd.where("username", "=", username));
    }

    @Override
    public UserInfo getUserInfo(long id) {
        return dao.fetch(UserInfo.class, id);
    }

    @Override
    public UserInfo addNewUser(UserInfo info) {
        try {
            return dao.insert(info);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean updateBuyer(UserInfo info) {
        return 1 == dao.update(info);
    }

    @Override
    public UserInfo getBuyerInfoByEmail(String email) {
        return dao.fetch(UserInfo.class, Cnd.where("email", "=", email));
    }

    @Override
    public UserInfo getUserInfoByPhone(String phone) {
        return dao.fetch(UserInfo.class, Cnd.where("phone", "=", phone));
    }

    @Override
    public boolean login(String username, String ip, Date loginTime) {
        Sql sql = Sqls.create("UPDATE UserInfo " +
                "SET loginCount = loginCount + 1, loginIp = @loginIp, loginTime = @loginTime " +
                "WHERE username = @username");
        sql.params().set("loginIp", ip);
        sql.params().set("loginTime", loginTime);
        sql.params().set("username", username);
        dao.execute(sql);
        return 1 == sql.getUpdateCount();
    }

    @Override
    public List<UserInfo> getAllBuyerInfos(List<UserInfo> buyerTypeList) {
        Condition condition = Cnd.where("buyerType", "in", buyerTypeList);
        return dao.query(UserInfo.class, condition);
    }

    @Override
    public List<UserInfo> getUserInfo(List<Long> userIds) {
        Condition condition = Cnd
                .where("id", "in", userIds);
        return dao.query(UserInfo.class, condition);
    }

    @Override
    public boolean setUsername(long userId, String username) {
        Chain chain = Chain
                .make("username", username);
        Condition condition = Cnd.where("id", "=", userId);
        return 1 == dao.update(UserInfo.class, chain, condition);
    }

    @Override
    public UserInfo getUserInfoByOpenid(String openid) {
        return dao.fetch(UserInfo.class, Cnd.where("openid", "=", openid));
    }

}
