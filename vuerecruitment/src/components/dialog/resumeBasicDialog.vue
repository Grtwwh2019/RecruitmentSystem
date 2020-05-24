<template>
  <el-dialog width="30%" :visible.sync="showVisiable" :close-on-click-modal="false" :close-on-press-escape="false" :before-close="handleClose"  @open="handleOpen">
    
    <div slot="title" class="header-title">
      <slot name="title"></slot>
    </div>

    <el-form style="width:90%;" v-if="this.basicForm.dialogType == 'basic'" ref="basicForm" :model="basicForm" label-width="80px" :rules="basicRules" > 
        
      <el-form-item prop="realname" label="姓名">
        <el-input
         v-model="basicForm.realname" 
         maxlength=12 
         :show-word-limit="true" 
         placeholder="请输入真实姓名"
         clearable>
        </el-input>
      </el-form-item>
      
      <el-form-item prop="birthday" label="生日">
        <el-date-picker
          style="width:100%;"
          v-model="basicForm.birthday"
          type="date"
          placeholder="请选择出生日期"
          format="yyyy 年 MM 月 dd 日"
          value-format="yyyy-MM-dd">
        </el-date-picker>
      </el-form-item>

      <el-form-item prop="gender" label="性别">
        <!-- :label=“1”，表示label的值为数字1，即期待数据值为数字
        label=“1”，表示label的值为字符串1，即期待的数据值为字符串1 -->
        <el-radio style="width:45%;" v-model="basicForm.gender" :label="1" border size="medium">男</el-radio>
        <el-radio style="width:45%;" v-model="basicForm.gender" :label="2" border size="medium">女</el-radio>
      </el-form-item>

      <el-form-item prop="telephone" label="手机号码">
        <el-input
         v-model="basicForm.telephone" 
         maxlength=11 
         placeholder="请输入手机号码"
         clearable>
        </el-input>
      </el-form-item>

      <el-form-item prop="email" label="联系邮箱">
        <el-input
         v-model="basicForm.email" 
         maxlength=30
         :show-word-limit="true" 
         placeholder="请输入联系邮箱"
         clearable>
        </el-input>
      </el-form-item>

      <el-form-item prop="currentstatus" label="当前身份">
        <!-- :label=“1”，表示label的值为数字1，即期待数据值为数字
        label=“1”，表示label的值为字符串1，即期待的数据值为字符串1 -->
        <el-radio style="width:45%;" v-model="basicForm.currentstatus" :label="1" border size="medium">学生</el-radio>
        <el-radio style="width:45%;" v-model="basicForm.currentstatus" :label="2" border size="medium">非学生</el-radio>
      </el-form-item>

      
      

    </el-form>

    <el-form style="width:90%;" v-if="this.basicForm.dialogType == 'advantage'" ref="basicForm" :model="basicForm" label-width="80px" :rules="basicRules" > 
      <el-form-item prop="advantage" >
        <el-input
          type="textarea"
          :rows="8"
          placeholder="请输入内容"
          resize="none"
          :show-word-limit="true"
          maxlength=300
          v-model="basicForm.advantage">
        </el-input>
      </el-form-item>
    </el-form>

    <el-form style="width:90%;" :inline="true" v-if="this.basicForm.dialogType == 'expected'" ref="basicForm" :model="basicForm" label-width="80px" :rules="basicRules" > 
      
      <el-form-item prop="expectpositionid" label="期望职位">
        <el-cascader style="width:390px;" :options="positionTypeSet" v-model="basicForm.expectpositionid" :props="positionTypeProp" :show-all-levels="false" placeholder="请选择职位类型" clearable></el-cascader>
      </el-form-item>

      <el-form-item prop="lowsalary" label="期望薪资">
        <el-input
         style="width:180px;" 
         v-model="basicForm.lowsalary" 
         maxlength=18
         placeholder="请输入最低期望薪资"
         clearable>
        </el-input>
        <span style="margin-left: 5px;">至</span>  
      </el-form-item>

      <el-form-item prop="highsalary">
        <el-input
         style="width:180px;" 
         v-model="basicForm.highsalary" 
         maxlength=18
         placeholder="请输入最高期望薪资"
         clearable>
        </el-input>
      </el-form-item>

      <el-form-item prop="industryid" label="行业">
        <el-cascader style="width:390px;" :options="industrySet" v-model="basicForm.industryid" :props="industryProp" :show-all-levels="false" placeholder="请选择行业，最多可选3个" @change="handleIndustryListChange" clearable></el-cascader>
      </el-form-item>

      <el-form-item prop="cityid" label="城市">
        <el-cascader style="width:390px;" :options="citySet" v-model="basicForm.cityid" :props="positionTypeProp" :show-all-levels="false" placeholder="请选择城市" clearable></el-cascader>
      </el-form-item>


    </el-form>

    <div class="submit_button">
      <el-button style="margin-left:20%;margin-top:35px;margin-bottom:20px;" 
        type="success" 
        @click="save">保存
      </el-button>
    </div>
  </el-dialog>

</template>

<script>
import { getAllPositionType, getAllIndustry, getAllCity } from "@/api/index";
export default {
  mounted() {
    this.init()
  },
  props: {
    showVisiable: {type: Boolean},
    baseInfo: {type: Object, default: null}
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
    return {
      positionTypeProp: {emitPath : false},
      industryProp: {multiple: true,emitPath : false},
      citySet: [],
      industrySet:[],
      positionTypeSet: [],
      basicRules: {
        realname: [{required: true, message: '请输入姓名', trigger: 'blur'}],
        birthday: [{required: true, message: '请输入生日', trigger: 'blur'}],
        gender: [{required: true, message: '请输入性别', trigger: 'blur'}],
        telephone: [{validator: checkPhone, trigger: 'blur'}, {required: true, message: '请输入手机号码', trigger: 'blur'}],
        email: [{ type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }, {required: true, message: '请输入邮箱', trigger: 'blur'}],
        currentstatus: [{required: true, message: '请输入当前身份', trigger: 'blur'}],
        advantage: [{required: true, message: '必填', trigger: 'blur'}],
        expectpositionid: [{required: true, message: '必填', trigger: 'blur'}],
        lowsalary: [{required: true, message: '必填', trigger: 'blur'}],
        highsalary: [{required: true, message: '必填', trigger: 'blur'}],
        industryid: [{required: true, message: '必填', trigger: 'blur'}],
        cityid: [{required: true, message: '必填', trigger: 'blur'}],
      },
      basicForm: {},
      tempIndustryList: []
    }
  },
  methods: {
    init() {
      getAllCity().then(resp => {
        if (resp && resp.status == 0) {
          this.citySet = resp.data
        } else {
          Message.error({message: resp.msg ? resp.msg : "未知错误，请联系管理员"})
        }
      })
      getAllIndustry().then(resp => {
        if (resp && resp.status == 0) {
          this.industrySet = resp.data
        } else {
          Message.error({message: resp.msg ? resp.msg : "未知错误，请联系管理员"})
        }
      })
      getAllPositionType().then(resp => {
        if (resp && resp.status == 0) {
          this.positionTypeSet = resp.data
        } else {
          Message.error({message: resp.msg ? resp.msg : "未知错误，请联系管理员"})
        }
      })
    },
    handleIndustryListChange(value) {
      if (value.length > 3) {
        this.$alert('所选行业不得超过3个', '提示', {
          confirmButtonText: '确定',
          type: 'warning',
          closeOnClickModal: false,
          closeOnPressEscape: false,
          showClose: false
        }).then(()=> {
          this.basicForm.industryid = this.tempIndustryList
        })
      } else {
        this.tempIndustryList = value
      }
    },
    save() {
      this.$refs.basicForm.validate((valid => {
        if (valid) {
          let param = {
            headermask: this.basicForm.headermask,
            realname: this.basicForm.realname,
            gender: this.basicForm.gender,
            birthday: this.basicForm.birthday,
            currentstatus: this.basicForm.currentstatus,
            advantage: this.basicForm.advantage,
            socialpage: this.basicForm.socialpage,
            otherattachment: this.basicForm.otherattachment,
            resumeattachment: this.basicForm.resumeattachment,
            expectpositionid: this.basicForm.expectpositionid,
            lowsalary: this.basicForm.lowsalary,
            highsalary: this.basicForm.highsalary,
            industryid: this.basicForm.industryid,
            cityid: String(this.basicForm.cityid),
            showVisiable: false
          }
          let param2 = {
            telephone: this.basicForm.telephone,
            email: this.basicForm.email
          }
          let type = "save"
          this.$emit('handleShowBasicDialog', param, param2, type)
        }
      }))
    },
    handleOpen() {
      let industryid = []
      this.baseInfo.industries.forEach(element => {
        industryid.push(element.industryId)
      });
      this.baseInfo.industryid = industryid
      this.baseInfo.cityid = Number(this.baseInfo.cityid)
      this.basicForm = Object.assign({}, this.baseInfo) 
      this.tempIndustryList = this.basicForm.industryid
    },
    handleClose() {
      this.$confirm('离开修改内容将不会保存，是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
      }).then(() => {
        this.$refs.basicForm.resetFields()
        this.$emit('handleShowBasicDialog', false)
      }).catch(() => {
          
      });
      
    }
  },
  //这个地方需要监听showVisiable的变化,如果showVisiable发生变化后,触发父组件对showVisiable的更新
  watch:{
    baseInfo: function(newVal, oldVal) {
      this.basicForm = this.baseInfo
    }
    // showVisiable : function (val) {
    //   // this.$emit('update:showVisiable', val);
    //   this.$emit('handleShowBasicDialog', val)
    // }
  }
}
</script>

<style scoped>
/* .modal-content {
    position: relative;
    background-color: #fff;
    border: 1px solid #999;
    border: 1px solid rgba(0,0,0,.2);
    border-radius: 6px;
    -webkit-box-shadow: 0 3px 9px rgba(0,0,0,.5);
    box-shadow: 0 3px 9px rgba(0,0,0,.5);
    background-clip: padding-box;
    outline: 0;
} */
</style>