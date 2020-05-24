<!-- jobsDetail -->
<template>
<div class='jobsDetail'
  v-loading="loading"
  element-loading-text="正在加载..."
  element-loading-spinner="el-icon-loading"
  element-loading-background="rgba(0, 0, 0, 0.8)">
    <div class="jobsTop">
        <div class="position_head">
            <div class="ph_left">
               <div class="job_name">
                   <p class="com_name">{{jobData.ecompany}}</p>
                   <h2 class="zhiwei">{{jobData.ename}}</h2>
                   <p class="jieshao">
                    <span class="salary">{{jobData.esalaryfloor}} - {{jobData.esalaryceilie}} </span>
                    <span>/ {{jobData.ecity}}/</span>
                    <span> 应届毕业生/</span>
                    <span>{{degrees[jobData.educationrequire]}} /</span>
                    <span>全职</span>
                   </p>
                   <ul class="tags">
                     <li>{{jobData.etype}}</li>
                       <!-- <li v-for="(item, index) in jobData.tab" :key="index">{{item}}</li> -->
                   </ul>
                   <p class="time">{{jobData.time}}发布于校园招聘</p>
               </div>
            </div>
            <div class="ph_right">
              <div class="jd_collection  job-collection" @click="collectPosition(jobData.id)">
                <i class="el-icon-star-off" v-if="showStts == 0"></i>
                <i class="el-icon-star-on" v-if="showStts == 1"></i>
                收藏
              </div>

              <div class="resume-deliver">
                <p rel="nofollow" class="send-CV-btn s-send-btn fr" @click="deliverResume(jobData.id)">投个简历</p>                                                                             
              </div>
              
            </div>
            
        </div>
    </div>
    <div class="container">
        <div class="content_l">
            <div class="items">
              <span class="title">岗位职责</span>
              <p style="margin-left:16px;word-wrap:break-word; " >{{jobData.eresponsibility}}</p>
            </div>
            <div class="items">
              <span class="title">岗位要求</span>
              <p style="margin-left:16px;word-wrap:break-word; " >
                {{jobData.erequirement}}
              </p>
              <!-- <ol class="commands"> -->
                  <!-- <li>理工科相关专业，硕士及以上学历，5年以上算法相关工作经验；</li>
                  <li>熟悉 Linux 平台，有扎实的Java / Python 开发能力，具备良好的数据结构和算法基础；</li>
                  <li>具有深度学习技术背景，或问答、对话相关项目背景者优先；</li>
                  <li>掌握机器学习基本算法并有丰富的实践经验；熟悉tensorflow/keras优先；</li>
                  <li>具备较强的自学能力和钻研精神，具有良好的沟通能力和团队合作能力，综合能力强。</li> -->
              <!-- </ol> -->
            </div>
           
        </div>
        <div class="content_r">
           
               <div class="top">
                   <img v-if="jobData.companyLogo" :src="ftp_prefix + 'img/' + jobData.companyLogo">
                   <div style="margin-top:20px;">{{jobData.ecompany}}</div>
               </div>
               <ul class="com_msg">
                   <li><i class="iconfont icon-hulianwang"></i>{{this.jobListParam.industry}}</li>
                   <li><i class="iconfont icon-tongjibaobiao"></i>{{financing[this.jobListParam.financing]}}</li>
                   <li><i class="iconfont icon-wode"></i>{{companySize[this.jobListParam.csize]}}</li>
                   <li></li>
               </ul>
        </div>
    </div>
    <el-backtop></el-backtop>
</div>
</template>

<script>
// import {jobList} from '../../../static/js/jobJSON';
import { companySize, financing, degree } from "@/const/index";
import { getDetail, collectPosition, getUserCollectionStatus, deliver } from "@/api/index";
export default {
  name: 'jobsDetail',
  components: {},
  data() {
    return {
      loading: false,
      showStts: 0,
      ftp_prefix: 'http://attachment.grtwwh.com:8080/',
      degrees: {},
      companySize: {},
      financing: {},
      jobData:{},
      jobListParam: {},
      user: {}
    };
  },
  mounted() {
    this.jobListParam = JSON.parse(window.sessionStorage.getItem("jobListParam"))
    // console.log(this.jobListParam)
    this.companySize = companySize
    this.financing = financing
    this.degree = degree
    this.user = JSON.parse(window.sessionStorage.getItem("user"))
    // this.user = this.$store.user
    if (this.user) {
      this.getUserCollectionStatus()
    }
    this.getDetail()
  },
  created(){
    // this.jobData = Object.assign({}, this.$route.params.job)
    // console.log(this.$route.query.job)
    // console.log(this.jobListParam)
    
  },
  methods: {
    collectPosition(id) {
      let param = {
        positionId: id,
        type: 0
      }
      collectPosition(param).then(resp => {
        if (resp && resp.status == 0) {
          this.showStts = resp.data.showStts
          this.$message({
            center: true,
            showClose: true,
            message: resp.msg,
            type: 'success'
          });
        }
        //  else {
        //   this.$message({
        //     center: true,
        //     showClose: true,
        //     message: resp.msg == null ? "未知错误，请联系管理员" : resp.msg,
        //     type: 'warning'
        //   });
        //   // this.$router.push({
        //   //   path: '/login'
        //   // })
        // }
      })
    },
    deliverResume(id) {
      this.$confirm('是否确认投递?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        closeOnClickModal: false,
        closeOnPressEscape: false,
        type: 'warning'
      }).then(() => {
        this.loading = true;
        deliver(id).then(resp => {
          this.loading = false
          if (resp && resp.status == 0) {
            this.$message({
              center: true,
              showClose: true,
              message: resp.msg,
              type: 'success'
            });
          } else {
            this.$message({
              center: true,
              showClose: true,
              message: resp.msg,
              type: 'error'
            });
          }
          // else {
          //   this.$message({
          //     center: true,
          //     showClose: true,
          //     message: resp.msg == null ? "未知错误，请联系管理员" : resp.msg,
          //     type: 'warning'
          //   });
          //   // this.$router.push({
          //   //   path: '/login'
          //   // })
          // }
        })
      }).catch(() => {
        this.$message({
            type: 'info',
            message: '已取消投递'
        });
      })
    },
    getDetail() {
      this.loading = true
      let param = {
        type: 0,
        id: this.jobListParam.id
      }
      getDetail(param).then(resp => {
        if (resp && resp.status == 0) {
          this.jobData = resp.data
          // console.log(resp)
        }
        this.loading = false
      })
    },
    getUserCollectionStatus() {
      let param = {
        type: 0,
        id: this.jobListParam.id
      }
      getUserCollectionStatus(param).then(resp => {
        if (resp && resp.status == 0) {
          this.showStts = resp.data.showStts
        }
      })
    }
  }
}
</script>
<style scoped>
.container{
    width:1024px;
    display: flex;
    margin:30px auto;
}
.jobsTop{
    width:100%;
    background:#f2f5f4;
    padding:30px 0;
}
.position_head{
    width:1024px;
    margin:0 auto;
    display: flex;
    justify-content: space-between;
}
.ph_left{
    max-width: 700px;
}
.com_name{
    margin-bottom:10px;
    font-size:14px;
    color:#555;
}
.zhiwei{
    display: inline-block;
    font-size:36px;
    color:#333;
    font-weight: 400;
    margin-bottom:10px;
}
.jieshao span{
    font-size:18px;
    color:#555;
}
.jieshao .salary{
    color:#fd5f39;
}
.tags{
    display: flex;
    margin:10px 0;
}
.tags li{
    font-size: 12px;
    padding: 3px 10px;
    margin-right: 5px;
    background: #fff;
    border-radius: 15px;
    color: #888;
}
.time,.items p,.commands li{
    color:#666;
    font-size:14px;
}
.content_l{
    padding:30px 30px 0 0;
    width:680px;
    border-right:2px solid #f2f2f2;
}
.items{
    margin-bottom: 20px;
}

.title{
    display: inline-block;
    font-size:18px;
    font-weight:bold;
    margin-bottom: 10px;
}
.commands{
    margin-left:16px;
}
.commands li{
   line-height:32px;
}
.content_r{
    padding:30px 0 0 30px;
}
.content_r .top img{
    width:100px;
    height:100px;
}
.content_r .top span{
    display: inline-block;
    margin-left:10px;
    font-size: 18px;
    color:#666;
}
.com_msg{
    margin-top:20px;
}
.com_msg li{
    margin-bottom: 15px;
    font-size:14px;
    color:#555;
}
.com_msg .iconfont{
    margin-right:10px;
}
.job-collection {
    display: inline-block;
    height: 44px;
    width: 104px;
    line-height: 44px;
    text-align: center;
    border: 1px solid #00b38a;
    border-radius: 3px;
    background-color: #ebf0ef;
    cursor: pointer;
    font-size: 14px;
    color: #00b38a;
}
.resume-deliver{
    display: inline-block;
    margin-left: 17px;
    padding: 0 30px;
    border: 1px solid #00b38a;
    border-radius: 3px;
    height: 44px;
    line-height: 44px;
    font-size: 18px;
    background-color: #00b38a;
    color: #fff;
    cursor: pointer;
}
</style>