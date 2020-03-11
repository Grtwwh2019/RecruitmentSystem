package com.zzj.recruitment.pojo;

import java.util.Date;

public class ResumeCertificate {
    private Integer id;

    private Integer rid;

    private Integer cid;

    private Date createtime;

    private Date updatetime;

    public ResumeCertificate(Integer id, Integer rid, Integer cid, Date createtime, Date updatetime) {
        this.id = id;
        this.rid = rid;
        this.cid = cid;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public ResumeCertificate() {
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

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
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