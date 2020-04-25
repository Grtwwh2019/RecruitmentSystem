<template>
  <div>
    <el-form
      ref="psdResetForm"
      class="psdResetContainer"
      :model="psdResetForm"
      >
      <h3 class="psdResetTitle">忘记密码</h3>
      <div v-if="question == null || question == ''">
        <el-form-item>
          <label style="margin-left: 50px; font-size:15px;">请选择找回方式：</label>
          <div style="margin-left: 50px;">
            <el-radio v-model="psdResetForm.type" label="email" border>邮箱</el-radio>
            <el-radio v-model="psdResetForm.type" label="telephone" border>手机号</el-radio>
          </div>
        </el-form-item>
        
        <el-form-item prop="username" style="margin-left: 50px; width: 222px;">
          <label style="font-size:15px;">请输入用户名：</label>
          <el-input size="normal" type="text" v-model="psdResetForm.username" auto-complete="off"
            placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-button size="normal" type="primary" style="margin-left: 50px; width: 150px;" @click="nextStep">下一步</el-button>
        <el-button size="normal" type="text" style="width: 70px;" @click="toLogin">返回登录</el-button>
      </div>

      <!-- 第二步 -->
      <div v-if="question && (psdResetForm.forgetToken == null || psdResetForm.forgetToken == '')">
        <el-form-item prop="verifyCode" style="margin-left: 50px; width: 222px;">
          <label style="font-size:15px;">{{question}}</label>
          <el-input v-if="psdResetForm.type=='email'" size="normal" type="text" v-model="psdResetForm.verifyCode" auto-complete="off"
            placeholder="请输入验证邮箱"></el-input>
            <el-input v-if="psdResetForm.type=='telephone'" size="normal" type="text" v-model="psdResetForm.verifyCode" auto-complete="off"
            placeholder="请输入验证手机号"></el-input>
        </el-form-item>
        <el-button size="normal" type="primary" style="margin-left: 50px; width: 105px;" @click="previousStep">上一步</el-button>
        <el-button size="normal" type="primary" style="width: 105px;" @click="nextStep2">下一步</el-button>
      </div>

      <!-- 第三步 -->
      <div v-if="psdResetForm.forgetToken">
        <el-form-item prop="passwordNew" style="margin-left: 50px; width: 222px;">
          <label style="font-size:15px;">请输入新密码：</label>
          <el-input size="normal" type="text" v-model="psdResetForm.passwordNew" auto-complete="off"
            placeholder="请输入新密码"></el-input>
        </el-form-item>
        <el-button size="normal" type="primary" style="margin-left: 50px; width: 105px;" @click="previousStep">上一步</el-button>
        <el-button size="normal" type="primary" style="width: 105px;" @click="resetPsd">重置密码</el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
import { forgetGetPsd, forgetCheckCode, forgetResetPassword } from "@/api/index";
import { Message } from 'element-ui'
export default {
  data() {
    return {
      question: '',
      psdResetForm: {
        username: 'zzj1234',
        type: '',
        verifyCode: '',
        forgetToken: '',
        passwordNew: ''
      }
    }
  },
  methods: {
    toLogin() {
      let path = this.$route.query.redirect;
      this.$router.replace((path == '/' || path == undefined) ? '/login' : path);
    },
    async nextStep() {
      let _result = await forgetGetPsd(this.psdResetForm)
      if (_result && _result.status == 1) {
        // 无此用户
         Message.error({message: _result.msg})
         return
      }
      this.question=_result.msg
    },
    previousStep() {
      this.psdResetForm.forgetToken = '' 
      this.question = ''
    },
    nextStep2() {
      forgetCheckCode(this.psdResetForm).then(resp => {
        if (resp) {
          if (resp && resp.status == 1) {
            // 验证失败
            Message.error({message: resp.msg})
            return
          } else if (resp.status == 0){
            // 验证成功,得到token
            this.psdResetForm.forgetToken = resp.data
          } else {
            Message.error({message: "未知错误，请联系管理员！"})
          }
        }
      })
    },
    resetPsd() {
      forgetResetPassword(this.psdResetForm).then(resp => {
        if (resp) {
          if (resp && resp.status == 1) {
            // 重置失败
            Message.error({message: resp.msg})
            return
          } else if (resp.status == 0){
            // 重置成功
            Message.success({message: resp.msg})
            this.toLogin()
          } else {
            Message.error({message: "未知错误，请联系管理员！"})
          }
        }
      })
    }
  }
}
</script>

<style>
  .psdResetContainer {
    border-radius: 15px;
    background-clip: padding-box;
    margin: 180px auto;
    width: 350px;
    padding: 15px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }

  .psdResetTitle {
      margin: 15px auto 20px auto;
      text-align: center;
      color: #505458;
  }
</style>