package com.zzj.recruitment.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-19 17:04:18
 */
public interface IFileService {

    String upload(MultipartFile file, String path, String remotePath);

    Boolean delete(String fileName, String remotePath);

}
