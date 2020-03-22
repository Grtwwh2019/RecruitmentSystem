package com.zzj.recruitment.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements
//        UserDetails,
        Serializable {
    private Integer id;

    private String nickname;

    private String telephone;

    private String email;

    private String username;

    private String password;

    private String license;

    private Integer companyid;

    private Integer empno;

    private String cardphoto;

    private String enterprisemail;

    private Integer authentication;

    private Date createtime;

    private Date updatetime;

    List<Role> roles;


    public User(Integer id, String nickname, String telephone, String email, String username, String password, String license, Integer companyid, Integer empno, String cardphoto, String enterprisemail,Integer authentication, Date createtime, Date updatetime) {
        this.id = id;
        this.nickname = nickname;
        this.telephone = telephone;
        this.email = email;
        this.username = username;
        this.password = password;
        this.license = license;
        this.companyid = companyid;
        this.empno = empno;
        this.cardphoto = cardphoto;
        this.enterprisemail = enterprisemail;
        this.authentication = authentication;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public User() {
        super();
    }

    public Integer getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Integer authentication) {
        this.authentication = authentication;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license == null ? null : license.trim();
    }

    public Integer getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }

    public Integer getEmpno() {
        return empno;
    }

    public void setEmpno(Integer empno) {
        this.empno = empno;
    }

    public String getCardphoto() {
        return cardphoto;
    }

    public void setCardphoto(String cardphoto) {
        this.cardphoto = cardphoto == null ? null : cardphoto.trim();
    }

    public String getEnterprisemail() {
        return enterprisemail;
    }

    public void setEnterprisemail(String enterprisemail) {
        this.enterprisemail = enterprisemail == null ? null : enterprisemail.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

   /* *//**
     * 返回用户的所有角色
     * @return
     *//*
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    *//**
     * 账户是否未过期
     * @return
     *//*
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    *//**
     * 账户是否未锁定
     * @return
     *//*
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    *//**
     * 密码是否未过期
     * @return
     *//*
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    *//**
     * 是否可用
     * @return
     *//*
    @Override
    public boolean isEnabled() {
        return true;
    }*/
}