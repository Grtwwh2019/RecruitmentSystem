package com.zzj.recruitment.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

public class Company {
    private Integer id;

    private String logo;

    @NotBlank(message = "公司名称不能为空")
    private String cname;

    private Integer productid;

    @NotBlank(message = "公司简介不能为空")
    private String cintroduction;

    @NotNull(message = "融资阶段不能为空！")
    private Integer financing;

    @NotNull(message = "所属行业不能为空！")
    private Integer industryid;

    @NotNull(message = "公司规模不能为空！")
    private Integer csize;

    @NotNull(message = "公司所在城市不能为空！")
    private String ccity;

    @NotNull(message = "公司全称不能为空")
    private String fullname;

    @NotNull(message = "法人代表不能为空")
    private String representative;

    @NotNull(message = "公司资产不能为空")
    private BigDecimal assets;

    @NotNull(message = "公司成立时间不能为空")
    @JsonFormat(locale = "zh", pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date establishtime;

    private String photo;

    private Date createtime;

    private Date updatetime;

    public Company(Integer id, String logo, String cname, Integer productid, String cintroduction, Integer financing, Integer industryid, Integer csize, String ccity, String fullname, String representative, BigDecimal assets, Date establishtime, String photo, Date createtime, Date updatetime) {
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

    public String getCcity() {
        return ccity;
    }

    public void setCcity(String ccity) {
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