package com.zzj.recruitment.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class Company {
    private Integer id;

    private String logo;

    private String cname;

    private Integer productid;

    private String cintroduction;

    private Integer financing;

    private Integer industryid;

    private Integer csize;

    private Integer ccity;

    private String fullname;

    private String representative;

    private BigDecimal assets;

    private Date establishtime;

    private String photo;

    private Date createtime;

    private Date updatetime;

    public Company(Integer id, String logo, String cname, Integer productid, String cintroduction, Integer financing, Integer industryid, Integer csize, Integer ccity, String fullname, String representative, BigDecimal assets, Date establishtime, String photo, Date createtime, Date updatetime) {
        this.id = id;
        this.logo = logo;
        this.cname = cname;
        this.productid = productid;
        this.cintroduction = cintroduction;
        this.financing = financing;
        this.industryid = industryid;
        this.csize = csize;
        this.ccity = ccity;
        this.fullname = fullname;
        this.representative = representative;
        this.assets = assets;
        this.establishtime = establishtime;
        this.photo = photo;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public Company() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname == null ? null : cname.trim();
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public String getCintroduction() {
        return cintroduction;
    }

    public void setCintroduction(String cintroduction) {
        this.cintroduction = cintroduction == null ? null : cintroduction.trim();
    }

    public Integer getFinancing() {
        return financing;
    }

    public void setFinancing(Integer financing) {
        this.financing = financing;
    }

    public Integer getIndustryid() {
        return industryid;
    }

    public void setIndustryid(Integer industryid) {
        this.industryid = industryid;
    }

    public Integer getCsize() {
        return csize;
    }

    public void setCsize(Integer csize) {
        this.csize = csize;
    }

    public Integer getCcity() {
        return ccity;
    }

    public void setCcity(Integer ccity) {
        this.ccity = ccity;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname == null ? null : fullname.trim();
    }

    public String getRepresentative() {
        return representative;
    }

    public void setRepresentative(String representative) {
        this.representative = representative == null ? null : representative.trim();
    }

    public BigDecimal getAssets() {
        return assets;
    }

    public void setAssets(BigDecimal assets) {
        this.assets = assets;
    }

    public Date getEstablishtime() {
        return establishtime;
    }

    public void setEstablishtime(Date establishtime) {
        this.establishtime = establishtime;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
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