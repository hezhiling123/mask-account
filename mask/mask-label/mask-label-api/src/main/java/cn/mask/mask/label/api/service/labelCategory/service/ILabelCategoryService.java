package cn.mask.mask.label.api.service.labelCategory.service;

import cn.mask.mask.common.core.framework.web.WrapperResponse;
import cn.mask.mask.common.core.framework.web.exception.MaskException;
import cn.mask.mask.label.api.service.labelCategory.dto.LabelCategoryDTO;
import cn.mask.mask.label.api.service.labelCategory.dto.QLabelCategoryDTO;
import cn.mask.mask.model.dto.page.PageResult;

import java.util.List;

/**
 * @author hezhiling
 */
public interface ILabelCategoryService {
    /**
     * 查询标签类别
     *
     * @param qLabelCategoryDTO {@link QLabelCategoryDTO} 查询类别
     * @return  a list of {@link QLabelCategoryDTO}
     * @throws MaskException    e
     */
    WrapperResponse<PageResult<LabelCategoryDTO>> listLabelCategoryByPage(QLabelCategoryDTO qLabelCategoryDTO) throws MaskException;

    /**
     * 增加标签类别
     *
     * @param labelCategoryDTOList  {@link LabelCategoryDTO}
     * @throws MaskException    e
     */
    WrapperResponse<List<LabelCategoryDTO>> addLabelCategory(List<LabelCategoryDTO> labelCategoryDTOList) throws MaskException;
}
