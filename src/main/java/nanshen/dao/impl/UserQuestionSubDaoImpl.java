package nanshen.dao.impl;

import nanshen.dao.UserQuestionSubDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.SystemUtil.PageInfo;
import nanshen.data.User.UserQuestionSub;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserQuestionSubDaoImpl
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
    public List<UserQuestionSub> getByUserId(long uid, PageInfo pageInfo) {
        Condition cnd = Cnd.where("userId", "=", uid).desc("id");
        return dao.query(UserQuestionSub.class, cnd, genaratePager(pageInfo));
    }

    @Override
    public UserQuestionSub get(long uid, long qid) {
        Condition cnd = Cnd.where("userId", "=", uid).and("qid", "=", qid);
        return dao.fetch(UserQuestionSub.class, cnd);
    }

    @Override
    public long count(long qid) {
        Condition cnd = Cnd.where("qid", "=", qid);
        return dao.count(UserQuestionSub.class, cnd);
    }

}
