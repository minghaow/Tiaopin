package nanshen.dao.Sku.impl;

import nanshen.dao.Sku.SkuSourceDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.Sku.SkuSource;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * NanShen
 *
 * @Author WANG Minghao
 */
@Repository
public class SkuSourceDaoImpl extends BaseDao implements SkuSourceDao {

    @Override
    public SkuSource insert(SkuSource skuSource) {
        return dao.insert(skuSource);
    }

    @Override
    public SkuSource get(long skuSourceId) {
        return dao.fetch(SkuSource.class, skuSourceId);
    }

    @Override
    public List<SkuSource> getByAnswerId(long aid) {
        Condition cnd = Cnd.where("aid", "=", aid).asc("id");
        return dao.query(SkuSource.class, cnd);
    }

    @Override
    public List<SkuSource> getByQuestionId(long qid) {
        Condition cnd = Cnd.where("qid", "=", qid).asc("id");
        return dao.query(SkuSource.class, cnd);
    }

    @Override
    public boolean remove(long id) {
        return dao.delete(SkuSource.class, id) == 1;
    }

    @Override
    public List<SkuSource> getBySkuId(long sid) {
        Condition cnd = Cnd.where("sid", "=", sid).desc("id");
        return dao.query(SkuSource.class, cnd);
    }

}
