package nanshen.data.Sku;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * Tiaopin
 *
 * @Author WANG Minghao
 */
@Table("SkuSource")
public class SkuSource {

    /** ID */
    @Id
    private long id;

    /** skuId */
    @Column
    private long sid;

    /** answer id */
    @Column
    private long aid;

    /** question id */
    @Column
    private long qid;

    public SkuSource() {
    }

    public SkuSource(long aid, long qid, long sid) {
        this.aid = aid;
        this.qid = qid;
        this.sid = sid;
    }

    public long getAid() {
        return aid;
    }

    public void setAid(long aid) {
        this.aid = aid;
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

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }
}
