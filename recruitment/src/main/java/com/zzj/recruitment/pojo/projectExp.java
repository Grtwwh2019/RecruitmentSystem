package com.zzj.recruitment.pojo;

import java.util.Date;

public class projectExp {
    private Integer id;

    private Integer resuid;

    private String name;

    private String role;

    private String url;

    private Date starttime;

    private Date endtime;

    private String description;

    private Date createtime;

    private Date updatetime;

    public projectExp(Integer id, Integer resuid, String name, String role, String url, Date starttime, Date endtime, String description, Date createtime, Date updatetime) {
        this.id = id;
        this.resuid = resuid;
        this.name = name;
        this.role = role;
        this.url = url;
        this.starttime = starttime;
        this.endtime = endtime;
        this.description = description;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public projectExp() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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