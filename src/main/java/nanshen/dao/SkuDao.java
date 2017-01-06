package nanshen.dao;

import nanshen.data.Sku.Sku;

import java.util.List;

/**
 * @author WANG Minghao
 */
public interface SkuDao {

    Sku insert(Sku sku);

    Sku get(long skuId);

    Sku getByShowId(long skuShowId);

    boolean update(Sku sku);

    boolean remove(long skuId);

    List<Sku> get(List<Long> skuIdList);

    List<Sku> getLatestSkuList(long count);
}
