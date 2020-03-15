package com.zzj.recruitment.common;

import com.zzj.recruitment.common.constant.ResponseCode;
import com.zzj.recruitment.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-11 16:41:14
 */
@Slf4j
public class ServerResponse<T> implements Serializable {

    private int status;
    private String msg;
    private T data;

    /**
     *
     * 如果第二个参数是String类型，会调用status，msg的构造函数
     * 如果想要返回的数据是String类型，则需要重写对外的public方法来规避该问题。
     *
     */


    private ServerResponse(int status) {
        this.status = status;
    }

    private ServerResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ServerResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private ServerResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }


    /**
     * 封装一个函数，对外提供 判断响应是否成功的方法
     */
    public boolean isSuccess() {
        return this.status == ResponseCode.SUCCESS.getCode() ? true : false;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    /**
     * 对外开放返回的方法
     * 泛型方法
     * <T> 是告诉编译器这是一个泛型方法
     * ServerResponse<T> 代表是该方法的返回值
     */

    public static <T> ServerResponse<T> createResponseBySuccess() {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
    }

    /**
     * 区分数据是String类型的情况
     * 当调用该方法时，证明是回传一个msg
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> ServerResponse<T> createResponseBySuccessMsg(String msg) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg);
    }
    public static <T> ServerResponse<T> createResponseBySuccess(T data) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), data);
    }
    public static <T> ServerResponse<T> createResponseBySuccess(String msg, T data) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg, data);
    }
    public static <T> ServerResponse<T> createResponseByError() {
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }
    public static <T> ServerResponse<T> createResponseByErrorMsg(String errorMsg) {
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(), errorMsg);
    }
    public static <T> ServerResponse<T> createResponseByErrorCustom(int errorCode, String errorMsg) {
        return new ServerResponse<T>(errorCode, errorMsg);
    }

    /**
     * 往前端写出Json的封装工具类
     * @param response
     * @param data
     */
    public static void createRenderJsonBySuccess(HttpServletResponse response, Object data) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(200);

            PrintWriter writer = response.getWriter();
//            writer.write(JSONUtil.toJsonStr(new JSONObject(ApiResponse.ofStatus(status, data), false)));
            writer.write(JsonUtil.obj2String(ServerResponse.createResponseBySuccess(data)));
        } catch (IOException e) {
            log.error("Response写出JSON异常，", e);
        }
    }

    public static void createRenderJsonByError(HttpServletResponse response, String msg) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(200);

            PrintWriter writer = response.getWriter();
//            writer.write(JSONUtil.toJsonStr(new JSONObject(ApiResponse.ofStatus(status, data), false)));
            writer.write(msg);
        } catch (IOException e) {
            log.error("Response写出JSON异常，", e);
        }
    }


}
