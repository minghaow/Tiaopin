package nanshen.dao.Sku.impl;

import nanshen.dao.Sku.SkuDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.PublicationStatus;
import nanshen.data.Sku.Sku;
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
public class SkuDaoImpl extends BaseDao implements SkuDao {

    @Override
    public Sku insert(Sku sku) {
        return dao.insert(sku);
    }

    @Override
    public Sku get(long skuId) {
        return dao.fetch(Sku.class, skuId);
    }

    @Override
    public Sku getByShowId(long skuShowId) {
        Condition cnd = Cnd.where("showId", "=", skuShowId);
        return dao.fetch(Sku.class, cnd);
    }

    @Override
    public boolean update(Sku sku) {
        return dao.update(sku) == 1;
    }

    @Override
    public boolean remove(long skuId) {
        return dao.delete(Sku.class, skuId) == 1;
    }

    @Override
    public List<Sku> get(List<Long> skuIdList) {
        Condition cnd = Cnd.where("id", "in", skuIdList).asc("id");
        return dao.query(Sku.class, cnd);
    }

    @Override
    public List<Sku> getLatestSkuList(long count) {
        Condition cnd = Cnd.where("status", "=", PublicationStatus.ONLINE).limit((int) count).desc("id");
        return dao.query(Sku.class, cnd);
    }

}
