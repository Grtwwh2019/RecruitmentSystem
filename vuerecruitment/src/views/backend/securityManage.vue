<template>
  <div>
    <el-form class="reset_password"
      :rules="rules"
      ref="securityForm"
      v-loading="loading"
      element-loading-text="正在修改..."
      element-loading-spinner="el-icon-loading"
      element-loading-background="rgba(0, 0, 0, 0.8)"
      :model="securityForm"
      >
      <p class="title">登录账号：{{userInfo.username}}</p>
      <div style="margin-top:22px;">
        <el-form-item prop="passwordOld">
          <el-input
              class="input_passwordOld input_security"
              maxlength="18"
              minlength="1"
              placeholder="请输入当前密码"
              v-model="securityForm.passwordOld"
              clearable
              :show-password="true">
            </el-input>
        </el-form-item>
      </div>

      <div>
        <el-form-item prop="passwordNew"> 
          <el-input
              class="input_passwordNew input_security"
              maxlength="18"
              minlength="1"
              placeholder="请输入新密码"
              v-model="securityForm.passwordNew"
              clearable
              :show-password="true">
            </el-input>
        </el-form-item>
      </div>

      <div>
        <el-form-item prop="comfirmPasswordNew">
          <el-input
              class="input_comfirmPasswordNew input_security"
              maxlength="18"
              minlength="1"
              placeholder="请再次输入新密码"
              v-model="securityForm.comfirmPasswordNew"
              clearable
              :show-password="true">
            </el-input>
        </el-form-item>
      </div>
      <el-button style="width:100%;" type="success" @click="submitSave">保存</el-button>
    </el-form>
  </div>
</template>

<script>
import { resetPassword } from "@/api/index";
import { Message } from 'element-ui'
export default {
  mounted() {
    this.userInfo = this.$route.query.userInfo
  },
  data() {
    var validatePass = (rule, value, callback) => {            
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.securityForm.passwordNew) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      loading: false,
      userInfo: {},
      securityForm: {
        passwordOld: '',
        passwordNew: '',
        comfirmPasswordNew: ''
      },
      rules: {
        passwordOld: [{required: true, message: '请输入密码', trigger: 'blur'}],
        passwordNew: [{required: true, message: '请输入密码', trigger: 'change'}],
        comfirmPasswordNew:[{validator: validatePass, trigger: 'change' }, {required: true, message: '请输入密码', trigger: 'change'}]
      }
    }
  },
  methods: {
    submitSave() {
      this.$refs.securityForm.validate((valid) => {
        if (valid) {
          this.loading = true
          resetPassword(this.securityForm).then(resp => {
            this.loading = false;
            if (resp && resp.status == 1 && resp.msg) {
              Message.error({message: resp.msg})
            } else if (resp && resp.status == 0 && resp.msg) {
              Message.success({message: resp.msg})
            } else {
              Message.error({message: "未知错误，请联系管理员！"})
            }
            this.$refs.securityForm.resetFields()
          })
        }
      })
    }
  }

}
</script>

<style scoped>
.reset_password{
    font-size: 16px;
    width: 400px;
    /* margin-left: 120px; */
    position: relative;
}
.title {
    padding-bottom: 20px;
    border-bottom: 1px dashed #d9d9d9;
}
.input_security >>> .el-input__inner{
    border-left: none;
    border-right: none;
    border-top: none;
    border-radius: 0;
    height: 34px;
    line-height: normal;
    width: 100%;
    padding: 6px 0;
    margin-top: 0px;
  }
</style>