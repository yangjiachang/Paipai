package com.coding.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coding.system.mapper.ArticleMapper;
import com.coding.system.domain.Article;
import com.coding.system.service.IArticleService;
import com.coding.common.support.Convert;

/**
 * 文章 服务层实现
 * 
 * @author 杨佳畅
 * @date 2018-12-17
 */
@Service
public class ArticleServiceImpl implements IArticleService 
{
	@Autowired
	private ArticleMapper articleMapper;

	/**
     * 查询文章信息
     * 
     * @param id 文章ID
     * @return 文章信息
     */
    @Override
	public Article selectArticleById(Long id)
	{
	    return articleMapper.selectArticleById(id);
	}
	
	/**
     * 查询文章列表
     * 
     * @param article 文章信息
     * @return 文章集合
     */
	@Override
	public List<Article> selectArticleList(Article article)
	{
	    return articleMapper.selectArticleList(article);
	}
	
    /**
     * 新增文章
     * 
     * @param article 文章信息
     * @return 结果
     */
	@Override
	public int insertArticle(Article article)
	{
	    return articleMapper.insertArticle(article);
	}
	
	/**
     * 修改文章
     * 
     * @param article 文章信息
     * @return 结果
     */
	@Override
	public int updateArticle(Article article)
	{
	    return articleMapper.updateArticle(article);
	}

	/**
     * 删除文章对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteArticleByIds(String ids)
	{
		return articleMapper.deleteArticleByIds(Convert.toStrArray(ids));
	}
	
}
