package com.zzj.recruitment.pojo;

import java.util.Date;

public class Collection {
    private Integer id;

    private Integer cpid;

    private Integer ccid;

    private Integer cuserid;

    private Date createtime;

    private Date updatetime;

    public Collection(Integer id, Integer cpid, Integer ccid, Integer cuserid, Date createtime, Date updatetime) {
        this.id = id;
        this.cpid = cpid;
        this.ccid = ccid;
        this.cuserid = cuserid;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public Collection() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCpid() {
        return cpid;
    }

    public void setCpid(Integer cpid) {
        this.cpid = cpid;
    }

    public Integer getCcid() {
        return ccid;
    }

    public void setCcid(Integer ccid) {
        this.ccid = ccid;
    }

    public Integer getCuserid() {
        return cuserid;
    }

    public void setCuserid(Integer cuserid) {
        this.cuserid = cuserid;
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