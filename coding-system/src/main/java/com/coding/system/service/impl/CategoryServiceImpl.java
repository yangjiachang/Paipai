package com.coding.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.coding.common.annotation.DataScope;
import com.coding.common.constant.UserConstants;
import com.coding.common.utils.StringUtils;
import com.coding.system.domain.SysDept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coding.system.mapper.CategoryMapper;
import com.coding.system.domain.Category;
import com.coding.system.service.ICategoryService;
import com.coding.common.support.Convert;

/**
 * 文章分类 服务层实现
 *
 * @author 杨佳畅
 * @date 2018-12-18
 */
@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 查询文章分类信息
     *
     * @param categoryId 文章分类ID
     * @return 文章分类信息
     */
    @Override
    public Category selectCategoryById(Integer categoryId) {
        return categoryMapper.selectCategoryById(categoryId);
    }

    /**
     * 查询文章分类列表
     *
     * @param category 文章分类信息
     * @return 文章分类集合
     */
    @Override
    @DataScope(tableAlias = "d")
    public List<Category> selectCategoryList(Category category) {
        return categoryMapper.selectCategoryList(category);
    }

    /**
     * 新增文章分类
     *
     * @param category 文章分类信息
     * @return 结果
     */
    @Override
    public int insertCategory(Category category) {
        Category info = categoryMapper.selectCategoryById(category.getParentId());
        category.setAncestors(info.getAncestors() + "," + category.getParentId());
        return categoryMapper.insertCategory(category);
    }

    /**
     * 修改文章分类
     *
     * @param category 文章分类信息
     * @return 结果
     */
    @Override
    public int updateCategory(Category category) {
        Category info = categoryMapper.selectCategoryById(category.getParentId());
        if (StringUtils.isNotNull(info)) {
            String ancestors = info.getAncestors() + "," + category.getParentId();
            category.setAncestors(ancestors);
            updateCategoryChildren(category.getCategoryId(), ancestors);
        }
        return categoryMapper.updateCategory(category);
    }

    /**
     * 修改子元素关系
     *
     * @param categoryId    分类ID
     * @param ancestors 元素列表
     */
    public void updateCategoryChildren(int categoryId, String ancestors) {
        Category category = new Category();
        category.setParentId(categoryId);
        List<Category> childrens = categoryMapper.selectCategoryList(category);
        for (Category children : childrens) {
            children.setAncestors(ancestors + "," + category.getParentId());
        }
        if (childrens.size() > 0) {
            categoryMapper.updateCategoryChildren(childrens);
        }
    }

    /**
     * 删除文章分类对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCategoryByIds(String ids) {
        return categoryMapper.deleteCategoryByIds(Convert.toStrArray(ids));
    }


    /**
     * 查询分类管理树
     *
     * @param category 分类信息
     * @return 所有分类信息
     */
    @Override
    @DataScope(tableAlias = "d")
    public List<Map<String, Object>> selectCategoryTree(Category category) {
        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        List<Category> CategoryList = categoryMapper.selectCategoryList(category);
        trees = getTrees(CategoryList, false, null);
        return trees;
    }

    /**
     * 对象转部门树
     *
     * @param categoryList 部门列表
     * @param isCheck      是否需要选中
     * @param roleDeptList 角色已存在菜单列表
     * @return
     */
    public List<Map<String, Object>> getTrees(List<Category> categoryList, boolean isCheck, List<
            String> roleDeptList) {

        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        for (Category category : categoryList) {
            Map<String, Object> categoryMap = new HashMap<String, Object>();
            categoryMap.put("id", category.getCategoryId());
            categoryMap.put("pId", category.getParentId());
            categoryMap.put("name", category.getCategoryName());
            categoryMap.put("title", category.getCategoryName());
            if (isCheck) {
                categoryMap.put("checked", roleDeptList.contains(category.getCategoryId() + category.getCategoryName()));
            } else {
                categoryMap.put("checked", false);
            }
            trees.add(categoryMap);
        }
        return trees;
    }

}
