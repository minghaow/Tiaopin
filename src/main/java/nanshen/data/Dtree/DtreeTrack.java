package nanshen.data.Dtree;

/**
 * DtreeTrack
 *
 * @Author WANG Minghao
 */
public class DtreeTrack {

    /** qid */
    private long qid;

    /** level */
    private long level = 0;

    /** option */
    private long option = 0;

    public DtreeTrack() {
    }

    public DtreeTrack(long level, long option, long qid) {
        this.level = level;
        this.option = option;
        this.qid = qid;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public long getOption() {
        return option;
    }

    public void setOption(long option) {
        this.option = option;
    }

    public long getQid() {
        return qid;
    }

    public void setQid(long qid) {
        this.qid = qid;
    }
}
