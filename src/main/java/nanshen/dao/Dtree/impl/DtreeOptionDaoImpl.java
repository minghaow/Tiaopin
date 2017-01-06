package nanshen.dao.Dtree.impl;

import nanshen.dao.Dtree.DtreeOptionDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.Dtree.DtreeOption;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WANG Minghao
 */
@Repository
public class DtreeOptionDaoImpl extends BaseDao implements DtreeOptionDao {

    @Override
    public List<DtreeOption> get(long topicId, long qid) {
        Condition cnd = Cnd
                .where("qid", "=", qid)
                .and("topicId", "=", topicId);
        return dao.query(DtreeOption.class, cnd);
    }

    @Override
    public DtreeOption get(long optionId) {
        return dao.fetch(DtreeOption.class, optionId);
    }

}
