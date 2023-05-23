package cn.mask.mask.product.api.service.category.service;

import cn.mask.mask.common.core.framework.web.WrapperResponse;
import cn.mask.mask.model.dto.page.PageResult;
import cn.mask.mask.product.api.service.category.dto.CategoryDTO;
import cn.mask.mask.product.api.service.category.dto.QCategoryDTO;
import cn.mask.mask.product.api.service.category.vo.CategoryVO;

import java.util.List;

/**
 * @author hezhiling
 */
public interface ICategoryService {
    /**
     * 列出分类信息
     *
     * @param qCategoryDTO {@link QCategoryDTO}
     * @return a {@link PageResult} of {@link CategoryDTO}
     * @throws Exception e
     */
    WrapperResponse<PageResult<CategoryDTO>> listCategoryByPage(QCategoryDTO qCategoryDTO) throws Exception;

    /**
     * 树形结构列出分类信息
     *
     * @param qCategoryDTO {@link QCategoryDTO}
     * @return a list of {@link CategoryVO}
     * @throws Exception e
     */
    WrapperResponse<List<CategoryVO>> listCategoryByTree(QCategoryDTO qCategoryDTO) throws Exception;

    /**
     * 添加分类列表
     *
     * @param categoryDTOList a list of {@link CategoryDTO}
     * @return void
     * @throws Exception e
     */
    WrapperResponse<Void> addCategory(List<CategoryDTO> categoryDTOList) throws Exception;
}
