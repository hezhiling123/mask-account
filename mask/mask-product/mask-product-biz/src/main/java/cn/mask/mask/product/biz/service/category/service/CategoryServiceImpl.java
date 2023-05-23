package cn.mask.mask.product.biz.service.category.service;

import cn.mask.mask.common.core.framework.web.WrapperResponse;
import cn.mask.mask.model.dto.page.PageResult;
import cn.mask.mask.product.api.service.category.dto.CategoryDTO;
import cn.mask.mask.product.api.service.category.dto.QCategoryDTO;
import cn.mask.mask.product.api.service.category.service.ICategoryService;
import cn.mask.mask.product.api.service.category.vo.CategoryVO;
import cn.mask.mask.product.biz.service.category.bo.ICategoryBO;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hezhiling
 * @version 1.0
 * @date 2023-05-07 10:11:02
 */
@Service
@RestController
@RequestMapping("/api/category")
public class CategoryServiceImpl implements ICategoryService {

    @Resource
    private ICategoryBO categoryBO;

    /**
     * 列出分类信息
     *
     * @param qCategoryDTO {@link QCategoryDTO}
     * @return a {@link PageResult} of {@link CategoryDTO}
     * @throws Exception e
     */
    @Override
    @PostMapping("/listCategoryByPage")
    public WrapperResponse<PageResult<CategoryDTO>> listCategoryByPage(@RequestBody QCategoryDTO qCategoryDTO) throws Exception {
        return WrapperResponse.success(categoryBO.listCategoryByPage(qCategoryDTO));
    }

    /**
     * 树形结构列出分类信息
     *
     * @param qCategoryDTO {@link QCategoryDTO}
     * @return a list of {@link CategoryVO}
     * @throws Exception e
     */
    @Override
    @PostMapping("/listCategoryByTree")
    public WrapperResponse<List<CategoryVO>> listCategoryByTree(@RequestBody QCategoryDTO qCategoryDTO) throws Exception {
        return WrapperResponse.success(categoryBO.listCategoryByTree(qCategoryDTO));
    }

    /**
     * 添加分类列表
     *
     * @param categoryDTOList a list of {@link CategoryDTO}
     * @return void
     * @throws Exception e
     */
    @Override
    public WrapperResponse<Void> addCategory(@RequestBody List<CategoryDTO> categoryDTOList) throws Exception {
        return null;
    }
}
