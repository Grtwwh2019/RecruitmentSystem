<template>
  <el-dialog class="employmentDialog" :title="title.title" width="40%" :visible.sync="showVisiable" :close-on-click-modal="false" :close-on-press-escape="false" @open="handleOpen" :before-close="handleClose">
    <el-form style="width:90%; margin-left:3%;" hide-required-asterisk ref="employment" :model="employment" label-width="80px" :rules="rules">
      
      <el-form-item prop="ename" label="职位名称">
        <el-input
         v-model="employment.ename" 
         maxlength=255
         placeholder="请输入职位名称"
         clearable>
        </el-input>
      </el-form-item>

      <el-form-item prop="etypeid" label="职位类型" >
        <el-cascader style="width:100%;" :options="this.$store.state.positionTypeSet" v-model.number="employment.etypeid" :props="uaualCascaderProp" :show-all-levels="false" placeholder="请选择职类" clearable></el-cascader>
      </el-form-item>

 
      <el-form-item prop="ecityid" label="工作城市">
        <el-cascader style="width:100%;" :options="this.$store.state.citySet" v-model="employment.ecityid" :props="uaualCascaderProp" :show-all-levels="false" placeholder="请选择城市" clearable></el-cascader>
      </el-form-item>

      <el-form-item prop="esalaryfloor" label="最低薪资">
        <el-input
         v-model.number="employment.esalaryfloor" 
         maxlength=18
         placeholder="请输入最低薪资"
         clearable>
        </el-input>
      </el-form-item>

      <el-form-item prop="esalaryceilie" label="最高薪资">
        <el-input
         v-model.number="employment.esalaryceilie" 
         maxlength=18
         placeholder="请输入最高薪资"
         clearable>
        </el-input>
      </el-form-item>

      <el-form-item prop="educationrequire" label="学历要求">
        <el-select style="width:100%;" v-model="employment.educationrequire" placeholder="请选择学历要求">
          <el-option
            v-for="(value, key) in degree"
            :key="key"
            :label="value"
            :value="Number(key)">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item prop="eresponsibility" label="岗位职责">
        <el-input
          type="textarea"
          :rows="8"
          placeholder="请输入岗位职责"
          resize="none"
          :show-word-limit="true"
          maxlength=3000
          v-model="employment.eresponsibility">
        </el-input>
      </el-form-item>

      <el-form-item prop="erequirement" label="岗位要求">
        <el-input
          type="textarea"
          :rows="8"
          placeholder="请输入岗位要求"
          resize="none"
          :show-word-limit="true"
          maxlength=3000
          v-model="employment.erequirement">
        </el-input>
      </el-form-item>

      <div class="submit_button" style="text-align:center;">
        <el-button style="margin-top:35px;margin-bottom:20px;margin-right:30px;" 
          type="success" 
          @click="submitForm">{{title.btn}}
        </el-button>

        <el-button style="margin-top:35px;margin-bottom:20px;margin-left:30px;" 
          
          @click="handleClose">取消
        </el-button>
      </div>

    </el-form>
  </el-dialog>
</template>

<script>
import { degree } from "@/const/index";
const title = {
  add: {title: "发布招聘信息", btn: "添加"},
  edit: {title: "编辑招聘信息", btn: "保存"}
}
export default {
  props: {
    showVisiable: {type: Boolean},
    employmentDetail: {type: Object},
    dialogType: {type: String}
  },
  mounted() {
    this.degree = degree
  },
  data() {
    var validateSalary = (rule, value, callback) => {
      if (value <= this.employment.esalaryfloor) {
        callback(new Error('最高工资不能小于最低工资'));
      } else {
        callback();
      }
    };
    return {
      title: {},
      degree: {},
      uaualCascaderProp: {
        emitPath : false
      },
      employment: {
        ename: null,
        etypeid: null,
        ecityid: null,
        esalaryfloor: null,
        esalaryceilie: null,
        educationrequire: null,
        eresponsibility: null,
        erequirement: null
      },
      rules: {
        ename: [{required: true, message: '必填', trigger: 'blur'}],
        etypeid: [{required: true, message: '必填', trigger: 'blur'}],
        ecityid: [{required: true, message: '必填', trigger: 'blur'}],
        esalaryfloor: [{type: 'number', trigger: 'blur', message:'请输入一个正整数'}, {required: true, message: '必填', trigger: 'blur'}],
        esalaryceilie: [{type: 'number', trigger: 'blur', message:'请输入一个正整数'}, {validator: validateSalary, trigger: blur}, {required: true, message: '必填', trigger: 'blur'}],
        educationrequire: [{required: true, message: '必填', trigger: 'blur'}],
        eresponsibility: [{required: true, message: '必填', trigger: 'blur'}],
        erequirement: [{required: true, message: '必填', trigger: 'blur'}]
      }
    }
  },
  methods: {
    submitForm() {
      this.$refs.employment.validate(valid => {
        if (valid) {
          // console.log(this.employment)
          this.$emit('handleEmploymentDialogClose', this.employment)
        }
      })
    },
    handleOpen() {
      this.title = title[this.dialogType]
      this.employment = Object.assign({}, this.employmentDetail)
      this.employment.etypeid = Number(this.employment.etypeid)
      this.employment.ecityid = Number(this.employment.ecityid)
    },
    handleClose() {
      this.$confirm('离开修改内容将不会保存，是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          closeOnClickModal: false,
          closeOnPressEscape: false,
          type: 'warning'
      }).then(() => {
        this.$refs.employment.resetFields()
        this.$emit('handleEmploymentDialogClose', false)
      }).catch(() => {
        
      })
    }
  }
}
</script>

<style scoped>

</style>