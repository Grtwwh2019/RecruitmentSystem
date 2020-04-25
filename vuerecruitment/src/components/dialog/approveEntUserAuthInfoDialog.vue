<template>
  <el-dialog class="userAuthInfoDialog" title="认证详情" width="25%" :visible.sync="showVisible" :close-on-click-modal="false" :close-on-press-escape="false" @open="handleOpen" :before-close="handleClose">
    <div class="my_title" >
      <h4>昵称：</h4>
    </div>
    <span class="my_info">
      <p style="margin-right:10px;">{{userInfo.nickname}}</p>
    </span>
    <div class="my_title" >
      <h4>公司：</h4>
    </div>
    <div class="my_info">
      <p style="margin-right:10px;">{{userInfo.companyName}}</p>
    </div>
    <div class="my_title" >
      <h4>员工号：</h4>
    </div>
    <div class="my_info">
      <p style="margin-right:10px;">{{userInfo.empNo}}</p>
    </div>
    <div class="my_title" >
      <h4>员工工牌：</h4>
    </div>
    <div class="my_info">
      <img style="width:200px;height:150px;margin: 0 auto;" :src="userInfo.cardPhoto" :alt="userInfo.empNo">
      </img>
    </div>
    <div class="my_title" >
      <h4>企业邮箱：</h4>
    </div>
    <div class="my_info">
      <p style="margin-right:10px;">{{userInfo.enterpriseMail}}</p>
    </div>
    <div class="my_title" >
      <h4>联系方式：</h4>
    </div>
    <div class="my_info">
      <p style="margin-right:10px;">{{userInfo.telephone}}</p>
    </div>
    <div class="my_title" >
      <h4>认证状态：</h4>
    </div>
    <div class="my_info">
      <p style="margin-right:10px;">{{status[userInfo.authentication]}}</p>
    </div>

    <div class="submit_button" style="text-align:center;">
      <el-button style="margin-top:35px;margin-bottom:20px;margin-right:30px;" 
        type="success" 
        v-if="userInfo.authentication == 4"
        @click="submitForm(1)">通过
      </el-button>

      <el-button v-if="userInfo.authentication == 4" style="margin-top:35px;margin-bottom:20px;margin-left:30px;" 
        @click="submitForm(2)">拒绝
      </el-button>

      <el-button v-if="userInfo.authentication != 4" style="margin-top:35px;margin-bottom:20px;margin-left:30px;" 
        @click="handleClose">取消
      </el-button>
    </div>
  </el-dialog>
</template>

<script>
const status = {
  "1":"通过",
  "3":"拒绝",
  "4":"待认证"
}
export default {
  props: {
    showVisible: {type: Boolean},
    userAuthInfo: {type: Object}
  },
  mounted() {
    this.init()
  },
  data() {
    return {
      status: {},
      userInfo: {}
    }
  },
  methods: {
    init() {
      this.status = status
    },
    submitForm(authStatus) {
      this.$confirm('是否确认操作?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          closeOnClickModal: false,
          closeOnPressEscape: false,
          type: 'warning'
      }).then(() => {
        this.$emit('handleDiaoClose', authStatus, this.userInfo.userId)
      }).catch(() => {

      })

      
    },
    handleOpen() {
      this.userInfo = Object.assign({}, this.userAuthInfo)
      // console.log(this.userInfo)
    },
    handleClose() {
      this.$emit('handleDiaoClose', false)
    }
  }
}
</script>

<style scoped>
.el-dialog__body {
    padding-top: 10px;
}
.my_info{
    display: inline-block;
    margin-left:20px;
}
.my_info p{
    /* margin:10px 0; */
    font-size:17px;
    color:#333;
}
.my_title{
  font-size: 18px;
  border-left: 3px solid #00b38a;
  padding-left: 5px;
  margin-bottom: 10px;
  margin-top: 10px;
}
</style>