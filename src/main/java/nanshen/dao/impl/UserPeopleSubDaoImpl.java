package nanshen.dao.impl;

import nanshen.dao.UserPeopleSubDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.User.UserPeopleSub;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserTopicSubDaoImpl
 *
 * @Author WANG Minghao
 */
@Repository
public class UserPeopleSubDaoImpl extends BaseDao implements UserPeopleSubDao {

    @Override
    public UserPeopleSub insert(UserPeopleSub sub) {
        return dao.insert(sub);
    }

    @Override
    public UserPeopleSub get(long id) {
        return dao.fetch(UserPeopleSub.class, id);
    }

    @Override
    public List<UserPeopleSub> getByUserId(long uid) {
        Condition cnd = Cnd.where("userId", "=", uid).desc("id");
        return dao.query(UserPeopleSub.class, cnd);
    }

}
