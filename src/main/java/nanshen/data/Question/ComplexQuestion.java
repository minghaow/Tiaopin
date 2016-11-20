package nanshen.data.Question;

import nanshen.data.User.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Question
 *
 * @Author WANG Minghao
 */
public class ComplexQuestion {

    private long id;

    private long showId = 0;

    private Question question = null;

    private UserInfo userInfo = null;

    private List<Answer> answerList = new ArrayList<Answer>();

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public ComplexQuestion(long id, long showId, Question question, UserInfo userInfo, List<Answer> answerList) {
        this.answerList = answerList;
        this.id = id;
        this.question = question;
        this.showId = showId;
        this.userInfo = userInfo;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public long getShowId() {
        return showId;
    }

    public void setShowId(long showId) {
        this.showId = showId;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
