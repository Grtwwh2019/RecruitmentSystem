package com.zzj.recruitment.pojo;

import java.util.Date;

public class PositionTypeSet {
    private Integer id;

    private String ptname;

    private Integer pparentid;

    private Date createtime;

    private Date updatetime;

    public PositionTypeSet(Integer id, String ptname, Integer pparentid, Date createtime, Date updatetime) {
        this.id = id;
        this.ptname = ptname;
        this.pparentid = pparentid;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public PositionTypeSet() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPtname() {
        return ptname;
    }

    public void setPtname(String ptname) {
        this.ptname = ptname == null ? null : ptname.trim();
    }

    public Integer getPparentid() {
        return pparentid;
    }

    public void setPparentid(Integer pparentid) {
        this.pparentid = pparentid;
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