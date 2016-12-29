package nanshen.data.Dtree;

import org.nutz.castor.Castor;
import org.nutz.castor.Castors;
import org.nutz.castor.FailToCastObjectException;

/**
 * DtreeQuestionType
 *
 * @author WANG Minghao
 */
public enum DtreeQuestionType {

    Q2("2个文字选项的问题", 2),

    Q3("3个文字选项的问题", 3),

    Q4("4个文字选项的问题", 4),

    QP2("2个图片选项的问题", 2),

    QP3("3个图片选项的问题", 3),

    QP4("4个图片选项的问题", 4),

    UNKNOWN("未知种类", 2);

    private String desc;

    private long optionAmount;

    DtreeQuestionType(String desc, long optionAmount) {
        this.desc = desc;
        this.optionAmount = optionAmount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getOptionAmount() {
        return optionAmount;
    }

    public void setOptionAmount(long optionAmount) {
        this.optionAmount = optionAmount;
    }

    public static DtreeQuestionType get(String name) {
        try {
            return valueOf(name);
        } catch (Exception e) {
            return UNKNOWN;
        }
    }

    /**
     * 当数据库中出现未知的状态时，该转换器会自动将其转换为{@link #UNKNOWN}，而不会报错
     */
    public static class StringToDtreeQuestionType extends Castor<String, DtreeQuestionType> {
        @Override
        public DtreeQuestionType cast(String src, Class<?> toType, String... args) throws FailToCastObjectException {
            return DtreeQuestionType.get(src);
        }

    }

    /**
     * 将转换器添加到Nutz
     */
    static {
        Castors.me().addCastor(StringToDtreeQuestionType.class);
    }
}
