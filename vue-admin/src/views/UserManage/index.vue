<template>
  <div class="userManage"
    v-loading="loading"
    element-loading-text="拼命加载中"
    element-loading-spinner="el-icon-loading"
    element-loading-background="rgba(0, 0, 0, 0.8)">

    <div class="filter-box">
      <el-form :inline="true" :model="searchVo" class="demo-form-inline" size="small">
        <el-form-item label="昵称">
          <el-input v-model="searchVo.nickname" placeholder="昵称" clearable=""></el-input>
        </el-form-item>

        <el-form-item label="电话">
          <el-input v-model="searchVo.telephone" placeholder="电话" clearable></el-input>
        </el-form-item>

        <el-form-item label="邮箱">
          <el-input v-model="searchVo.email" placeholder="邮箱" clearable></el-input>
        </el-form-item>

        <el-form-item label="账号">
          <el-input v-model="searchVo.username" placeholder="账号" clearable></el-input>
        </el-form-item>

        <el-form-item prop="companyid" label="公司">
          <el-select
            style="width:215px;"
            v-model="searchVo.companyid"
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

        <el-form-item label="工号">
          <el-input v-model="searchVo.empno" placeholder="工号" clearable></el-input>
        </el-form-item>

        <el-form-item prop="type" label="状态">
          <el-select v-model="searchVo.authentication" placeholder="状态" style="width:215px;" clearable>
              <el-option
              v-for="(value, key) in auth"
              :key="key"
              :label="value"
              :value="Number(key)">
              </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="时间" style="margin-right:30px;" prop="valueDate">
            <el-date-picker
              style="width:215px;"
              v-model="valueDate"
              type="daterange"
              range-separator="至"
              start-placeholder="创建时间"
              end-placeholder="创建时间"
              placeholder="请选择创建时间"
              format="yyyy 年 MM 月 dd 日"
              value-format="yyyy-MM-dd"
              unlink-panels
              clearable>
            </el-date-picker>
          </el-form-item>

        
        <el-form-item style="margin-left:100px">
          <el-button type="primary" @click="onSubmit" style="width:120px;background-color: #00b38a;border-color:#00b38a;">查询</el-button>
        </el-form-item>
      </el-form>  
    </div>

    <div class="table-data">
      <el-table
          :cell-style='cellStyle' 
          :header-cell-style='rowClass'
          :data="userList.list"
          height="520"
          style="width: 65%">

          <el-table-column
            prop="id"
            label="用户Id"
            width="100">
          </el-table-column>

          <el-table-column
            prop="nickname"
            label="昵称"
            width="180"
            >
          </el-table-column>

          <el-table-column
            prop="company"
            label="所属公司"
            width="180"
            >
          </el-table-column>

          <el-table-column
            prop="empno"
            label="员工号"
            width="180"
            >
          </el-table-column>

          <el-table-column
            prop="telephone"
            label="联系电话"
            width="180"
            >
          </el-table-column>

          <el-table-column
            prop="email"
            label="联系邮箱"
            :show-overflow-tooltip="true"
            width="180"
            >
          </el-table-column>

          <el-table-column
            prop="username"
            label="登录账号"
            width="180"
            >
          </el-table-column>

          <el-table-column
            prop="authentication"
            label="账号状态"
            width="120"
            >
            <template slot-scope="scope">
              {{auth[scope.row.authentication]}}
            </template>
          </el-table-column>

          <el-table-column
            prop="createtime"
            label="创建时间"
            width="180"
            >
          </el-table-column>

          <el-table-column
            fixed="right"
            label="操作"
            width="180">
            <template slot-scope="scope">
              <el-button style="background-color: #00b38a;color:#fff;margin-right:10px;"  size="mini" @click="manage(scope.row.id, 1)" >启用</el-button>
              <el-button type="danger" size="mini" @click="manage(scope.row.id, 2)" >禁用</el-button>
            </template>
          </el-table-column>

      </el-table>

      <div class="itemListPagination">
        <el-pagination
          background
          prev-text="上一页"
          next-text="下一页"
          layout="prev, pager, next, jumper"
          :page-size.sync="userList.pageSize"
          :current-page.sync="userList.pageNum"
          :total="userList.total"
          @current-change="handleCurrentChange">
        </el-pagination>
      </div>
    </div>

  </div>
</template>

<script>
import { auth } from "@/const/index";
export default {
  mounted() {
    this.init()
  },
  data() {
    return {
      auth: {},
      loading: false,
      searchVo: {},
      companys: [],
      options: [],
      valueDate: [],
      userList: {
        list: [],
        pageSize:15,
        pageNum: 1,
        total:1
      },
      pageNum: 1,
      dialogDetail: {
        type: 'user',
        title: '',
        btn: ''
      }
    }
  },
  methods: {
    init () {
      this.auth = auth
      this.getAllUserList(1)
    },
    manage(id, status) {
      this.$confirm('此操作将更改账号状态，是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        closeOnClickModal: false,
        closeOnPressEscape: false,
        type: 'warning'
      }).then(() => {
        this.post('/admin/manageAccount.do/' + id + '/' + status).then(resp => {
          if (resp) {
            this.getAllUserList(this.userList.pageNum)
          }
        })
      }).catch(() => {
          this.$message({
          type: 'info',
          message: '已取消操作'
          })
      })
      
    },
    onSubmit() {
      if (this.valueDate) {
        this.searchVo.createTImeFrom = this.valueDate[0]
        this.searchVo.createTImeTo = this.valueDate[1]
      }
      this.getAllUserList(1)
    },
    handleCurrentChange(page) {
      this.getAllUserList(page)
    },
    getAllUserList(pageNum) {
      this.loading=true
      this.postJson('/admin/getAllUserList.do/'+ pageNum, this.searchVo).then(resp => {
        if (resp.data) {
          this.userList = resp.data
          this.loading = false
          this.valueDate = []
        }
      })
    },
    cellStyle({row, column, rowIndex, columnIndex}){
      return 'text-align:center';
    },
    rowClass({row, rowIndex}){
     return 'text-align:center'
    },
    remoteMethod(query) {
      // query为当前输入值
      this.companys = []
      if (query !== '') {
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
          this.options = this.companys.filter(item => {
            return item.label.indexOf(query) > -1
          })
        }, 200)
      } else {
        this.options = []
      }
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