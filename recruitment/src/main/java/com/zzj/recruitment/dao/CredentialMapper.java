package com.zzj.recruitment.dao;

import com.zzj.recruitment.pojo.Credential;

public interface CredentialMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Credential record);

    int insertSelective(Credential record);

    Credential selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Credential record);

    int updateByPrimaryKey(Credential record);
}