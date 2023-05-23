package cn.mask.mask.product.biz.service.category.bo.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.mask.mask.model.dto.page.PageResult;
import cn.mask.mask.product.api.service.category.dto.CategoryDTO;
import cn.mask.mask.product.api.service.category.dto.QCategoryDTO;
import cn.mask.mask.product.api.service.category.vo.CategoryVO;
import cn.mask.mask.product.biz.service.category.bo.ICategoryBO;
import cn.mask.mask.product.biz.service.category.dao.CategoryMapper;
import cn.mask.mask.product.biz.service.category.pojo.po.CategoryPO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hezhiling
 * @version 1.0
 * @date 2023-05-07 10:13:52
 */
@Component
public class CategoryBOImpl implements ICategoryBO {

    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 列出分类信息
     *
     * @param qCategoryDTO {@link QCategoryDTO}
     * @return a {@link PageResult} of {@link CategoryDTO}
     * @throws Exception e
     */
    @Override
    public PageResult<CategoryDTO> listCategoryByPage(QCategoryDTO qCategoryDTO) throws Exception {
        categoryMapper.beginPager(qCategoryDTO);
        List<CategoryPO> categoryDTOList = categoryMapper.listCategory(qCategoryDTO);
        return categoryMapper.endPager(categoryDTOList, CategoryDTO.class);
    }

    /**
     * 树形结构列出商品列表。
     *
     * @param qCategoryDTO {@link QCategoryDTO}
     * @return a list of {@link CategoryVO}
     * @throws Exception e
     */
    @Override
    public List<CategoryVO> listCategoryByTree(QCategoryDTO qCategoryDTO) throws Exception {
        List<CategoryPO> categoryPOList = categoryMapper.listCategory(qCategoryDTO);
        List<CategoryVO> categoryVOList = BeanUtil.copyToList(categoryPOList, CategoryVO.class);
        if (CollectionUtil.isEmpty(categoryVOList)) {
            return categoryVOList;
        }
        Integer minLevel = categoryVOList.stream().min(Comparator.comparingInt(CategoryVO::getLevel)).get().getLevel();
        return categoryVOList.stream()
                .filter(categoryVO -> categoryVO.getLevel().equals(minLevel))
                .peek(categoryVO -> categoryVO.setSubCategoryList(getSubCategoryList(categoryVO, categoryVOList)))
                .sorted(Comparator.comparingInt(CategoryVO::getSeq)).collect(Collectors.toList());
    }

    /**
     * 获取子分类树
     *
     * @param categoryVO      当前节点
     * @param allCategoryList 全部节点信息
     * @return 当前子节点的子节点信息，按seq排序
     */
    private List<CategoryVO> getSubCategoryList(CategoryVO categoryVO, List<CategoryVO> allCategoryList) {
        return allCategoryList.stream().filter(categoryVO1 -> categoryVO1.getParentId().equals(categoryVO.getCategoryId()))
                .peek(categoryVO2 -> categoryVO2.setSubCategoryList(getSubCategoryList(categoryVO2, allCategoryList)))
                .sorted(Comparator.comparingInt(CategoryVO::getSeq)).collect(Collectors.toList());
    }
}
