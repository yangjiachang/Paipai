package com.coding.system.service;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;

import java.io.File;
import java.io.InputStream;

/**
 * @Author: 杨佳畅
 * @Description: 七牛服务
 * @Date: Created in 2018/12/20 下午11:27
 */
public interface IQiNiuService {
    Response uploadFile(File file) throws QiniuException;

    Response uploadFile(InputStream InputStream) throws QiniuException;

    Response delete(String key) throws QiniuException;
}
