package nanshen.data.Question;

import nanshen.data.User.UserInfo;

/**
 * Question
 *
 * @Author WANG Minghao
 */
public class ComplexAnswer {

    private long qid = 0;

    private long aid = 0;

    private long qShowId = 0;

    private long aShowId = 0;

    private Question question = null;

    private UserInfo userInfo = null;

    private Answer answer = null;

    private String imgUrl = "";

    public ComplexAnswer(long aid, Answer answer, long aShowId, long qid, long qShowId, Question question,
                         UserInfo userInfo, String imgUrl) {
        this.aid = aid;
        this.answer = answer;
        this.aShowId = aShowId;
        this.qid = qid;
        this.qShowId = qShowId;
        this.question = question;
        this.userInfo = userInfo;
        this.imgUrl = imgUrl;
    }

    public long getAid() {
        return aid;
    }

    public void setAid(long aid) {
        this.aid = aid;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public long getaShowId() {
        return aShowId;
    }

    public void setaShowId(long aShowId) {
        this.aShowId = aShowId;
    }

    public long getQid() {
        return qid;
    }

    public void setQid(long qid) {
        this.qid = qid;
    }

    public long getqShowId() {
        return qShowId;
    }

    public void setqShowId(long qShowId) {
        this.qShowId = qShowId;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
