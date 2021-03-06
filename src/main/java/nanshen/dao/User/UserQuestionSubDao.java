package nanshen.dao.User;

import nanshen.data.SystemUtil.PageInfo;
import nanshen.data.User.UserQuestionSub;

import java.util.List;

/**
 * @author WANG Minghao
 */
public interface UserQuestionSubDao {

    UserQuestionSub insert(UserQuestionSub sub);

    UserQuestionSub get(long id);

    List<UserQuestionSub> getByUserId(long uid, PageInfo pageInfo);

    UserQuestionSub get(long uid, long qid);

    long count(long qid);

    boolean remove(long qid, long uid);
}
