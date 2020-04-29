<template>
  <div>
    <Head></Head>
    <div>
      <transition enter-active-class="animated bounceInLeft">
       <!-- <router-view :key="$route.fullPath"/> -->
       <router-view />
      </transition>
    </div>
    <Foot></Foot>
  </div>
  
</template>

<script>
import Head from '@/components/base/Head'
import Foot from '@/components/base/Foot'
export default {
  components:{
    Head,
    Foot  
  }
}
</script>

<style>

</style>