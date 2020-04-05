package com.zzj.recruitment.pojo;

import java.util.Date;

public class ResumeDelivery {
    private Integer id;

    private Integer resumeId;

    private Integer employmentId;

    private Date createtime;

    private Date updatetime;

    public ResumeDelivery(Integer id, Integer resumeId, Integer employmentId, Date createtime, Date updatetime) {
        this.id = id;
        this.resumeId = resumeId;
        this.employmentId = employmentId;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public ResumeDelivery() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getResumeId() {
        return resumeId;
    }

    public void setResumeId(Integer resumeId) {
        this.resumeId = resumeId;
    }

    public Integer getEmploymentId() {
        return employmentId;
    }

    public void setEmploymentId(Integer employmentId) {
        this.employmentId = employmentId;
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