package nanshen.dao.Dtree;

import nanshen.data.Dtree.DtreeOption;

import java.util.List;

/**
 * @author WANG Minghao
 */
public interface DtreeOptionDao {

    List<DtreeOption> get(long topicId, long qid);

    DtreeOption get(long optionId);

}
