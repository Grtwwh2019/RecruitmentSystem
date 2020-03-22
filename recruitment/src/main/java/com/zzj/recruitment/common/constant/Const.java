package com.zzj.recruitment.common.constant;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-11 20:54:27
 */
public class Const {


    /**
     * 缓存公司详情 key
     */
    public static final String COMPANY_DETAIL = "company_detail_";
    /**
     * 缓存职位详情 key
     */
    public static final String POSITION_DETAIL = "position_detail_";

    /**
     * 缓存简历信息 key
     */
    public static final String RESUME_INFO = "resumeinfo_";

    /**
     * 公告列表缓存 key
     */
    public static final String ANNOUNCELIST = "announcelist_";

    /**
     * 招聘信息的状态
     * 状态（0——封禁，1——正常）
     */
    public enum  employmentStatus {
        FORBIDDEN(0, "FORBIDDEN"),
        NORMAL(1, "NORMAL");

        Integer status;
        String desc;

        employmentStatus(Integer status, String desc) {
            this.status = status;
            this.desc = desc;
        }

        public Integer getStatus() {
            return status;
        }

        public String getDesc() {
            return desc;
        }
    }

    /**
     * 收藏职位\收藏公司的操作的操作：redis
     */
    public interface collectionCache {
        String COMPANY = "collect_company_user:";
        String POSITION = "collect_position_user:";
    }

    /**
     * 忘记密码的token前缀
     */
    public static final String FORGET_TOKEN_PREFIX = "forgetToken_";

    // redis中返回给前端的验证码的key
    public final static String CAPTCHA_ENTITY = "captcha_entity:";

    /**
     * Redis的过期时间
     */
    public interface RedisCacheExtime{
        int REDIS_SESSION_EXTIME = 60 * 30;//30分钟
        int REDIS_EXTIME_DAY = 60 * 60 * 24;//一天
    }

    /**
     * 权限资源的key
     */
    public static final String RIGHT_RESOURCE = "right_resource_";

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
    public static final String TELEPHONE = "telephone";
    public static final String PASSWORD = "password";

    /**
     * 角色信息，可扩展
     */
    public enum Role{
        ROLE_user(1, "user"),
        ROLE_enterUser(2, "enterUser"), // 企业用户
        ROLE_enterAccount(3, "enterAccount"), // 企业账号
        ROLE_admin(4, "admin"); // 管理员

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
