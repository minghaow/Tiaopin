package nanshen.service;

import nanshen.data.Question.*;
import nanshen.data.SystemUtil.ExecInfo;
import nanshen.data.SystemUtil.ExecResult;
import nanshen.data.SystemUtil.PageInfo;
import nanshen.data.User.UserInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Question related services
 *
 * @author WANG Minghao
 */
public interface QuestionService {

    List<ComplexQuestion> getHotQuestions(List<QuestionType> typeList, PageInfo pageInfo);

    List<Question> getNewQuestions(List<QuestionType> typeList, PageInfo pageInfo);

    Question getQuestion(long questionId);

    ComplexQuestion getComplexQuestionByShowId(UserInfo userInfo, long qShowId);

    ComplexQuestion getQuestionAndAnswerByShowId(long qShowId, long aShowId);

    ComplexAnswer getComplexAnswerByShowId(UserInfo userInfo, long aShowId);

    List<ComplexAnswer> getHotAnswers(PageInfo pageInfo);

    List<ComplexQuestion> getComplexQuestionByIdList(List<Long> questionIdList);

    ComplexAnswer getComplexAnswerByAidAndQid(long aid, long qid);

    ExecInfo subByQid(long qid, UserInfo userInfo);

    ExecInfo subCancelByQid(long qid, UserInfo userInfo);

    List<ComplexQuestion> getSubList(UserInfo userInfo, PageInfo pageInfo);

    ExecInfo upAnswer(long aid, UserInfo userInfo);

    ExecInfo upCancelAnswer(long aid, UserInfo userInfo);

    ExecResult<Answer> createAnswer(long qid, UserInfo userInfo);

    ExecInfo uploadImage(long aid, String name, MultipartFile file) throws IOException;

    ExecInfo submitAnswer(long aid, String content, UserInfo userInfo);

    List<ComplexAnswer> getAnswersByUid(long uid, PageInfo pageInfo);

    long getAnswerCntByUid(long uid);
}
