package com.zzj.recruitment.pojo;

import java.util.Date;

public class Credential {
    private Integer id;

    private String cname;

    private Integer cparent;

    private Date createtime;

    private Date updatetime;

    public Credential(Integer id, String cname, Integer cparent, Date createtime, Date updatetime) {
        this.id = id;
        this.cname = cname;
        this.cparent = cparent;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public Credential() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname == null ? null : cname.trim();
    }

    public Integer getCparent() {
        return cparent;
    }

    public void setCparent(Integer cparent) {
        this.cparent = cparent;
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