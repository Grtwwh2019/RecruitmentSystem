<template>
  <div class="container clearfix" id="messageContaner">
    <div class="content-left">
      <div class="left-wrap">
        <div class="setting-box">
          <h2 class="main-title">我的消息</h2>
          <div class="setting-btn"></div>
        </div>
        <div class="category-tab">
          <div class="item-wrap">
            <ul class="tab" id="tabContainer">
              <li class="tab-item">
                <p :class="readed == 0 ? '' : 'item-active'" @click="readed = 1">未读消息</p>
              </li>
              <li class="sep">
                <span>|</span>
              </li>
              <li class="tab-item">
                <p :class="readed == 0 ? 'item-active' : ''"  @click="readed = 0">已读消息</p>
              </li>
            </ul>
          </div>
        </div>
        <div class="twrap">
          <div class="tab-content" id="msgContent">
            <div class="t-content-item" style="height: 550px;overflow-y: auto;">
              <ul class="msg-list" id="allListContainer" v-infinite-scroll="load" infinite-scroll-disabled="disabled" :infinite-scroll-immediate="false">
                <li class="msg-box" v-for="(announce,index) in announceList" :key="index">
                  <div class="msg-body">
                    <div class="msg-content clearfix">
                      <div class="msg-head clearfix">
                        <div class="fl">{{announce.status == 0 ? "已读" : "未读"}}</div>
                        <div class="fr">{{announce.createTime}}</div>
                      </div>
                      <dl class="info">
                        <dd class="msg-info">
                          <h4>
                              <p class="announce-title">标题：{{announce.title}}</p>
                              <!-- <em>·</em> -->
                              <p class="announce-content">内容：{{announce.content}}</p>
                          </h4>  
                        </dd>
                        <dt class="op">
                          <a class="show-detail" @click="toAnnounceDetail(announce.id)">去看详情</a>
                        </dt>
                      </dl>
                    </div>
                  </div>
                </li>
                <!-- <el-row style="height: 50px" v-if="loading"
                    v-loading="loading"
                    element-loading-text="拼命加载中"
                    element-loading-spinner="el-icon-loading"
                    element-loading-background="rgba(0, 0, 0, 0.3)"></el-row> -->
                <!-- <p v-if="loading">加载中...</p> -->
                <!-- <p v-if="noMore">没有更多了</p> -->
                <p v-if="loading" style="margin-top:10px;" class="loading">
                  <span></span>
                </p>
                <p v-if="noMore" style="text-align:center;margin-top:10px;font-size:15px;color:#ccc">没有更多了</p>
              </ul>
            </div>
          </div>
        </div>
        <announceDetailDialog :showVisiable.sync="dialogVisible" :announceDetail.sync="announceDetail" @handleAnnounceDialogClose = "handleAnnounceDialogClose"/>
      </div>
    </div>
  </div>
</template>

<script>
import { getAnnounceList, getAnnounceDetail } from "@/api/index";
import announceDetailDialog from "@/components/dialog/announceDetailDialog.vue";
export default {
  components: {
    announceDetailDialog
  },
  mounted() {
    this.init()
  },
  computed: {
    noMore() {
      //当起始页数大于总页数时停止加载
      return this.count >= this.totalPages;
    },
    disabled() {
      return this.loading || this.noMore;
    }
  },
  watch: {
    readed: {
      handler(newVal, oldVal) {
        this.count = 1
        this.announceList = []
        this.getAnnounceList()
      }
    }
  },
  data() {
    return {
      dialogVisible: false,
      loading: false,
      count: 1,
      readed: 1,
      announceList: [],
      announceDetail: {},
      totalPages: ""
    }
  },
  methods: {
    init() {
      this.getAnnounceList()
    },
    load () {
      //滑到底部时进行加载
      this.loading = true;
      setTimeout(() => {
        this.count += 1; //页数+1
        this.getAnnounceList(); //调用接口，此时页数+1，查询下一页数据
      }, 1000);
    },
    async toAnnounceDetail(announceid) {
      // setTimeout(() => {
      //   }, 2000)
      let resp = await getAnnounceDetail(announceid)
      if (resp && resp.status == 0) {
        this.announceDetail = resp.data
        // console.log(this.announceDetail)
      } else {
        this.$message.warning(resp.msg ? resp.msg : "未知错误，请联系管理员")
      }
      this.dialogVisible = true
      
    },
    async handleAnnounceDialogClose() {
      if (this.readed == 1) {
        this.loading = true
        this.count = 1
        let resp = await getAnnounceList(this.count, this.readed)
        if (resp && resp.status == 0) {
            this.announceList = resp.data.list
            this.totalPages = resp.data.pages
            // console.log(this.count)
            this.loading = false;
          } else {
            this.$message.warning(resp.msg ? resp.msg : "未知错误，请联系管理员")
          }
      }
      this.dialogVisible = false
    },
    getAnnounceDetail(id) {
      getAnnounceDetail(id).then(resp => {
        if (resp && resp.status == 0) {
          this.announceDetail = resp.data
          // console.log(this.announceDetail)
        } else {
          this.$message.warning(resp.msg ? resp.msg : "未知错误，请联系管理员")
        }
      })
    },
    getAnnounceList() {
      // console.log(this.count)
      getAnnounceList(this.count, this.readed).then(resp => {
        if (resp && resp.status == 0) {
          this.announceList = this.announceList.concat(resp.data.list)
          this.totalPages = resp.data.pages
          // console.log(this.count)
          this.loading = false;
        } else {
          this.$message.warning(resp.msg ? resp.msg : "未知错误，请联系管理员")
        }
      })
    }
  }
}
</script>

<style scoped>
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
.announce-content{
  overflow: hidden;
  text-overflow:ellipsis;
  white-space: nowrap;
  /* width:100px; */
  /* -webkit-line-clamp: 2; */
}
.announce-title{
  overflow: hidden;
  text-overflow:ellipsis;
  white-space: nowrap;
  width:180px;
}
.msg-box .show-detail {
    display: inline-block;
    color: #00b38a;
    text-decoration: underline;
    cursor: pointer;
}
.msg-box dl dd h4 {
    margin: 0;
    font-size: 14px;
}
h1, h2, h3, h4, h5, h6 {
    margin: 10px 0;
    font-weight: 400;
}

.msg-box dl dt {
    float: right;
    width: 85px;
}
.msg-box .msg-content .info {
    float: left;
    width: 100%;
    margin-top: 5px;
}
.fr {
    float: right;
}
.fl {
    float: left;
}
.msg-box .msg-content .msg-head {
    color: #bababa;
}
.msg-box .msg-content {
    padding-left: 35px;
}
.msg-box .msg-body {
    margin-top: 5px;
    /* 上右下左 */
    /* padding: 6px 15px 15px 0; */
    padding: 6px 15px 94px 0;
}
.msg-box {
    border: 1px solid #e1e1e1;
    margin-bottom: 15px;
}
.twrap {
    position: relative;
    width: 100%;
    min-height: 360px;
}
/* p {
  color: #555;
  background-color: transparent;
  text-decoration: none;
} */
em, i {
    font-style: normal;
}
.tab .sep span {
    margin-top: -2px;
    display: block;
    color: #d5dadf;
}
.tab .sep {
    float: left;
    padding: 0 12px;
    font-size: 16px;
}
.tab-item p{
  cursor: pointer;
}
.tab .tab-item {
    float: left;
    font-size: 16px;
}
/* ul, ol {
    margin: 0;
    padding: 0;
    list-style: none;
} */
.category-tab {
    margin-bottom: 12px;
    height: 32px;
}
.setting-box .setting-btn {
    position: absolute;
    right: 0;
    font-size: 16px;
    top: -12px;
    margin: 0;
    padding-left: 10px;
    background-color: #fff;
}
.setting-box h2 {
    position: absolute;
    left: 0;
    top: -14px;
    font-size: 18px;
    font-weight: 600;
    color: #555;
    margin: 0;
    padding-right: 10px;
    background-color: #fff;
    vertical-align: bottom;
}
.setting-box {
    position: relative;
    border-top: 2px solid #f5f5f5;
    margin: 10px 0 28px;
}
#messageContaner .left-wrap {
    padding-right: 40px;
}
#messageContaner .content-left {
    float: left;
    border-right: 2px solid #f5f5f5;
    width: 700px;
    box-shadow: 2px 0 #f9f9f9;
    min-height: 658px;
}
.container {
    width: 1024px;
    margin: 50px auto 0;
    padding-bottom: 150px;
    font-family: "Hiragino Sans GB","Microsoft Yahei",SimSun,Arial,"Helvetica Neue",Helvetica;
    color: #333;
}
.item-active {
  color: #00b38a;
}
</style>