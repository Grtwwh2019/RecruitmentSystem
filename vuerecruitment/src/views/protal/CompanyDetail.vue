<template>
  <div class="companyDetail">
    <div class="top_info">
      <div class="top_info_wrap">
        <img v-if="companyDetail.logo" :src="ftp_prefix + 'img/' + companyDetail.logo" :alt="companyDetail.cname + 'Logo'" width="164" height="164">
        <div class="company_info">
          <div class="company_main">
            <h1 class="company_main_title">
              <p>{{companyDetail.cname}}</p>
                <!-- <a href="https://www.tophant.com" class="hovertips" target="_blank" rel="nofollow" title="上海斗象信息科技有限公司">
                    斗象科技
                </a> -->
            </h1>
            <div class="jd_collection  job-collection" @click="collectCompany(companyDetail.id)">
              <i class="el-icon-star-off" v-if="showStts == 0"></i>
              <i class="el-icon-star-on" v-if="showStts == 1"></i>
              收藏
            </div>
          </div>
        </div>
      </div>
      
    </div>

    <div id="company_navs" class="company_navs">
      <div class="company_navs_wrap">
        <ul data-pjax="">
          <li class="current">
              <p href="">公司主页</p>
          </li>
          <!-- <li>
              <a href="https://www.lagou.com/gongsi/j33219.html" data-lg-tj-id="9100" data-lg-tj-no="0002" data-lg-tj-cid="idnull">招聘职位（82）</a>
          </li>
          <li>
              <a href="https://www.lagou.com/gongsi/j33219.html?schoolJob=true" data-lg-tj-id="9100" data-lg-tj-no="0003" data-lg-tj-cid="idnull">校招职位（2）</a>
          </li>
          <li>
              <a href="https://www.lagou.com/gongsi/q33219.html" data-lg-tj-id="9100" data-lg-tj-no="0004" data-lg-tj-cid="idnull">公司问答</a>
          </li> -->
        </ul>
      </div>
    </div>
    
    <div id="main_container">
      <div id="container_left">
        <div id="containerCompanyDetails">
          <div class="item_container" id="company_intro">
            <div class="item_ltitle">{{companyDetail.cname}}介绍</div>
            <div class="item_content">
              <div class="company_intro_text" style="display: block;">
                  <span class="company_content">
                    <p style="word-wrap:break-word; ">{{companyDetail.cintroduction}}</p>
                  </span>
                  <!-- <span class="text_over" style="background-position: right top;">展开</span> -->
              </div>
              <div class="company_image_gallery">
                <div id="imageGallery">
                  <el-carousel :interval="5000" arrow="always">
                    <el-carousel-item v-for="(item,index) in photo" :key="index">
                      <img width="497px" height="280px" :src="ftp_prefix + 'img/' + item" >
                      <!-- <h3>{{ item }}</h3> -->
                    </el-carousel-item>
                  </el-carousel>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div id="container_right">
        <div class="right_bottom_margin"></div>
        <div class="item_container" id="basic_container">
          <div class="item_ltitle">{{companyDetail.cname}}基本信息</div>
          <div class="item_content" style="padding-bottom: 40px;">
            <ul>
              <li>
                  <i class="el-icon-aim"></i>
                  <span>{{companyDetail.industry}}</span>
              </li>
              <li>
                  <i class="el-icon-box"></i>
                  <span>{{financing[companyDetail.financing]}}</span>
              </li>
              <li>
                  <i class="el-icon-office-building"></i>
                  <span>{{companySize[companyDetail.csize]}}</span>
              </li>
                          <li>
                  <i class="el-icon-location-outline"></i>
                  <span>{{companyDetail.ccityName}}</span>
              </li>
            </ul>
          </div>
          <div class="company_bussiness_info_container">
            <div class="title">工商信息
              <div class="slash"></div>
            </div>
            <div class="info_item">
              <div class="info_item_title">
                <i class="el-icon-monitor"></i>
                <span>工商信息</span>
              </div>
              <div class="content">{{companyDetail.fullname}}</div>
            </div>
            <div class="info_item">
              <div class="info_item_title">
                <i class="el-icon-time"></i>
                <span>成立时间</span>
              </div>
              <div class="content">{{companyDetail.establishtime}}</div>
            </div>
            <div class="info_item">
              <div class="info_item_title">
                <i class="el-icon-coin"></i>
                <span>注册资本</span>
              </div>
              <div class="content">{{companyDetail.assets}}万人民币</div>
            </div>
            <div class="info_item">
                <div class="info_item_title">
                  <i class="el-icon-user"></i>
                  <span>法人代表</span>
                </div>
                <div class="content">{{companyDetail.representative}}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <el-backtop></el-backtop>
  </div>
</template>

<script>
import { getDetail, collectCompany, getUserCollectionStatus } from "@/api/index";
import { companySize, financing, degree } from "@/const/index";
export default {
  mounted() {
    this.init()
  },
  data() {
    return {
      ftp_prefix: 'http://attachment.grtwwh.com:8080/',
      showStts: 0,
      photo: [],
      companySize: {},
      financing: {},
      companyDetail: {},
      user:{}
    }
  },
  methods: {
    init() {
      // this.user = this.$store.user
      this.user = JSON.parse(window.sessionStorage.getItem("user"))
      if (this.user) {
        this.getUserCollectionStatus()
      }
      this.companySize = companySize
      this.financing = financing
      this.getDetail()
    },
    getUserCollectionStatus() {
      let param = {
        type: 1,
        id: this.$route.params.id
      }
      getUserCollectionStatus(param).then(resp => {
        if (resp && resp.status == 0) {
          this.showStts = resp.data.showStts
        }
      })
    },
    collectCompany(id) {
      let param = {
        companyId: id,
        type: 1
      }
      collectCompany(param).then(resp => {
        if (resp && resp.status == 0) {
          this.showStts = resp.data.showStts
          this.$message({
            center: true,
            showClose: true,
            message: resp.msg,
            type: 'success'
          });
        } 
        // else {
        //   this.$message({
        //     center: true,
        //     showClose: true,
        //     message: resp.msg == null ? "未知错误，请联系管理员" : resp.msg,
        //     type: 'warning'
        //   });
        // }
      })
    },
    getDetail() {
      let param = {
        type: 1,
        id: this.$route.params.id
      }
      getDetail(param).then(resp => {
        if (resp && resp.status == 0) {
          this.companyDetail = resp.data
          console.log(this.companyDetail)
          this.photo = this.companyDetail.photo.split(",")
          console.log(this.photo)
        }
      })
    }
  }

}
</script>

<style scoped>
.job-collection {
    display: inline-block;
    height: 44px;
    width: 104px;
    line-height: 44px;
    text-align: center;
    border: 1px solid #00b38a;
    border-radius: 3px;
    /* background-color: #ebf0ef; */
    margin-left: 520px;
    cursor: pointer;
    font-size: 14px;
    color: #00b38a;
}
.company_navs_wrap li.current p {
    color: #333;
}
.company_navs_wrap li p {
    display: block;
    padding: 12px 2px;
    font-size: 18px;
    color: #999;
}
.company_navs_wrap li {
    display: inline-block;
    height: 50px;
    border-top: 2px solid transparent;
    border-bottom: 2px solid transparent;
    margin-right: 35px;
    position: relative;
}
.company_navs_wrap li.current {
    border-bottom: 2px solid #00b38a;
}
.company_navs_wrap ul {
    float: left;
}
.company_navs_wrap {
    width: 1024px;
    height: 54px;
    margin: 0 auto;
}
.company_navs {
    background-color: #f2f5f4;
}
#imageGallery {
    position: relative;
    z-index: 1;
    width: 520px;
    height: 300px;
    margin: 0 auto 50px;
}
.company_bussiness_info_container .info_item .content {
    font-size: 14px;
    line-height: 18px;
    padding-top: 5px;
    color: #666;
    padding-left: 22px;
}
.company_bussiness_info_container .info_item_title span {
    font-size: 14px;
    margin-left: 8px;
}
.company_bussiness_info_container .info_item_title {
    font-size: 14px;
    color: #333;
    line-height: 14px;
}
.company_bussiness_info_container .info_item {
    padding-bottom: 24px;
}
.company_bussiness_info_container .slash {
    display: inline-block;
    border-bottom: 1px solid #e8e8e8;
    width: 157px;
    margin-left: 15px;
    vertical-align: middle;
}
.company_bussiness_info_container .title {
    font-size: 18px;
    color: #000;
    font-weight: 700;
    min-width: 300px;
    margin-bottom: 32px;
}
.company_bussiness_info_container {
    margin-bottom: 46px;
}
li {
    text-align: -webkit-match-parent;
}
i {
    font-style: normal;
}
#basic_container .item_content>ul>li>span {
    position: relative;
    top: 2px;
}
#basic_container .item_content>ul>li>i {
    display: inline-block;
    width: 15px;
    height: 15px;
    margin-right: 20px;
    vertical-align: middle;
}
#basic_container .item_content>ul li+li {
    margin-top: 30px;
}
.item_content>ul>li {
    height: 15px;
    line-height: 15px;
}
.item_content>ul {
    margin-left: 20px;
}
#container_right .right_bottom_margin {
    margin-bottom: 40px;
}
#container_right {
    float: right;
    width: 245px;
}
ul, ol {
    margin: 0;
    padding: 0;
    list-style: none;
}
#company_intro .company_intro_text {
    display: none;
    min-height: 20px;
    margin-bottom: 30px;
    overflow-y: hidden;
    color: #555;
}
.item_container .item_ltitle {
    position: absolute;
    top: -15px;
    left: 0;
    height: 28px;
    padding-right: 16px;
    font-size: 18px;
    line-height: 28px;
    color: #000;
    background-color: #fff;
}
.item_container {
    position: relative;
    width: 100%;
    min-height: 90px;
    padding: 40px 0;
    margin-top: 10px;
    border-top: 1px solid #e8e8e8;
}
#container_left {
    float: left;
    width: 710px;
    padding-right: 35px;
    -webkit-box-shadow: 8px 0 4px -4px #f5f5f5;
    -moz-box-shadow: 8px 0 4px -4px #f5f5f5;
    box-shadow: 8px 0 4px -4px #f5f5f5;
}
#main_container {
    width: 1024px;
    margin: 40px auto;
    overflow: hidden;
    color: #555;
    font-size: 14px;
    min-height: 300px;
}
img {
    border: 0;
    vertical-align: top;
    display: inline-block;
}
.top_info {
    padding: 34px 0 0;
    background: #f2f5f4;
}
.top_info .top_info_wrap {
    position: relative;
    width: 1024px;
    height: 164px;
    margin: 0 auto;
    border: 1px solid #eaeaea;
    background: #fff;
}
.top_info .top_info_wrap img {
    position: absolute;
    top: 0;
    left: 0;
    width: 164px;
    height: 164px;
    border-right: 1px solid #eaeaea;
}
.top_info .top_info_wrap .company_info {
    margin-left: 165px;
}
.company_main {
  position: relative;
  height: 81px;
  padding: 20px 20px 0;
}
.company_main .company_main_title {
    float: left;
    max-width: 350px;
    max-height: 33px;
    padding-right: 2px;
    margin: 0;
    line-height: 33px;
    font-size: 24px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    word-wrap: normal;
}
.company_main .company_main_title p {
    line-height: 33px;
    color: #555;
    background-color: transparent;
    text-decoration: none;
}
.companyDetail{
  font-family: "Hiragino Sans GB","Microsoft Yahei",SimSun,Arial,"Helvetica Neue",Helvetica;
  color: #333;
}
</style>