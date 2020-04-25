<template>
  <el-dialog class="resumeDetailDialog" title="简历详情" width="40%" :visible.sync="showVisiable" :close-on-click-modal="false" :close-on-press-escape="false" @open="handleOpen" :before-close="handleClose">
    <div class="my_resume">
      <div class="basic">
        <div class="my_title" >
            <h4>基本信息</h4>
        </div>
        <div class="my_img">
          <img fit="fill" :src="ftp_prefix + 'img/' + resumeInfo.baseInfo.headermask" :size="80" class="avatar"></img>
        </div>
        <div class="my_info">
          <h3 style="margin-right:10px;">姓名：{{resumeInfo.baseInfo.realname}}</h3>
          <i v-show="resumeInfo.baseInfo.gender == 1" class="el-icon-male"></i>
          <i v-show="resumeInfo.baseInfo.gender == 2" class="el-icon-female"></i>
          <p v-if="resumeInfo.educationExpList.length > 0">{{resumeInfo.baseInfo.education.schoolName}} / {{degree[resumeInfo.baseInfo.education.degree]}}</p>
          <p class="tel">
              <span><i class="iconfont icon-shouji"></i>{{resumeInfo.baseInfo.telephone}}</span>
              <span><i class="iconfont icon-youxiang"></i>{{resumeInfo.baseInfo.email}}</span>
          </p>
        </div>
      </div>

      <div class="my_tml">
        <ul>
          <li>
            <div class="my_title" >
              <h4>求职意向</h4>
            </div>
            <ul class="res_list">
              <li>
                <i v-show="resumeInfo.baseInfo.expectposition" class="iconfont icon-H"></i>{{resumeInfo.baseInfo.expectposition}}
                <i v-show="resumeInfo.baseInfo.city" class="iconfont icon-dingwei"></i>{{resumeInfo.baseInfo.city}}
                <i v-show="resumeInfo.baseInfo.lowsalary" class="iconfont icon-wallet"></i>{{resumeInfo.baseInfo.lowsalary}} - {{resumeInfo.baseInfo.highsalary}}
              </li>
              <!-- <li><i class="iconfont icon-dingwei"></i>{{resumeInfo.baseInfo.city}}</li> -->
              <!-- <li><i class="iconfont icon-wallet"></i>{{resumeInfo.baseInfo.lowsalary}} - {{resumeInfo.baseInfo.highsalary}}</li> -->
              <li><i v-show="resumeInfo.baseInfo.industryDesc" class="iconfont icon-icon_dingtalk_line"></i>{{resumeInfo.baseInfo.industryDesc}}</li>
            </ul>
          </li>
          <li>
            <div class="my_title" >
              <h4>个人优势</h4>
            </div>
            <div style="padding-left:15px;position: relative;min-height: 40px;">
              <div class="my_intro" style="word-break:break-all;width: 90%;margin-top: 13px;display: flex;font-size: 14px;color: #666;line-height: 30px;">
                <p>{{resumeInfo.baseInfo.advantage}}</p>
              </div>
            </div>
          </li>
          <li >
            <div class="my_title">
              <h4>教育经历</h4>
            </div>
            <div v-if="resumeInfo.educationExpList && resumeInfo.educationExpList.length > 0">              
              <div class="school_info" style="padding: 8px 0 8px 15px;position: relative;min-height: 40px;" v-for="(school, index) in resumeInfo.educationExpList" :key="index">
                <div class="sc_in">
                    <h3 >{{school.schoolName}}</h3>
                    <p class="r1">{{school.startdate}} 至 {{school.enddate}}</p>
                </div>
                <p style="margin-bottom:10px">{{degree[school.degree]}} {{schoolType[school.schoolType]}} / {{school.major}}</p>
              </div>
            </div>
            <div v-else style="text-align:center;">无</div>
          </li>
          <li>
            <div class="my_title">
              <h4>工作经历</h4>
            </div>
            <div v-if="resumeInfo.workExpList && resumeInfo.workExpList.length > 0">
              <ul class="work-exp_group" v-for="(workExp,index) in resumeInfo.workExpList" :key="index">
                <li class="work-exp_list" style="padding: 8px 0 8px 15px;position: relative;min-height: 40px;">
                  <div class="primary-info" style="padding: 7px 0;color: #61687c;white-space: pre-wrap;overflow: hidden;word-break: break-all;">
                    <div class="info-text">
                      <h4 class="name" style="max-width: 420px;word-break: break-all;float: left;color: #414a60;">
                        <span>{{workExp.cname}}</span>
                        <span v-if="workExp.department">／ {{workExp.department}}</span>
                      </h4>
                      <span class="gray period" style="font-size: 12px;padding-left: 30px;float: left;position: relative;top: 2px;">
                        {{workExp.startdate}} 至 {{workExp.enddate}}
                      </span>
                    </div>
                    <h4 style="font-size: 15px;font-weight: 700;margin-top:20px">
                      <span class="prev-line" style="max-width: 250px;">{{workExp.positionTypeZh}}</span>
                    </h4>
                    <div class="info-text" style="padding: 7px 0;color: #61687c;white-space: pre-wrap;overflow: hidden;word-break: break-all;">
                      <span class="text-type" style="font-weight: 700;">内容：
                      </span>{{workExp.workcontent}}
                    </div>
                    <div class="keywords" style="padding: 7px 0 5px;">
                      <span v-for="tag in workExp.skillTags" v-if="tag"
                        style="display: inline-block;font-size: 12px;line-height: 20px;color: #9fa3b0;padding: 0 14px;margin-right: 10px;border: 1px solid #cfd1d7;border-radius: 50px;">
                        {{tag.skillTag}}
                      </span>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
            <div v-else style="text-align:center;">无</div>
          </li>
          <li>
            <div class="my_title">
              <h4>项目经历</h4>
            </div>
            <div v-if=" resumeInfo.projectExpList && resumeInfo.projectExpList.length > 0">
              <ul  class="project-exp" v-for="(project, index) in resumeInfo.projectExpList" :key="index">
                <li class="work-exp_list" style="padding: 8px 0 8px 15px;position: relative;min-height: 40px;">
                  <div class="primary-info" style="padding: 7px 0;color: #61687c;white-space: pre-wrap;overflow: hidden;word-break: break-all;">
                    <div class="info-text">
                      <h4 class="name" style="max-width: 420px;word-break: break-all;float: left;color: #414a60;">
                        <span>{{project.projectName}}</span>
                      </h4>
                      <span class="gray period" style="font-size: 12px;padding-left: 30px;float: left;position: relative;top: 2px;">
                        {{project.starttime}} 至 {{project.endtime}}
                      </span>
                    </div>
                    <h4 style="font-size: 15px;font-weight: 700;margin-top:20px;margin-bottom:20px">
                      <span class="prev-line" style="max-width: 250px;">{{project.role}}</span>
                    </h4>
                    <div class="info-text" style="padding: 7px 0;color: #61687c;white-space: pre-wrap;overflow: hidden;word-break: break-all;">
                      <span class="text-type" style="font-weight: 700;">内容：
                      </span>{{project.description}}
                    </div>
                    
                  </div>
                </li>
              </ul>
            </div>
            <div v-else style="text-align:center;">无</div>
          </li>
          <li>
            <div class="my_title">
              <h4>资格证书</h4>
            </div>
            <ul v-if="resumeInfo.certificationList && resumeInfo.certificationList.length > 0" class="certification">
              <li class="certificate-list" style="padding: 8px 0 8px 15px;position: relative;min-height: 40px;">
                <div class="primary-info">
                  <div class="visible-wrap" style="position: relative;padding-right: 70px;max-height: 66px;overflow: hidden;">
                    <div>
                      <span class="resume-cert-tag"
                        style="display: inline-block;
                              font-size: 12px;
                              font-weight: 400;
                              color: #61687c;
                              white-space: nowrap;
                              height: 23px;
                              line-height: 22px;
                              border-radius: 2px;
                              border: 1px solid #d0d1d6;
                              padding: 0 10px;
                              margin: 5px 10px 5px 0;
                              overflow: hidden;
                              vertical-align: top;
                              position: relative;"
                        v-for="(item, index) in resumeInfo.certificationList">
                        {{item.certificationName}}
                      </span>
                      
                    </div>
                  </div>
                </div>
              </li>
            </ul>
            <div v-else style="text-align:center;">无</div>
          </li>
          <li v-if="resumeInfo.baseInfo.socialpage && resumeInfo.baseInfo.socialpage != ''">
            <div class="my_title">
              <h4>社交主页</h4>
            </div>
            <div class="social-page" 
              v-if="resumeInfo.baseInfo.socialpage"
              style="padding: 8px 0 8px 15px;
              position: relative;
              min-height: 40px;">
              <div>
                <img :src="ftp_prefix + 'img/' + 'socialPage.png'" class="social-img">
                <a :href="resumeInfo.baseInfo.socialpage" target="_blank">{{resumeInfo.baseInfo.socialpage}}</a>
              </div>
            </div>
          </li>
          <li v-if="resumeInfo.baseInfo.resumeattachment && resumeInfo.baseInfo.resumeattachment != ''">
            <div class="my_title" >
              <h4>简历附件</h4>
            </div>
            <div class="social-page" 
              
              style="padding: 8px 0 8px 15px;
              position: relative;
              min-height: 40px;">
              <div>
                <a :href="ftp_prefix + 'attachment/' + resumeInfo.baseInfo.resumeattachment" target="_blank">{{resumeInfo.baseInfo.resumeattachment}}</a>
              </div>
            </div>
          </li>
          <li v-if="resumeInfo.baseInfo.otherattachment && resumeInfo.baseInfo.otherattachment != ''">
            <div class="my_title" >
              <h4>其他附件</h4>
            </div>
            <div class="social-page" 
              
              style="padding: 8px 0 8px 15px;
              position: relative;
              min-height: 40px;">
              <div>
                <a :href="ftp_prefix + 'attachment/' + resumeInfo.baseInfo.otherattachment" target="_blank">{{resumeInfo.baseInfo.otherattachment}}</a>
              </div>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </el-dialog>
</template>

<script>
import { degree, schoolType } from "@/const/index";
export default {
  props: {
    showVisiable: {type: Boolean},
    resumeDetail: {type: Object}
  },
  mounted() {
    this.degree = degree
    this.schoolType = schoolType
  },
  data(){
    return {
      schoolType:{},
      degree: {},
      ftp_prefix: 'http://attachment.grtwwh.com:8080/',
      resumeInfo: {
        baseInfo:{},
        workExpList:{},
        projectExpList:{},
        educationExpList:{},
        certificationList:{}
      }
    }
  },
  methods: {
    handleOpen() {
      this.resumeInfo.baseInfo = this.resumeDetail.resumeBaseInfoVo
      this.resumeInfo.workExpList =  this.resumeDetail.workExpListVos
      this.resumeInfo.projectExpList =  this.resumeDetail.projectExpListVos
      this.resumeInfo.educationExpList = this.resumeDetail.educationExpListVos
      this.resumeInfo.certificationList = this.resumeDetail.certificationListVos
      if (this.resumeInfo.educationExpList.length > 1) {
        // let educationExpList = []
        let tempDegree = 0
        this.resumeInfo.educationExpList.forEach(element => {
          if (element.degree > tempDegree) {
            tempDegree = element.degree
          }
        });
        let i = 0
        this.resumeInfo.educationExpList.forEach(element => {
          if (element.degree == tempDegree) {
            this.resumeInfo.baseInfo.education = element
          }
        })
        this.resumeInfo.educationExpList.sort(this.compare("degree", 1))
        this.resumeInfo.workExpList.sort(this.compare("id", 1))
        this.resumeInfo.projectExpList.sort(this.compare("id", 1))
        this.resumeInfo.certificationList.sort(this.compare("id", 2))
      } else {
        this.resumeInfo.baseInfo.education = this.resumeInfo.educationExpList[0]
      }
      console.log(this.resumeInfo.educationExpList && this.resumeInfo.educationExpList.length > 0)
    },
    handleClose() {
      this.$emit('handleResumeDetailClose', false)
    },
    compare(prop, type) {
      return function (obj1, obj2) {
        var val1 = obj1[prop];
        var val2 = obj2[prop];
        if (!isNaN(Number(val1)) && !isNaN(Number(val2))) {
          val1 = Number(val1);
          val2 = Number(val2);
        }
        // 1:降序
        if (type == 1) {
          if (val1 > val2) {
            return -1;
          } else if (val1 < val2) {
            return 1;
          } else {
            return 0;
          }  
          // 2.升序
        } else if (type == 2) {
          if (val1 < val2) {
            return -1;
          } else if (val1 > val2) {
            return 1;
          } else {
            return 0;
          }  
        }
        
      } 
    }
  }
}
</script>

<style scoped>
a:link {text-decoration:underline;color: #00b38a;}    /* 未被访问的链接 */
a:visited {text-decoration:none;color: #00b38a;} /* 已被访问的链接 */
a:hover {text-decoration:underline;color: #00b38a;}   /* 鼠标指针移动到链接上 */
a:active {text-decoration:underline;color: #00b38a;}  /* 正在被点击的链接 */
.iconfont{
    vertical-align: middle;
}

.res_list li{
    color:#666;
    margin-top:15px;
}
.res_list .iconfont{
  padding-left:15px;
  margin-right:10px;
}
.social-img {
    width: 25px;
    height: 25px;
    border-radius: 100%;
    margin-right: 15px;
    vertical-align: middle;
}
.info-text {
    padding: 7px 0;
    color: #61687c;
    white-space: pre-wrap;
    overflow: hidden;
    word-break: break-all;
}
.sc_in{
  width:550px;
  /* margin-top: 10px; */
  margin-bottom:12px;
}
.my_title{
  font-size: 18px;
  border-left: 3px solid #00b38a;
  padding-left: 5px;
  margin-bottom: 10px;
  margin-top: 10px;
}
.my_img img{
  padding-left:15px;
  width:80px;
  height:80px;
    /* border-radius: 50%; */
}
.my_img,h3,h4,.add,.school_img,.school_info{
    display: inline-block;
}
.tel span{
    margin-right:20px;
}
.my_info{
    display: inline-block;
    margin-left:20px;
}
.my_info p{
    margin:10px 0;
    font-size:14px;
    color:#333;
}
</style>