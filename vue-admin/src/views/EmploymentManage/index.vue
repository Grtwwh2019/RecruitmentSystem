<template>
  <div class="employmentManage"
    v-loading="loading"
    element-loading-text="拼命加载中"
    element-loading-spinner="el-icon-loading"
    element-loading-background="rgba(0, 0, 0, 0.8)">

    <div class="filter-box">
      <el-form ref="searchVo" :inline="true" :model="searchVo" class="demo-form-inline" size="small">
        <el-form-item prop="ename" label="职位" style="margin-right:30px;">
            <el-input
            v-model="searchVo.ename" 
            maxlength=255
            placeholder="请输入职位名称"
            clearable>
            </el-input>
          </el-form-item>

          <el-form-item prop="companyId" label="公司" style="margin-right:35px;">
            <el-select
              v-model="searchVo.companyId"
              :filterable="true"
              :remote="true"
              :reserve-keyword="false"
              placeholder="请输入公司关键词"
              :remote-method="remoteCompanyMethod"
              :loading="loading"
              clearable
              >
              <el-option
                v-for="(item, index) in companyOptions"
                :key="index"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item prop="city" label="城市" style="margin-right:30px;">
            <el-cascader :companyOptions="this.$store.state.citySet" v-model="searchVo.city" :props="uaualCascaderProp" :show-all-levels="false" placeholder="请选择城市" clearable></el-cascader>
          </el-form-item>

          <el-form-item prop="etypeid" label="职类" style="margin-right:30px;">
            <el-cascader :companyOptions="this.$store.state.positionTypeSet" v-model="searchVo.etypeid" :props="uaualCascaderProp" :show-all-levels="false" placeholder="请选择职类" clearable></el-cascader>
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

          <el-form-item label="发布人">
          <el-select
            v-model="searchVo.publisherId"
            :filterable="true"
            :remote="true"
            :reserve-keyword="false"
            placeholder="请输入发布人"
            :remote-method="remotePublisherMethod"
            :loading="loading"
            clearable
            >
            <el-option
              v-for="(item, index) in publishersOptions"
              :key="index"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>

          <el-form-item label="时间" style="margin-right:30px;">
            <el-date-picker
              style="width:217px;"
              v-model="valueDate"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              placeholder="请选择发布时间"
              format="yyyy 年 MM 月 dd 日"
              value-format="yyyy-MM-dd"
              unlink-panels
              clearable
              >
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

          <el-form-item style="float:right;margin-right:205px;">
            <el-button type="primary" @click="onSubmit" style="width:120px;background-color: #00b38a;border-color:#00b38a;">查询</el-button>
            <el-button style="" @click="resetForm('searchVo')">重置</el-button>
          </el-form-item>
      </el-form>
    </div>

    <div class="table-data">
        <el-table
          :cell-style='cellStyle' 
          :header-cell-style='rowClass'
          :data="employmentiList.list"
          height="520"
          style="width: 65%">

          <el-table-column
            prop="id"
            label="信息Id"
            width="100">
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
            prop="ecompany"
            label="所属公司"
            width="150">
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
            prop="publisher"
            label="发布人"
            width="150">
          </el-table-column>

          <el-table-column
            prop="createTime"
            label="发布日期"
            width="150">
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
            width="120">
            <template slot-scope="scope">
              <el-button style="color: #00b38a;text-decoration:underline;" type="text" @click="showDialog(scope.row)" >详细信息</el-button>
            </template>
          </el-table-column>

          

        </el-table>
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

    <employmentDIalog :dialogDetail.sync="dialogDetail" :showVisiable.sync="showVisiable" :dataSource.sync="employmentDetail" @handleDialogClose = "handleDialogClose"/>


  </div>
</template>

<script>
import employmentDIalog from '@/components/employmentDIalog.vue'
import { companySize, financing, degree, status } from "@/const/index";
export default {
  components:{
    employmentDIalog
  },
  mounted() {
    this.init()
  },
  data() {
    return {
      publishers: [],
      publishersOptions: [],
      companys: [],
      companyOptions: [],
      loading: false,
      degree: {},
      status: {},
      searchVo: {},
      valueDate: [],
      uaualCascaderProp: {
        emitPath : false
      },
      employmentiList: {
        list: [],
        pageSize:15,
        pageNum: 1,
        total:1
      },
      dialogDetail: {
        type: 'employment',
        title: '',
        btn: ''
      },
      showVisiable: false,
      employmentDetail: {}
    }
  },
  methods: {
    init() {
      this.status = status
      this.degree = degree
      this.getEmploymentList(1)
    },
    handleDialogClose(id, status) {
      if (id){
        this.post('/admin/managePosition.do/' + id + '/' + status).then(resp => {
          if (resp) {
            this.getEmploymentList(1)
          }
        })
      }
      this.showVisiable = false
    },
    showDialog(employment) {
      this.dialogDetail.title = '招聘信息详情'
      // 0 -- 封禁 1 -- 正常（解封）
      this.dialogDetail.btn = employment.estatus == 0 ? '解封' : '封禁'
      if (employment.id) {
        this.get('/common/getDetail.do/0/' + employment.id).then(resp => {
          if (resp) {
            this.employmentDetail = resp.data
            this.showVisiable = true
          }
        })
      }
    },
    onSubmit() {
      if (this.valueDate) {
        this.searchVo.createTImeFrom = this.valueDate[0]
        this.searchVo.createTImeTo = this.valueDate[1]
      }
      this.getEmploymentList(1)
    },
    getEmploymentList(pageNum) {
      this.loading=true
      this.postJson('/admin/getPositionList.do/' + pageNum, this.searchVo).then(resp => {
        if (resp.data) {
          this.employmentiList = resp.data
          this.loading = false
          this.valueDate = []
        }
      })
    },
    handleCurrentChange(page) {
      this.getEmploymentList(page)
    },
    resetForm(formName) {
      if (this.$refs[formName] != undefined){
        this.$refs[formName].resetFields();
      }
    },
    remotePublisherMethod(query) {
      // query为当前输入值
      if (query !== '') {
        this.publishers = []
        this.get('/admin/getUserListByUserName.do/' + query).then(resp => {
          if (resp && resp.status == 0) {
            let result = resp.data
            result.map(item=>{
              this.publishers.push({
                label: item.nickName,
                value: item.id
              })
            })
          }
        })
        this.loading = true
        setTimeout(() => {
          this.loading = false
          this.publishersOptions = this.publishers.filter(item => {
            return item.label.indexOf(query) > -1
          })
        }, 200)
      } else {
        this.publishersOptions = []
      }
    },
    remoteCompanyMethod(query) {
      // query为当前输入值
      if (query !== '') {
        this.companys = []
        this.get('/common/getCompanyListByCname.do/' + query).then(resp => {
          if (resp && resp.status == 0) {
            let result = resp.data
            result.map(item =>{
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
          this.companyOptions = this.companys.filter(item => {
            return item.label.indexOf(query) > -1
          })
        }, 200)
      } else {
        this.companyOptions = []
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
.itemListPagination{
  padding-left: 10%;
  margin-top: 30px;
  margin-bottom: 30px;
  /* padding: 40px; */
  /* text-align: center; */
}
.filter-box {
  padding: 40px 40px 10px;
  width: 60%;;
  border-bottom: 2px #00b38a solid;
}
.table-data {
  padding: 20px 40px;
}
</style>