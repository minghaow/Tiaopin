package nanshen.dao.impl;

import nanshen.dao.UserSkuLikeMapDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.SystemUtil.PageInfo;
import nanshen.data.User.UserSkuLikeMap;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserSkuLikeMapImpl
 *
 * @Author WANG Minghao
 */
@Repository
public class UserSkuLikeMapDaoImpl extends BaseDao implements UserSkuLikeMapDao {

    @Override
    public UserSkuLikeMap insert(UserSkuLikeMap sub) {
        return dao.insert(sub);
    }

    @Override
    public UserSkuLikeMap get(long uid, long sid) {
        Condition cnd = Cnd.where("uid", "=", uid).and("sid", "=", sid);
        return dao.fetch(UserSkuLikeMap.class, cnd);
    }

    @Override
    public List<UserSkuLikeMap> getByUid(long uid, PageInfo pageInfo) {
        Condition cnd = Cnd.where("uid", "=", uid).desc("id");
        return dao.query(UserSkuLikeMap.class, cnd, genaratePager(pageInfo));
    }

    @Override
    public long countSkuLike(long sid) {
        Condition cnd = Cnd.where("sid", "=", sid);
        return dao.count(UserSkuLikeMap.class, cnd);
    }

}
