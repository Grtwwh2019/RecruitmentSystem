package com.zzj.recruitment.pojo;

import java.util.List;

public class Right {
    private Integer id;

    private String url;

    private String path;

    private String component;

    private String name;

    private String iconcls;

    private Boolean keepalive;

    private Boolean requireauth;

    private Integer parentid;

    private Boolean enabled;

    /**
     * 每一个权限资源都有相对应的角色
     */
    private List<Role> roles;

    public Right(Integer id, String url, String path, String component, String name, String iconcls, Boolean keepalive, Boolean requireauth, Integer parentid, Boolean enabled) {
        this.id = id;
        this.url = url;
        this.path = path;
        this.component = component;
        this.name = name;
        this.iconcls = iconcls;
        this.keepalive = keepalive;
        this.requireauth = requireauth;
        this.parentid = parentid;
        this.enabled = enabled;
    }

    public Right() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component == null ? null : component.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIconcls() {
        return iconcls;
    }

    public void setIconcls(String iconcls) {
        this.iconcls = iconcls == null ? null : iconcls.trim();
    }

    public Boolean getKeepalive() {
        return keepalive;
    }

    public void setKeepalive(Boolean keepalive) {
        this.keepalive = keepalive;
    }

    public Boolean getRequireauth() {
        return requireauth;
    }

    public void setRequireauth(Boolean requireauth) {
        this.requireauth = requireauth;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}