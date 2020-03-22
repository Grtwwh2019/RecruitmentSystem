package com.zzj.recruitment.pojo;

import java.util.Date;

public class workExp {
    private Integer id;

    private Integer resuid;

    private String cname;

    private Integer industryid;

    private String department;

    private String workcontent;

    private String position;

    private Integer wpositiontypeid;

    private Date startdate;

    private Date enddate;

    private Date createtime;

    private Date updatetime;

    public workExp(Integer id, Integer resuid, String cname, Integer industryid, String department, Integer wskillid, String workcontent, String position, Integer wpositiontypeid, Date startdate, Date enddate, Date createtime, Date updatetime) {
        this.id = id;
        this.resuid = resuid;
        this.cname = cname;
        this.industryid = industryid;
        this.department = department;
        this.workcontent = workcontent;
        this.position = position;
        this.wpositiontypeid = wpositiontypeid;
        this.startdate = startdate;
        this.enddate = enddate;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public workExp() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getResuid() {
        return resuid;
    }

    public void setResuid(Integer resuid) {
        this.resuid = resuid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname == null ? null : cname.trim();
    }

    public Integer getIndustryid() {
        return industryid;
    }

    public void setIndustryid(Integer industryid) {
        this.industryid = industryid;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public String getWorkcontent() {
        return workcontent;
    }

    public void setWorkcontent(String workcontent) {
        this.workcontent = workcontent == null ? null : workcontent.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public Integer getWpositiontypeid() {
        return wpositiontypeid;
    }

    public void setWpositiontypeid(Integer wpositiontypeid) {
        this.wpositiontypeid = wpositiontypeid;
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