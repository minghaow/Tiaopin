package nanshen.dao.Banner.impl;

import nanshen.dao.Banner.BannerDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.Banner.Banner;
import nanshen.data.PublicationStatus;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * BannerDaoImpl
 *
 * @Author WANG Minghao
 */
@Repository
public class BannerDaoImpl extends BaseDao implements BannerDao {

    @Override
    public List<Banner> getOnline() {
        Condition condition = Cnd.where("status", "=", PublicationStatus.ONLINE).desc("id");
        return dao.query(Banner.class, condition);
    }
}
