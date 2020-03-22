package com.zzj.recruitment.util;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-19 17:19:11
 */
@Component
public class FTPUtil {

    private static final Logger logger = LoggerFactory.getLogger(FTPUtil.class);

    @Autowired
    PropertiesUtil propertiesUtil;

    private static PropertiesUtil propertiesUtils;

    @PostConstruct
    public void init() {
        propertiesUtils = this.propertiesUtil;
    }

    private static String ftpIp = null;
    private static String ftpUser = null;
    private static String ftpPass = null;

    private static void initConfig() {
        ftpIp = propertiesUtils.getFtp_server_ip();
        ftpUser = propertiesUtils.getFtp_user();
        ftpPass = propertiesUtils.getFtp_pass();
    }



    public FTPUtil() {
    }

    public FTPUtil(String ip, Integer port, String user, String pass) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.pass = pass;
    }


    /**
     * 可上传多个文件
     * @param fileList
     * @return
     */
    public static boolean uploadFile(String path, List<File> fileList) throws IOException {
        initConfig();
        FTPUtil ftpUtil = new FTPUtil(ftpIp, 21, ftpUser, ftpPass);
        logger.info("开始连接ftp服务器！");
        boolean result = ftpUtil.uploadFileWithPath(path, fileList);
        logger.info("开始连接ftp服务器，结束上传，上传结果：{}", result);
        return result;
    }

    public static boolean deleteFile(String remotePath, String filePath) throws IOException {
        initConfig();
        FTPUtil ftpUtil = new FTPUtil(ftpIp, 21, ftpUser, ftpPass);
        logger.info("开始连接ftp服务器！");
        boolean result = ftpUtil.delete(remotePath,filePath);
        logger.info("开始连接ftp服务器，结束删除，删除结果：{}", result);
        return result;
    }



    private boolean delete(String remotePath, String filePath) throws IOException {
        boolean deleteResult = true;
        // 连接FTP服务器
        boolean loginResult = connectServer(this.ip, this.port, this.user, this.pass);
        if (loginResult) {
            try {
                // 更改工作目录
                ftpClient.changeWorkingDirectory(remotePath);
                // 打开本地的被动模式
                ftpClient.enterLocalPassiveMode();
                ftpClient.deleteFile(filePath);
            } catch (IOException e) {
                logger.error("删除文件异常!",e);
                deleteResult = false;
                e.printStackTrace();
            } finally {
                ftpClient.disconnect();
            }
        }
        return deleteResult;
    }

    /**
     *
     * @param remotePath ：表示服务器中文件夹的路径，可以在上传文件时，让路径层级更多
     * @param fileList
     * @return
     */
    private boolean uploadFileWithPath(String remotePath, List<File> fileList) throws IOException {
        boolean uploaded = true;
        FileInputStream fis = null;
        // 连接FTP服务器
        boolean loginResult = connectServer(this.ip, this.port, this.user, this.pass);
        if (loginResult) {
            try {
                // 更改工作目录
                ftpClient.changeWorkingDirectory(remotePath);
                // 设置缓冲区大小
                ftpClient.setBufferSize(1024);
                // 设置编码格式
                ftpClient.setControlEncoding("UTF-8");
                // 设置为二进制的文件类型
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                // 打开本地的被动模式
                ftpClient.enterLocalPassiveMode();
                for (File file : fileList) {
                    fis = new FileInputStream(file);
                    // 存储文件
                    ftpClient.storeFile(file.getName(), fis);
                }
            } catch (IOException e) {
                logger.error("上传文件异常!",e);
                uploaded = false;
                e.printStackTrace();
            } finally {
                fis.close();
                ftpClient.disconnect();
            }
        }
        return uploaded;
    }


    private boolean connectServer(String ip, Integer port, String user, String pwd) {
        boolean isSuccess = false;
        ftpClient = new FTPClient();
        try {
            ftpClient.connect(ip);
            isSuccess = ftpClient.login(user, pwd);
        } catch (IOException e) {
            logger.error("连接FTP服务器异常！",e);
            e.printStackTrace();
        }
        return isSuccess;
    }

    private String ip;
    private Integer port;
    private String user;
    private String pass;
    private FTPClient ftpClient;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }
}
