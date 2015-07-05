package nanshen.dao.impl;

import nanshen.dao.AdminUserInfoDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.AdminUserInfo;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 管理员用户信息
 *
 * @Author WANG Minghao
 */
@Repository
public class AdminUserInfoDaoImpl extends BaseDao implements AdminUserInfoDao {

    @Override
    public AdminUserInfo insert(AdminUserInfo userInfo) {
        return dao.insert(userInfo);
    }

    @Override
    public List<AdminUserInfo> getAll() {
        Condition cnd = Cnd.where("createTime", ">", "2015-06-01");
        return dao.query(AdminUserInfo.class, cnd);
    }

}