package com.zzj.recruitment.common.constant;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-11 20:54:27
 */
public class Const {

    /**
     * 忘记密码的token前缀
     */
    public static final String FORGET_TOKEN_PREFIX = "forgetToken_";

    // redis中返回给前端的验证码的key
    public final static String CAPTCHA_ENTITY = "captcha_entity";
    // redis中的验证码的Token的key
    public final static String TOKEN_CAPTCHA_ENTITY = "token_captcha_entity";

    /**
     * Redis的过期时间
     */
    public interface RedisCacheExtime{
        int REDIS_SESSION_EXTIME = 60 * 30;//30分钟
    }

    /**
     * 权限资源的key
     */
    public static final String RIGHT_RESOURCE = "right_resource";

    /**
     * 时间的格式
     */
    public static final String DATE_STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 已登录、未登录的用户
     */
    public static final String LOGINED_USER = "logined_user";
    public static final String LOGIN_NEEDED = "login_needed";

    /**
     * 昵称、账号、邮箱、手机号、密码
     */
    public static final String NICK_NAME = "nickName";
    public static final String USER_NAME = "userName";
    public static final String EMAIL = "email";
    public static final String TELPHONE = "telephone";
    public static final String PASSWORD = "password";

    /**
     * 角色信息，可扩展
     */
    public enum Role{
        ROLE_user(1, "user"),
        ROLE_enterUser(2, "enterUser"), // 企业用户
        ROLE_enterAccount(3, "enterAccount"), // 企业账号
        ROLE_admin(4, "admin"); //

        private int code;
        private String roleStr;

        Role(int code, String roleStr) {
            this.code = code;
            this.roleStr = roleStr;
        }

        public int getCode() {
            return code;
        }

        public String getRoleStr() {
            return roleStr;
        }

    }
}
