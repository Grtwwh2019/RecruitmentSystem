package com.zzj.recruitment.common.constant;

import java.security.IdentityScope;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-11 20:54:27
 */
public class Const {

    /**
     * 缓存中所有资格证书
     */
    public static final String CITY_SET = "city_set";

    /**
     * 缓存中所有资格证书
     */
    public static final String CERTIFICATION_SET = "certification_set";

    /**
     * 缓存中所有职位类型
     */
    public static final String POSITIONTYPE_SET = "positiontype_set";

    /**
     * 缓存中所有行业类型
     */
    public static final String INDUSTRY_SET = "industry_set";

    /**
     * ftp的文件夹：img，attachment
     */
    public interface ftp_folder {
        String IMG = "img";
        String ATTACHMENT = "attachment";
    }

    /**
     * 缓存中被禁用的用户原来的认证情况
     */
    public static final String FORBID_USER_ORG_AUTH = "forbid_user_org_auth_";

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
     * 公告详细信息缓存 key
     */
    public static final String ANNOUNCE_DETAIL = "announce_detail_";

    /**
     * 公告列表缓存 key
     */
    public static final String ANNOUNCE_LIST = "announcelist_";

    /**
     * 招聘信息的状态
     * 状态（0——封禁，1——正常）
     */
    public enum  employmentStatus {
        FORBIDDEN(0, "FORBIDDEN"),
        NORMAL(1, "NORMAL");

        int status;
        String desc;

        employmentStatus(int status, String desc) {
            this.status = status;
            this.desc = desc;
        }

        public int getStatus() {
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
        int REDIS_SESSION_EXTIME = 60 * 60 * 24;//30分钟
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
     * 用户认证情况
     * 是否已认证，1---是，2---否，3---拒绝 4---企业用户待认证 ,5---企业账号待认证（求职者注册默认为2，企业账号注册默认为5）
     */
    public enum authentication{
        FORBIDED(0, "forbided"),
        PASS(1, "pass"),
        NORMAL(2, "normal"),
        DENY(3, "deny"),
        TO_BE_ENTUSER(4, "to_be_entuser"),
        TO_BE_ENTACCOUNT(5, "to_be_entaccount");

        private int code;
        private String desc;

        authentication(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }
    }

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
