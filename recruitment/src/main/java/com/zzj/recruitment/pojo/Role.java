package com.zzj.recruitment.pojo;

public class Role {
    private Integer id;

    private String name;

    private String namezh;

    public Role(Integer id, String name, String namezh) {
        this.id = id;
        this.name = name;
        this.namezh = namezh;
    }

    public Role() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getNamezh() {
        return namezh;
    }

    public void setNamezh(String namezh) {
        this.namezh = namezh == null ? null : namezh.trim();
    }
}