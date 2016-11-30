package nanshen.dao;

import nanshen.data.Sku.SkuSource;

import java.util.List;

/**
 * @author WANG Minghao
 */
public interface SkuSourceDao {

    SkuSource insert(SkuSource skuSource);

    SkuSource get(long skuSourceId);

    List<SkuSource> getByAnswerId(long aid);

    List<SkuSource> getByQuestionId(long qid);

    boolean remove(long id);

    List<SkuSource> getBySkuId(long sid);
}
