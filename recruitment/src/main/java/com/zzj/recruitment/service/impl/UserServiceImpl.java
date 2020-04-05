package com.zzj.recruitment.service.impl;

import com.zzj.recruitment.common.ServerResponse;
import com.zzj.recruitment.common.constant.Const;
import com.zzj.recruitment.dao.*;
import com.zzj.recruitment.pojo.ResumeDelivery;
import com.zzj.recruitment.pojo.User;
import com.zzj.recruitment.pojo.UserRole;
import com.zzj.recruitment.service.IUserService;
import com.zzj.recruitment.util.EncryptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-11 17:45:44
 */
@Service("userService")
public class UserServiceImpl implements IUserService
//        , UserDetailsService
{

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    ResumeMapper resumeMapper;

    @Autowired
    ResumeDeliveryMapper resumeDeliveryMapper;

    @Autowired
    EmploymentMapper employmentMapper;

    @Autowired
    RedisTemplate redisTemplate;

/*
    *//**
     * 根据用户名查询用户对象，然后返回
     * User的pojo类就是UserDetails的一个实例
     *
     * param username：登录的用户名
     * @return
     * @throws UsernameNotFoundException
     *//*
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在！");
        }
        user.setRoles(userMapper.getUserRolesById(user.getId()));
        return user;
    }*/

    @Override
    @Transactional
    public ServerResponse<String> register(User user, Integer role) {
        // 1.校验昵称、账号、邮箱、手机号、密码是否符合要求
        ServerResponse<String> validResponse = this.checkValid(user.getNickname(), Const.NICK_NAME);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        validResponse = this.checkValid(user.getUsername(), Const.USER_NAME);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        validResponse = this.checkValid(user.getEmail(), Const.EMAIL);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        validResponse = this.checkValid(user.getTelephone(), Const.TELEPHONE);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        validResponse = this.checkValid(user.getPassword(), Const.PASSWORD);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        // 2.将密码加密后存储
        String EncryPassword = EncryptionUtil.encrypt(user.getPassword());
        user.setPassword(EncryPassword);
        Integer newUserId = userMapper.insertSelective(user);
        if (newUserId != null) {
            if (role != null) {
                if (role == 4) { // 企业账号
                    // todo 校验营业执照，公司ID，不能为空，格式要正确
                }
                UserRole userRole = new UserRole();
                userRole.setRoleId(role);
                userRole.setUserId(newUserId);
                int result = userRoleMapper.insertSelective(userRole);
                if (result > 0) {
                    // todo 只要创建了用户就自动新建一个在线简历
                    return ServerResponse.createResponseBySuccessMsg("注册成功！");
                }
            }
        }
        return ServerResponse.createResponseByErrorMsg("注册失败，参数错误！");
    }

    @Override
    public ServerResponse<User> userlogin(String username, String password) {
        ServerResponse<String> response = this.checkValid(username, Const.USER_NAME);
        if (response.isSuccess()) {
            return ServerResponse.createResponseByErrorMsg("用户不存在");
        }

        User user = userMapper.selectByUsername(username);
        // 先判断其账号的认证情况，是否被禁用
        if (user.getAuthentication() != Const.authentication.FORBIDED.getCode()) {
            // 判断登录密码是否原始加密的密码
            if (EncryptionUtil.match(password, user.getPassword())) {
                user.setPassword(null);
                return ServerResponse.createResponseBySuccess("登录成功！", user);
            }
            return ServerResponse.createResponseByErrorMsg("登录失败，密码错误！");
        }
        return ServerResponse.createResponseByErrorMsg("登录失败，账号已被禁用！");
    }

    @Override
    public ServerResponse<User> updateUserInfo(User user, User currentUser) {
        int result = userMapper.checkRepetition(user.getEmail(), Const.EMAIL);
        if (result > 0) {
            return ServerResponse.createResponseByErrorMsg("更新失败，邮箱已存在");
        }
        result = userMapper.checkRepetition(user.getTelephone(), Const.TELEPHONE);
        if (result > 0) {
            return ServerResponse.createResponseByErrorMsg("更新失败，手机已存在");
        }
        currentUser.setNickname(user.getNickname() != null ? user.getNickname() : currentUser.getNickname());
        currentUser.setEmail(user.getEmail() != null ? user.getEmail() : currentUser.getEmail());
        currentUser.setTelephone(user.getTelephone() != null ? user.getTelephone() : currentUser.getTelephone());
        User updateUser = user;
        updateUser.setId(currentUser.getId());
        result = userMapper.updateByPrimaryKeySelective(updateUser);
        if (result > 0) {
            return ServerResponse.createResponseBySuccess("更新成功！", updateUser);
        }
        return ServerResponse.createResponseByErrorMsg("更新失败，未知错误！");
    }


    /**
     * 根据type 发送验证码
     *
     * @param username
     * @param type
     * @return
     */
    @Override
    public ServerResponse<String> selectVerifyType(String username, String type) {
        User user = userMapper.selectByUsername(username);
        String result = null;
        switch (type.trim()) {
            case Const.TELEPHONE:
                // todo 获取手机号，发送验证码的逻辑
                result = user.getTelephone();
                break;
            case Const.EMAIL:
                result = user.getEmail();
                break;
        }
        if (StringUtils.isNotBlank(result)) {
            return ServerResponse.createResponseBySuccessMsg("请输入您的" + type + "：");
        } else {
            return ServerResponse.createResponseBySuccessMsg("您不存在" + type + "，请换一种找回方式！");
        }
    }

    /**
     * 检查输入的验证码是否正确
     * @param username
     * @param verificationCode
     * @return
     */
    @Override
    public ServerResponse<String> checkCode(String username, String verificationCode, String type) {
        // todo 从缓存中获取验证码，和该验证码进行比对，如果相等，返回一个token（UUID）并放在缓存中
        User user = userMapper.selectByUsername(username);
        boolean result = false;
        switch (type.trim()) {
            case Const.TELEPHONE:
                if (StringUtils.equals(user.getTelephone(), verificationCode)) {
                    result = true;
                }
                break;
            case Const.EMAIL:
                if (StringUtils.equals(user.getEmail(), verificationCode)) {
                    result = true;
                }
                break;
        }
        if (result) {
            String forget_token = UUID.randomUUID().toString();
            redisTemplate.opsForValue().set(Const.FORGET_TOKEN_PREFIX + username, forget_token, 60 * 5, TimeUnit.SECONDS);
            return ServerResponse.createResponseBySuccess("验证成功！", forget_token);
        }

        return ServerResponse.createResponseByErrorMsg("验证失败，请重新验证！");
    }


    /**
     * 重新设置密码
     *
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    @Override
    @Transactional
    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {
        if (StringUtils.equals(forgetToken, (String) (redisTemplate.opsForValue().get(Const.FORGET_TOKEN_PREFIX + username)))) {
            redisTemplate.delete(Const.FORGET_TOKEN_PREFIX + username);
            // 重置密码，要将密码加密
            String encryptPassword = EncryptionUtil.encrypt(passwordNew);
            int result = userMapper.resetPasswordByUsername(username, encryptPassword);
            if (result > 0) {
                return ServerResponse.createResponseBySuccessMsg("重置密码成功！");
            }
        }
        return ServerResponse.createResponseByErrorMsg("重置密码失败，token失效！");
    }


    /**
     * 登录状态下的重置密码
     * @param username
     * @param passwordOld
     * @param passwordNew
     * @return
     */
    @Override
    public ServerResponse<String> resetPassword(String username, String passwordOld, String passwordNew) {
        User user = userMapper.selectByUsername(username);
        // 要使用加密工具类来检测
        if (EncryptionUtil.match(passwordOld, user.getPassword())) {
            ServerResponse<String> response = this.checkValid(passwordNew, Const.PASSWORD);
            if (!response.isSuccess()) {
                return ServerResponse.createResponseByErrorMsg("修改失败，密码格式不符合！");
            }
            // 将新密码加密
            String encryptPassword = EncryptionUtil.encrypt(passwordNew);
            int result = userMapper.resetPasswordByUsername(username, encryptPassword);
            if (result > 0) {
                return ServerResponse.createResponseBySuccessMsg("修改密码成功！");
            }
        }
        return ServerResponse.createResponseByErrorMsg("修改密码失败，旧密码输入错误！");
    }

    /**
     * 昵称、账号、邮箱、手机号、密码的校验
     */
    public ServerResponse<String> checkValid(String value, String type) {
        boolean flag = true;
        // isEmpty：如果有空格，则不为空字符串
        // isBlank：空格也当作空字符串
        String typec = type.trim();
        if (StringUtils.isNotBlank(typec)) {
            //todo 优化 ——> 减少重复的代码
            switch (typec) {
                case Const.NICK_NAME:
                    // 昵称
                    if (StringUtils.isBlank(value) || value.trim().length() <= 0 || value.trim().length() > 12) {
                        return ServerResponse.createResponseByErrorMsg("校验失败，昵称为空或长度超出0-12范围。");
                    } else if (this.hasBlank(value)){
                        return ServerResponse.createResponseByErrorMsg("校验失败，昵称不能包含空格。");
                    } else if (userMapper.checkRepetition(value, type) > 0) {
                        return ServerResponse.createResponseByErrorMsg("校验失败，已存在相同的昵称。");
                    }
                    break;
                case Const.USER_NAME:
                    // 账号长度 大于0小于等于12，不为空，不能有空格，不能重复
                    if (StringUtils.isBlank(value) || value.trim().length() <= 0 || value.trim().length() > 12) {
                        return ServerResponse.createResponseByErrorMsg("校验失败，用户名为空或长度超出0-12范围。");
                    } else if (this.hasBlank(value)){
                        return ServerResponse.createResponseByErrorMsg("校验失败，用户名不能包含空格。");
                    } else if (userMapper.checkRepetition(value, type) > 0) {
                        return ServerResponse.createResponseByErrorMsg("校验失败，已存在相同的用户名。");
                    }
                    break;
                case Const.EMAIL:
                    // 邮箱符合邮箱格式，不能为空，长度不能超过32，不能重复
                    if (StringUtils.isBlank(value) || value.trim().length() <= 0 || value.trim().length() > 32) {
                        return ServerResponse.createResponseByErrorMsg("校验失败，邮箱为空或长度超出0-32范围。");
                    } else if (userMapper.checkRepetition(value, type) > 0) {
                        return ServerResponse.createResponseByErrorMsg("校验失败，已存在相同邮箱。");
                    } else if (!this.checkEmail(value.trim())) {
                        return ServerResponse.createResponseByErrorMsg("校验失败，邮箱不符合格式！");
                    }
                    break;
                case Const.TELEPHONE:
                    // 手机号不能超过11位，不能为空，不能重复
                    if (!this.checkTelPhone(value) || StringUtils.isBlank(value)) {
                        return ServerResponse.createResponseByErrorMsg("手机号为空或格式不正确。");
                    } else if (userMapper.checkRepetition(value, type) > 0) {
                        return ServerResponse.createResponseByErrorMsg("校验失败，已存在相同手机号。");
                    }
                    break;
                case Const.PASSWORD:
                    // 密码不为空，长度不超过18位
                    if (StringUtils.isBlank(value) || value.trim().length() <= 0 || value.trim().length() > 18) {
                        return ServerResponse.createResponseByErrorMsg("密码为空或长度超出范围18。");
                    }
                    break;
                default:
                    flag = false;
            }
        } else {
            flag = false;
        }
        if (flag == false){
            return ServerResponse.createResponseByErrorMsg("参数出错！");
        }
        return ServerResponse.createResponseBySuccessMsg("校验成功！");
    }


    /**
     * 验证手机号是否合法
     * @return
     */
    private boolean checkTelPhone(String mobile){
        if (mobile.length() != 11)
        {
            return false;
        }else{
            /**
             * 移动号段正则表达式
             */
            String pat1 = "^((13[4-9])|(147)|(15[0-2,7-9])|(178)|(18[2-4,7-8]))\\d{8}|(1705)\\d{7}$";
            /**
             * 联通号段正则表达式
             */
            String pat2  = "^((13[0-2])|(145)|(15[5-6])|(176)|(18[5,6]))\\d{8}|(1709)\\d{7}$";
            /**
             * 电信号段正则表达式
             */
            String pat3  = "^((133)|(153)|(177)|(18[0,1,9])|(149))\\d{8}$";
            /**
             * 虚拟运营商正则表达式
             */
            String pat4 = "^((170))\\d{8}|(1718)|(1719)\\d{7}$";

            Pattern pattern1 = Pattern.compile(pat1);
            Matcher match1 = pattern1.matcher(mobile);
            boolean isMatch1 = match1.matches();
            if(isMatch1){
                return true;
            }
            Pattern pattern2 = Pattern.compile(pat2);
            Matcher match2 = pattern2.matcher(mobile);
            boolean isMatch2 = match2.matches();
            if(isMatch2){
                return true;
            }
            Pattern pattern3 = Pattern.compile(pat3);
            Matcher match3 = pattern3.matcher(mobile);
            boolean isMatch3 = match3.matches();
            if(isMatch3){
                return true;
            }
            Pattern pattern4 = Pattern.compile(pat4);
            Matcher match4 = pattern4.matcher(mobile);
            boolean isMatch4 = match4.matches();
            if(isMatch4){
                return true;
            }
            return false;
        }
    }


    /**
     * 判断邮箱是否合法
     * @param email
     * @return
     */
    //正则表达式
    private boolean checkEmail(String email){
        /**
         *   ^匹配输入字符串的开始位置
         *   $结束的位置
         *   \转义字符 eg:\. 匹配一个. 字符  不是任意字符 ，转义之后让他失去原有的功能
         *   \t制表符
         *   \n换行符
         *   \\w匹配字符串  eg:\w不能匹配 因为转义了
         *   \w匹配包括字母数字下划线的任何单词字符
         *   \s包括空格制表符换行符
         *   *匹配前面的子表达式任意次
         *   .小数点可以匹配任意字符
         *   +表达式至少出现一次
         *   ?表达式0次或者1次
         *   {10}重复10次
         *   {1,3}至少1-3次
         *   {0,5}最多5次
         *   {0,}至少0次 不出现或者出现任意次都可以 可以用*号代替
         *   {1,}至少1次  一般用+来代替
         *   []自定义集合     eg:[abcd]  abcd集合里任意字符
         *   [^abc]取非 除abc以外的任意字符
         *   |  将两个匹配条件进行逻辑“或”（Or）运算
         *   [1-9] 1到9 省略123456789
         *    邮箱匹配 eg: ^[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\.){1,3}[a-zA-z\-]{1,}$
         *
         */
        if (StringUtils.isBlank(email)) {
            return false;
        }
        String RULE_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        //正则表达式的模式 编译正则表达式
        Pattern p = Pattern.compile(RULE_EMAIL);
        //正则表达式的匹配器
        Matcher m = p.matcher(email);
        //进行正则匹配\
        return m.matches();
    }

    /**
     * 判断字符串有无空格
     * @param string
     * @return
     */
    private boolean hasBlank(String string) {
        if (string.indexOf(' ') != -1) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 简历投递
     *
     * @param user
     * @param employmentId：职位Id
     * @return
     */
    @Override
    @Transactional
    public ServerResponse<String> deliverResume(User user, Integer employmentId) {
        // 要判断是否存在该职位
        Integer result = employmentMapper.selectIfExistById(employmentId);
        if (result <= 0) {
            return ServerResponse.createResponseByErrorMsg("投递失败，该职位不存在！");
        }
        Integer resumeId = resumeMapper.selectResumeIdByUserId(user.getId());
        if (resumeId > 0) {
            // 要判断是否已投递
            result = resumeDeliveryMapper.selectDeliveredByResumeIdEmpId(resumeId, employmentId);
            if (result > 0) {
                return ServerResponse.createResponseByErrorMsg("投递失败，已投递！");
            }
            ResumeDelivery resumeDelivery = new ResumeDelivery();
            resumeDelivery.setResumeId(resumeId);
            resumeDelivery.setEmploymentId(employmentId);
            result = resumeDeliveryMapper.insertSelective(resumeDelivery);
            if (result > 0) {
                return ServerResponse.createResponseBySuccess("投递成功！");
            }
        }
        return ServerResponse.createResponseByErrorMsg("投递失败！");
    }
}
