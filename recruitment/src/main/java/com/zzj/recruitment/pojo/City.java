package com.zzj.recruitment.pojo;

public class City {
    private String id;

    private String cityname;

    private String parentid;

    private Integer level;

    private String namepath;

    public City(String id, String cityname, String parentid, Integer level, String namepath) {
        this.id = id;
        this.cityname = cityname;
        this.parentid = parentid;
        this.level = level;
        this.namepath = namepath;
    }

    public City() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname == null ? null : cityname.trim();
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid == null ? null : parentid.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getNamepath() {
        return namepath;
    }

    public void setNamepath(String namepath) {
        this.namepath = namepath == null ? null : namepath.trim();
    }
}