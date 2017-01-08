package nanshen.dao.impl;

import nanshen.dao.UserQuestionSubDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.User.UserQuestionSub;
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
public class UserQuestionSubDaoImpl extends BaseDao implements UserQuestionSubDao {

    @Override
    public UserQuestionSub insert(UserQuestionSub sub) {
        return dao.insert(sub);
    }

    @Override
    public UserQuestionSub get(long id) {
        return dao.fetch(UserQuestionSub.class, id);
    }

    @Override
    public List<UserQuestionSub> getByUserId(long uid) {
        Condition cnd = Cnd.where("userId", "=", uid).desc("id");
        return dao.query(UserQuestionSub.class, cnd);
    }

}
