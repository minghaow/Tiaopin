package nanshen.service.impl;

import nanshen.dao.Dtree.DtreeOptionDao;
import nanshen.dao.Dtree.DtreeQuestionDao;
import nanshen.dao.Question.AnswerDao;
import nanshen.dao.Question.QuestionDao;
import nanshen.dao.UserInfoDao;
import nanshen.data.Dtree.DtreeOption;
import nanshen.data.Dtree.DtreeQuestion;
import nanshen.data.Dtree.DtreeQuestionType;
import nanshen.service.AccountService;
import nanshen.service.DtreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private AccountService accountService;

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


}
