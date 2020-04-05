package com.zzj.recruitment.dao;

import com.zzj.recruitment.pojo.Role;
import com.zzj.recruitment.pojo.User;
import com.zzj.recruitment.vo.EnterpriseAccountListVo;
import com.zzj.recruitment.vo.EnterpriseUserInfoVo;
import com.zzj.recruitment.vo.UserDetailInfoVo;
import com.zzj.recruitment.vo.UserSearchVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    Integer insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkRepetition(@Param("value") String value, @Param("type") String type);

    User selectByUsername(String username);

    List<Role> getUserRolesById(Integer id);

    int resetPasswordByUsername(@Param("username") String username, @Param("passwordNew") String passwordNew);

    Integer saveEnterpriseUserInfo(EnterpriseUserInfoVo enterpriseUserInfoVo);

    EnterpriseUserInfoVo selectEnterpriseUserById(@Param("entUserId") Integer entUserId, @Param("companyid") Integer companyid);

    Integer updateAuthenticationByUserId(@Param("entUserId") Integer entUserId, @Param("authentication") int authentication);

    List<EnterpriseUserInfoVo> selectEnterpriseUserList(@Param("companyid")Integer companyid, @Param("userid")Integer userid);

    List<EnterpriseAccountListVo> selectEnterpriseAccount(int auth);

    List<UserDetailInfoVo> selectUserListBySearchVo(UserSearchVo userSearchVo);
}