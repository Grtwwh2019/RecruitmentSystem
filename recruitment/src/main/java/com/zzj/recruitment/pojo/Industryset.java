package com.zzj.recruitment.pojo;

import java.util.Date;

public class Industryset {
    private Integer id;

    private String iname;

    private Integer iparentid;

    private Date createtime;

    private Date updatetime;

    public Industryset(Integer id, String iname, Integer iparentid, Date createtime, Date updatetime) {
        this.id = id;
        this.iname = iname;
        this.iparentid = iparentid;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public Industryset() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIname() {
        return iname;
    }

    public void setIname(String iname) {
        this.iname = iname == null ? null : iname.trim();
    }

    public Integer getIparentid() {
        return iparentid;
    }

    public void setIparentid(Integer iparentid) {
        this.iparentid = iparentid;
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