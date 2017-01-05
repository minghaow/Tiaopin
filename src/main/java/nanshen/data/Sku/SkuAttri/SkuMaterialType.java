package nanshen.data.Sku.SkuAttri;

import org.nutz.castor.Castor;
import org.nutz.castor.Castors;
import org.nutz.castor.FailToCastObjectException;

/**
 * MaterialType
 *
 * @author WANG Minghao
 */
public enum SkuMaterialType {

    ALL("都可以"),

    GLASS("玻璃制品"),

    IRON("金属"),

    CHINA("陶瓷"),

    CLOTH("纺织品"),

    TREE("竹木"),

    CHEMICAL("高分子"),

    BUNDLE("组合材料"),

    STONE("石头"),

    UNKNOWN("未知种类");

    private String desc;

    SkuMaterialType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static SkuMaterialType get(String name) {
        try {
            return valueOf(name);
        } catch (Exception e) {
            return UNKNOWN;
        }
    }

    /**
     * 当数据库中出现未知的状态时，该转换器会自动将其转换为{@link #UNKNOWN}，而不会报错
     */
    public static class StringToSkuMaterialType extends Castor<String, SkuMaterialType> {
        @Override
        public SkuMaterialType cast(String src, Class<?> toType, String... args) throws FailToCastObjectException {
            return SkuMaterialType.get(src);
        }

    }

    /**
     * 将转换器添加到Nutz
     */
    static {
        Castors.me().addCastor(StringToSkuMaterialType.class);
    }
}
