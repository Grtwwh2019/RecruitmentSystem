<template>
  <div class="layout">
    <Layout>
      <TheHeader />
      <Content>
        <router-view name="content"></router-view>
      </Content>
      <TheFooter />
    </Layout>
  </div>
</template>

<script>
// @ is an alias to /src
import TheHeader from "@/components/commons/TheHeader.vue";
import TheFooter from "@/components/commons/TheFooter.vue";

export default {
  name: "home",
  components: {
    TheHeader,
    TheFooter
  }
};
</script>


<style lang="less" scoped>
.layout {
  border: 1px solid #d7dde4;
  background: #f5f7f9;
  position: relative;
  border-radius: 4px;
  overflow: hidden;
}
</style>
