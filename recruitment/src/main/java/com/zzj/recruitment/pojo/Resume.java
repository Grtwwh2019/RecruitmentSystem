package com.zzj.recruitment.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class Resume {
    private Integer id;

    private String headermask;

    private String realname;

    private Integer gender;

    private Date birthday;

    private Integer currentstatus;

    private String advantage;

    private String socialpage;

    private String otherattachment;

    private String resumeattachment;

    private Integer positionid;

    private BigDecimal lowsalary;

    private BigDecimal highsalary;

    private Integer industryiid;

    private Integer cityid;

    private Integer qcid;

    private Date createtime;

    private Date updatetime;

    public Resume(Integer id, String headermask, String realname, Integer gender, Date birthday, Integer currentstatus, String advantage, String socialpage, String otherattachment, String resumeattachment, Integer positionid, BigDecimal lowsalary, BigDecimal highsalary, Integer industryiid, Integer cityid, Integer qcid, Date createtime, Date updatetime) {
        this.id = id;
        this.headermask = headermask;
        this.realname = realname;
        this.gender = gender;
        this.birthday = birthday;
        this.currentstatus = currentstatus;
        this.advantage = advantage;
        this.socialpage = socialpage;
        this.otherattachment = otherattachment;
        this.resumeattachment = resumeattachment;
        this.positionid = positionid;
        this.lowsalary = lowsalary;
        this.highsalary = highsalary;
        this.industryiid = industryiid;
        this.cityid = cityid;
        this.qcid = qcid;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public Resume() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHeadermask() {
        return headermask;
    }

    public void setHeadermask(String headermask) {
        this.headermask = headermask == null ? null : headermask.trim();
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getCurrentstatus() {
        return currentstatus;
    }

    public void setCurrentstatus(Integer currentstatus) {
        this.currentstatus = currentstatus;
    }

    public String getAdvantage() {
        return advantage;
    }

    public void setAdvantage(String advantage) {
        this.advantage = advantage == null ? null : advantage.trim();
    }

    public String getSocialpage() {
        return socialpage;
    }

    public void setSocialpage(String socialpage) {
        this.socialpage = socialpage == null ? null : socialpage.trim();
    }

    public String getOtherattachment() {
        return otherattachment;
    }

    public void setOtherattachment(String otherattachment) {
        this.otherattachment = otherattachment == null ? null : otherattachment.trim();
    }

    public String getResumeattachment() {
        return resumeattachment;
    }

    public void setResumeattachment(String resumeattachment) {
        this.resumeattachment = resumeattachment == null ? null : resumeattachment.trim();
    }

    public Integer getPositionid() {
        return positionid;
    }

    public void setPositionid(Integer positionid) {
        this.positionid = positionid;
    }

    public BigDecimal getLowsalary() {
        return lowsalary;
    }

    public void setLowsalary(BigDecimal lowsalary) {
        this.lowsalary = lowsalary;
    }

    public BigDecimal getHighsalary() {
        return highsalary;
    }

    public void setHighsalary(BigDecimal highsalary) {
        this.highsalary = highsalary;
    }

    public Integer getIndustryiid() {
        return industryiid;
    }

    public void setIndustryiid(Integer industryiid) {
        this.industryiid = industryiid;
    }

    public Integer getCityid() {
        return cityid;
    }

    public void setCityid(Integer cityid) {
        this.cityid = cityid;
    }

    public Integer getQcid() {
        return qcid;
    }

    public void setQcid(Integer qcid) {
        this.qcid = qcid;
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