package nanshen.dao;

import nanshen.data.SystemUtil.ExecInfo;
import nanshen.data.SystemUtil.PageInfo;
import nanshen.data.User.UserAnswerUp;

import java.util.List;

/**
 * @author WANG Minghao
 */
public interface UserAnswerUpDao {

    UserAnswerUp insert(UserAnswerUp up);

    UserAnswerUp get(long id);

    List<UserAnswerUp> getByUserId(long uid, PageInfo pageInfo);

    UserAnswerUp get(long uid, long aid);

    long count(long qid);

    ExecInfo remove(long aid, long uid);
}
