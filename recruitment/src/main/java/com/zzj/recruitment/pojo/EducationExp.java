package com.zzj.recruitment.pojo;

import java.util.Date;

public class EducationExp {
    private Integer id;

    private Integer resumeid;

    private Integer schoolid;

    private Integer schooltype;

    private Integer degree;

    private String major;

    private Date startdate;

    private Date enddate;

    private String schoolexper;

    private Date createtime;

    private Date updatetime;

    public EducationExp(Integer id, Integer resumeid, Integer schoolid, Integer schooltype, Integer degree, String major, Date startdate, Date enddate, String schoolexper, Date createtime, Date updatetime) {
        this.id = id;
        this.resumeid = resumeid;
        this.schoolid = schoolid;
        this.schooltype = schooltype;
        this.degree = degree;
        this.major = major;
        this.startdate = startdate;
        this.enddate = enddate;
        this.schoolexper = schoolexper;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public EducationExp() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getResumeid() {
        return resumeid;
    }

    public void setResumeid(Integer resumeid) {
        this.resumeid = resumeid;
    }

    public Integer getSchoolid() {
        return schoolid;
    }

    public void setSchoolid(Integer schoolid) {
        this.schoolid = schoolid;
    }

    public Integer getSchooltype() {
        return schooltype;
    }

    public void setSchooltype(Integer schooltype) {
        this.schooltype = schooltype;
    }

    public Integer getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major == null ? null : major.trim();
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public String getSchoolexper() {
        return schoolexper;
    }

    public void setSchoolexper(String schoolexper) {
        this.schoolexper = schoolexper == null ? null : schoolexper.trim();
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