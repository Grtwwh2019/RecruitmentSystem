<template>
  <div class="employment-manage">
    <div class="title" style="margin:0 auto;width: 928px;">
      <div class="title_box" style="padding-top:30px;color:rgb(0, 179, 138);font-size:26px;">
        招聘信息管理
        <div style="border-bottom: 2px rgb(0, 179, 138) solid;padding-top:20px;"></div>
      </div>
    </div>
    <div class="filter">
      <div class="filter_box">
        <el-form :model="searchVo" ref="searchVo" :rules="rules" inline style="padding: 25px;" >

          <el-form-item prop="ename" label="职位" style="margin-right:30px;">
            <el-input
            v-model="searchVo.ename" 
            maxlength=255
            placeholder="请输入职位名称"
            clearable>
            </el-input>
          </el-form-item>

          <el-form-item prop="city" label="城市" style="margin-right:30px;">
            <el-cascader :options="this.$store.state.citySet" v-model="searchVo.city" :props="uaualCascaderProp" :show-all-levels="false" placeholder="请选择城市" clearable></el-cascader>
          </el-form-item>

          <el-form-item prop="etypeid" label="职类" style="margin-right:30px;">
            <el-cascader :options="this.$store.state.positionTypeSet" v-model="searchVo.etypeid" :props="uaualCascaderProp" :show-all-levels="false" placeholder="请选择职类" clearable></el-cascader>
          </el-form-item>
          
          <el-form-item prop="education" label="学历" style="margin-right:30px;">
            <el-select v-model="searchVo.education" placeholder="请选择学历要求" clearable>
              <el-option
                v-for="(value, key) in degree"
                :key="key"
                :label="value"
                :value="Number(key)">
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="时间" style="margin-right:30px;" prop="valueDate">
            <el-date-picker
              style="width:217px;"
              v-model="searchVo.valueDate"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              placeholder="请选择发布时间"
              format="yyyy 年 MM 月 dd 日"
              value-format="yyyy-MM-dd"
              unlink-panels
              clearable
              @input="handleDateChange">
            </el-date-picker>
          </el-form-item>

          <el-form-item prop="estatus" label="状态" style="margin-right:30px;">
            <el-select v-model="searchVo.estatus" placeholder="请选择状态" clearable>
              <el-option
                v-for="(value, key) in status"
                :key="key"
                :label="value"
                :value="Number(key)">
              </el-option>
            </el-select>
          </el-form-item>

          <div style="margin-left:77.5%;">
            <el-button style="background-color:#00b38a;color:#fff;" @click="searchEmploymentList">查询</el-button>
            <el-button style="" @click="resetForm('searchVo')">重置</el-button>
          </div>


        </el-form>
      </div>
    </div>

    <div class="op">
      <div class="op_box">
        <el-button style="font-size:24px;height:50px;width:100%;background-color:#00b38a;color:#fff;" @click="showDialog('add')">发布招聘信息</el-button>
      </div>
    </div>

    <div class="itemList">
      <div class="itemList-box">
        <el-table
          align='center' 
          :cell-style='cellStyle' 
          :header-cell-style='rowClass'
          :data="employmentiList.list"
          :row-class-name="tableRowClassName"
          style="width: 100%">

          <el-table-column
            prop="createTime"
            label="发布日期"
            width="160">
          </el-table-column>

          <el-table-column
            prop="ename"
            label="职位名称"
            width="300">
          </el-table-column>

          <el-table-column
            prop="etype"
            label="职位类型"
            width="180">
          </el-table-column>

          <el-table-column
            prop="ecity"
            label="工作城市"
            width="120">
          </el-table-column>

          <el-table-column
            label="学历要求"
            width="120">
            <template slot-scope="scope">
              {{degree[scope.row.educationrequire]}}
            </template>
          </el-table-column>

          <el-table-column
            label="状态"
            width="120">
            <template slot-scope="scope">
              <span v-if="scope.row.estatus == 0">封禁</span>
              <span v-else-if="scope.row.estatus == 1">正常</span>
            </template>
          </el-table-column>

          <el-table-column
            fixed="right"
            label="操作"
            width="100">
            <template slot-scope="scope">
              <el-button style="color: #00b38a;" type="text" @click="showDialog('edit', scope.row.id)" >编辑</el-button>
              <el-button style="color: #00b38a;" type="text" @click="deleteEmployment(scope.row.id)" >删除</el-button>
            </template>
          </el-table-column>

          

        </el-table>
      </div>

      <div class="itemListPagination">
        
        <el-pagination
          background
          prev-text="上一页"
          next-text="下一页"
          layout="prev, pager, next, jumper"
          :page-size.sync="employmentiList.pageSize"
          :current-page.sync="employmentiList.pageNum"
          :total="employmentiList.total"
          @current-change="handleCurrentChange">
        </el-pagination>
      </div>
    </div>

    <employmentDialog  :showVisiable.sync="dialogVisible" :dialogType.sync="dialogType" :employmentDetail.sync="employmentDetail" @handleEmploymentDialogClose = "handleEmploymentDialogClose"></employmentDialog>

    <el-backtop></el-backtop>
  </div>
</template>

<script>
import { companySize, financing, degree, status } from "@/const/index";
import { getEmploymentList, getDetail, addUpdateEmploymentInfo, deleteEmploymentInfo } from "@/api/index";
import employmentDialog from "@/components/dialog/addOrUpdateEmploymentDialog.vue";
export default {
  components: {
    employmentDialog
  },
  mounted() {
    this.init()
    
  },
  data() {
    return {
      dialogType: "",
      dialogVisible: false,
      employmentDetail: {},
      uaualCascaderProp: {
        emitPath : false
      },
      pageNum: 1,
      degree: {},
      status: {},
      searchVo: {
        ename: null,
        city: null,
        etypeid: null,
        education: null,
        valueDate: null,
        estatus: null
      },
      rules: {},
      employmentiList: {}
    }
  },
  methods: {
    init() {
      this.status = status
      this.degree = degree
      this.getEmploymentList(1)
    },
    deleteEmployment(id){
      this.$confirm('删除后不可恢复，是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          closeOnClickModal: false,
          closeOnPressEscape: false,
          type: 'warning'
      }).then(() => {
        deleteEmploymentInfo(id).then(resp => {
        if (resp && resp.status == 0) {
          this.$message.success(resp.msg)
          this.getEmploymentList(1)
        }else{
          this.$message.warning(resp.msg ? resp.msg : "未知错误，请联系管理员")
        }
      })
      }).catch(() => {
        
      })
      
    },
    async showDialog(type, id) {
      this.dialogType = type
      // console.log(id)
      if (id) {
        let resp = await getDetail({type:0, id: id})
        if (resp && resp.status == 0) {
          this.employmentDetail = resp.data
        }else{
          this.$message.warning(resp.msg ? resp.msg : "未知错误，请联系管理员")
        }
      }
      this.dialogVisible = true
    },
    handleEmploymentDialogClose(employment) {
      if (employment) {
        this.employmentDetail = employment
        this.addUpdateEmploymentInfo(employment.id)
      }
      this.employmentDetail = {}
      this.dialogVisible = false
    },
    addUpdateEmploymentInfo(id) {
      let eid = ""
      if (id) {
        eid = id
      }
      addUpdateEmploymentInfo(this.employmentDetail, eid).then(resp => {
        if(resp && resp.status == 0) {
          this.$message.success(resp.msg)
          this.getEmploymentList(1)
        } else{
          this.$message.warning(resp.msg ? resp.msg : "未知错误，请联系管理员")
        }
      })
    },
    searchEmploymentList() {
      this.getEmploymentList(1)
    },
    async getDetail(id) {
      let param = {
        type: 0,
        id: id
      }
      let resp = await getDetail(param)
      if (resp && resp.status == 0) {
        this.employmentDetail = resp.data
      }else{
        this.$message.warning(resp.msg ? resp.msg : "未知错误，请联系管理员")
      }
    },
    async getEmploymentList(pageNum) {
      if (this.searchVo.valueDate) {
        this.searchVo.createTImeFrom = this.searchVo.valueDate[0]
        this.searchVo.createTImeTo = this.searchVo.valueDate[1]
      }
      let param = {
        pageNum: pageNum,
        searchVo: this.searchVo
      }
      let resp = await getEmploymentList(param)
      if (resp && resp.status == 0) {
        this.employmentiList = resp.data
        // console.log(this.employmentiList)
      } else{
        this.$message.warning(resp.msg ? resp.msg : "未知错误，请联系管理员")
      }
    },
    tableRowClassName({row, rowIndex}) {
      if (row.estatus === 0) {
        return 'warning-row';
      } else {
        return '';
      }
      
    },
    handleCurrentChange(currentPage){
      // console.log(this.searchVo)
      // console.log(currentPage)
      this.getEmploymentList(currentPage)
      // window.scrollTo(0,0);
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    // 解决vue中el-date-picker type=daterange日期不回显的问题
    handleDateChange(value) {
      if (value) {
        this.$nextTick(() => {
          this.$delete(this.searchVo, "valueDate")
          this.$set(this.searchVo, "valueDate", [value[0], value[1]])
        });
      } else {
        this.$delete(this.searchVo, "valueDate")
      }
    },
    cellStyle({row, column, rowIndex, columnIndex}){
     return 'text-align:center';
    },
    rowClass({row, rowIndex}){
     return 'text-align:center'
    }
  }
}
</script>

<style scoped>
.el-table__body .warning-row {
  background: red;
}
.itemListPagination{
  margin-top: 30px;
  text-align: center;
}
.employment-manage{
  /* text-align: center; */
}
.op_box{
  /* border: 0 solid #00b38a; */
  width: 928px;
  /* height: 46px; */
  margin: 0 auto;
  /* margin-top: 20px; */
  position: relative;
  /* background-color: #fafafa; */
  /* border: 1px solid #ededed; */
  /* min-height: 36px; */
  /* padding: 6px 0; */
}
.filter {
  padding:30px 0;
}
.filter_box{
  /* border: 0 solid #00b38a; */
  width: 928px;
  /* height: 46px; */
  margin: 0 auto;
  /* margin-top: 20px; */
  position: relative;
  background-color: #fafafa;
  border: 1px solid #ededed;
  /* min-height: 36px; */
  /* padding: 6px 0; */
}
.itemList {
  padding:30px 0;
}
.itemList-box{
  /* border: 0 solid #00b38a; */
  width: 928px;
  /* height: 46px; */
  margin: 0 auto;
  /* margin-top: 20px; */
  position: relative;
  /* background-color: #fafafa; */
  /* border: 1px solid #ededed; */
  /* min-height: 36px; */
  /* padding: 6px 0; */
}
</style>