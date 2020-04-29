<template>
  <div class="approveEntUser"
    v-loading="loading"
    element-loading-text="拼命加载中"
    element-loading-spinner="el-icon-loading"
    element-loading-background="rgba(0, 0, 0, 0.8)">
    <div class="title" style="margin:0 auto;width: 928px;">
      <div class="title_box" style="padding-top:30px;color:rgb(0, 179, 138);font-size:26px;">
        企业用户审批
        <div style="border-bottom: 2px rgb(0, 179, 138) solid;padding-top:20px;"></div>
      </div>
    </div>

    <!-- <div class="filter">
      <div class="filter_box">
        <el-form :model="searchVo" ref="searchVo" :rules="rules" inline style="padding: 25px;" >
        </el-form>
      </div>
    </div> -->

    <div class="itemList">
      <div class="itemList-box">
        <el-table
          align='center' 
          stripe
          :cell-style='cellStyle' 
          :header-cell-style='rowClass'
          :data="enterpriseUserInfoList.list"
          height="700"
          style="width: 100%">

          <el-table-column
            type="index"
            width="50">
          </el-table-column>

          <el-table-column
            prop="nickname"
            label="用户昵称"
            width="150">
          </el-table-column>

          <el-table-column
            prop="empNo"
            label="员工号码"
            width="150">
          </el-table-column>

          <el-table-column
            prop="telephone"
            label="联系方式"
            width="150">
          </el-table-column>

          <el-table-column
            prop="telephone"
            label="状态"
            width="150">
            <template slot-scope="scope">
              <span v-if="scope.row.authentication == 1">通过</span>
              <span v-if="scope.row.authentication == 3">拒绝</span>
              <span v-if="scope.row.authentication == 4">待认证</span>
            </template>
          </el-table-column>

          <el-table-column
            fixed="right"
            label="操作"
            width="100">
            <template slot-scope="scope">
              <el-button style="color: #00b38a;" type="text" @click="showDialog(scope.row.userId)" >认证详情</el-button>
              <!-- <el-button style="color: #00b38a;" type="text" @click="deleteEmployment(scope.row.id)" >删除</el-button> -->
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
          :page-size.sync="enterpriseUserInfoList.pageSize"
          :current-page.sync="enterpriseUserInfoList.pageNum"
          :total="enterpriseUserInfoList.total"
          @current-change="handleCurrentChange">
        </el-pagination>
      </div>
    </div>

    <approveEntUserAuthInfoDialog :showVisible.sync="dialogVisible" :userAuthInfo.sync="userAuthInfo" @handleDiaoClose="handleDiaoClose"/>


  </div>
</template>

<script>
import { getApplyEntUserList, getEntUserAuthInfo, approveEntUser } from "@/api/index";
import approveEntUserAuthInfoDialog from "@/components/dialog/approveEntUserAuthInfoDialog.vue"
export default {
  components: {
    approveEntUserAuthInfoDialog
  },
  mounted() {
    this.init()
  },
  data() {
    return {
      loading: false,
      dialogVisible: false,
      pageNum: 1,
      // 是否已认证，0---禁用（账号被禁用），1---是，2---否(账号正常)，
      // 3---拒绝 4---企业用户待认证 ,5---企业账号待认证（求职者注册默认为2，企业账号注册默认为5）
      // authInfo: 0, 
      enterpriseUserInfoList: [],
      userAuthInfo: {}
    }
  },
  methods: {
    init() {
      this.getApplyEntUserList(1)
    },
    handleDiaoClose(authStatus, userId) {
      if (authStatus && userId) {
        approveEntUser(userId, authStatus).then(resp => {
          this.$message({
            center: true,
            showClose: true,
            message: "操作成功！",
            type: 'success'
          });
          this.getApplyEntUserList(1)
        })
      }
      this.dialogVisible = false
    },
    async showDialog(id) {
      let resp = await getEntUserAuthInfo(id)
      this.userAuthInfo = resp.data
      this.dialogVisible = true
    },
    getApplyEntUserList(pageNum) {
      this.loading = true
      getApplyEntUserList(pageNum).then(resp => {
        if (resp && resp.status == 0) {
          this.enterpriseUserInfoList = resp.data
          // console.log(this.enterpriseUserInfoList)
        }
        this.loading = false
      })
    },
    handleCurrentChange(currentPage){
      // console.log(this.searchVo)
      // console.log(currentPage)
      this.getApplyEntUserList(currentPage)
      // window.scrollTo(0,0);
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
  margin-top: 30px;
  text-align: center;
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