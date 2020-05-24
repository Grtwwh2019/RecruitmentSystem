<template>
  <div></div>
</template>

<script>
import { userLogout } from "@/api/index";
import { Message } from 'element-ui'
export default {
  created() {
    this.logout()
  },
  methods: {
    logout() {
      userLogout().then(resp => {
        if (resp.status == 1 && resp.msg) {
            Message.error({message: resp.msg})
        } else if (resp.status == 0) {
          Message.success({message: resp.msg})
        }
      })
      // window.sessionStorage.removeItem("user")
      window.sessionStorage.removeItem("user")
      let path = this.$route.query.redirect;
      this.$router.replace((path == '/' || path == undefined) ? '/index' : path);
    }
  }
}
</script>

<style>

</style>