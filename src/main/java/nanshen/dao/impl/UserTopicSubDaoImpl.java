package nanshen.dao.impl;

import nanshen.dao.UserTopicSubDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.User.UserTopicSub;
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
public class UserTopicSubDaoImpl extends BaseDao implements UserTopicSubDao {

    @Override
    public UserTopicSub insert(UserTopicSub sub) {
        return dao.insert(sub);
    }

    @Override
    public UserTopicSub get(long id) {
        return dao.fetch(UserTopicSub.class, id);
    }

    @Override
    public List<UserTopicSub> getByUserId(long uid) {
        Condition cnd = Cnd.where("userId", "=", uid).desc("id");
        return dao.query(UserTopicSub.class, cnd);
    }

    @Override
    public boolean remove(long tid, long uid) {
        Condition cnd = Cnd.where("userId", "=", uid).and("tid", "=", tid);
        return dao.clear(UserTopicSub.class, cnd) > 0;
    }

}
