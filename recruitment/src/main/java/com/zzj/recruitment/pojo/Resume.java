package com.zzj.recruitment.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class Resume {
    private Integer id;

    private Integer ruserid;

    private String headermask;

    private String realname;

    private Integer gender;

    private Date birthday;

    private Integer currentstatus;

    private String advantage;

    private String socialpage;

    private String otherattachment;

    private String resumeattachment;

    private Integer expectpositionid;

    private BigDecimal lowsalary;

    private BigDecimal highsalary;

    private String cityid;

    private Date createtime;

    private Date updatetime;

    public Resume(Integer id, Integer ruserid, String headermask, String realname, Integer gender, Date birthday, Integer currentstatus, String advantage, String socialpage, String otherattachment, String resumeattachment, Integer expectpositionid, BigDecimal lowsalary, BigDecimal highsalary, String cityid, Date createtime, Date updatetime) {
        this.id = id;
        this.ruserid = ruserid;
        this.headermask = headermask;
        this.realname = realname;
        this.gender = gender;
        this.birthday = birthday;
        this.currentstatus = currentstatus;
        this.advantage = advantage;
        this.socialpage = socialpage;
        this.otherattachment = otherattachment;
        this.resumeattachment = resumeattachment;
        this.expectpositionid = expectpositionid;
        this.lowsalary = lowsalary;
        this.highsalary = highsalary;
        this.cityid = cityid;
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

    public Integer getRuserid() {
        return ruserid;
    }

    public void setRuserid(Integer ruserid) {
        this.ruserid = ruserid;
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

    public Integer getExpectpositionid() {
        return expectpositionid;
    }

    public void setExpectpositionid(Integer expectpositionid) {
        this.expectpositionid = expectpositionid;
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

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid == null ? null : cityid.trim();
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