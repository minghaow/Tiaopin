package nanshen.data.Question;

import nanshen.utils.ViewUtils;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
 * Answer
 *
 * @Author WANG Minghao
 */
@Table("Answer")
public class Answer {

    /** ID */
    @Id
    private long id;

    /** showId */
    @Column
    private long showId = 0;

    /** question short id */
    @Column
    private long questionId = 0;

    /** upload user */
    @Column
    private long userId = 0;
    private String userName = "";

    /** content */
    @Column
    private String content;

    /** total view about the question */
    @Column
    private long viewCnt = 0;

    /** comment count */
    @Column
    private long commentCnt = 0;

    /** up count */
    @Column
    private long upCnt = 0;

    /** down count */
    @Column
    private long downCnt = 0;

    /** answer status */
    @Column
    private AnswerStatus status = AnswerStatus.DRAFT;

    /** create time for this answer, will fill when create */
    @Column
    private Date createTime = new Date();

    /** update time for this answer, all operator will update this value */
    @Column
    private Date updateTime = new Date();

    public Answer() {
    }

    public Answer(long questionId, String content, AnswerStatus status, long userId) {
        this.questionId = questionId;
        this.content = content;
        this.showId = generateShowId(System.currentTimeMillis(), userId);
        this.status = status;
        this.userId = userId;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public long getCommentCnt() {
        return commentCnt;
    }

    public void setCommentCnt(long commentCnt) {
        this.commentCnt = commentCnt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public long getDownCnt() {
        return downCnt;
    }

    public void setDownCnt(long downCnt) {
        this.downCnt = downCnt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getShowId() {
        return showId;
    }

    public void setShowId(long showId) {
        this.showId = showId;
    }

    public AnswerStatus getStatus() {
        return status;
    }

    public void setStatus(AnswerStatus status) {
        this.status = status;
    }

    public long getUpCnt() {
        return upCnt;
    }

    public void setUpCnt(long upCnt) {
        this.upCnt = upCnt;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public String getShowUpdateTime() {
        return ViewUtils.convertDateToStringWithoutTime(updateTime);
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getViewCnt() {
        return viewCnt;
    }

    public void setViewCnt(long viewCnt) {
        this.viewCnt = viewCnt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long generateShowId(long timeMillis, long userId) {
        if(userId<=0 || timeMillis<=0) {
            return 0;
        }
        long showId = timeMillis + userId * 1000001;
        char[] c = String.valueOf(showId).toCharArray();
        for(int i = 0; i < c.length; i++) {
            int number = c[i] - '0';
            number = (number + 3) % 10;
            c[i] = (char) ('0' + number);
        }
        String showIdStr = new String(c);
        return Long.parseLong(showIdStr);
    }
}
