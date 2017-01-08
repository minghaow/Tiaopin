package nanshen.dao;

import nanshen.data.User.UserPeopleSub;

import java.util.List;

/**
 * @author WANG Minghao
 */
public interface UserPeopleSubDao {

    UserPeopleSub insert(UserPeopleSub sub);

    UserPeopleSub get(long id);

    List<UserPeopleSub> getByUserId(long uid);

}
