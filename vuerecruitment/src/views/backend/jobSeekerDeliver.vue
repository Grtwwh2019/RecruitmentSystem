<template>
  <div class="jobSeekerDelivert-Resume">
    <div class="title" style="margin:0 auto;width: 928px;">
      <div class="title_box" style="padding-top:30px;color:rgb(0, 179, 138);font-size:26px;">
        求职者简历投递情况
        <div style="border-bottom: 2px rgb(0, 179, 138) solid;padding-top:20px;"></div>
      </div>
    </div>

    <div class="filter">
      <div class="filter_box">
        <el-form :model="searchVo" ref="searchVo" :rules="rules" inline style="padding: 25px;">
           
          <el-form-item prop="jobberRealName" label="姓名" style="margin-right:30px;">
            <el-input
            v-model="searchVo.jobberRealName" 
            maxlength=255
            placeholder="请输入求职者姓名"
            clearable>
            </el-input>
          </el-form-item>

          <el-form-item prop="education" label="性别" style="margin-right:30px;">
            <el-select v-model="searchVo.gender" placeholder="请选择求职者性别" clearable>
              <el-option
                v-for="(value, key) in gender"
                :key="key"
                :label="value"
                :value="Number(key)">
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item prop="education" label="学历" style="margin-right:30px;">
            <el-select v-model="searchVo.education" placeholder="请选择求职者学历" clearable>
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

          <span style="float:right; padding-right:48px;">
            <el-button style="background-color:#00b38a;color:#fff;" @click="searchResumeList">查询</el-button>
            <el-button style="" @click="resetForm('searchVo')">重置</el-button>
          </span>

        </el-form>
      </div>
    </div>

    <div class="itemList">
      <div class="itemList-box">
        <el-table
          align='center' 
          stripe
          :cell-style='cellStyle' 
          :header-cell-style='rowClass'
          :data="resumeList.list"
          height="700"
          style="width: 100%">

          <el-table-column
            prop="deliveryTime"
            label="投递日期"
            width="150">
          </el-table-column>

          <el-table-column
            prop="employmentName"
            label="职位名称"
            width="280">
            <template slot-scope="scope">
              <a style="text-decoration:underline;color:#00b38a;cursor:pointer;" @click="toJobDetail(scope.row.employmentId)">{{scope.row.employmentName}}</a>
            </template>
          </el-table-column>

          <el-table-column
            prop="realname"
            label="投递人"
            width="160">
          </el-table-column>

          <el-table-column
            label="性别"
            width="120">
            <template slot-scope="scope">
              {{gender[scope.row.gender]}}
            </template>
          </el-table-column>

          <el-table-column
            label="年龄"
            width="120">
            <template slot-scope="scope">
              {{getAge(scope.row.birthday)}}
            </template>
          </el-table-column>

          <el-table-column
            label="学历"
            width="120">
            <template slot-scope="scope">
              {{degree[scope.row.education]}}
            </template>
          </el-table-column>

          <el-table-column
            prop="telephone"
            label="联系方式"
            width="160">
          </el-table-column>
          

          <el-table-column
            fixed="right"
            label="操作"
            width="100">
            <template slot-scope="scope">
              <el-button style="color: #00b38a;" type="text" @click="showDialog(scope.row.id, scope.row.employmentId)" >查看简历</el-button>
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
          :page-size.sync="resumeList.pageSize"
          :current-page.sync="resumeList.pageNum"
          :total="resumeList.total"
          @current-change="handleCurrentChange">
        </el-pagination>
      </div>

      <jobseekerResumeDetailDialog :showVisiable.sync="dialogVisible" :resumeDetail="resumeDetailInfo" @handleResumeDetailClose="handleResumeDetailClose" />

    </div>
  </div>
</template>

<script>
import { gender, degree } from "@/const/index";
import { getUserResumeList, getResumeDetailInfo, searchList } from "@/api/index";
import jobseekerResumeDetailDialog from "@/components/dialog/jobseekerResumeDetailDialog.vue";
export default {
  components: {
    jobseekerResumeDetailDialog
  },
  mounted() {
    this.init()
  },
  data() {
    return {
      resumeDetailInfo: {},
      age: "",
      degree: {},
      gender: {},
      resumeList: [],
      pageNum: 1,
      dialogVisible: false,
      searchVo:{
        employmentId:null,
        jobberRealName:null,
        gender:null,
        education:null,
        createTImeFrom:null,
        createTImeTo:null
      },
      rules: {}
    }
  },
  methods: {
    init() {
      this.gender = gender
      this.degree = degree
      this.getUserResumeList(1)
    },
    async toJobDetail(employmentId){
      let job = {}
      let param = {
        type: 0,
        pageNum: 1,
        searchVo: {
          employmentId: employmentId
        }
      }
      let resp = await searchList(param)
      if (resp && resp.status == 0) {
        job = resp.data.list[0]
        window.sessionStorage.setItem("jobListParam", JSON.stringify(job))
        // 在新页面打开
        let routeUrl = this.$router.resolve({
          path: "/jobsDetail",
          query: {
            job: job
          }
        })
        window.open(routeUrl.href, '_blank');
      } else {
        this.$message.warning(resp.msg ? resp.msg : "未知错误，请联系管理员")
      }
//       export const searchList = (params) => {
//   return postJson('/common/searchList.do/' + params.type + "/" + params.pageNum, params.searchVo)
// }
    },
    handleResumeDetailClose() {
      this.dialogVisible = false
    },
    async showDialog(resumeId, employmentId) {
      let resp = await getResumeDetailInfo(resumeId, employmentId)
      if (resp && resp.status == 0) {
        this.resumeDetailInfo = resp.data
      } else {
        this.$message.warning(resp.msg ? resp.msg : "未知错误，请联系管理员")
      }
      // console.log(this.resumeDetailInfo)
      this.dialogVisible = true
    },
    searchResumeList() {
      this.getUserResumeList(1)
    },
    async getUserResumeList(pageNum) {
      let param = {
        pageNum: pageNum,
        searchVo: this.searchVo
      }
      let resp = await getUserResumeList(param)
      if (resp && resp.status == 0) {
        this.resumeList = resp.data
        // console.log(this.resumeList)
      }else{
        this.$message.warning(resp.msg ? resp.msg : "未知错误，请联系管理员")
      }
    },
    handleCurrentChange(currentPage){
      // console.log(this.searchVo)
      // console.log(currentPage)
      this.getUserResumeList(currentPage)
      // window.scrollTo(0,0);
    },
    // 根据生日（出生年月日）计算年龄
    getAge(birthday){
      let birthdays = new Date(birthday.replace(/-/g, "/"));
      let d = new Date();
      let age =
        d.getFullYear() -
        birthdays.getFullYear() -
        (d.getMonth() < birthdays.getMonth() ||
        (d.getMonth() == birthdays.getMonth() &&
        d.getDate() < birthdays.getDate())
          ? 1
          : 0);
      this.age = age;
      return age
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
    resetForm(formName) {
      this.$refs[formName].resetFields();
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