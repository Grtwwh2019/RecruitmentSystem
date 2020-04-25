<template>
  <div id="app">

    <!-- <transition enter-active-class="animated bounceInLeft"> -->
      <router-view/>
    <!-- </transition> -->
  </div>
</template>

<script>
export default {
  name: 'App'

}
</script>

<style>
*{margin:0;padding:0}
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
ul{
  list-style: none;
}
a{
  text-decoration: none;
}
.l1{
  float:left;
}
.r1{
  float:right
}
</style>