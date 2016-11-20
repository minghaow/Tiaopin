package nanshen.data.Question;

import org.nutz.castor.Castor;
import org.nutz.castor.Castors;
import org.nutz.castor.FailToCastObjectException;

/**
 * AnswerStatus
 *
 * @author WANG Minghao
 */
public enum AnswerStatus {

    DRAFT("草稿"),

    ONLINE("线上"),

    PRIVATEE("仅自己可见"),

    UNKNOWN("未知种类");

    private String desc;

    AnswerStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public static AnswerStatus get(String name) {
        try {
            return valueOf(name);
        } catch (Exception e) {
            return UNKNOWN;
        }
    }

    /**
     * 当数据库中出现未知的状态时，该转换器会自动将其转换为{@link #UNKNOWN}，而不会报错
     */
    public static class StringToAnswerStatus extends Castor<String, AnswerStatus> {
        @Override
        public AnswerStatus cast(String src, Class<?> toType, String... args) throws FailToCastObjectException {
            return AnswerStatus.get(src);
        }

    }

    /**
     * 将转换器添加到Nutz
     */
    static {
        Castors.me().addCastor(StringToAnswerStatus.class);
    }
}
