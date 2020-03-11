package com.zzj.recruitment.pojo;

import java.util.Date;

public class ResumeIndustry {
    private Integer id;

    private Integer rid;

    private Integer iid;

    private Date createtime;

    private Date updatetime;

    public ResumeIndustry(Integer id, Integer rid, Integer iid, Date createtime, Date updatetime) {
        this.id = id;
        this.rid = rid;
        this.iid = iid;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public ResumeIndustry() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getIid() {
        return iid;
    }

    public void setIid(Integer iid) {
        this.iid = iid;
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