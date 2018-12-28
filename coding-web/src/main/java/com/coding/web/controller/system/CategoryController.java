package com.coding.web.controller.system;

import java.util.List;
import java.util.Map;

import com.coding.common.utils.StringUtils;
import com.coding.framework.util.ShiroUtils;
import com.coding.system.domain.SysDept;
import io.swagger.models.auth.In;
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
import com.coding.system.domain.Category;
import com.coding.system.service.ICategoryService;
import com.coding.framework.web.base.BaseController;
import com.coding.framework.web.page.TableDataInfo;
import com.coding.common.base.AjaxResult;
import com.coding.common.utils.ExcelUtil;

/**
 * 文章分类 信息操作处理
 *
 * @author 杨佳畅
 * @date 2018-12-18
 */
@Controller
@RequestMapping("/system/category")
public class CategoryController extends BaseController {
    private String prefix = "system/category";

    @Autowired
    private ICategoryService categoryService;

    @RequiresPermissions("system:category:view")
    @GetMapping()
    public String category() {
        return prefix + "/category";
    }

    /**
     * 查询文章分类列表
     */
    @RequiresPermissions("system:category:list")
    @GetMapping("/list")
    @ResponseBody
    public List<Category> list(Category category) {
        List<Category> list = categoryService.selectCategoryList(category);
        return list;
    }


    /**
     * 导出文章分类列表
     */
    @RequiresPermissions("system:category:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Category category) {
        List<Category> list = categoryService.selectCategoryList(category);
        ExcelUtil<Category> util = new ExcelUtil<Category>(Category.class);
        return util.exportExcel(list, "category");
    }

    /**
     * 新增文章分类
     */
    @GetMapping("/add/{parentId}")
    public String add(@PathVariable("parentId") int parentId, ModelMap mmap) {
        mmap.put("category", categoryService.selectCategoryById(parentId));
        return prefix + "/add";
    }

    /**
     * 新增保存文章分类
     */
    @RequiresPermissions("system:category:add")
    @Log(title = "文章分类", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Category category) {
        category.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(categoryService.insertCategory(category));
    }

    /**
     * 修改文章分类
     */
    @GetMapping("/edit/{categoryId}")
    public String edit(@PathVariable("categoryId") Integer categoryId, ModelMap mmap) {
        Category category = categoryService.selectCategoryById(categoryId);
        if (StringUtils.isNotNull(category) && 100 == categoryId) {
            category.setParentName("无");

        }
        mmap.put("category", category);
        return prefix + "/edit";
    }

    /**
     * 修改保存文章分类
     */
    @RequiresPermissions("system:category:edit")
    @Log(title = "文章分类", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Category category) {
        category.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(categoryService.updateCategory(category));
    }

    /**
     * 删除文章分类
     */
    @RequiresPermissions("system:category:remove")
    @Log(title = "文章分类", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(categoryService.deleteCategoryByIds(ids));
    }


    /**
     * 选择分类树
     */
    @GetMapping("/selectCategoryTree/{categoryId}")
    public String selectDepe(@PathVariable("categoryId") Integer categoryId, ModelMap mmap) {
        mmap.put("category", categoryService.selectCategoryById(categoryId));
        return prefix + "/tree";
    }


    /**
     * 加载部门分类树
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Map<String, Object>> treeData() {
        List<Map<String, Object>> tree = categoryService.selectCategoryTree(new Category());
        return tree;
    }

}
