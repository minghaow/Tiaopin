package nanshen.dao.User.impl;

import nanshen.dao.User.UserInfoDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.User.UserInfo;
import nanshen.utils.LogUtils;
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
 * @author WANG Minghao
 */
@Repository
public class UserInfoDaoImpl extends BaseDao implements UserInfoDao {

    @Override
    public UserInfo getBuyerInfoByUsername(String username) {
        UserInfo info = dao.fetch(UserInfo.class, Cnd.where("username", "=", username));
        return dao.fetchLinks(info, "authoritiesInDb");
    }

    @Override
    public UserInfo getUserInfo(long id) {
        UserInfo info =  dao.fetch(UserInfo.class, id);
        return dao.fetchLinks(info, "authoritiesInDb");
    }

    @Override
    public UserInfo addNewUser(UserInfo info) {
        try {
            return dao.insert(info);
        } catch (Exception e) {
            LogUtils.info(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean updateBuyer(UserInfo info) {
        return 1 == dao.update(info);
    }

    @Override
    public UserInfo getBuyerInfoByEmail(String email) {
        UserInfo info = dao.fetch(UserInfo.class, Cnd.where("email", "=", email));
        return dao.fetchLinks(info, "authoritiesInDb");
    }

    @Override
    public UserInfo getUserInfoByPhone(String phone) {
        UserInfo info = dao.fetch(UserInfo.class, Cnd.where("phone", "=", phone));
        return dao.fetchLinks(info, "authoritiesInDb");
    }

    @Override
    public long login(String uid, String ip, Date loginTime) {
        Sql sql = Sqls.create("UPDATE UserInfo " +
                "SET loginCount = loginCount + 1, loginIp = @loginIp, loginTime = @loginTime, tempLoginId = @tempLoginId " +
                "WHERE id = @uid");
        long tempLoginId = System.currentTimeMillis();
        sql.params().set("loginIp", ip);
        sql.params().set("loginTime", loginTime);
        sql.params().set("uid", uid);
        sql.params().set("tempLoginId", tempLoginId);
        dao.execute(sql);
        if (1 == sql.getUpdateCount()) {
            return tempLoginId;
        }
        return 0;
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
        UserInfo info = dao.fetch(UserInfo.class, Cnd.where("openid", "=", openid));
        return dao.fetchLinks(info, "authoritiesInDb");
    }

}
