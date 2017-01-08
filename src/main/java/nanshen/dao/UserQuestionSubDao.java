package nanshen.dao;

import nanshen.data.User.UserQuestionSub;

import java.util.List;

/**
 * @author WANG Minghao
 */
public interface UserQuestionSubDao {

    UserQuestionSub insert(UserQuestionSub sub);

    UserQuestionSub get(long id);

    List<UserQuestionSub> getByUserId(long uid);

}
