package com.zzj.recruitment.pojo;

import java.util.Date;

public class Cerdential {
    private Integer id;

    private String 证书名字;

    private Integer 证书父类;

    private Date createtime;

    private Date updatetime;

    public Cerdential(Integer id, String 证书名字, Integer 证书父类, Date createtime, Date updatetime) {
        this.id = id;
        this.证书名字 = 证书名字;
        this.证书父类 = 证书父类;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public Cerdential() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String get证书名字() {
        return 证书名字;
    }

    public void set证书名字(String 证书名字) {
        this.证书名字 = 证书名字 == null ? null : 证书名字.trim();
    }

    public Integer get证书父类() {
        return 证书父类;
    }

    public void set证书父类(Integer 证书父类) {
        this.证书父类 = 证书父类;
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