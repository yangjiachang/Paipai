package com.coding.system.service;

import com.coding.system.domain.Article;
import java.util.List;

/**
 * 文章 服务层
 * 
 * @author 杨佳畅
 * @date 2018-12-17
 */
public interface IArticleService 
{
	/**
     * 查询文章信息
     * 
     * @param id 文章ID
     * @return 文章信息
     */
	public Article selectArticleById(Long id);
	
	/**
     * 查询文章列表
     * 
     * @param article 文章信息
     * @return 文章集合
     */
	public List<Article> selectArticleList(Article article);
	
	/**
     * 新增文章
     * 
     * @param article 文章信息
     * @return 结果
     */
	public int insertArticle(Article article);
	
	/**
     * 修改文章
     * 
     * @param article 文章信息
     * @return 结果
     */
	public int updateArticle(Article article);
		
	/**
     * 删除文章信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteArticleByIds(String ids);
	
}
