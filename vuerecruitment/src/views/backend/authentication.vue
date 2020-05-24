<template>
  <div class="goto_authentication" v-if="(userInfo.authentication == 4 || userInfo.authentication == 2) && roleInfo.id == 1">
    <div class="module-head clearfix">
      <div>
        <el-steps :active="step" finish-status="success" simple>
          <el-step title="步骤 1" ></el-step>
          <el-step title="步骤 2" ></el-step>
          <el-step title="步骤 3" ></el-step>
        </el-steps>
      </div>
      
      <h1 v-if="step == 1">身份认证类型</h1>
      <h1 v-if="step == 2">身份认证申请-基本信息填写</h1>
      <h1 v-if="step == 3">身份认证申请-申请结果</h1>

    </div>
    <div class="module-body">
      <div class="authentic-type-box" v-if="step == 1 && userInfo.authentication == 2">
        <div class="authentic-type">
          <div class="type-pic">
            <img src="http://attachment.grtwwh.com:8080/img/type2.png">
          </div>
          <h2>公司用户</h2>
          <p>可发布招聘信息</p>
          <el-button type="success" round @click="toApprove">马上认证</el-button>
          
        </div>
        <div class="authentic-mod" style="text-align: center;">
          <div class="module-box">
          <div class="module-head clearfix">
          </div>
          <div class="module-body">
          <div class="authentic-step-box">
          <div class="authentic-step">
          <h1 style="margin-bottom:30px;">认证流程</h1>
          <div class="step-num">1</div>
          <h2>选择认证类型</h2>
          <p>认证类型为企业用户。</p>
          </div>
          <div class="authentic-step">
          <div class="step-num step2">2</div>
          <h2>填写认证类型</h2>
          <p>填写公司、部门、职位等身份信息。</p>
          </div>
          <div class="authentic-step">
          <div class="step-num step3">3</div>
          <h2>上传认证类型</h2>
          <p>根据认证类型，上传Offer/工牌截图、验证企业邮箱。</p>
          </div>
          <div class="authentic-step">
          <div class="step-num step4">4</div>
          <h2>企业审理</h2>
          <p>企业会审核认证材料，并在5个工作日内通过公告通知审核结果。</p>
          </div>
          </div>
          </div>
          </div>
        </div>
      </div>

      <el-form v-if="step == 2 && userInfo.authentication == 2"
        class="authentic-form"
        ref="authenticForm"
        :rules="rules"
        :model="authenticForm"
        label-width="110px"
        label-position="right"
        >

        <el-form-item prop="cardPhoto" label="上传认证资料">
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
            :on-success="handleAvatarSuccess">
            <el-button slot="trigger" size="small" type="primary" plain 
              style="
              background: #dff6f1;
              border: 1px solid #1ebf9b;
              color: #1ebf9b;
              text-align: center;
              height: 40px;
              position: absolute;
              left: 0;
              top: 0;
              width: 80%;
              ">点击上传</el-button>
            <div slot="tip" class="el-upload__tip" style="position: absolute; left: 0; top: 30px;">上传工牌截图</div>
          </el-upload>
        </el-form-item>

        <el-form-item style="margin-top:40px;" label="认证公司" prop="companyId">
          <el-select
            style="
              width:80%; 
              text-align: center; 
              position: absolute;
              left: 0;
              top: 0;"
            v-model="authenticForm.companyId"
            :filterable="true"
            :remote="true"
            :reserve-keyword="false"
            placeholder="请输入公司关键词"
            :remote-method="remoteMethod"
            :loading="loading"
            clearable
            >
            <el-option
              v-for="(item, index) in options"
              :key="index"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        
        <el-form-item  prop="empNo" label="员工号">
          <el-input size="middle" style="width:80%; text-align: center; position: absolute;left: 0;top: 0; margin-top:5px;" 
          type="text" v-model.number="authenticForm.empNo" auto-complete="off"
          placeholder="请输入员工号"></el-input>
        </el-form-item>

        <el-form-item  prop="enterpriseMail" label="验证企业邮箱">
          <el-input size="middle" style="width:80%; text-align: center; position: absolute;left: 0;top: 0; margin-top:5px;" 
          type="text" v-model.trim="authenticForm.enterpriseMail" auto-complete="off"
          placeholder="请输入企业邮箱"></el-input>
        </el-form-item>

        <div class="authentic-form-btn">
          <el-button type="info" plain @click="previousStep">返回上一步</el-button>
          <el-button type="success" @click="submitApprove">确认申请认证</el-button>
        </div>


      </el-form>

      <div class="authentic-success" v-if="step == 3 || userInfo.authentication == 4">
        <h1>提交申请成功，待审核！</h1>
      </div>

    </div>
  </div>
  
  <div v-else-if="roleInfo.id != 1">
    <div class="authentic-type-box" style="margin:3% 15%">
      <div class="authentic-type">
        <div class="type-pic">
          <img src="http://attachment.grtwwh.com:8080/img/type2.png">
        </div>
        <p style="margin-top:65px">您已通过认证，无需重复认证！</p>
        <h2 style="margin-top:-5px">您当前的身份为</h2>
        <el-tag type="success" style="width:100px; heigh:35px;font-size: 18px;margin-top:20px">{{roleInfo.namezh}}</el-tag>
      </div>
    </div>
  </div>
  <div class="authentic-success" v-else-if="userInfo.authentication == 3">
    <h1>认证已被拒绝，请重新提交认证信息！</h1>
    <el-button style="width:250px;margin-top: 30px;" 
      @click="toApproveAgain">重新认证
    </el-button>
  </div>
</template>

<script>
import { getUserInfo, getCompanyListByCname, applyEnterpriseUser, deleteFile } from "@/api/index";
import { Message } from 'element-ui'

export default {
  // 离开路由前的钩子函数
  beforeRouteLeave (to, from, next) {
    // if (this.step != 3) {
    //   let params = {
    //     fileName: this.authenticForm.cardPhoto,
    //     remotePath: this.uploadParam.type
    //   }
    //   if (params.fileName != null && params.fileName != '' && params.fileName.length != 0) {
    //     deleteFile(params)
    //   }
      
    // }
    
    next()
  },
  mounted() {
    getUserInfo().then(resp => {
      if (resp && resp.status == 0) {
        this.userInfo = resp.data
      }
      if(this.userInfo.authentication == 4) {
        this.step = 3
      }
    })
    this.roleInfo = this.$route.query.roleInfo
  },
  data() {
    var checkCompany = (rule, value, callback) => {
      if (value == null || !value) {
        return callback(new Error('请选择公司'))
      } else {
        callback();
      }
    }
    return {
      // 文件列表
      fileList: [],
      uploadParam: {
        type: "img"
      },
      // 显示的company列表
      companys: [],
      options: [],
      loading: false,
      step: 1,
      userInfo: {},
      roleInfo: {},
      authenticForm: {
        cardPhoto: '',
        companyId: null,
        empNo: '',
        enterpriseMail: ''
      },
      rules: {
        enterpriseMail: [{ type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }, {required: true, message: '请输入邮箱', trigger: 'blur'}],
        empNo: [
          {required: true, message: '请输入员工号', trigger: 'blur'},
          {            
            validator(rule,value,callback){               
              if(Number.isInteger(Number(value)) && Number(value) > 0){                
                callback();
              }else{                 
                callback(new Error("请输入有效的员工号"));               
              }             
            },             
            trigger: 'blur',           
          }
        ],
        companyId: [{validator: checkCompany, trigger: 'blur'}, {required: true, message: '请选择公司', trigger: 'change'}],
      }
    }
  },
  methods: {
    toApproveAgain() {
      this.step = 1
      this.userInfo.authentication = 2
    },
    submitApprove() {
      this.$refs.authenticForm.validate((valid) => {
        if (valid) {
          this.$refs.upload.submit() // 上传文件
        }
      })
    },
    previousStep() {
      this.step = 1
      // let params = {
      //   fileName: this.authenticForm.cardPhoto,
      //   remotePath: this.uploadParam.type
      // }
      // if (params.fileName != null && params.fileName != '' && params.fileName.length != 0) {
      //   deleteFile(params)
      // }
      this.$refs.authenticForm.resetFields()
    },
    remoteMethod(query) {
      // query为当前输入值
      if (query !== '') {
        getCompanyListByCname(query).then(resp => {
          if (resp && resp.status == 0) {
            let result = resp.data
            result.map(item=>{
              this.companys.push({
                label: item.cname,
                value: item.id
              })
            })
          }
        })
        this.loading = true
        setTimeout(() => {
          this.loading = false
          this.options = this.companys.filter(item => {
            return item.label.indexOf(query) > -1
          })
        }, 200)
      } else {
        this.options = []
      }
    },
    toApprove() {
      // to setp 2
      this.step = 2
    },
    // 上传文件之前
    beforeUpload(file) {
      if (!file) {
        Message.error({message: '请上传文件'})
      }
    },
    // 上传成功的钩子
    handleAvatarSuccess(response, file, fileList) {
      if (response && response.status === 0) {
        this.authenticForm.cardPhoto = response.data.uri
        applyEnterpriseUser(this.authenticForm).then(async resp => {
          if (resp.status == 1 && resp.msg) {
            Message.error({message: resp.msg})
            let params = {
              fileName: this.authenticForm.cardPhoto,
              remotePath: this.uploadParam.type
            }
            if (params.fileName != null && params.fileName != '' && params.fileName.length != 0) {
              deleteFile(params)
            }
          } else if (resp.status == 0) {
            // 申请成功，跳转step 3
            this.step = 3
            
          } else {
            Message.error({message: '未知错误，请联系管理员！'})
            let params = {
              fileName: this.authenticForm.cardPhoto,
              remotePath: this.uploadParam.type
            }
            if (params.fileName != null && params.fileName != '' && params.fileName.length != 0) {
              deleteFile(params)
            }
          }
        })
      }
    }
  }
}
</script>

<style scoped>
.goto_authentication{
    width: 80%;
    border: 1px solid #e0e0e0;
    border-radius: 3px;
    margin-bottom: 10px;
    background: #fff;
}
.module-head {
    padding: 15px 20px 0;
    line-height: normal;
    position: relative;
}

.module-head h1, .module-head h2 {
    font-size: 16px;
    line-height: 16px;
    float: left;
    font-weight: 600;
    white-space: nowrap;
    margin-right: 5px;
    margin-top: 10px;
}
.module-body {
    padding: 20%;
    text-align: center;
}
.authentic-type {
    position: relative;
    width: 275px;
    height: 185px;
    display: inline-block;
    text-align: center;
    border-radius: 5px;
    border: 1px solid #ddd;
    font-size: 14px;
    margin-right: 30px;
}
.authentic-type h2 {
    margin-top: 60px;
    font-size: 16px;
    font-weight: 600;
}
.authentic-type p {
    font-size: 14px;
    color: #7c7c7c;
    margin: 10px 0 20px;
}
.type-pic {
    position: absolute;
    width: 98px;
    height: 98px;
    left: 50%;
    top: -50px;
    margin-left: -49px;
}
.authentic-mod {
    background: #fff;
}
.authentic-step-box {
    position: relative;
    margin-right: -50px;
    padding: 30px 0 20px;
    text-align: center;
}
.authentic-step {
    display: inline-block;
    width: 170px;
    margin-right: 50px;
    vertical-align: top;
}
.step-num {
    color: #fff;
    background: #e87c71;
    border-radius: 50%;
    text-align: center;
    line-height: 45px;
    height: 45px;
    width: 45px;
    margin: 0 auto 10px;
    font-size: 22px;
    position: relative;
}
.authentic-form {
    text-align: center;
    width: 100%;
    margin-top: -70px;
    font-size: 14px;
}
.authentic-form-btn {
    text-align: center;
    padding-top: 20px;
    padding-left: 50px;
}

</style>