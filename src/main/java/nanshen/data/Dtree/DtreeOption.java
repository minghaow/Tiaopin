package nanshen.data.Dtree;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * DtreeQuestion
 *
 * @Author WANG Minghao
 */
@Table("DtreeOption")
public class DtreeOption {

    /** ID */
    @Id
    private long id;

    /** topicId */
    @Column
    private long topicId = 0;

    /** qid */
    @Column
    private long qid = 0;

    /** question level */
    @Column
    private long level = 0;

    /** nextQid */
    @Column
    private long nextQid = 0;

    /** content */
    @Column
    private String content;

    /** skuType */
    @Column
    private String skuType;

    /** isImg */
    @Column
    private DtreeOptionType type = DtreeOptionType.UNKNOWN;

    public DtreeOption() {
    }

    public DtreeOption(String content, long qid, DtreeOptionType type, long topicId, long nextQid) {
        this.content = content;
        this.qid = qid;
        this.topicId = topicId;
        this.type = type;
        this.nextQid = nextQid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getQid() {
        return qid;
    }

    public void setQid(long qid) {
        this.qid = qid;
    }

    public DtreeOptionType getType() {
        return type;
    }

    public void setType(DtreeOptionType type) {
        this.type = type;
    }

    public long getTopicId() {
        return topicId;
    }

    public void setTopicId(long topicId) {
        this.topicId = topicId;
    }

    public long getNextQid() {
        return nextQid;
    }

    public void setNextQid(long nextQid) {
        this.nextQid = nextQid;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public String getSkuType() {
        return skuType;
    }

    public void setSkuType(String skuType) {
        this.skuType = skuType;
    }
}
