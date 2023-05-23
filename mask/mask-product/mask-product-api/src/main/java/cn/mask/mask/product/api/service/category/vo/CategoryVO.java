package cn.mask.mask.product.api.service.category.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author hezhiling
 * @version 1.0
 * @date 2023-05-22 22:02:06
 */
@Data
public class CategoryVO {

    /**
     * 分类id
     */
    private Long categoryId;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 父id
     */
    private Long parentId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 分类描述
     */
    private String categoryDecr;

    /**
     * 分类地址{parent_id}-{child_id},...
     */
    private String path;

    /**
     * 状态 1:enable, 0:disable, -1:deleted
     */
    private Byte valiFlag;

    /**
     * 分类图标
     */
    private String icon;

    /**
     * 分类的显示图片
     */
    private String imgUrl;

    /**
     * 分类层级 从0开始
     */
    private Integer level;

    /**
     * 排序
     */
    private Integer seq;

    /**
     * 创建者id
     */
    private String crterId;

    /**
     * 创建者名称
     */
    private String crterName;

    /**
     * 创建时间
     */
    private Date crteTime;

    /**
     * 更新者Id
     */
    private String updterId;

    /**
     * 更新者名称
     */
    private String updterName;

    /**
     * 更新时间
     */
    private Date updtTime;

    /**
     * 子分类树
     */
    private List<CategoryVO> subCategoryList;
}
