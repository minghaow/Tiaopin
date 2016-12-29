package nanshen.data.Dtree;

import nanshen.data.Question.QuestionStatus;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;
import java.util.List;

/**
 * DtreeQuestion
 *
 * @Author WANG Minghao
 */
@Table("DtreeQuestion")
public class DtreeQuestion {

    /** ID */
    @Id
    private long id;

    /** showId */
    @Column
    private long showId = 0;

    /** topicId */
    @Column
    private long topicId = 0;

    /** upload user */
    @Column
    private long userId = 0;

    /** question level */
    @Column
    private long level = 0;

    /** title, attractive information */
    @Column
    private String title;

    /** question description, explain the title */
    @Column
    private String description;

    /** question options */
    private List<DtreeOption> optionList;

    /** total view about the question */
    @Column
    private long viewCnt = 0;

    /** total subscribe user */
    @Column
    private long subCnt = 0;

    /** question status */
    @Column
    private QuestionStatus status = QuestionStatus.DRAFT;

    /** question type */
    @Column
    private DtreeQuestionType type = DtreeQuestionType.UNKNOWN;

    /** create time for this question, will fill when create */
    @Column
    private Date createTime = new Date();

    /** update time for this question, all operator will update this value */
    @Column
    private Date updateTime = new Date();

    public DtreeQuestion() {
    }

    public DtreeQuestion(String title, String description, QuestionStatus status, DtreeQuestionType type, long userId) {
        this.description = description;
        this.showId = generateShowId(System.currentTimeMillis(), userId);
        this.status = status;
        this.title = title;
        this.type = type;
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public QuestionStatus getStatus() {
        return status;
    }

    public void setStatus(QuestionStatus status) {
        this.status = status;
    }

    public long getSubCnt() {
        return subCnt;
    }

    public void setSubCnt(long subCnt) {
        this.subCnt = subCnt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getUpdateTime() {
        return updateTime;
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

    public DtreeQuestionType getType() {
        return type;
    }

    public void setType(DtreeQuestionType type) {
        this.type = type;
    }

    public List<DtreeOption> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<DtreeOption> optionList) {
        this.optionList = optionList;
    }

    public long getTopicId() {
        return topicId;
    }

    public void setTopicId(long topicId) {
        this.topicId = topicId;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
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
