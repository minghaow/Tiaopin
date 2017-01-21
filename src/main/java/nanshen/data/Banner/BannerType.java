package nanshen.data.Banner;

import org.nutz.castor.Castor;
import org.nutz.castor.Castors;
import org.nutz.castor.FailToCastObjectException;

/**
 * UserMessageType
 *
 * @author WANG Minghao
 */
public enum BannerType {

    A("回答"),

    Q("问题"),

    T("话题"),

    P("达人"),

    DT("决策树"),

    IMG("图片"),

    UNKNOWN("未知");

    private String name;

    BannerType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static BannerType get(String type) {
        try {
            return valueOf(type);
        } catch (Exception e) {
            return UNKNOWN;
        }
    }

    /**
     * 当数据库中出现未知的状态时，该转换器会自动将其转换为{@link #UNKNOWN}，而不会报错
     */
    public static class StringToBannerType extends Castor<String, BannerType> {

        @Override
        public BannerType cast(String src, Class<?> toType, String... args) throws FailToCastObjectException {
            return BannerType.get(src);
        }

    }

    /**
     * 将转换器添加到Nutz
     */
    static {
        Castors.me().addCastor(StringToBannerType.class);
    }

}