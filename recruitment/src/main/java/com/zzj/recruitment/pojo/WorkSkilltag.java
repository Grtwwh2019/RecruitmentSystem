package com.zzj.recruitment.pojo;

import java.util.Date;

public class WorkSkilltag {
    private Integer id;

    private Integer wid;

    private Integer sid;

    private Date createtime;

    private Date updatetime;

    public WorkSkilltag(Integer id, Integer wid, Integer sid, Date createtime, Date updatetime) {
        this.id = id;
        this.wid = wid;
        this.sid = sid;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public WorkSkilltag() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWid() {
        return wid;
    }

    public void setWid(Integer wid) {
        this.wid = wid;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
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