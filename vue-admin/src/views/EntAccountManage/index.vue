<template>
  <div class="entAccountManage"
    v-loading="loading"
    element-loading-text="拼命加载中"
    element-loading-spinner="el-icon-loading"
    element-loading-background="rgba(0, 0, 0, 0.8)">

    <div class="op filter-box">
      <div class="op_box ">
        <el-radio-group v-model="auth" fill="#00b38a" @change="handleOnChange" >
          <el-radio-button :label="2">未认证</el-radio-button>
          <el-radio-button :label="1">已认证</el-radio-button>
        </el-radio-group>
      </div>
    </div>

    <div class="table-data">
        <el-table
          :cell-style='cellStyle' 
          :header-cell-style='rowClass'
          :data="entAccountList.list"
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
            width="180">
          </el-table-column>

          <el-table-column
            prop="username"
            label="登录账号"
            width="180">
          </el-table-column>

          <el-table-column
            prop="telephone"
            label="联系电话"
            width="180">
          </el-table-column>

          <el-table-column
            prop="email"
            label="联系邮箱"
            show-overflow-tooltip
            width="200">
          </el-table-column>

          <el-table-column
            prop="authentication"
            label="认证情况"
            width="200">
            <template slot-scope="scoped">
              {{authInfo[scoped.row.authentication]}}
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
            :page-size.sync="entAccountList.pageSize"
            :current-page.sync="entAccountList.pageNum"
            :total="entAccountList.total"
            @current-change="handleCurrentChange">
          </el-pagination>
        </div>
    </div>

    <entAccountDialog :dialogDetail.sync="dialogDetail" :showVisiable.sync="showVisiable" :dataSource.sync="entAccountDetail" @handleDialogClose = "handleDialogClose"/>

  </div>
  
</template>

<script>
import entAccountDialog from '@/components/entAccountDialog.vue'
import { auth } from "@/const/index";
export default {
  components:{
    entAccountDialog
  },
  mounted() {
    this.init()
  },
  data() {
    return {
      authInfo: {},
      auth: 2,
      loading: false,
      entAccountList: {
        list: [],
        pageSize:15,
        pageNum: 1,
        total:1
      },
      dialogDetail: {
        type: 'entAccount',
        title: '',
        btn: ''
      },
      showVisiable: false,
      entAccountDetail: {}
    }
  },
  methods: {
    init() {
      this.authInfo = auth
      this.getApplyEntAccountList(1,this.auth)
    },
    handleDialogClose(id, auth) {
      // approve ：1---通过认证； 2---拒绝认证
      if (id) {
        this.post('/admin/approveEnterpriseAccount.do/' + id + '/' + auth).then(resp => {
          if (resp) {
            this.getApplyEntAccountList(1, 2)
          }
        })
      }
      this.showVisiable = false
    },
    showDialog(entAccount) {
      this.dialogDetail.title = '企业账号详情'
      this.dialogDetail.btn = this.auth == 1 ? null : '确认审批'
      this.loading = true
      if (entAccount) {
        this.get('/admin/getEntAccountAuthInfo.do/' + entAccount.id).then(resp => {
          if (resp) {
            this.entAccountDetail = resp.data
            this.loading = false
            this.showVisiable = true
          }
        })
      }
    },
    handleOnChange(auth) {
      this.getApplyEntAccountList(1,auth)
    },
    getApplyEntAccountList(pageNum, auth) {
      // （1---已认证，2---未认证）
      this.loading = true
      this.get('/admin/getApplyEntAccountList.do/' + pageNum + '/' + auth).then(resp => {
        if (resp) {
          this.entAccountList = resp.data
          this.loading = false
        }
      })
    },
    handleCurrentChange(page) {
      this.getApplyEntAccountList(page, this.auth)
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