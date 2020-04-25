<!-- slidebar -->
<template>
<div class='slidebar'>
    
    <div class="clear_fix">
        <div class="slide_bar">
           <div class="menu_box" 
            v-for="parent in positionTypeSet" :key="parent.value" 
            @mouseover="showSubmenu(parent.label, parent.value)" @mouseleave="hideSubmenu(parent.label)">
               <ul class="menu_main">
                   <li>{{parent.label}}</li>
                   <li><router-link :to="{path:'/searchList', query: {etypeid: parent.children[0].children[1].value}}">{{parent.children[0].children[1].label}}</router-link></li>
                   <li><router-link :to="{path:'/searchList', query: {etypeid: parent.children[0].children[2].value}}">{{parent.children[0].children[2].label}}</router-link></li>
                   <li><router-link :to="{path:'/searchList', query: {etypeid: parent.children[0].children[3].value}}">{{parent.children[0].children[3].label}}</router-link></li>
                   <li><router-link :to="{path:'/searchList', query: {etypeid: parent.children[0].children[4].value}}">{{parent.children[0].children[4].label}}</router-link></li>
                   <li><i class="iconfont icon-xiangyou"></i></li>
               </ul>
               <div class="sub_menu" v-show="parent.value == showSubMenuflag">
                   <div class="item" v-for="children1 in parent.children" :key="children1.value">
                    <h5>{{children1.label}}</h5>
                    <ul>
                       <li v-for="children2 in children1.children" :key="children2.value" >
                           <router-link :to="{path:'/searchList', query: {etypeid: children2.value}}"> {{children2.label}}</router-link>    
                       </li>
                    </ul>
                   </div>
               </div>
           </div>
        </div>
  <div class="home_banner">
<div class="lubo">
     <transition-group tag="ul" class="lubo_box" enter-active-class="animated zoomInLeft" leave-active-class="animated zoomOutUp">
         <li v-for="(list,index) in lunboList" :key="list.id" v-show="index===currentIndex" @mouseenter="stop" @mouseleave="go">
          <a :href="list.clickUrl" rel="external nofollow" >
             <img :src="ftp_prefix + list.url" :alt="list.id">
          </a>
         </li>
     </transition-group>
     <div class="carousel-items">
   <span class="dot" v-for="(item,index) in lunboList.length" :key=index :class="{'active':index===currentIndex}" @click="change(index)">
   </span>
  </div>
</div>
<div class="lubo_btn" v-show="lunboFlag">
     <span class="left_btn" @click="prevHandel" @mouseover="stop" @mouseleave="go">
         <i class="iconfont icon-xiangzuo1"></i></span>
     <span class="right_btn" @click="nextHandel" @mouseover="stop" @mouseleave="go">
         <i class="iconfont icon-xiangyou1"></i></span>
</div>
    </div>
  </div>
</div>
</template>

<script>
// import {jishuJSON,chanpingJSON,shejiJSON,yunyingJSON,shichangJSON,lunboList} from '../../../static/js/indexJSON'
import { lunboList } from "@/const/index";
export default {
  name: 'SlideBar',
  components: {},
  mounted() {
    this.positionTypeSet = this.$store.state.positionTypeSet
    // console.log(this.positionTypeSet)
  },
  watch: {
  },
  data() {
  //这里存放数据
    return {
      ftp_prefix: 'http://attachment.grtwwh.com:8080/',
      showSubMenuflag: 0,
      positionTypeSet: [],
      flag:false,
      flag2:false,
      flag3:false,
      flag4:false,
      flag5:false,
      lunboFlag:true,
      // jishuData: jishuJSON,
      // chanpingJSON:chanpingJSON,
      // shejiJSON:shejiJSON,
      // yunyingJSON:yunyingJSON,
      lunboList:lunboList,
      // shichangJSON:shichangJSON,
      currentIndex:0,
      timer:''
    };
  },
  methods: {
    showSubmenu(type, id){
      this.showSubMenuflag = id
      // this.showSubMenuflag = true
      // if(type == '技术'){
      //     this.showSubMenuflag = id
      //   }else if(type == '产品'){
      //     this.showSubMenuflag = id
      //   }else if(type == '设计'){
      //     this.flag3 = true
      //   }else if(type == 'yy'){
      //     this.flag4 = true
      //   }else if(type == 'sc'){
      //     this.flag5 = true
      //   }
        
    },
    hideSubmenu(type){
      this.showSubMenuflag = 0
        // if(type == '技术'){
        //   this.showSubMenuflag[1] = false
        // }else if(type == '职能'){
        //     this.showSubMenuflag[5] = 0
        // }else if(type == 'sj'){
        //     this.flag3 = false
        // }else if(type == 'yy'){
        //     this.flag4 = false
        // }else if(type == 'sc'){
        //     this.flag5 = false
        // }
    },
    go() {
      this.timer = setInterval(() => {
        this.autoPlay()
      },4000)
      },
      stop() {
      clearInterval(this.timer)
      this.timer = null
      },
      change(index) {
      this.currentIndex = index
      },
      autoPlay() {
      this.currentIndex++
      if (this.currentIndex > this.lunboList.length - 1) {
        this.currentIndex = 0
      }
      },
      prevHandel(){
        this.currentIndex--;
        if(this.currentIndex < 0){
            this.currentIndex = this.lunboList.length - 1;
        }
      },
      nextHandel(){
        this.autoPlay();
      }
    },
  }
</script>
<style scoped>

a{
    color:#666;
}
.slide_bar{
    float:left;
    width:380px;
}
.menu_main{
    display: flex;
    font-size:14px;
    justify-content: space-between;
    line-height:25px;
    padding:10px 0;
}
.menu_box a:hover{
    text-decoration: underline;
    color:#0dca9f;
}
.menu_box{
    position:relative;
    margin:2px auto;
    transition:all 0.2s ease;
}
.menu_box:first-child{
    margin-top:-10px;
}
.menu_box:hover{
    padding:0px 10px;
    box-shadow: -2px 1px 5px #999;
    border-radius: 5px;
}
.menu_main li:first-child{
    font-size: 18px;
}
.sub_menu{
    position:absolute;
    left:320px;
    top:0;
    width:600px;
    box-shadow: 2px 5px 8px #999;
    border-radius: 5px;
    background-color:#fff;
    padding:20px;
    z-index: 5;
}
.sub_menu ul{
    display:flex;
    flex-wrap: wrap;
}
.sub_menu ul li{
    font-size:14px;
    padding:0 10px;
    color:#999;
    line-height:30px;
}
.item{
    margin-bottom: 20px;
}
.home_banner{
    position: relative;
    margin-left: 400px;
    margin-top: 20px;
    overflow: hidden;
}
.lubo{ width: 100%;clear: both; position: relative; height:365px;margin:0 auto;}
  .lubo_box{ position: relative; width: 100%; height:100%;list-style: none; }
  .lubo_box li{ float: left;position: absolute; top: 0; left: 0; width: 100%; height:100%; filter:alpha(opacity=0);}
  .lubo_box li a{ display: block;width: 100%;  height: 100%;}
  .lubo_box li img{ width: 100%; height: 100%;}
  /*圆点*/
  .carousel-items {
 position: absolute;
 z-index: 2;
 top: 340px;
 width: 100%;
 margin: 0 auto;
 text-align: center;
 font-size: 0;
  }
  .carousel-items span {
  display: inline-block;
  height: 6px;
  width: 30px;
  margin: 0 3px;
  background-color: #b2b2b2;
  cursor: pointer;
 }
 .carousel-items span:hover{
    background:yellow;
 }
 .active {
  background-color: #0dca9f !important;
 }
  /*按钮*/
  .lubo_btn{ position: absolute; width: 100%; top: 140px;}
  .lubo_btn .iconfont{font-size:40px;}
  .left_btn:hover, .right_btn:hover{background:#0dca9f;transition: all 1s ease;}
  .left_btn, .right_btn{ 
      width: 40px;
       height: 80px;opacity: 0.8;filter:alpha(opacity=80); cursor: pointer; color: #fff; line-height: 80px; font-size: 30px; text-align: center;-webkit-user-select:none;-moz-user-select:none;-ms-user-select:none;user-select:none;}
  .left_btn{ float: left;}
  .right_btn{ float: right;}
</style>