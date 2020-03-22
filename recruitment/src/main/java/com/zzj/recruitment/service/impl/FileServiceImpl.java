package com.zzj.recruitment.service.impl;

import com.google.common.collect.Lists;
import com.zzj.recruitment.service.IFileService;
import com.zzj.recruitment.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-19 17:04:32
 */
@Service("fileService")
public class FileServiceImpl implements IFileService {

    @Autowired
    FTPUtil ftpUtil;

    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String upload(MultipartFile file, String path, String remotePath) {
        String filename = file.getOriginalFilename();
        // 扩展名 abc.jpg --> jpg
        String fileExtensionName = filename.substring(filename.lastIndexOf(".") + 1);
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
        logger.info("开始上传文件，上传文件的文件名：{}，上传的路径：{}，新文件名：{}", filename, path, uploadFileName);

        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }

        // 真正要上传的文件
        File targetFile = new File(path, uploadFileName);
        try {
            file.transferTo(targetFile);
            // 将`targetFile上传到FTP服务器
            ftpUtil.uploadFile(remotePath,Lists.newArrayList(targetFile));
            // 已经上传到ftp服务器
            // 上传完以后将upload下的文件删除 ---> upload 是属于Tomcat服务器的
            targetFile.delete();
        } catch (IOException e) {
            logger.error("上传文件异常！", e);
            e.printStackTrace();
        }
        return targetFile.getName();
    }

    @Override
    public Boolean delete(String fileName, String remotePath) {
        boolean result = true;
        try {
            result = ftpUtil.deleteFile(remotePath, fileName);
        } catch (IOException e) {
            result = false;
            logger.error("删除文件异常！", e);
            e.printStackTrace();
        }
        return result;
    }
}
