<template>
  <div class="companyManage"
    v-loading="loading"
    element-loading-text="拼命加载中"
    element-loading-spinner="el-icon-loading"
    element-loading-background="rgba(0, 0, 0, 0.8)">

    <div class="filter-box">
      <el-form :inline="true" :model="searchVo" class="demo-form-inline" size="small">

        <el-form-item prop="companyId" label="公司" style="margin-right:35px;">
          <el-select
            v-model="searchVo.companyId"
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

        <el-form-item prop="industry" label="行业" style="margin-right:35px;">
          <el-cascader :options="this.$store.state.industrySet" v-model="searchVo.industry" :props="uaualCascaderProp" :show-all-levels="false" placeholder="请选择行业" clearable></el-cascader>
        </el-form-item>

        <el-form-item prop="csize" label="规模" style="margin-right:35px;">
          <el-select v-model="searchVo.csize" placeholder="请选择规模" clearable>
            <el-option
              v-for="(value, key) in companySize"
              :key="key"
              :label="value"
              :value="Number(key)">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item prop="finacing" label="融资" style="margin-right:35px;">
          <el-select v-model="searchVo.finacing" placeholder="请选择融资阶段" clearable>
            <el-option
              v-for="(value, key) in financing"
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
              placeholder="请选择公司信息创建时间"
              format="yyyy 年 MM 月 dd 日"
              value-format="yyyy-MM-dd"
              unlink-panels
              clearable>
            </el-date-picker>
          </el-form-item>


        <el-form-item style="margin-left:45px;">
          <el-button type="primary" @click="onSubmit" style="width:120px;background-color: #00b38a;border-color:#00b38a;">查询</el-button>
        </el-form-item>
      </el-form>  
    </div>

    <!-- <div class="op">
      <div class="op_box">
        <el-button style="margin-left:40px;margin-top:20px;font-size:18px;height:40px;width:10%;background-color:#00b38a;color:#fff;" @click="showDialog('add')">发布公告</el-button>
      </div>
    </div> -->


    <div class="table-data">
      <el-table
          :cell-style='cellStyle' 
          :header-cell-style='rowClass'
          :data="companyList.list"
          height="520"
          style="width: 65%">

          <el-table-column
            prop="id"
            label="公司Id"
            width="100">
          </el-table-column>

          <el-table-column
            prop="cname"
            label="公司名称"
            width="280"
            :show-overflow-tooltip="true">
          </el-table-column>

          <el-table-column
            prop="csize"
            label="规模"
            width="150"
            >
            <template slot-scope="scope">
              <span>{{companySize[scope.row.csize]}}</span>
            </template>
          </el-table-column>

          <el-table-column
            prop="financing"
            label="融资阶段"
            width="150"
            >
            <template slot-scope="scope">
              <span>{{financing[scope.row.financing]}}</span>
            </template>
          </el-table-column>

          <el-table-column
            prop="industry"
            label="行业"
            width="180"
            >
          </el-table-column>

          <el-table-column
            prop="ccity"
            label="城市"
            width="180"
            >
          </el-table-column>

          <el-table-column
            label="操作"
            fixed="right"
            width="100">
            <template slot-scope="scope">
              <el-button style="color: #00b38a;" type="text" @click="showDialog('edit', scope.row)" >编辑</el-button>
              <el-button style="color: #00b38a;" type="text" @click="deletecompany(scope.row.id)" >删除</el-button>
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
        :page-size.sync="companyList.pageSize"
        :current-page.sync="companyList.pageNum"
        :total="companyList.total"
        @current-change="handleCurrentChange">
      </el-pagination>
    </div>

    <commonDialog :dialogDetail.sync='dialogDetail' :showVisiable.sync='showVisiable' :dataSource.sync='companyDetail' @handleDialogClose='handleDialogClose' />


  </div>

</template>

<script>
import commonDialog from '@/components/commonDialog.vue'
import { companySize, financing } from "@/const/index";
export default {
  components:{
    commonDialog
  },
  mounted() {
    this.init()
  },
  data() {
    return {
      companySize: {},
      financing: {},
      uaualCascaderProp: {
        emitPath : false
      },
      loading: false,
      searchVo: {},
      companys: [],
      options: [],
      valueDate: [],
      companyList: {
        list: [],
        pageSize:15,
        pageNum: 1,
        total:1
      },
      pageNum: 1,
      dialogDetail: {
        type: 'company',
        title: '',
        btn: ''
      },
      showVisiable: false,
      companyDetail: {}
    }
  },
  methods: {
    init() {
      this.companySize = companySize
      this.financing = financing
      this.getAllCompanyList(1)
    },
    deletecompany(companyId) {
      this.$confirm('删除后不可恢复，是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          closeOnClickModal: false,
          closeOnPressEscape: false,
          type: 'warning'
      }).then(() => {
        this.postJson('/admin/deleteCompany.do/' + companyId).then(resp => {
        if (resp) {
          this.getAllCompanyList(1)
        }
      })
      }).catch(() => {
        
      })
    },
    async showDialog(type, company) {
      this.loading = true
      if (company) {
        this.dialogDetail.title = '编辑公司信息'
        this.dialogDetail.btn = '保存'
        let resp = await this.get('/common/getDetail.do/1/' + company.id)
        if (resp) {
          this.companyDetail = resp.data
          this.showVisiable = true
          this.loading = false
        }
      }
    },
    async handleDialogClose(company) {
      if (company) {
        let param = {
          id: company.id,
          cname: company.cname,
          logo:company.logo,
          cintroduction: company.cintroduction,
          financing: company.financing,
          industryid: company.industryid,
          csize: company.csize,
          ccity: String(company.ccityId),
          fullname: company.fullname,
          representative: company.representative,
          assets: company.assets,
          photo:company.photo,
          establishtime: company.establishtime
        }
        let resp = await this.postJson('/admin/updateCompany.do', param)
        if (resp) {
          this.getAllCompanyList(1)
        }
      }
      this.companyDetail = {}
      this.showVisiable = false
    },
    onSubmit() {
      if (this.valueDate) {
        this.searchVo.createTImeFrom = this.valueDate[0]
        this.searchVo.createTImeTo = this.valueDate[1]
      }
      this.getAllCompanyList(1)
    },
    handleCurrentChange(currentPage) {
      this.getAllCompanyList(currentPage)
    },
    getAllCompanyList(pageNum) {
      this.loading = true
      this.postJson('/common/searchList.do/1/'+ pageNum, this.searchVo).then(resp => {
        if (resp.data) {
          this.companyList = resp.data
          this.loading = false
          this.valueDate = []
        }
      })
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