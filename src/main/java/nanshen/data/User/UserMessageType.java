package nanshen.data.User;

import org.nutz.castor.Castor;
import org.nutz.castor.Castors;
import org.nutz.castor.FailToCastObjectException;

/**
 * UserMessageType
 *
 * @author WANG Minghao
 */
public enum UserMessageType {

    ANSWER_UP("回答赞同"),

    ANSWER_REPORT("回答举报"),

    TEXT("普通信息"),

    UNKNOWN("未知");

    private String name;

    UserMessageType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static UserMessageType get(String type) {
        try {
            return valueOf(type);
        } catch (Exception e) {
            return UNKNOWN;
        }
    }

    /**
     * 当数据库中出现未知的状态时，该转换器会自动将其转换为{@link #UNKNOWN}，而不会报错
     */
    public static class StringToUserMessageType extends Castor<String, UserMessageType> {

        @Override
        public UserMessageType cast(String src, Class<?> toType, String... args) throws FailToCastObjectException {
            return UserMessageType.get(src);
        }

    }

    /**
     * 将转换器添加到Nutz
     */
    static {
        Castors.me().addCastor(StringToUserMessageType.class);
    }

}