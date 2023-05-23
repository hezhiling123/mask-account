package cn.mask.mask.product.api.service.category.dto;

import lombok.Data;

import java.util.List;

/**
 * @author hezhiling
 * @version 1.0
 * @date 2023-05-16 21:17:48
 */
@Data
public class CategoryDTO {
    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 店铺ID
     */
    private Long shopId;

    /**
     * 父ID
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
     * 状态1：enable，0：disable,-1:deleted
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
     * 分类层级，从0开始
     */
    private Integer level;

    /**
     * 排序
     */
    private Integer seq;

    /**
     * 子列表
     */
    private List<CategoryDTO> subCategoryList;
}
