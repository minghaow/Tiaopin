package nanshen.dao.Order;

import nanshen.data.Discount.Discount;

/**
 * @author WANG Minghao
 */
public interface DiscountDao {

    Discount insert(Discount discount);

    Discount get(String code);

    boolean update(Discount discount);

}
