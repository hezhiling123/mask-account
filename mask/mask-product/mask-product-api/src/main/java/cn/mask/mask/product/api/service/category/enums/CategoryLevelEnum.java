package cn.mask.mask.product.api.service.category.enums;

/**
 * 商品分类等级枚举类
 * @author hezhiling
 */
public enum CategoryLevelEnum {
    /**
     * 第一级
     */
    First(0),

    /**
     * 第二级
     */
    SECOND(1),

    /**
     * 第三级
     */
    THIRD(2)
    ;

    private final Integer value;

    public Integer value() {
        return value;
    }

    CategoryLevelEnum(Integer value) {
        this.value = value;
    }
}
