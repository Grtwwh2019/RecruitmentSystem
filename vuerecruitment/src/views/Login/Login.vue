<template>
  <el-form
            :rules="rules"
            ref="loginForm"
            v-loading="loading"
            element-loading-text="正在登录..."
            element-loading-spinner="el-icon-loading"
            element-loading-background="rgba(0, 0, 0, 0.8)"
            :model="loginForm"
            class="loginContainer">
            <h3 class="loginTitle">用户登录</h3>
            <el-form-item prop="username">
                <el-input size="normal" type="text" v-model="loginForm.username" auto-complete="off"
                          placeholder="请输入用户名"></el-input>
            </el-form-item>
            <el-form-item prop="password">
                <el-input size="normal" type="password" v-model="loginForm.password" auto-complete="off"
                          placeholder="请输入密码"></el-input>
            </el-form-item>
            <el-form-item prop="captcha">
                <el-input size="normal" type="captcha" v-model="loginForm.captcha" auto-complete="off"
                          placeholder="请输入验证码" @keydown.enter.native="submitLogin"></el-input>
            </el-form-item>
            <el-form-item>
              <img
                  @click="changeCaptcha"
                  style="width: 350px; height: 35px"
                  :src="imgurl"
                  />
            </el-form-item>
            <!-- <el-checkbox size="normal" class="loginRemember" v-model="checked"></el-checkbox> -->
            <el-button size="normal" type="primary" style="width: 100%;background-color:#00b38a;border:#00b38a;" @click="submitLogin">登录</el-button>
            <el-button style="margin-left:50px; margin-top: 20px;" size="mini" round @click="toRegister">免费注册</el-button>
            <el-button style="margin-left:80px; margin-top: 20px;color:#00b38a;" size="middle" type="text" @click="toForgetPsd">忘记密码</el-button>
        </el-form>
</template>

<script>
import {userLogin, getCaptcha, getHighestRole} from '@/api/index.js'
import { Message } from 'element-ui'

export default {
  name: "Login",
  mounted() {
    this.getCaptcha()
  },
  data() {
      return {
          loading: false,
          // 验证码图片
          imgurl: '',
          // 登录信息
          loginForm: {
              username: '',
              password: '',
              captcha: '',
              captchaToken: ''
          },
          checked: true,
          rules: {
              username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
              password: [{required: true, message: '请输入密码', trigger: 'blur'}]
          }
      }
  },
  methods: {
      toForgetPsd() {
        let path = this.$route.query.redirect;
        this.$router.replace((path == '/' || path == undefined) ? '/forgetResetPsd' : path);
      },
      toRegister() {
        let path = this.$route.query.redirect;
        this.$router.replace((path == '/' || path == undefined) ? '/register' : path);
      },
      changeCaptcha() {
        this.getCaptcha()
      },
      getCaptcha() {
        getCaptcha().then(resp => {
          if (resp) {
            this.loginForm.captchaToken = resp.data.token
            this.imgurl = 'data:image/jpg;base64,'+ resp.data.captchaImg
          }
        })
      },
      submitLogin() {
          this.$refs.loginForm.validate((valid) => {
              if (valid) {
                  this.loading = true;
                  userLogin(this.loginForm).then(async resp => {
                      this.loading = false;
                      if (resp.status == 1 && resp.msg) {
                          Message.error({message: resp.msg})
                          this.getCaptcha()
                          // this.$store.commit('INIT_CURRENTHR', resp.obj);
                      } else if (resp.status == 0) {
                        Message.success({message: resp.msg})
                        let user = resp.data
                        let result = await getHighestRole()
                        if (result && result.status == 0) {
                          user.role = result.data
                        }
                        // console.log(user)
                        // let uuid = this.uuid()
                        // window.localStorage.setItem("user", uuid)
                        window.localStorage.setItem("user", JSON.stringify(user));
                        let path = this.$route.query.redirect;
                        this.$router.replace((path == '/' || path == undefined) ? '/index' : path);
                      } else {
                        Message.error({message: '未知错误，请联系管理员！'})
                      }
                  })
                  // this.post('/doLogin', this.loginForm).then(resp => {
                  //     this.loading = false;
                  //     if (resp) {
                  //         this.$store.commit('INIT_CURRENTHR', resp.obj);
                  //         window.sessionStorage.setItem("user", JSON.stringify(resp.obj));
                  //         let path = this.$route.query.redirect;
                  //         this.$router.replace((path == '/' || path == undefined) ? '/home' : path);
                  //     }
                  // })
              } else {
                  this.$message.error('请输入所有字段');
                  return false;
              }
          });
      },
      uuid() {
        var s = [];
        var hexDigits = "0123456789abcdef";
        for (var i = 0; i < 36; i++) {
          s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
        }
        s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
        s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the clock_seq_hi_and_reserved to 01
        s[8] = s[13] = s[18] = s[23] = "-";

        var uuid = s.join("");
        return uuid;
      }

  }
}
</script>

<style scoped>
    .loginContainer {
        border-radius: 15px;
        background-clip: padding-box;
        margin: 180px auto;
        width: 350px;
        padding: 15px 35px 15px 35px;
        background: #fff;
        border: 1px solid #eaeaea;
        box-shadow: 0 0 25px #cac6c6;
    }

    .loginTitle {
        margin: 15px auto 20px auto;
        text-align: center;
        color: #00b38a;
    }

    .loginRemember {
        text-align: left;
        margin: 0px 0px 15px 0px;
    }
</style>