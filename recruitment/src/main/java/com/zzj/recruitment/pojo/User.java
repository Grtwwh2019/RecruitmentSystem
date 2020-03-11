package com.zzj.recruitment.pojo;

import java.util.Date;

public class User {
    private Integer id;

    private String nickname;

    private String telephone;

    private String email;

    private Integer role;

    private String username;

    private String password;

    private String license;

    private Integer companyid;

    private Integer empno;

    private String cardphoto;

    private String enterprisemail;

    private Date createtime;

    private Date updatetime;

    public User(Integer id, String nickname, String telephone, String email, Integer role, String username, String password, String license, Integer companyid, Integer empno, String cardphoto, String enterprisemail, Date createtime, Date updatetime) {
        this.id = id;
        this.nickname = nickname;
        this.telephone = telephone;
        this.email = email;
        this.role = role;
        this.username = username;
        this.password = password;
        this.license = license;
        this.companyid = companyid;
        this.empno = empno;
        this.cardphoto = cardphoto;
        this.enterprisemail = enterprisemail;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public User() {
        super();
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

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
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
}