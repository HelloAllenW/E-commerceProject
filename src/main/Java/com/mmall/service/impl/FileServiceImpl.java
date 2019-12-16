package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service("iFileService")
public class FileServiceImpl implements IFileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    public String upload(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename(); // 获取上传文件的原始文件名
        // 获取扩展名
        // abc.jpg ： 从后向前开始查找第一个 . +1表示的是获取.后面的
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        // 创建新文件名
        String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;
        logger.info("开始上传文件，上传文件的文件名:{}，上传的路径:{}，新文件名:{}", fileName, path, uploadFileName);

        // 创建目录
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }

        // 创建文件
        File targetFile = new File(path, uploadFileName);
        try {
            file.transferTo(targetFile); // 文件已经成功上传 upload

            // 将 targetFile 上传到我们的FTP服务器
            FTPUtil.uploadFile(Lists.<File>newArrayList(targetFile)); // 已上传
            // 上传完之后，删除 upload 下面的文件
            targetFile.delete();

        } catch (IOException e) {
            logger.error("上传文件异常", e);
            return null;
        }

        return targetFile.getName(); // 目标文件文件名

    }
}
