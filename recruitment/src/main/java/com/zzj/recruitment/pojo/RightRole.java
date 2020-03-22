package com.zzj.recruitment.pojo;

public class RightRole {
    private Integer id;

    private Integer rightId;

    private Integer roleId;

    public RightRole(Integer id, Integer rightId, Integer roleId) {
        this.id = id;
        this.rightId = rightId;
        this.roleId = roleId;
    }

    public RightRole() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRightId() {
        return rightId;
    }

    public void setRightId(Integer rightId) {
        this.rightId = rightId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}