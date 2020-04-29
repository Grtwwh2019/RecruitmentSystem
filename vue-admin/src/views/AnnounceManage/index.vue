<template>
  <div class="announceManage"
    v-loading="loading"
    element-loading-text="拼命加载中"
    element-loading-spinner="el-icon-loading"
    element-loading-background="rgba(0, 0, 0, 0.8)">

    <div class="filter-box">
      <el-form :inline="true" :model="searchVo" class="demo-form-inline" size="small">
        <el-form-item label="标题">
          <el-input v-model="searchVo.title" placeholder="标题" clearable></el-input>
        </el-form-item>

        <el-form-item prop="type" label="类型">
          <el-select style="width:100%;" v-model="searchVo.type" placeholder="请选择类型" clearable>
              <el-option
              v-for="(value, key) in announceType"
              :key="key"
              :label="value"
              :value="Number(key)">
              </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="接收人">
          <el-select
            v-model="searchVo.receiver"
            :filterable="true"
            :remote="true"
            :reserve-keyword="false"
            placeholder="请输入接收人"
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

        <el-form-item label="时间" style="margin-right:30px;" prop="valueDate">
            <el-date-picker
              style="width:200px;"
              v-model="valueDate"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              placeholder="请选择发布时间"
              format="yyyy 年 MM 月 dd 日"
              value-format="yyyy-MM-dd"
              unlink-panels
              clearable>
            </el-date-picker>
          </el-form-item>

<!-- "id": null,
"title" :null,
"type": null,
"receiver": null,
"status": null,
"createTImeFrom":"",
"createTImeTo":"" -->

        <el-form-item style="float:right;margin-right:205px;">
          <el-button type="primary" @click="onSubmit" style="width:120px;background-color: #00b38a;border-color:#00b38a;">查询</el-button>
        </el-form-item>
      </el-form>  
    </div>

    <div class="op">
      <div class="op_box">
        <el-button style="margin-left:40px;margin-top:20px;font-size:18px;height:40px;width:10%;background-color:#00b38a;color:#fff;" @click="showDialog('add')">发布公告</el-button>
      </div>
    </div>

    <div class="table-data">
      <el-table
          :cell-style='cellStyle' 
          :header-cell-style='rowClass'
          :data="announceList.list"
          height="520"
          style="width: 65%">

          <el-table-column
            prop="id"
            label="公告Id"
            width="100">
          </el-table-column>

          <el-table-column
            prop="title"
            label="标题"
            width="280"
            :show-overflow-tooltip="true">
          </el-table-column>

          <el-table-column
            prop="type"
            label="类型"
            width="150"
            >
            <template slot-scope="scope">
              <span v-if="scope.row.type == 1">系统通知</span>
              <span v-else-if="scope.row.type == 2">公司通知</span>
            </template>
          </el-table-column>

          <el-table-column
            prop="receiverName"
            label="接收人"
            width="150"
            >
          </el-table-column>

          <el-table-column
            prop="createTime"
            label="发送时间"
            width="180"
            sortable
            >
          </el-table-column>

            <!-- fixed="right" -->
          <el-table-column
            label="操作"
            width="100">
            <template slot-scope="scope">
              <el-button style="color: #00b38a;" type="text" @click="showDialog('edit', scope.row)" >编辑</el-button>
              <el-button style="color: #00b38a;" type="text" @click="deleteAnnounce(scope.row.id)" >删除</el-button>
            </template>
          </el-table-column>

      </el-table>
<!-- content: "pKi7zhzQkZLI59maBkHnADfbZge9TQnfTCe7Lj5nqPHTb3CJCMUZ6lf4tXe7Lj4gVIDSjKFTmUkKAzSxVPevHMCEdWUxPokjpWiyFxvNj1ZJwgzWQdn7hRjNW7EHqQL9k0Ra3CMSRy1jioVhuqv5KljmJcL3RX2cHKtYjukZPZjr4VfgrfLWfhuptXbSJDVB6v2yuCuq"
createTime: "2020-12-01 13:47:46"
id: 2
receiverName: "诸冰佑"
status: 0
title: "KpBEjr89drrJWtnkmE0WwCmOOsHZE6sMmknKtXd4vchFjoR0g7FQ3yssKdJBO6U3"
type: 1 -->
    </div>

    <div class="itemListPagination">
      <el-pagination
        background
        prev-text="上一页"
        next-text="下一页"
        layout="prev, pager, next, jumper"
        :page-size.sync="announceList.pageSize"
        :current-page.sync="announceList.pageNum"
        :total="announceList.total"
        @current-change="handleCurrentChange">
      </el-pagination>
    </div>

    <commonDialog :dialogDetail.sync="dialogDetail" :showVisiable.sync="showVisiable" :dataSource.sync="announceDetail" @handleDialogClose = "handleDialogClose"/>



  </div>

</template>

<script>
const announceType = {
  '1': '系统通知',
  '2': '公司通知'
}
import commonDialog from '@/components/commonDialog.vue'
export default {
  components:{
    commonDialog
  },
  mounted() {
    this.init()
  },
  data() {
    return {
      announceType: {},
      loading: false,
      searchVo: {},
      receivers: [],
      options: [],
      valueDate: [],
      announceList: {
        list: [],
        pageSize:15,
        pageNum: 1,
        total:1
      },
      pageNum: 1,
      dialogDetail: {
        type: 'announce',
        title: '',
        btn: ''
      },
      showVisiable: false,
      announceDetail: {}
    }
  },
  methods: {
    init() {
      this.announceType = announceType
      this.getAllAnnounceList(1)
    },
    deleteAnnounce(announceId) {
      this.$confirm('删除后不可恢复，是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          closeOnClickModal: false,
          closeOnPressEscape: false,
          type: 'warning'
      }).then(() => {
        this.postJson('/admin/deleteAnnounce.do/' + announceId).then(resp => {
        if (resp) {
          this.getAllAnnounceList(1)
        }
      })
      }).catch(() => {
        
      })
    },
    async showDialog(type, announce) {
      if (type == 'add') {
        this.dialogDetail.title='发布公告'
        this.dialogDetail.btn= '确认发布'
        this.showVisiable = true
        // this.announceDetail.sender =  this.$store.state.currentUser.id
      } else if (type == 'edit') {
        this.dialogDetail.title = '编辑公告内容'
        this.dialogDetail.btn = '保存'
        // let resp = get('/backend/user/getAnnounceDetail.do/' + announce.id
        let resp = await this.get('/backend/user/getAnnounceDetail.do/' + announce.id)
        if (resp) {
          this.announceDetail = resp.data
          this.announceDetail.sender =  this.$store.state.currentUser.id
          this.announceDetail.receiverName = announce.receiverName
          this.showVisiable = true
        }
        
      }
    },
    handleDialogClose(announce) {
      if (announce) {
        this.announceDetail = announce
        this.addOrUpdateAnnounce(announce.id)
      }
      this.announceDetail = {}
      this.showVisiable = false
    },
    onSubmit() {
      if (this.valueDate) {
        this.searchVo.createTImeFrom = this.valueDate[0]
        this.searchVo.createTImeTo = this.valueDate[1]
      }
      this.getAllAnnounceList(1)
    },
    handleCurrentChange(currentPage) {
      this.getAllAnnounceList(currentPage)
    },
    getAllAnnounceList(pageNum) {
      this.loading=true
      this.postJson('/admin/getAllAnnounceList.do/'+ pageNum, this.searchVo).then(resp => {
        if (resp.data) {
          this.announceList = resp.data
          this.loading = false
          this.valueDate = []
        }
      })
    },
    addOrUpdateAnnounce(id) {
      if (id) {
        this.postJson('/admin/addOrUpdateAnnounce.do/' + this.announceDetail.id, this.announceDetail).then(resp => {
          if (resp) {
            this.getAllAnnounceList(1)
          }
        })
      } else {
        this.postJson('/admin/addOrUpdateAnnounce.do/', this.announceDetail).then(resp => {
          if (resp) {
            this.getAllAnnounceList(1)
          }
        })
      }
    },
    remoteMethod(query) {
      // query为当前输入值
      this.receivers = []
      if (query !== '') {
        this.get('/admin/getUserListByUserName.do/' + query).then(resp => {
          if (resp && resp.status == 0) {
            let result = resp.data
            result.map(item=>{
              this.receivers.push({
                label: item.nickName,
                value: item.id
              })
            })
          }
        })
        this.loading = true
        setTimeout(() => {
          this.loading = false
          this.options = this.receivers.filter(item => {
            return item.label.indexOf(query) > -1
          })
        }, 200)
      } else {
        this.options = []
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