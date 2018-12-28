package com.coding.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.coding.common.base.BaseEntity;

import java.util.Date;

/**
 * 文章表 sys_article
 *
 * @author 杨佳畅
 * @date 2018-12-17
 */
public class Article extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 文章id
     */
    private Long id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容md版
     */
    private String content;

    /**
     * 文章内容html版
     */
    private String htmlContent;

    /**
     * 文章标签
     */
    private String tags;

    /**
     * 文章分类id
     */
    private Integer categoryId;

    /**
     * 点赞数量
     */
    private Integer voteSize;

    /**
     * 评论量
     */
    private Integer commentSize;

    /**
     * 阅读量
     */
    private Integer readSize;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 分类对象
     */
    private Category category;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTags() {
        return tags;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setVoteSize(Integer voteSize) {
        this.voteSize = voteSize;
    }

    public Integer getVoteSize() {
        return voteSize;
    }

    public void setCommentSize(Integer commentSize) {
        this.commentSize = commentSize;
    }

    public Integer getCommentSize() {
        return commentSize;
    }

    public void setReadSize(Integer readSize) {
        this.readSize = readSize;
    }

    public Integer getReadSize() {
        return readSize;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id" , getId())
                .append("title" , getTitle())
                .append("content" , getContent())
                .append("htmlContent" , getHtmlContent())
                .append("tags" , getTags())
                .append("categoryId" , getCategoryId())
                .append("voteSize" , getVoteSize())
                .append("commentSize" , getCommentSize())
                .append("readSize" , getReadSize())
                .append("createBy" , getCreateBy())
                .append("createTime" , getCreateTime())
                .append("updateBy" , getUpdateBy())
                .append("updateTime" , getUpdateTime())
                .toString();
    }
}
