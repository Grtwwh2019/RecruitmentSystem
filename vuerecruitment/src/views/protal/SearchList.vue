<template>
  <div class='index'
    v-loading="loading"
    element-loading-text="正在查询..."
    element-loading-spinner="el-icon-loading"
    element-loading-background="rgba(0, 0, 0, 0.8)">
    <div class="index_top">
      <div class="search">
        <div class="search_box">
          <div class="tab-wrapper" style="margin-bottom: 13px;">
            <el-radio-group fill="#00b38a" v-model="searchType" @change="initList">
              <el-radio-button :label="0">职位</el-radio-button>
              <el-radio-button :label="1">公司</el-radio-button>
            </el-radio-group>
          </div>
          <input class="search_input" v-model="searchKeyWord" type="text" placeholder="请输入搜索关键字" />
          <input type="submit" class="search_btn" value="搜索" @click="searchList(1)" />
          <!-- 热点收集算法 -->
          <!-- <div class="hotSearch">
              <span>热门搜索：</span>
              <ul class="hotList">
                  <li v-for="(item,index) in hotList" :key="index"><router-link to="">{{item}}</router-link></li>
              </ul>
          </div> -->
        </div>
      </div>
    </div>
    <div class="index-left">
        <div class="filter">
          <div class="filter_box">
            <el-form :model="searchVo" ref="searchVo" :rules="rules" inline style="padding: 25px;" >

              <el-form-item prop="city" label="城市" style="margin-right:35px;">
                <el-cascader :options="this.$store.state.citySet" v-model="searchVo.city" :props="uaualCascaderProp" :show-all-levels="false" placeholder="请选择城市" clearable></el-cascader>
              </el-form-item>

              <el-form-item v-show="searchType == 1" prop="industry" label="行业" style="margin-right:35px;">
                <el-cascader :options="this.$store.state.industrySet" v-model="searchVo.industry" :props="uaualCascaderProp" :show-all-levels="false" placeholder="请选择行业" clearable></el-cascader>
              </el-form-item>


              <el-form-item v-show="searchType == 0" prop="etypeid" label="职类" style="margin-right:35px;">
                <el-cascader :options="this.$store.state.positionTypeSet" v-model="searchVo.etypeid" :props="uaualCascaderProp" :show-all-levels="false" placeholder="请选择职类" clearable></el-cascader>
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
              
              <el-form-item v-show="searchType == 0" prop="education" label="学历" style="margin-right:35px;">
                <el-select v-model="searchVo.education" placeholder="请选择学历要求">
                  <el-option
                    v-for="(value, key) in degrees"
                    :key="key"
                    :label="value"
                    :value="Number(key)">
                  </el-option>
                </el-select>
              </el-form-item>
              
              <el-form-item prop="ecompanyId" v-show="searchType == 0" label="公司" style="margin-right:35px;">
                <el-select
                  v-model="searchVo.ecompanyId"
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

              <el-form-item v-show="searchType == 0" label="时间" style="margin-right:35px;" prop="valueDate">
                <el-date-picker
                  style="width:508px;"
                  v-model="searchVo.valueDate"
                  type="daterange"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  placeholder="请选择发布时间"
                  format="yyyy 年 MM 月 dd 日"
                  value-format="yyyy-MM-dd"
                  unlink-panels
                  @input="handleDateChange">
                </el-date-picker>
              </el-form-item>

              <el-button style="width:257px;" @click="resetForm('searchVo')">重置搜索框</el-button>

            </el-form>
          </div>
        </div>
        <div class="itemList">
          <div class="itemList-box">
            <!-- style="margin: 0;padding: 0;font-weight: 400;" -->
            <ul class="jobList" v-if="searchType == 0" >
              <li v-for="job in jobList.list" :key="job.id" @click="toJobDetail(job)" style="cursor:pointer; border-bottom: 1px solid #ddd;padding-bottom: 100px;margin-bottom:30px;">
                <div class="reco-job-main">
                  <div class="reco-job-pic">
                    <a target="_blank">
                      <img :src="ftp_prefix+'img/'+job.companyLogo">
                    </a>
                  </div>
                  <div class="reco-job-cont">
                    <p class="reco-job-title" target="_blank">{{job.ename}}</p>
                    <div class="reco-job-com">
                      <p target="_blank">{{job.ecompany}}</p>
                    </div>
                    <span style="font-size: 13px;">{{job.industry}} / {{financing[job.financing]}} / {{companySize[job.csize]}}</span>
                  </div>
                  <div class="reco-job-info">
                    <div class="">
                      <span class="nk-txt-ellipsis job-address">
                        <i class="el-icon-map-location"></i>{{job.ecity}}
                      </span>
                      <span><span class="ico-nb">¥</span>{{job.esalaryfloor}}-{{job.esalaryceilie}}</span>
                      <span>应届毕业生 / {{degrees[job.educationrequire]}}</span>
                      <span style="margin-left: 30px;">{{job.createTime}} 发布</span>
                      <!-- <span class="job-status-tag job-status-tag1">处理快</span> -->
                    </div>
                    <div class="reco-job-detail">
                      <span style="margin-right:10px;">
                        <p target="_blank"
                          class="publisher-name level-color-3" data-card-uid="355461362" 
                          style="margin:0;font-size:16px;" data-card-index="1" data-title="" data-tips-index="12">{{job.etype}}</p>
                        
                        <!-- <a href="/user/authentication" class="identify-icon" target="_blank" :data-title="job.ename" data-tips-index="13">
                          <img class="identity-icon" data-identity="5" src="//static.nowcoder.com/nc/image/identity/5.png">
                        </a> -->
                      </span>
                      <!-- <div class="reco-job-status">
                        <span class="intern_center" data-title="当前职位处理率：100%" data-tips-index="14">简历处理率：100%</span>
                        <span class="" data-title="当前职位处理时间：1天" data-tips-index="15">平均处理：1天</span>
                      </div> -->
                    </div>
                  </div>
                </div>
              </li>
            </ul>
            <ul class="companyList" v-if="searchType == 1">
              <li v-for="company in companyList.list" :key="company.id" @click="toCompanyDetail(company)" style="cursor:pointer; border-bottom: 1px solid #ddd;padding-bottom: 100px;margin-bottom:30px;">
                <div class="cl_left" style="float: left;margin-right: 30px;">
                  <p style="width: 80px;height: 80px;display: block;border: 1px solid #f2f2f2;margin-right: 17px;"
                    <img style="border: 0;display: block;vertical-align: bottom;" :src="ftp_prefix + 'img/' + company.logo" width="80" height="80">
                  </p>
                </div>

                <div class="cl_r" style="float: left;width: 800px;zoom: 1;">
                  <div class="cl_r_top" style="padding-top: 8px;height: 37px;line-height: 37px;">
                    <h3><p class="company_link">{{company.cname}}</p></h3>
                    <div style="margin-left: 30px;">{{company.representative}}</div>
                  </div>
                  <div class="cl_r_bot">
                    <ul class="list_c">
                      <li class="address"><i class="el-icon-location-outline" style="margin-right:5px;"></i>{{company.ccity}}</li>
                      <li class="indu"><i class="el-icon-aim" style="margin-right:5px;"></i>{{company.industry}}</li>
                      <li class="csize"><i class="el-icon-office-building" style="margin-right:5px;"></i>{{companySize[company.csize]}}</li>
                      <li class="finacing"><i class="el-icon-box" style="margin-right:5px;"></i>{{financing[company.financing]}}</li>
                      <!-- <li class="posi"><a href="https://www.lagou.com/gongsi/j40459.html" target="_blank"><span></span>258个在招职位</a></li>
                      <li class="inter"><a href="https://www.lagou.com/gongsi/i40459.html" target="_blank"><span></span>60个面试评价</a></li> -->
                    </ul>
                  </div>
                </div>
              </li>
            </ul>
            
            <div class="itemListPagination">
              <el-pagination
                v-show="searchType == 0"
                background
                prev-text="上一页"
                next-text="下一页"
                layout="prev, pager, next, jumper"
                :page-size.sync="jobList.pageSize"
                :current-page.sync="jobList.pageNum"
                :total="jobList.total"
                @current-change="handleCurrentChange">
              </el-pagination>
              
              <el-pagination
                v-show="searchType == 1"
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
          </div>
        </div>

    </div>
    <el-backtop></el-backtop>
    
  </div>
</template>

<script>
// import { getAllPositionType, getAllIndustry, getAllCity } from "@/api/index";
import { companySize, financing, degree } from "@/const/index";
import { getCompanyListByCname, searchList } from "@/api/index";

export default {
  mounted() {
    // console.log(this.$route.query.searchKeyWord)
    this.init()
  },
  data() {
    return {
      loading: false,
      ftp_prefix: 'http://attachment.grtwwh.com:8080/',
      companyList: {},
      jobList:{},
      valueDate: [],
      // 显示的company列表
      companys: [],
      options: [],
      loading: false,
      degrees: {},
      companySize: {},
      financing: {},
      uaualCascaderProp: {
        emitPath : false
      },
      searchType: 0,
      searchKeyWord: '',
      searchVo: {
        city: null,
        industry: null,
        etypeid: null,
        csize: null,
        financing: null,
        education: null,
        ecompanyId: null,
        valueDate: null
      },
      rules: {}
    }
  },
  methods: {
    init() {
      this.degrees = degree
      this.companySize = companySize
      this.financing = financing
      if (this.$route.query.searchKeyWord) {
        this.searchKeyWord = this.$route.query.searchKeyWord
      }
      if (this.$route.query.etypeid) {
        this.searchVo.etypeid = this.$route.query.etypeid
      }
      if (this.$route.query.searchType) {
        this.searchType = this.$route.query.searchType
      }
      this.searchList(1)
    },
    initList() {
      this.searchList(1)
      this.$refs.searchVo.resetFields()
    },
    toCompanyDetail(company) {
      // window.sessionStorage.setItem("jobListParam", JSON.stringify(job))
      // 在新页面打开
      let routeUrl = this.$router.resolve({
        path: "/companyDetail/" + company.id
        // query: {
        //   companyDetail: companyDetail
        // }
      })
      window.open(routeUrl.href, '_blank');
    },
    toJobDetail(job) {
      window.sessionStorage.setItem("jobListParam", JSON.stringify(job))
      // 在新页面打开
      let routeUrl = this.$router.resolve({
        path: "/jobsDetail",
        query: {
          job: job
        }
      })
      window.open(routeUrl.href, '_blank');
    },
    handleCurrentChange(currentPage){
      // console.log(this.searchVo)
      // console.log(currentPage)
      this.searchList(currentPage)
      window.scrollTo(0,0);
    },
    searchList(pageNum) {
      this.searchType == 0 ? this.searchVo.ename = this.searchKeyWord : this.searchVo.cname = this.searchKeyWord 
      if (this.searchVo.valueDate) {
        this.searchVo.createTImeFrom = this.searchVo.valueDate[0]
        this.searchVo.createTImeTo = this.searchVo.valueDate[1]
      }
      let param = {
        pageNum: pageNum,
        type: this.searchType,
        searchVo: this.searchVo
      }
      // console.log(param)
      this.loading = true
      searchList(param).then(resp => {
        this.loading = false
        if (resp && resp.status == 0) {
          this.searchType == 0 ? this.jobList = resp.data : this.companyList = resp.data
          // console.log(this.jobList)
        }
      })
    },
    remoteMethod(query) {
      // query为当前输入值
      if (query !== '') {
        getCompanyListByCname(query).then(resp => {
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
    }
  }
}
</script>

<style scoped>
.index{
    clear:both;
    width:100%;
}
.index_top{
    position: relative;
    background-color: #F2F5F4;
    padding-bottom: 20px;
}
.scan{
    position:absolute;
    padding:10px;
    border:1px solid #eee;
    right:50px;
    top:20px;
    text-align: center;
    background-color:#fff;
}
.scan span{
    display: block;
    font-size:12px;
    color:#00b38a;
    margin-bottom:10px;
}
.scan img{
    width:85px;
    height:85px;
}
.scan .cuo{
    background: #fff;
    padding: 2px;
    text-align: center;
    display: inline-block;
    position: absolute;
    border: 1px solid #eee;
    top: -1px;
    right: -22px;
    cursor: pointer;
}
.search{
    padding:30px 0;
}
.search_box{
    border: 0 solid #00b38a;
    width: 938px;
    /* height: 46px; */
    margin: 0 auto;
    position: relative;
}
.search_input{
    width:760px;
    height:20px;
    float: left;
    font-size: 16px;
    padding:15px;
    border:1px solid #e8e8e8;
    border-right:none;
}
.search_input:focus{
  outline: 1px solid #00b38a;
  border-right:none;
}
.search_btn{
    width:140px;
    height:52px;
    font-size: 18px;
    color:#fff;
    border:none;
    background-color: #00b38a;
    margin-left:-2px;
}
.index-left {
  position: relative;
  padding-bottom: 20px;
  /* width: 100%;
  height:300px; */
  /* float: left; */
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
.reco-job-pic {
  width: 50px;
  height: 50px;
  line-height: 50px;
  float: left;
  margin-right: 20px;
  overflow: hidden;
}
.reco-job-pic img {
  width: 100%;
}
.reco-job-cont {
  width: 260px;
  padding-right: 50px;
  margin-right: 20px;
  float: left;
  border-right: 1px solid #eee;
}
.reco-job-com, .reco-job-title {
  font-size: 17px;
  display: block;
  margin-bottom: 5px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  font-weight: 600;
}
.reco-job-com {
  font-size: 15px;
  font-weight: 400;
}
.reco-job-info {
  font-size: 14px;
  color: #888;
  float: left;
  width: 495px;
}
.job-address {
    display: inline-block;
    vertical-align: middle;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    max-width: 200px;
    margin-right: 30px;
}
.reco-job-info i {
    margin-right: 3px;
}
.reco-job-info .ico-nb {
    background: #888;
    color: #fff;
    border-radius: 50%;
    width: 14px;
    height: 14px;
    font-size: 12px;
    line-height: 16px;
    margin-right: 3px;
    display: inline-block;
    vertical-align: text-top;
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
.itemListPagination{
  text-align: center;
}
.cl_r_top h3 {
    float: left;
    font-size: 20px;
    max-width: 318px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    word-wrap: normal;
    margin: 0;
}
.cl_r_top div {
    font-size: 16px;
    color: #555;
    max-width: 507px;
    float: left;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    word-wrap: normal;
    padding-left: 10px;
}
.cl_r_bot .list_c {
    height: 36px;
    zoom: 1;
}
.cl_r_bot .list_c li {
    float: left;
    margin-top: 4px;
    margin-right: 45px;
    color: #777;
}
</style>