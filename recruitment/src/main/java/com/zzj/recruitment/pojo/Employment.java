package com.zzj.recruitment.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class Employment {
    private Integer id;

    private String ename;

    private Integer etypeid;

    private Integer ecityid;

    private BigDecimal esalary;

    private Integer ecompanyid;

    private String eresponsibility;

    private String erequirement;

    private Integer epublisher;

    private Integer eupdater;

    private Integer estatus;

    private Date createtime;

    private Date updatetime;

    public Employment(Integer id, String ename, Integer etypeid, Integer ecityid, BigDecimal esalary, Integer ecompanyid, String eresponsibility, String erequirement, Integer epublisher, Integer eupdater, Integer estatus, Date createtime, Date updatetime) {
        this.id = id;
        this.ename = ename;
        this.etypeid = etypeid;
        this.ecityid = ecityid;
        this.esalary = esalary;
        this.ecompanyid = ecompanyid;
        this.eresponsibility = eresponsibility;
        this.erequirement = erequirement;
        this.epublisher = epublisher;
        this.eupdater = eupdater;
        this.estatus = estatus;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public Employment() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename == null ? null : ename.trim();
    }

    public Integer getEtypeid() {
        return etypeid;
    }

    public void setEtypeid(Integer etypeid) {
        this.etypeid = etypeid;
    }

    public Integer getEcityid() {
        return ecityid;
    }

    public void setEcityid(Integer ecityid) {
        this.ecityid = ecityid;
    }

    public BigDecimal getEsalary() {
        return esalary;
    }

    public void setEsalary(BigDecimal esalary) {
        this.esalary = esalary;
    }

    public Integer getEcompanyid() {
        return ecompanyid;
    }

    public void setEcompanyid(Integer ecompanyid) {
        this.ecompanyid = ecompanyid;
    }

    public String getEresponsibility() {
        return eresponsibility;
    }

    public void setEresponsibility(String eresponsibility) {
        this.eresponsibility = eresponsibility == null ? null : eresponsibility.trim();
    }

    public String getErequirement() {
        return erequirement;
    }

    public void setErequirement(String erequirement) {
        this.erequirement = erequirement == null ? null : erequirement.trim();
    }

    public Integer getEpublisher() {
        return epublisher;
    }

    public void setEpublisher(Integer epublisher) {
        this.epublisher = epublisher;
    }

    public Integer getEupdater() {
        return eupdater;
    }

    public void setEupdater(Integer eupdater) {
        this.eupdater = eupdater;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
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