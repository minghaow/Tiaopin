package nanshen.service;

import nanshen.data.Dtree.DtreeQuestion;
import nanshen.data.Dtree.DtreeTrack;
import nanshen.data.Sku.Sku;

import java.util.List;

/**
 * Question related services
 *
 * @author WANG Minghao
 */
public interface DtreeService {

    DtreeQuestion getNextDtreeQuestion(long topicId, long qid);

    List<Sku> getResult(List<DtreeTrack> dtreeTrackList);

}
