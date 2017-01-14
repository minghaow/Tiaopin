package nanshen.dao;

import nanshen.data.SystemUtil.PageInfo;
import nanshen.data.User.UserSkuLikeMap;

import java.util.List;

/**
 * @author WANG Minghao
 */
public interface UserSkuLikeMapDao {

    UserSkuLikeMap insert(UserSkuLikeMap map);

    UserSkuLikeMap get(long uid, long sid);

    List<UserSkuLikeMap> getByUid(long uid, PageInfo pageInfo);

    long countSkuLike(long sid);

    boolean remove(long sid, long uid);
}
