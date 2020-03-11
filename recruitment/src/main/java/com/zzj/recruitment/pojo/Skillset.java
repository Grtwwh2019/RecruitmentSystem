package com.zzj.recruitment.pojo;

import java.util.Date;

public class Skillset {
    private Integer id;

    private String sname;

    private Integer spositiontypeid;

    private Date createtime;

    private Date updatetime;

    public Skillset(Integer id, String sname, Integer spositiontypeid, Date createtime, Date updatetime) {
        this.id = id;
        this.sname = sname;
        this.spositiontypeid = spositiontypeid;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public Skillset() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname == null ? null : sname.trim();
    }

    public Integer getSpositiontypeid() {
        return spositiontypeid;
    }

    public void setSpositiontypeid(Integer spositiontypeid) {
        this.spositiontypeid = spositiontypeid;
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