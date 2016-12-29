package nanshen.data.Dtree;

import org.nutz.castor.Castor;
import org.nutz.castor.Castors;
import org.nutz.castor.FailToCastObjectException;

/**
 * DtreeQuestionType
 *
 * @author WANG Minghao
 */
public enum DtreeOptionType {

    TEXT("文字"),

    IMG("图片"),

    UNKNOWN("未知种类");

    private String desc;

    DtreeOptionType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static DtreeOptionType get(String name) {
        try {
            return valueOf(name);
        } catch (Exception e) {
            return UNKNOWN;
        }
    }

    /**
     * 当数据库中出现未知的状态时，该转换器会自动将其转换为{@link #UNKNOWN}，而不会报错
     */
    public static class StringToDtreeQuestionType extends Castor<String, DtreeOptionType> {
        @Override
        public DtreeOptionType cast(String src, Class<?> toType, String... args) throws FailToCastObjectException {
            return DtreeOptionType.get(src);
        }

    }

    /**
     * 将转换器添加到Nutz
     */
    static {
        Castors.me().addCastor(StringToDtreeQuestionType.class);
    }
}
