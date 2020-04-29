<template>
  <div class="register-Form">
    <el-form

            :rules="rules"
            :inline="true"
            label-width="120px"
            ref="registerForm"
            v-loading="loading"
            label-position="right"
            element-loading-text="正在注册..."
            element-loading-spinner="el-icon-loading"
            element-loading-background="rgba(0, 0, 0, 0.8)"
            :model="registerForm"
            class="registerContainer">
            <h3 class="registerTitle">用户注册</h3>
            <div>
              <el-form-item prop="role">
                <div style="margin-left:250px;text-align: center;">
                  <el-radio v-model="role" label="1" border @change="showAuth = false">求职者</el-radio>
                  <el-radio v-model="role" label="4" border @change="showAuth = true">企业账号</el-radio>
              </div>  
              </el-form-item>
            </div>
            <el-form-item prop="nickname" label="昵称">
                <el-input size="normal" type="text" v-model.trim="registerForm.nickname" auto-complete="off"
                          placeholder="请输入昵称"></el-input>
            </el-form-item>
            <el-form-item prop="telephone" label="电话">
                <el-input size="normal" type="text" v-model.number="registerForm.telephone" auto-complete="off"
                          placeholder="请输入电话号码"></el-input>
            </el-form-item>
            <el-form-item prop="email" label="邮箱">
                <el-input size="normal" type="text" v-model.trim="registerForm.email" auto-complete="off"
                          placeholder="请输入邮箱"></el-input>
            </el-form-item>
            <el-form-item prop="username" label="用户名">
                <el-input size="normal" type="text" v-model.trim="registerForm.username" auto-complete="off"
                          placeholder="请输入用户名"></el-input>
            </el-form-item>
            <el-form-item prop="password" label="密码">
                <el-input size="normal" type="password" v-model.trim="registerForm.password" auto-complete="off"
                          placeholder="请输入密码"></el-input>
            </el-form-item>
            <el-form-item prop="confirmPassword" label="确认密码">
                <el-input size="normal" type="password" v-model.trim="registerForm.confirmPassword" auto-complete="off"
                          placeholder="请再次输入密码"></el-input>
            </el-form-item>
            <div v-if="showAuth">
              <el-form-item prop="license" label="上传营业执照" >
                  <el-upload
                    name="uploadFile"
                    class="upload-demo"
                    ref="upload"
                    action="/common/upload.do"
                    :data="uploadParam"
                    :limit="1"
                    :file-list="fileList"
                    :auto-upload="false"
                    :before-upload="beforeUpload"
                    :on-success="handleAvatarSuccess"
                    :on-error="handleAvatarError">
                    <el-button slot="trigger" size="small" type="primary" plain style="width:534px;">选取文件</el-button>
                    <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
                  </el-upload>
              </el-form-item>
            </div>
            <div>
              <el-form-item prop="captcha" label="验证码">
                  <el-input size="normal" type="captcha" v-model.trim="registerForm.captcha" auto-complete="off"
                            placeholder="请输入验证码" @keydown.enter.native="submitRegister"></el-input>
              </el-form-item>
              <el-form-item>
                <img
                    @click="changeCaptcha"
                    style="margin-left:40px; width: 200px; height: 40px"
                    :src="imgurl"
                    >
                  </img>
              </el-form-item>
            </div>
            <!-- <el-checkbox size="normal" class="registerRemember" v-model="checked"></el-checkbox> -->
            <el-button size="normal" type="primary" style="width: 30%; margin-left:100px;" @click="submitRegister">注册</el-button>
              <el-button style="margin-left:100px; margin-top: 20px;" size="middle" round @click="toLogin">直接登录</el-button>
            <div>
            </div>
        </el-form>
  </div>
  
</template>

<script>
import {upload, register, getCaptcha} from '@/api/index'
import { Message } from 'element-ui'
export default {
  mounted() {
    this.getCaptcha()
  },
  data() {
    var checkPhone = (rule, value, callback) => {
      const phoneReg = /^1[3|4|5|6|7|8][0-9]{9}$/
      if (!value) {
        return callback(new Error('电话号码不能为空'))
      }
      setTimeout(() => {
        
        if (!Number.isInteger(+value)) {
          callback(new Error('请输入数字值'))
        } else {
          if (phoneReg.test(value)) {
            callback()
          } else {
            callback(new Error('电话号码格式不正确'))
          }
        }
      }, 100)
    }
    var validatePass = (rule, value, callback) => {            
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.registerForm.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      loading: false,
      // 上传文件时附带的参数
      uploadParam: {
        type: 'img'
      },
      // 文件列表
      fileList: [],
      // 验证码图片
      imgurl: '',
      // 角色 1---求职者，4---企业账号
      role: '',
      showAuth: false,
      // 注册信息
      registerForm: {
        nickname: '',
        telephone: '',
        email: '',
        username: '',
        password: '',
        // 确认密码
        confirmPassword: '',
        // 营业执照
        license: '',
        captcha: '',
        captchaToken: ''
      },
      // 校验规则
      rules: {
          nickname: [{required: true, message: '请输入昵称', trigger: 'blur'}],
          telephone: [{validator: checkPhone, trigger: 'blur'}, {required: true, message: '请输入电话号码', trigger: 'blur'}],
          email: [{ type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }, {required: true, message: '请输入邮箱', trigger: 'blur'}],
          username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
          password: [{required: true, message: '请输入密码', trigger: 'blur'}],
          captcha: [{required: true, message: '请输入验证码', trigger: 'blur'}],
          confirmPassword:[{validator: validatePass, trigger: 'blur' }]
      }
    }
  },
  methods: {
    // 上传文件
    uploadLicense() {
      this.uploadParam.type = 'img'
      this.$refs.upload.submit()
    },
    toLogin() {
      let path = this.$route.query.redirect;
      this.$router.replace((path == '/' || path == undefined) ? '/login' : path);
    },
    changeCaptcha() {
      this.getCaptcha()
    },
    getCaptcha() {
      getCaptcha().then(resp => {
        if (resp) {
          this.registerForm.captchaToken = resp.data.token
          this.imgurl = 'data:image/jpg;base64,'+ resp.data.captchaImg
        }
      })
    },
    register() {
      register(this.registerForm, this.role).then(resp => {
        this.loading = false
        if (resp.status == 1 && resp.msg) {
            Message.error({message: resp.msg})
            this.getCaptcha()
            // this.$store.commit('INIT_CURRENTHR', resp.obj);
        } else if (resp.status == 0) {
            Message.success({message: resp.msg})
            this.toLogin()
        } else {
            Message.error({message: '未知错误，请联系管理员！'})
        }
      })
    },
    submitRegister() {
      this.$refs.registerForm.validate((valid) => {
        if (valid) {
          this.loading = true
          if (this.role == 1) {
            // 求职者
            this.register()
          } else if (this.role == 4) {
            // 企业账号
            this.uploadLicense()
          } else {
            alert("请选择注册账号类型！")
            return
          }
        }
      })
    },
    // 上传文件之前
    beforeUpload(file) {
      if (!file) {
        Message.error({message: '请上传文件'})
      }
      const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        return isJPG && isLt2M;
      
    },
    // 文件上传成功时的钩子
    handleAvatarSuccess(response, file, fileList) {
      if (response && response.status === 0) {
        this.registerForm.license = response.data.uri
        // 上传成功再去注册
        this.register()
      } else {
        Message.success({message: response.msg})
      }
    },
    // 文件上传失败时钩子
    handleAvatarError(err, file, fileList) {
      console.log(err)
      return false
    }
  }
}
</script>

<style>
   .registerContainer {
        border-radius: 15px;
        background-clip: padding-box;
        margin: 180px auto;
        width: 800px;
        padding: 15px 35px 15px 35px;
        background: #fff;
        border: 1px solid #eaeaea;
        box-shadow: 0 0 25px #cac6c6;
    }

    .registerTitle {
        margin: 15px auto 20px auto;
        text-align: center;
        color: #505458;
    }

    .registerRemember {
        text-align: left;
        margin: 0px 0px 15px 0px;
    }
</style>