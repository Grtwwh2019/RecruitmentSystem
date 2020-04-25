<template>
  <el-container class="setting-container">
    <el-aside class="setting-aside" style="width:10%;">
      <el-tabs tab-position="left" v-model="tabCode" @tab-click="handleClick" style="height: 150px;">
        <el-tab-pane name="1" label="个人信息"></el-tab-pane>
        <el-tab-pane name="2" label="安全管理"></el-tab-pane>
        <el-tab-pane name="3" label="身份认证"></el-tab-pane>
      </el-tabs>
    </el-aside>
    <el-main class="setting-main">
      <router-view/>
    </el-main>
  </el-container>

</template>

<script>
import { getUserInfo, getHighestRole } from "@/api/index";
export default {
  mounted() {
    // console.log(1)
    this.getUserInfo()
    this.$router.push({
      path: "/personalInfo",
      query: {
        userInfo: this.userInfo,
        roleInfo: this.roleInfo
      }
    })
  },
  data() {
    return {
      tabCode: '1',
      userInfo: {},
      roleInfo: {}
    }
  },
  methods: {
    getUserInfo(){
      getUserInfo().then(resp => {
        if (resp && resp.status == 0) {
          this.userInfo = resp.data
        }
      })
      getHighestRole().then(resp => {
        if (resp && resp.status == 0) {
          this.roleInfo = resp.data
        }
      })
    },
    handleClick(tab, event) {
      switch(tab.name) {
        case '1':
          this.$router.push({
            path: "/personalInfo",
            query: {
              userInfo: this.userInfo,
              roleInfo: this.roleInfo
            }
          })
          break
        case '2':
          this.$router.push({
            path: "/securityManage",
            query: {
              userInfo: this.userInfo,
              roleInfo: this.roleInfo
            }
          })
          break
        case '3':
          this.$router.push({
            path: "/authentication",
            query: {
              userInfo: this.userInfo,
              roleInfo: this.roleInfo
            }
          })
          break
      }

    }
  },
  watch: {
　　// 利用watch方法检测路由变化：
　　'$route': function (to, from) {
        if (to.path == "/setting") {
　　　　  this.$router.push(from.path)
        }
　　}
  }
}
</script>

<style scoped>
  .setting-container {
    clear:both;
    width:100%;
    margin-bottom: 240px;
  }

  .setting-aside {
    margin-top: 5%;
    margin-left: 20%;
  }

  .setting-main{
    margin-top: 5%;
    /* width: 80%; */
    float: left;
    /* width: 80%; */
    position: relative;
  }
</style>

