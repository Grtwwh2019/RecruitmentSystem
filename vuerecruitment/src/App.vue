<template>
  <div id="app">
    <router-view />
  </div>
</template>


<script>
// @ is an alias to /src
import TheHeader from "@/components/commons/TheHeader.vue";
import TheFooter from "@/components/commons/TheFooter.vue";

export default {
  name: "app",
  components: {
    TheHeader,
    TheFooter
  }
};
</script>


<style lang="less">
#app {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
</style>
