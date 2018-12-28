package com.coding.web.controller.system;

import java.util.List;

import com.coding.framework.util.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.coding.common.annotation.Log;
import com.coding.common.enums.BusinessType;
import com.coding.system.domain.Article;
import com.coding.system.service.IArticleService;
import com.coding.framework.web.base.BaseController;
import com.coding.framework.web.page.TableDataInfo;
import com.coding.common.base.AjaxResult;
import com.coding.common.utils.ExcelUtil;

/**
 * 文章 信息操作处理
 *
 * @author 杨佳畅
 * @date 2018-12-17
 */
@Controller
@RequestMapping("/system/article")
public class ArticleController extends BaseController {
    private String prefix = "system/article" ;

    @Autowired
    private IArticleService articleService;

    @RequiresPermissions("system:article:view")
    @GetMapping()
    public String article() {
        return prefix + "/article" ;
    }

    /**
     * 查询文章列表
     */
    @RequiresPermissions("system:article:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Article article) {
        startPage();
        List<Article> list = articleService.selectArticleList(article);
        return getDataTable(list);
    }


    /**
     * 导出文章列表
     */
    @RequiresPermissions("system:article:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Article article) {
        List<Article> list = articleService.selectArticleList(article);
        ExcelUtil<Article> util = new ExcelUtil<Article>(Article.class);
        return util.exportExcel(list, "article");
    }

    /**
     * 新增文章
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add" ;
    }

    /**
     * 新增保存文章
     */
    @RequiresPermissions("system:article:add")
    @Log(title = "文章" , businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Article article) {
        article.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(articleService.insertArticle(article));
    }

    /**
     * 修改文章
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Article article = articleService.selectArticleById(id);
        mmap.put("article" , article);
        return prefix + "/edit" ;
    }

    /**
     * 修改保存文章
     */
    @RequiresPermissions("system:article:edit")
    @Log(title = "文章" , businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Article article) {
        return toAjax(articleService.updateArticle(article));
    }

    /**
     * 删除文章
     */
    @RequiresPermissions("system:article:remove")
    @Log(title = "文章" , businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(articleService.deleteArticleByIds(ids));
    }

}
