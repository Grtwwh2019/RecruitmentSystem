package com.zzj.recruitment.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class Announce {

    @Min(value = 1, message = "id参数出错。")
    private Integer id;

    @NotBlank(message = "标题不能为空")
    private String title;

    @NotBlank(message = "内容不能为空")
    private String content;

    @NotNull(message = "类型不能为空!")
    @Min(value = 0, message = "类型参数出错。")
    private Integer type;

    private Integer sender;

    @NotNull(message = "接受者不能为空!")
    @Min(value = 1, message = "接受者参数出错。")
    private Integer receiver;

    private Integer status;

    @JsonFormat(locale = "zh", pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createtime;

    @JsonFormat(locale = "zh", pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updatetime;

    public Announce(Integer id, String title, String content, Integer type, Integer sender, Integer receiver, Integer status, Date createtime, Date updatetime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.type = type;
        this.sender = sender;
        this.receiver = receiver;
        this.status = status;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public Announce() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSender() {
        return sender;
    }

    public void setSender(Integer sender) {
        this.sender = sender;
    }

    public Integer getReceiver() {
        return receiver;
    }

    public void setReceiver(Integer receiver) {
        this.receiver = receiver;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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