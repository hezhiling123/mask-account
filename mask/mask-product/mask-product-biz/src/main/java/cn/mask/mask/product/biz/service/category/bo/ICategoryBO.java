package cn.mask.mask.product.biz.service.category.bo;

import cn.mask.mask.model.dto.page.PageResult;
import cn.mask.mask.product.api.service.category.dto.CategoryDTO;
import cn.mask.mask.product.api.service.category.dto.QCategoryDTO;
import cn.mask.mask.product.api.service.category.vo.CategoryVO;

import java.util.List;


/**
 * @author hezhiling
 */
public interface ICategoryBO {
    /**
     * 列出分类信息
     *
     * @param qCategoryDTO {@link QCategoryDTO}
     * @return a {@link PageResult} of {@link CategoryDTO}
     * @throws Exception e
     */
    PageResult<CategoryDTO> listCategoryByPage(QCategoryDTO qCategoryDTO) throws Exception;

    /**
     * 树形结构列出商品列表。
     * @param qCategoryDTO {@link QCategoryDTO}
     * @return a list of {@link CategoryVO}
     * @throws Exception e
     */
    List<CategoryVO> listCategoryByTree(QCategoryDTO qCategoryDTO) throws Exception;
}
