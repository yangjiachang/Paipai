package com.coding.system.mapper;

import com.coding.system.domain.Category;
import com.coding.system.domain.SysDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章分类 数据层
 * 
 * @author 杨佳畅
 * @date 2018-12-18
 */
public interface CategoryMapper 
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
     * 删除文章分类
     * 
     * @param categoryId 文章分类ID
     * @return 结果
     */
	public int deleteCategoryById(Integer categoryId);
	
	/**
     * 批量删除文章分类
     * 
     * @param categoryIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteCategoryByIds(String[] categoryIds);

	/**
	 * 修改子元素关系
	 *
	 * @param categorys 子元素
	 * @return 结果
	 */
	public int updateCategoryChildren(@Param("categorys") List<Category> categorys);

}