package nanshen.data.Question;

import org.nutz.castor.Castor;
import org.nutz.castor.Castors;
import org.nutz.castor.FailToCastObjectException;

/**
 * QuestionType
 *
 * @author WANG Minghao
 */
public enum QuestionType {

    CCC("3C产品"),

    CLOTH("服装"),

    SMART("智能产品"),

    SMART_HOME("智能家具"),

    DECO_HOME("家具装饰"),

    UNKNOWN("未知种类");

    private String desc;

    QuestionType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public static QuestionType get(String name) {
        try {
            return valueOf(name);
        } catch (Exception e) {
            return UNKNOWN;
        }
    }

    /**
     * 当数据库中出现未知的状态时，该转换器会自动将其转换为{@link #UNKNOWN}，而不会报错
     */
    public static class StringToQuestionType extends Castor<String, QuestionType> {
        @Override
        public QuestionType cast(String src, Class<?> toType, String... args) throws FailToCastObjectException {
            return QuestionType.get(src);
        }

    }

    /**
     * 将转换器添加到Nutz
     */
    static {
        Castors.me().addCastor(StringToQuestionType.class);
    }
}
