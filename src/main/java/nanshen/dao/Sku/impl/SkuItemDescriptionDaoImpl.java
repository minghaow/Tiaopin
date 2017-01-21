package nanshen.dao.Sku.impl;

import nanshen.dao.Sku.SkuItemDescriptionDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.Sku.SkuItemDescription;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.springframework.stereotype.Repository;

/**
 * NanShen
 *
 * @Author WANG Minghao
 */
@Repository
public class SkuItemDescriptionDaoImpl extends BaseDao implements SkuItemDescriptionDao {

    @Override
    public SkuItemDescription get(long itemId) {
        Condition cnd = Cnd.where("itemId", "=", itemId);
        return dao.fetch(SkuItemDescription.class, cnd);
    }

}
