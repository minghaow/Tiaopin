package nanshen.data.Question;

import nanshen.constant.SystemConstants;
import nanshen.utils.ViewUtils;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private String userDesc = "";

    /** description */
    @Column
    private String description;

    /** content */
    @Column
    private String content;

    /** content without html */
    @Column
    private String cleanContent;

    private boolean uped = false;

    private List<AnswerCleanContent> cleanContentList = new ArrayList<AnswerCleanContent>();

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

    private String readableTime = "";

    public Answer() {
    }

    public Answer(long questionId, String description, String content, AnswerStatus status, long userId) {
        this.questionId = questionId;
        this.content = content;
        this.description = description;
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

    public String getUserDesc() {
        return userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCleanContent() {
        return cleanContent;
    }

    public void setCleanContent(String cleanContent) {
        this.cleanContent = cleanContent;
    }

    public List<AnswerCleanContent> getCleanContentList() {
        return cleanContentList;
    }

    public void setCleanContentList(List<AnswerCleanContent> cleanContentList) {
        this.cleanContentList = cleanContentList;
    }

    public String getReadableTime() {
        return ViewUtils.convertDateToFromNowString(createTime);
    }

    public void setReadableTime(String readableTime) {
        this.readableTime = readableTime;
    }

    public boolean isUped() {
        return uped;
    }

    public void setUped(boolean uped) {
        this.uped = uped;
    }

    public long generateShowId(long timeMillis, long userId) {
        if(userId<=0 || timeMillis<=0) {
            return 0;
        }
        long showId = timeMillis + userId * 1000001;
        char[] c = String.valueOf(showId).toCharArray();
        for(int i = 0; i < c.length; i++) {
            int number = c[i] - '0';
            number = (number + SystemConstants.SHOWID_ANSWER) % 10;
            c[i] = (char) ('0' + number);
        }
        String showIdStr = new String(c);
        return Long.parseLong(showIdStr);
    }
}
