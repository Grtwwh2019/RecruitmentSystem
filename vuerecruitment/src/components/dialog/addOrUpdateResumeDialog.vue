<template>
  <el-dialog :title="addOrUpdateData.title" width="30%":visible.sync="showVisiable" :close-on-click-modal="false" :close-on-press-escape="false"  @open="handleOpen" :before-close="handleClose">

    <el-form v-if="this.addOrUpdateData.dialogType == 'addEdu' || this.addOrUpdateData.dialogType == 'editEdu'" style="width:90%; margin-left:3%;" ref="addOrUpdateForm" :model="addOrUpdateForm" label-width="80px" :rules="rules" > 
      
      <el-form-item label="学校名称" prop="schoolId">
        <el-select
          style="width:100%;"
          v-model="addOrUpdateForm.schoolId"
          :filterable="true"
          :remote="true"
          :reserve-keyword="false"
          placeholder="请输入学校名称"
          :remote-method="remoteSchoolMethod"
          :loading="loading"
          value-key="value"
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

      <el-form-item
        prop="valueDate" label="在校时间">
        <el-date-picker
          style="width:100%;"
          v-model="addOrUpdateForm.valueDate"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          placeholder="请选择入学时间"
          format="yyyy 年 MM 月 dd 日"
          value-format="yyyy-MM-dd"
          @input="handleDateChange"
          unlink-panels>
        </el-date-picker>
      </el-form-item>

      <el-form-item prop="degree" label="学历">
        <el-select style="width:100%;" v-model="addOrUpdateForm.degree" placeholder="请选择学历">
          <el-option
            v-for="(value, key) in degrees"
            :key="key"
            :label="value"
            :value="Number(key)">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item v-if="addOrUpdateForm.degree == 5" prop="schoolType" label="学校类型">
        <!-- :label=“1”，表示label的值为数字1，即期待数据值为数字
        label=“1”，表示label的值为字符串1，即期待的数据值为字符串1 -->
        <el-radio style="width:45%;" v-model="addOrUpdateForm.schoolType" :label="1" border size="medium">全日制</el-radio>
        <el-radio style="width:45%;" v-model="addOrUpdateForm.schoolType" :label="2" border size="medium">非全日制</el-radio>
      </el-form-item>

      <el-form-item prop="major" label="专业">
        <el-input
         v-model="addOrUpdateForm.major" 
         maxlength=255
         placeholder="请输入专业"
         clearable>
        </el-input>
      </el-form-item>


      <el-form-item prop="schoolexp" label="学校经历（选填）" >
        <el-input
          type="textarea"
          :rows="8"
          placeholder="请输入内容"
          resize="none"
          :show-word-limit="true"
          maxlength=300
          v-model="addOrUpdateForm.schoolexp">
        </el-input>
      </el-form-item>


    </el-form>

    <el-form v-if="this.addOrUpdateData.dialogType == 'addWork' || this.addOrUpdateData.dialogType == 'editWork'" style="width:90%; margin-left:3%;" ref="addOrUpdateForm" :model="addOrUpdateForm" label-width="80px" :rules="rules" > 
      
      
      <el-form-item prop="cname" label="公司名称">
        <el-input
        style="width:100%;"
         v-model="addOrUpdateForm.cname" 
         maxlength=255
         placeholder="公司名称"
         clearable>
        </el-input>
      </el-form-item>

      <el-form-item prop="industryId" label="所属行业">
        <el-cascader style="width:100%;" :options="industrySet" v-model="addOrUpdateForm.industryId" :props="industryProp" :show-all-levels="false" placeholder="请选择所属行业" clearable></el-cascader>
      </el-form-item>

      <el-form-item prop="department" label="所属部门（选填）">
        <el-input
         v-model="addOrUpdateForm.department" 
         maxlength=255
         placeholder="例如：技术开发部"
         clearable>
        </el-input>
      </el-form-item>

      <el-form-item prop="positionId" label="职位类型">
        <el-cascader style="width:100%;" :options="positionTypeSet" v-model="addOrUpdateForm.positionId" :props="industryProp" :show-all-levels="false" placeholder="请选择职位类型" @change="handlePositionTypeChange(addOrUpdateForm.positionId)" clearable></el-cascader>
      </el-form-item>

      <el-form-item prop="position" label="职位名称（选填）">
        <el-input
         v-model="addOrUpdateForm.position" 
         maxlength=128
         placeholder="例如：Java开发"
         clearable>
        </el-input>
      </el-form-item>

      <el-form-item 
        prop="valueDate" label="在职时间">
        <el-date-picker
          style="width:100%;"
          v-model="addOrUpdateForm.valueDate"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          placeholder="请选择在职时间"
          format="yyyy 年 MM 月 dd 日"
          value-format="yyyy-MM-dd"
          @input="handleDateChange"
          unlink-panels>
        </el-date-picker>
      </el-form-item>

      <el-form-item prop="skillTags" label="技能标签">
        <el-select style="width:100%;" ref="skillTags" @focus="handleToSelectPositionType" v-model="addOrUpdateForm.skillTags" multiple :multiple-limit="3" value-key="value" placeholder="请选择你的技能标签（最多3个）">
          <el-option
            v-for="item in skilltags"
            :key="item.value"
            :label="item.label"
            :value="item.value"
            clearable>
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item prop="workcontent" label="工作内容">
        <el-input
          type="textarea"
          :rows="8"
          placeholder="请输入内容"
          resize="none"
          :show-word-limit="true"
          maxlength=1500
          v-model="addOrUpdateForm.workcontent">
        </el-input>
      </el-form-item>

    </el-form>

    <el-form v-if="this.addOrUpdateData.dialogType == 'addProject' || this.addOrUpdateData.dialogType == 'editProject'" style="width:90%; margin-left:3%;" ref="addOrUpdateForm" :model="addOrUpdateForm" label-width="80px" :rules="rules" > 
      
      <el-form-item prop="projectName" label="项目名称">
        <el-input
         v-model="addOrUpdateForm.projectName" 
         maxlength=128
         placeholder="例如：直聘网"
         clearable>
        </el-input>
      </el-form-item>

      <el-form-item prop="role" label="项目角色">
        <el-input
         v-model="addOrUpdateForm.role" 
         maxlength=128
         placeholder="例如：后端开发"
         clearable>
        </el-input>
      </el-form-item>

      <el-form-item prop="url" label="项目链接（选填）">
        <el-input
         v-model="addOrUpdateForm.url" 
         maxlength=128
         placeholder="例如：github"
         clearable>
        </el-input>
      </el-form-item>

      <el-form-item 
        prop="valueDate" label="项目时间">
        <el-date-picker
          style="width:100%;"
          v-model="addOrUpdateForm.valueDate"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          placeholder="请选择项目时间"
          format="yyyy 年 MM 月 dd 日"
          value-format="yyyy-MM-dd"
          @input="handleDateChange"
          unlink-panels>
        </el-date-picker>
      </el-form-item>

      <el-form-item prop="description" label="项目描述" >
        <el-input
          type="textarea"
          :rows="8"
          placeholder="请输入内容"
          resize="none"
          :show-word-limit="true"
          maxlength=1500
          v-model="addOrUpdateForm.description">
        </el-input>
      </el-form-item>
      

    </el-form>

    <el-form v-if="this.addOrUpdateData.dialogType == 'addCertification' || this.addOrUpdateData.dialogType == 'editCertification'" style="width:90%; margin-left:3%;" ref="addOrUpdateForm" :model="addOrUpdateForm" label-width="80px" :rules="rules" > 
      <el-tag class="el-icon-s-flag" v-if="addOrUpdateForm.certificationList" style="margin-bottom: 3%;">
        已选：{{addOrUpdateForm.certificationList.length}} / 30
      </el-tag>
      <el-cascader
      style="width:100%;"
      v-model="addOrUpdateForm.certificationList"
      placeholder="请输入选择资格证书，最多30个"
      :multiple-limit="30"
      :show-all-levels="false"
      :options="certificationSet"
      :props="certificationProps"
      @change="handleCertificationListChange"
      filterable
      clearable></el-cascader>
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
import { getSchoolListBySname, getAllIndustry, getAllPositionType, getSkillTagsByParentId, getAllCertification } from "@/api/index"
import { degree, schoolType } from "@/const/index";
import { Message } from 'element-ui'
export default {
  props: {
    showVisiable: {type: Boolean},
    addOrUpdateData: {type: Object, default: null}
  },
  mounted() {
    this.degrees = degree
    // this.schoolType = schoolType
    this.initCascader()
  },
  data() {
    return {
      loading: false,
      certificationProps:{
        multiple: true,
        emitPath : false 
      },
      certificationSet: [],
      // 控制技能标签下拉框显示
      disableSkillTags: true,
      // 根据PositionId获得技能标签
      skilltags: [],
      positionTypeSet: {},
      // industrySet的props属性：emitPath:false只返回该节点的值
      industryProp: {emitPath : false},
      industrySet: {},
      degrees: [],
      options: [],
      schools: [],
      rules:{
        schoolId: [{required: true, message: '必填', trigger: 'blur'}],
        valueDate: [{required: true, message: '必填', trigger: 'blur'}],
        degree: [{required: true, message: '必填', trigger: 'blur'}],
        major: [{required: true, message: '必填', trigger: 'blur'}],
        cname: [{required: true, message: '必填', trigger: 'blur'}],
        industryId: [{required: true, message: '必填', trigger: 'blur'}],
        workcontent: [{required: true, message: '必填', trigger: 'blur'}],
        positionId: [{required: true, message: '必填', trigger: 'blur'}],
        projectName: [{required: true, message: '必填', trigger: 'blur'}],
        role: [{required: true, message: '必填', trigger: 'blur'}],
        description: [{required: true, message: '必填', trigger: 'blur'}]
      },
      // valueDate: [],
      addOrUpdateForm: {
        valueDate: []
      },
      tempCertificationList: []
    }
  },
  watch:{
    // addOrUpdateData: function(newVal, oldVal) {
    //   this.addOrUpdateForm = this.addOrUpdateData.params
    // }
  },
  methods: {
    handleCertificationListChange(value) { 
      if (value.length > 30) {
        this.$alert('所选证书不得超过30个', '提示', {
          confirmButtonText: '确定',
          type: 'warning',
          closeOnClickModal: false,
          closeOnPressEscape: false,
          showClose: false
        }).then(()=> {
          this.addOrUpdateForm.certificationList = this.tempCertificationList
        })
      } else {
        this.tempCertificationList = value
      }
    },
    // 解决vue中el-date-picker type=daterange日期不回显的问题
    handleDateChange(value) {
      if (value) {
        this.$nextTick(() => {
          this.$delete(this.addOrUpdateForm, "valueDate")
          this.$set(this.addOrUpdateForm, "valueDate", [value[0], value[1]])
        });
      } else {
        this.$delete(this.addOrUpdateForm, "valueDate")
      }
    },
    handleToSelectPositionType() {
      if (!this.addOrUpdateForm.positionId) {
        this.$refs.skillTags.blur()
        if (this.disableSkillTags) {
          Message.error({message:"请先选择职位类型"})
          this.disableSkillTags = false
        } else {
          this.disableSkillTags = true
        }
      }
    },
    handlePositionTypeChange(parentId) {
      this.getSkillTagsByParentId(parentId)
      this.addOrUpdateForm.skillTags = []
    },
    handleOpen() {
      // this.addOrUpdateForm = JSON.parse(JSON.stringify(this.addOrUpdateData.params))
      // 解决父子组件传值，子组件改变值后父组件的值也改变的问题
      this.addOrUpdateForm = Object.assign({}, this.addOrUpdateData.params)
      this.tempCertificationList = this.addOrUpdateForm.certificationList
      // console.log(this.addOrUpdateForm)
      this.getSkillTagsByParentId(this.addOrUpdateForm.positionId)
      this.addOrUpdateForm.valueDate = []
      if (this.addOrUpdateForm && this.addOrUpdateForm.startdate) {
        this.$set(this.addOrUpdateForm, "valueDate", [this.addOrUpdateForm.startdate, this.addOrUpdateForm.enddate]);
        // this.addOrUpdateForm.valueDate[0] = this.addOrUpdateForm.startdate
        // this.addOrUpdateForm.valueDate[1] = this.addOrUpdateForm.enddate
      }
      // projectExp
      if (this.addOrUpdateForm && this.addOrUpdateForm.starttime) {
        this.addOrUpdateForm.valueDate[0] = this.addOrUpdateForm.starttime
        this.addOrUpdateForm.valueDate[1] = this.addOrUpdateForm.endtime
      }
      if(this.addOrUpdateForm && this.addOrUpdateForm.schoolId != null && this.addOrUpdateForm.schoolId != '') {
        this.options.push({
          label: this.addOrUpdateForm.schoolName,
          value: this.addOrUpdateForm.schoolId
        })
      }
      if (this.addOrUpdateForm.skillTags) {
        let arr = []
        this.addOrUpdateForm.skillTags.forEach(element => {
          console.log(element)
          arr.push(element.id)
        });
        this.addOrUpdateForm.skillTags = arr
      }
    },
    save() {
      this.$refs.addOrUpdateForm.validate((valid => {
        if (valid) {
          if (this.addOrUpdateData.dialogType == "addEdu" || this.addOrUpdateData.dialogType == "editEdu") {
            this.addOrUpdateForm.startdate = this.addOrUpdateForm.valueDate[0]
            this.addOrUpdateForm.enddate = this.addOrUpdateForm.valueDate[1]
            if (this.addOrUpdateForm.degree != 5) {
              this.addOrUpdateForm.schoolType = null
            }
            this.$emit('handleResumeDialogClose', this.addOrUpdateForm, "addEdu")
          } else if (this.addOrUpdateData.dialogType == "addWork" || this.addOrUpdateData.dialogType == "editWork") {
            this.addOrUpdateForm.startdate = this.addOrUpdateForm.valueDate[0]
            this.addOrUpdateForm.enddate = this.addOrUpdateForm.valueDate[1]
            // this.addOrUpdateForm.industryId = this.addOrUpdateForm.industryId[0]
            // console.log(this.addOrUpdateForm)
            this.$emit('handleResumeDialogClose', this.addOrUpdateForm, "addWork")
          } else if (this.addOrUpdateData.dialogType == "addProject" || this.addOrUpdateData.dialogType == "editProject") {
            this.addOrUpdateForm.starttime = this.addOrUpdateForm.valueDate[0]
            this.addOrUpdateForm.endtime = this.addOrUpdateForm.valueDate[1]
            this.$emit('handleResumeDialogClose', this.addOrUpdateForm, "addProject")
          } else if (this.addOrUpdateData.dialogType == "addCertification" || this.addOrUpdateData.dialogType == "editCertification") {
            this.$emit('handleResumeDialogClose', this.addOrUpdateForm, "addCertification")
          } else{
            this.$emit('handleResumeDialogClose', false)
          }
        }
      }))
    },
    handleClose() {
      this.$confirm('离开修改内容将不会保存，是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          closeOnClickModal: false,
          closeOnPressEscape: false,
          type: 'warning'
      }).then(() => {
        // debugger
        // console.log(this.addOrUpdateForm)
        // this.addOrUpdateForm.industryId = null
        this.addOrUpdateForm.valueDate = []
        this.addOrUpdateForm = this.addOrUpdateData
        this.$refs.addOrUpdateForm.resetFields()
        this.$emit('handleResumeDialogClose', false)
      }).catch(() => {
          // this.$message({
          //     type: 'info',
          //     message: '已取消删除'
          // });
      });
      // this.addOrUpdateForm.valueDate = []
      // this.$refs.addOrUpdateForm.resetFields()
      // this.$emit('handleResumeDialogClose', false)
    },
    remoteSchoolMethod(query) {
      // query为当前输入值
      if (query !== '') {
        getSchoolListBySname(query).then(resp => {
          if (resp && resp.status == 0) {
            let result = resp.data
            result.map(item=>{
              this.schools.push({
                label: item.schoolName,
                value: item.id
              })
            })
          }
        })
        this.loading = true
        setTimeout(() => {
          this.loading = false
          this.options = this.schools.filter(item => {
            return item.label.indexOf(query) > -1
          })
        }, 200)
      } else {
        this.options = []
      }
    },
    getSkillTagsByParentId(pid) {
      if (pid) {
        getSkillTagsByParentId(pid).then(resp => {
          if (resp && resp.status == 0) {
            this.skilltags = resp.data
          }
        })
      }
    },
    initCascader() {
      getAllCertification().then(resp => {
        if (resp && resp.status == 0) {
          this.certificationSet = resp.data
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
    }
  }
}
</script>

<style>

</style>