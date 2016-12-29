package nanshen.service.impl;

import nanshen.dao.Dtree.DtreeOptionDao;
import nanshen.dao.Dtree.DtreeQuestionDao;
import nanshen.dao.Question.AnswerDao;
import nanshen.dao.Question.QuestionDao;
import nanshen.dao.UserInfoDao;
import nanshen.data.Dtree.DtreeOption;
import nanshen.data.Dtree.DtreeQuestion;
import nanshen.data.Dtree.DtreeQuestionType;
import nanshen.data.Dtree.DtreeTrack;
import nanshen.data.Sku.Sku;
import nanshen.service.DtreeService;
import nanshen.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Tiaopin
 *
 * @Author WANG Minghao
 */
@Service
public class DtreeServiceImpl implements DtreeService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private AnswerDao answerDao;

    @Autowired
    private DtreeQuestionDao dtreeQuestionDao;

    @Autowired
    private DtreeOptionDao dtreeOptionDao;

    @Autowired
    private SkuService skuService;

    @Override
    public DtreeQuestion getNextDtreeQuestion(long topicId, long qid) {
        DtreeQuestion dtreeQuestion = dtreeQuestionDao.get(topicId, qid);
        if (dtreeQuestion.getType() == DtreeQuestionType.QF) {

        } else {
            List<DtreeOption> dtreeOptionList = dtreeOptionDao.get(topicId, qid);
            dtreeQuestion.setOptionList(dtreeOptionList);
        }
        return dtreeQuestion;
    }

    @Override
    public List<Sku> getResult(List<DtreeTrack> dtreeTrackList) {
        Sku sku1 = skuService.getByShowSid(1);
        Sku sku2 = skuService.getByShowSid(2);
        List<Sku> skuList = new ArrayList<Sku>();
        skuList.add(sku1);
        skuList.add(sku2);
        return skuList;
    }


}
