package cn.mask.mask.product.api.service.category.dto;

import cn.mask.mask.model.dto.page.PageInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author hezhiling
 * @version 1.0
 * @date 2023-05-16 21:17:24
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QCategoryDTO extends PageInfo {
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
     * 分类地址{parent_id}-{child_id},...
     */
    private String path;

    /**
     * 状态1：enable，0：disable,-1:deleted
     */
    private Byte valiFlag;

    /**
     * 分类层级，从0开始
     */
    private Integer level;

    /**
     * 排序
     */
    private Integer seq;

    /**
     * 创建者Id
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
}
