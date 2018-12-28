package com.coding.web.controller.system;

import com.coding.common.annotation.Log;
import com.coding.common.base.AjaxResult;
import com.coding.common.config.Global;
import com.coding.common.enums.BusinessType;
import com.coding.framework.shiro.service.SysPasswordService;
import com.coding.framework.util.FileUploadUtils;
import com.coding.framework.util.ShiroUtils;
import com.coding.framework.web.base.BaseController;
import com.coding.system.domain.QiNiuPutRet;
import com.coding.system.domain.SysUser;
import com.coding.system.service.IQiNiuService;
import com.coding.system.service.ISysDictDataService;
import com.coding.system.service.ISysUserService;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import io.swagger.annotations.ApiResponse;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;


/**
 * 个人信息 业务处理
 *
 */
@Controller
@RequestMapping("/system/user/profile")
public class SysProfileController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(SysProfileController.class);

    private String prefix = "system/user/profile";

    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysPasswordService passwordService;

    @Autowired
    private ISysDictDataService dictDataService;

    @Autowired
    private IQiNiuService qiNiuService;

    @Autowired
    private Gson gson;

    @Value("${qiniu.cnd.prefix}")
    private String qiniuPrefix;

    /**
     * 个人信息
     */
    @GetMapping()
    public String profile(ModelMap mmap) {
        SysUser user = getSysUser();
        user.setSex(dictDataService.selectDictLabel("sys_user_sex", user.getSex()));
        mmap.put("user", user);
        mmap.put("roleGroup", userService.selectUserRoleGroup(user.getUserId()));
        mmap.put("postGroup", userService.selectUserPostGroup(user.getUserId()));
        return prefix + "/profile";
    }

    @GetMapping("/checkPassword")
    @ResponseBody
    public boolean checkPassword(String password) {
        SysUser user = getSysUser();
        String encrypt = new Md5Hash(user.getLoginName() + password + user.getSalt()).toHex().toString();
        if (user.getPassword().equals(encrypt)) {
            return true;
        }
        return false;
    }

    @GetMapping("/resetPwd/{userId}")
    public String resetPwd(@PathVariable("userId") Long userId, ModelMap mmap) {
        mmap.put("user", userService.selectUserById(userId));
        return prefix + "/resetPwd";
    }

    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    @ResponseBody
    public AjaxResult resetPwd(SysUser user) {
        user.setSalt(ShiroUtils.randomSalt());
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        int rows = userService.resetUserPwd(user);
        if (rows > 0) {
            setSysUser(userService.selectUserById(user.getUserId()));
            return success();
        }
        return error();
    }

    /**
     * 修改用户
     */
    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") Long userId, ModelMap mmap) {
        mmap.put("user", userService.selectUserById(userId));
        return prefix + "/edit";
    }

    /**
     * 修改头像
     */
    @GetMapping("/avatar/{userId}")
    public String avatar(@PathVariable("userId") Long userId, ModelMap mmap) {
        mmap.put("user", userService.selectUserById(userId));
        return prefix + "/avatar";
    }

    /**
     * 修改用户
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    @ResponseBody
    public AjaxResult update(SysUser user) {
        if (userService.updateUserInfo(user) > 0) {
            setSysUser(userService.selectUserById(user.getUserId()));
            return success();
        }
        return error();
    }

    /**
     * 保存头像
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PostMapping("/updateAvatar")
    @ResponseBody
    public AjaxResult updateAvatar(SysUser user, @RequestParam("avatarfile") MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                InputStream inputStream = file.getInputStream();
                Response response = qiNiuService.uploadFile(inputStream);
                //上传成功
                if (response.isOK()) {
                    // 返回七牛响应的格式
                    QiNiuPutRet ret = gson.fromJson(response.bodyString(), QiNiuPutRet.class);
                    // 上传的图片地址
                    String avatar = qiniuPrefix + ret.hash;
                    user.setAvatar(avatar);
                    if (userService.updateUserInfo(user) > 0) {
                        setSysUser(userService.selectUserById(user.getUserId()));
                        return success();
                    }
                } else {
                    return AjaxResult.error(response.statusCode, response.getInfo());
                }
            }
            return error();
        } catch (QiniuException e) {
            // 上传出现七牛异常的处理
            Response response = e.response;
            try {
                return AjaxResult.error(response.statusCode, response.bodyString());
            } catch (QiniuException e1) {
                e1.printStackTrace();
                return AjaxResult.error("七牛上传图片异常");
            }
        } catch (Exception e) {
            log.error("修改头像失败！", e);
            return error(e.getMessage());
        }
    }
}
