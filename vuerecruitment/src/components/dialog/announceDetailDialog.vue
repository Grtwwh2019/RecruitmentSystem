<template>
  <el-dialog class="announceDialog" title="公告详情" width="25%" :visible.sync="showVisiable" :close-on-click-modal="false" :close-on-press-escape="false" @open="handleOpen" :before-close="handleClose">

    <div class="title">标题：{{announce.title}}</div>
    <div class="sender">发送人：{{announce.sender}}</div>
    <!-- <div class="status">状态：{{announce.status == 1 ? "未读" : "已读"}}</div> -->
    <div class="type">类型：{{announce.type == 1 ? "系统通知" : ""}}</div>
    <div class="content">内容：{{announce.content}}</div>
    <div class="createTime">发送时间：{{announce.createTime}}</div>
    <!-- <div class="submit_button"> -->
      <el-button style="margin-left:40%;margin-top:35px" 
        type="plain" size="medium"
        @click="handleClose">确定
      </el-button>
    <!-- </div> -->
  </el-dialog>
</template>

<script>
export default {
  props: {
    showVisiable: {type: Boolean},
    announceDetail: {type: Object}
  },
  mounted() {
    
  },
  data() {
    return {
      announce: {}
    }
  },
  methods: {
    handleOpen() {
      this.announce = Object.assign({}, this.announceDetail)
      // console.log(this.announce)
    },
    handleClose() {
      this.$emit('handleAnnounceDialogClose', false)
    }
  }
}
</script>

<style scoped>
.announceDialog div{
  line-height: 1.8;
  font-size: 15px;
  border-bottom: 1px solid #ddd;
  padding-bottom: 10px;
  margin-bottom: 10px;
}

</style>