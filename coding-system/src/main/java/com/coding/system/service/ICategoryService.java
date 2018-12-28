package com.coding.system.service;

import com.coding.system.domain.Category;
import com.coding.system.domain.SysDept;

import java.util.List;
import java.util.Map;

/**
 * 文章分类 服务层
 * 
 * @author 杨佳畅
 * @date 2018-12-18
 */
public interface ICategoryService 
{
	/**
     * 查询文章分类信息
     * 
     * @param categoryId 文章分类ID
     * @return 文章分类信息
     */
	public Category selectCategoryById(Integer categoryId);
	
	/**
     * 查询文章分类列表
     * 
     * @param category 文章分类信息
     * @return 文章分类集合
     */
	public List<Category> selectCategoryList(Category category);
	
	/**
     * 新增文章分类
     * 
     * @param category 文章分类信息
     * @return 结果
     */
	public int insertCategory(Category category);
	
	/**
     * 修改文章分类
     * 
     * @param category 文章分类信息
     * @return 结果
     */
	public int updateCategory(Category category);
		
	/**
     * 删除文章分类信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteCategoryByIds(String ids);

	/**
	 * 查询分类管理树
	 *
	 * @param category 分类信息
	 * @return 所有分类信息
	 */
	public List<Map<String, Object>> selectCategoryTree(Category category);
	
}
