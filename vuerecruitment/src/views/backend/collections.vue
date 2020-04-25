<template>
  <div id="container">
    <div class="clearfix">
      <div class="new_section fl">
        <dl class="c_collections">
          <dt>
            <h1 v-if="type == 0">                   
                <em></em> 我收藏的职位
            </h1>
            <h1 v-if="type == 1">                   
                <em></em> 我收藏的公司
            </h1>
            <h1 v-if="type == 2">                   
                <em></em> 已投递的职位
            </h1>
          </dt>
          <dd>
            <form id="collectionsForm" style="height: 600px;overflow-y: auto;">
              <ul class="reset my_collections" v-if="type == 0" v-infinite-scroll="load" infinite-scroll-disabled="disabled" :infinite-scroll-immediate="false">
                <li v-for="(collection, index) in collectionList" :key="index" >
                  <a style="cursor: pointer;" :title="collection.ecompany" @click="toJobDetail(collection)"> 
                    <img :src="ftp_prefix + 'img/' + collection.companyLogo" :alt="collection.ecompany">
                  </a>
                  <div class="co_item">
                    <h2 :title="collection.ename" style="cursor: pointer;" @click="toJobDetail(collection)">
                      <a>
                        <em style="max-width: 200px;">{{collection.ename}}</em>
                        <span>（{{collection.esalaryceilie}} - {{collection.esalaryfloor}}）</span>
                      </a>
                    </h2>
                    <span class="co_time">{{collection.createTime}}</span>
                    <div class="co_cate">{{collection.ecompany}} / {{collection.ecity}} / 应届毕业生 / {{degree[collection.educationrequire]}}</div>
                    <a class="send-CV-btn collection_link" v-show="type == 0" @click="deliverResume(collection.id)">投个简历</a>
                    <i></i>
                    <a class="collectionCancel collection_link collected" v-if="collectionStatus[collection.id] == undefined || collectionStatus[collection.id] == 1" @click="collect(collection.id)"> 取消收藏 </a>
                    <a class="collectionCancel collection_link collected" v-if="collectionStatus[collection.id] == 0" @click="collect(collection.id)"> 收藏 </a>
                  </div>
                </li>
                <p v-if="loading" style="margin-top:10px;" class="loading">
                  <span></span>
                </p>
                <p v-if="noMore" style="text-align:center;margin-top:10px;font-size:15px;color:#ccc">没有更多了</p>
              </ul>

              <ul class="reset my_collections" v-if="type == 1" v-infinite-scroll="load" infinite-scroll-disabled="disabled" :infinite-scroll-immediate="false">
                <li v-for="(collection, index) in collectionList" :key="index">
                  <a :title="collection.cname" style="cursor: pointer;" @click="toCompanyDetail(collection)"> 
                    <img :src="ftp_prefix + 'img/' + collection.logo" :alt="collection.cname">
                  </a>
                  <div class="co_item">
                    <h2 :title="collection.cname" style="cursor: pointer;" @click="toCompanyDetail(collection)">
                      <a>
                        <em style="max-width: 200px;">{{collection.cname}}</em>
                        <!-- <span>{{collection.representative}}</span> -->
                      </a>
                    </h2>
                    <!-- <span class="co_time">{{collection.createTime}}</span> -->
                    <div class="co_cate">{{collection.industry}} / {{collection.ccity}} / {{financing[collection.financing]}} / {{companySize[collection.csize]}}</div>
                    <!-- <i></i> -->
                    <a class="collectionCancel collection_link collected" style="padding:10px;" v-if="collectionCStatus[collection.id] == undefined || collectionCStatus[collection.id] == 1" @click="collect(collection.id)"> 取消收藏 </a>
                    <a class="collectionCancel collection_link collected" style="padding:10px;" v-if="collectionCStatus[collection.id] == 0" @click="collect(collection.id)"> 收藏 </a>
                  </div>
                </li>
                <p v-if="loading" style="margin-top:10px;" class="loading">
                  <span></span>
                </p>
                <p v-if="noMore" style="text-align:center;margin-top:10px;font-size:15px;color:#ccc">没有更多了</p>
              </ul>

              <ul class="reset my_collections" v-if="type == 2" v-infinite-scroll="load" infinite-scroll-disabled="disabled" :infinite-scroll-immediate="false">
                <li style="cursor:pointer;" v-for="(delivery, index) in collectionList" :key="index" @click="toJobDetail(delivery)">
                  <div class="d_item clearfix" style="border-left: 3px solid #91cebe;padding: 3px 12px;">
                    <div class="d_job" style="margin: 0;padding: 5px 0;font-weight: 400;">
                      <a class="d_job_link" :title="delivery.ename" style="overflow: hidden;text-overflow:ellipsis;white-space: nowrap;display:inline-block;width:500px;" >
                          <em class="d_job_name" 
                              style="font-size: 18px;color: #0d9572;" >
                              {{delivery.ename}}
                          </em>
                          <span class="d_job_salary" style="font-size: 17px;color: #e6775c;">（{{delivery.esalaryceilie}} - {{delivery.esalaryfloor}}）</span>
                          <!--  -->
                      </a>
                    </div>
                    <div class="d_company" style="margin: 5px 0;font-size: 16px;">
                      <a :title="delivery.ecompany" style="color: #555;">
                        {{delivery.ecompany}}
                        <span style="color: #999;">[{{delivery.ecity}}]</span>
                      </a>
                    </div>
                    <div class="d_resume" style="margin: 5px 0;color: #999;">
                      <span style="font-size: 15px;color: #555;">
                        投递成功
                      </span>
                      <span class="d_time" style="float: right;color: #999;">{{delivery.createTime}}</span>
                    </div>
                  </div>
                </li>
                <p v-if="loading" style="margin-top:10px;" class="loading">
                  <span></span>
                </p>
                <p v-if="noMore" style="text-align:center;margin-top:10px;font-size:15px;color:#ccc">没有更多了</p>
              </ul>
            </form>
          </dd>
        </dl>
      </div>

      <div class="content_r">
        <div class="collections_nav_button">
          <div class="mr_r_nav">
            <ul class="clearfix">
              <li data-lg-tj-track-code="favorite_favorite" data-lg-tj-track-type="1" >
                <!-- <a @click="changeType(1)">公司收藏夹<i class="sc"></i></a> -->
                <a @click="changeType(0)">职位收藏夹<i class="sc"></i></a>
              </li>
              <li data-lg-tj-track-code="favorite_favorite" data-lg-tj-track-type="1" >
                <a @click="changeType(1)">公司收藏夹<i class="sc"></i></a>
                <!-- <a @click="changeType(0)">职位收藏夹<i class="sc"></i></a> -->
              </li>
              <li data-lg-tj-track-code="deliver_deliver" data-lg-tj-track-type="1">
                  <a @click="changeType(2)">
                  投递箱<i class="td"></i>
                  </a>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { degree, companySize, financing } from "@/const/index";
import { getCollectionList, getUserCollectionStatus, collectPosition, collectCompany, deliver, getResumeDeliverList } from "@/api/index";
export default {
  mounted() {
    // debugger
    this.init()
  },
  data() {
    return {
      ftp_prefix: "http://attachment.grtwwh.com:8080/",
      degree: {},
      companySize: {},
      financing: {},
      type: this.$route.query.type,
      pageNum: 1,
      showStts: 1,
      collectionStatus: [],
      collectionCStatus: [],
      collectionList: [],
      loading: false,
      pagesTotal: ""
    }
  },
  watch: {
    type:{
      handler(newVal, oldVal) {
        // debugger
        // console.log(this.pageNum)
        if (newVal == 0 || newVal == 1) {
          this.getCollectionList()
        } else if (newVal == 2) {
          this.getResumeDeliverList()
        }
      }
    }
  },
  computed: {
    noMore() {
      //当起始页数大于总页数时停止加载
      return this.pageNum >= this.pagesTotal;
    },
    disabled() {
      return this.loading || this.noMore;
    }
  },
  methods: {
    init() {
      // debugger
      console.log("mounted")
      this.degree = degree
      this.companySize = companySize
      this.financing = financing
      let type = this.$route.query.type
      if(type == 0 || type == 1) {
        this.getCollectionList()
      } else if (type == 2) {
        this.getResumeDeliverList()
      }
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
    changeType(type) {
      this.collectionList = []
      this.pageNum = 1
      // setTimeout(() => {
      // },200)
      switch(type) {
        case 0:
          // console.log(this.$route.name)
          this.$router.push({
            path: '/collections',
            query: {type: type}
          })
          // this.$route.name = 'favorites'
          break
        case 1:
          // console.log(this.$route.name)
          this.$router.push({
            path: '/collections',
            query: {type: type}
          })
          // this.$route.name = 'favorites'
          break
        case 2:
          // debugger
          // console.log(this.$route.name)
          this.$router.push({
            path: '/delivery',
            query: {type: type}
          })
          // this.$router.go(0)
          // this.$route.name = 'delivery'
          break
      }
      
      this.type = type
      
    },
    load(){
      //滑到底部时进行加载
      this.loading = true;
      setTimeout(() => {
        this.pageNum += 1; //页数+1
        if (this.type == 0 || this.type == 1) {
          this.getCollectionList(); //调用接口，此时页数+1，查询下一页数据
        } else if (this.type == 2) {
          this.getResumeDeliverList()
        }
      }, 1000);
    },
    async collect(id) {
      // console.log(this.collectionStatus[id])
      if (this.type == 0) {
        let param = {
          positionId: id,
          type: 0
        }
        let resp = await collectPosition(param)
        if (resp && resp.status == 0) {
          // this.collectionStatus[id] = resp.data.showStts
          this.$set(this.collectionStatus, id, resp.data.showStts)
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
            message: resp.msg == null ? "未知错误，请联系管理员" : resp.msg,
            type: 'warning'
          });
        }
      } else if (this.type == 1) {
        let param = {
          companyId: id,
          type: 1
        }
        let resp = await collectCompany(param)
        if (resp && resp.status == 0) {
          // this.showStts = resp.data.showStts
          this.$set(this.collectionCStatus, id, resp.data.showStts)
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
            message: resp.msg == null ? "未知错误，请联系管理员" : resp.msg,
            type: 'warning'
          });
        }
      }
    },
    deliverResume(id) {
      this.$confirm('是否确认投递?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        closeOnClickModal: false,
        closeOnPressEscape: false,
        type: 'warning'
      }).then(() => {
        deliver(id).then(resp => {
          console.log(123)
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
              message: resp.msg == null ? "未知错误，请联系管理员" : resp.msg,
              type: 'warning'
            });
          }
        })
      }).catch(() => {
        this.$message({
            type: 'info',
            message: '已取消投递'
        });
      })
    },
    getResumeDeliverList() {
      getResumeDeliverList(this.pageNum).then(resp => {
        if (resp && resp.status == 0) {
          this.collectionList = this.collectionList.concat(resp.data.list)
          this.pagesTotal = resp.data.pages
          this.loading = false;
        } else {
          this.$message.error(resp.msg ? resp.msg : "未知错误，请联系管理员")
        }
      })
    },
    getCollectionList() {
      getCollectionList(this.type, this.pageNum).then(resp => {
        if (resp && resp.status == 0) {
          this.collectionList = this.collectionList.concat(resp.data.list)
          this.pagesTotal = resp.data.pages
          this.loading = false;
        } else {
          this.$message.error(resp.msg ? resp.msg : "未知错误，请联系管理员")
        }
      })
    },
    getUserCollectionStatus(type, id) {
      let param = {
        type: type,
        id: id
      }
      getUserCollectionStatus(param).then(resp => {
        if (resp && resp.status == 0) {
          this.collectionStatus[id] = resp.data.showStts
        }
      })
    }
  }
}
</script>

<style scoped>
.mr_r_nav li a .td {
    background-position: 0 -316px;
}
.mr_r_nav li a i {
    position: absolute;
    left: 50%;
    margin-left: -14px;
    top: 12px;
    width: 30px;
    height: 25px;
    background: url(//www.lgstatic.com/lg-www-fed/mycenter-sidenav/modules/common/mycenter-nav-button/img/icons_mr_da12dfe.png) no-repeat;
}
.mr_r_nav li a .sc {
    background-position: -35px -316px;
}
.mr_r_nav li a {
    position: relative;
    display: block;
    padding-top: 40px;
    height: 26px;
    text-align: center;
    background-color: #fafafa;
    cursor: pointer;
}
.mr_r_nav li {
    float: left;
    width: 88px;
    height: 66px;
    margin-right: 3px;
    border: 1px solid #f2f2f2;
    -moz-border-radius: 3px;
    -webkit-border-radius: 3px;
    border-radius: 3px;
}
.mr_r_nav ul, .mr_r_nav ol {
    list-style: none;
    padding: 0;
}
.mr_r_nav {
    /* margin-bottom: 18px; */
}
.content_r {
    float: left;
    width: 282px;
    margin-left: 42px;
}
.loading span {
  display: inline-block;
  width: 20px;
  height: 20px;
  border: 2px solid #409eff;
  border-left: transparent;
  background-color: #ccc;
  animation: zhuan 0.5s linear infinite;
  border-radius: 50%;
}
@keyframes zhuan {
  0% {
    transform: rotate(0);
  }
  100% {
    transform: rotate(360deg);
  }
}
em {
    font-style: normal;
}
.collectionCancel span {
    float: left;
    width: 120px;
    text-align: center;
    font: 14px Arial,"Hiragino Sans GB","Microsoft Yahei",SimSun;
    color: #777;
    border: 2px solid #f2f2f2;
    text-decoration: none!important;
    background: #fff;
    padding: 3px;
    position: absolute;
    right: -40px;
    top: -28px;
    display: none;
}
.collection_link {
    font-size: 14px;
    color: #019875;
    float: right;
    margin-left: 10px;
    margin-top: 4px;
    *margin-top: -22px;
    line-height: 1;
    padding-left: 10px;
    margin-left: 0;
    cursor: pointer;
}
ul.my_collections li i {
    float: right;
    width: 1px;
    height: 24px;
    background: url(//www.lgstatic.com/lg-www-fed/mycenter-sidenav/modules/common/img/arr_0c4e32c.png) -29px center no-repeat;
    margin-left: 10px;
    *margin-top: -22px;
}
.collection_link {
    font-size: 14px;
    color: #019875;
    float: right;
    margin-left: 10px;
    margin-top: 4px;
    *margin-top: -22px;
    line-height: 1;
    padding-left: 10px;
    margin-left: 0;
}
ul.my_collections .co_cate {
    clear: both;
    color: #555;
}
ul.my_collections .co_time {
    color: #999;
    font-size: 12px;
    float: right;
}
ul.my_collections h2 a span {
    font-size: 16px;
    color: #e6775c;
}
ul.my_collections h2 a {
    color: #0d9572;
}
ul.my_collections h2 {
    font-size: 18px;
    color: #0d9572;
    margin: 0 0 3px;
    float: left;
    max-width: 414px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}
ul.my_collections li .co_item {
    margin-left: 82px;
    margin-top: -67px;
}
ul.my_collections li img {
    width: 67px;
    height: 67px;
    border: 2px solid #ededed;
    /* position: absolute; */
}
a {
    color: #555;
    background-color: transparent;
    text-decoration: none;
}
ul.my_collections li {
    padding: 7px;
    margin-bottom: 25px;
    background: #fff;
    overflow: hidden;
}
ul.my_collections {
    margin: 15px 40px;
}
.new_section>dl dd {
    min-height: 300px;
    padding: 20px 0;
    clear: both;
}
.new_section>dl dt em {
    background: #fff url(//www.lgstatic.com/lg-www-fed/mycenter-sidenav/modules/common/img/resume_arr_1f2014c.png) no-repeat;
    width: 24px;
    height: 28px;
    position: absolute;
    top: 0;
    right: 0;
}
.new_section>dl dt h1 {
    font-size: 24px;
    color: #555;
}
.new_section h1 {
    font-weight: 400;
    line-height: 42px;
    margin: 0 0 20px;
}
.new_section>dl dt {
    background: #fafafa;
    height: 50px;
    border-bottom: 1px solid #ebebeb;
    position: relative;
    padding: 10px 0 0 20px;
}
.new_section>dl {
    background-color: #fafafa;
    margin-bottom: 175px;
}
.new_section {
    width: 700px;
}
.fl {
    float: left;
}
#container {
    width: 1024px;
    margin: 35px auto 0;
    padding-bottom: 150px;
}
</style>