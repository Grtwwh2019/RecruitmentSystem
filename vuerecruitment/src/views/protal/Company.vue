<!-- company -->
<template>
<div class='company'>
    <!-- <div class="banner">
        <h3>欢迎来到北理珠招聘网公司展示</h3>
        <div class="back" @click="back(x)"><i class="iconfont icon-xiangzuo1"></i></div>
        <div class="next" @click="next(x)"><i class="iconfont icon-xiangyou1"></i></div>
        <div class="item_list">  
         <ul class="banner_box" ref="imgList">
              <li v-for="(item, index) in companyJson" :key="index">
                  <div class="first">
                      <img :src="item.img" >
                      <span class="com_name">{{item.name}}</span>
                      <div class="second">
                          <div class="sec_inner">
                              <p>{{item.msg}}</p>
                              <div to="" class="sec_btn">进入公司主页</div>
                          </div>
                      </div>
                  </div>
              </li> 
         </ul>
        </div>  
    </div> -->
    <div class="container">
        <div class="container_inner">
            <div class="items" ref="company">
               <span>公司地点:</span>
                <ul ref="city">
                    <li v-for="(item, index) in cityJSON" :key="index">
                      <div :class="isCityactive == (item.value) ? 'bg' : ''" @click='cityfn(item.value)'>{{item.label}}</div>
                    </li>   
                </ul>
                <span class="more" @mouseover="showMore" @mouseleave="close">更多<i class="iconfont" :class="moreClass"></i></span>
            </div>
            <div class="items">
               <span>融资阶段:</span>
                <ul>
                    <li v-for="(value, key) in rongziJSON" :key="key">
                      <div :class="isRongziactive == (key) ? 'bg' : ''" @click='rongzifn(key)'>{{value}}</div>
                    </li>   
                </ul>     
            </div>
            <div class="items">
               <span>公司规模:</span>
                <ul>
                    <li v-for="(value, key) in personNum" :key="key"><div to="">{{value}}</div></li>   
                </ul> 
            </div>
            <div class="items" ref="indu">
               <span>行业领域:</span>
                <ul ref="industry">
                    <li v-for="(item, index) in hangyeJSON" :key="index"><div>{{item.label}}</div></li>   
                </ul>
                <span class="more" @click="showMore2()">更多<i class="iconfont" :class="moreClass2"></i></span>
                <!-- <span class="more" @mouseover="showMore2" @mouseleave="close2">更多<i class="iconfont" :class="moreClass2"></i></span> -->
            </div>
        </div>
        <!-- <div class="container_inner" style="margin-top:10px;">
            <div class="items" style="margin-bottom:0">
               <span>排序方式:</span>
                <ul>
                    <li v-for="(item, index) in sortJSON" :key="index"><div to="">{{item}}</div></li>   
                </ul>
               <label class="sel r1"><input type="checkbox" />有面试评价</label> 
            </div>
        </div> -->
        <!-- <HotCompany></HotCompany> -->
    </div>
</div>
</template>

<script>
// import Head from '../bases/Head'
// import HotCompany from '../bases/HotCompany'
// import {companyJson,cityJSON,rongziJSON,personNum,hangyeJSON,sortJSON} from '../../../static/js/companyJSON'
import { companySize, financing, degree } from "@/const/index";
export default {
  name: 'Company',
  components: {
    // Head,
    // HotCompany
  },
  mounted() {
    this.init()
  },
  data() {
    //这里存放数据
    return {
      isRongziactive: 1,
      isCityactive:'', //默认第一个有样式
      showIndustry: false,
      companyJson:{},
      cityJSON:[],
      rongziJSON: {},
      personNum: {},
      hangyeJSON: {},
      // sortJSON: sortJSON,
      moreClass: 'icon-xiala',
      moreClass2: 'icon-xiala',
      x:0
    };
  },
  methods: {
    init() {
      let children = []
      this.$store.state.citySet.forEach(element => {
        children.push(element.children)
      });
      let c = []
      children.forEach(element => {
        element.forEach(e => {
          c.push(e)
        });
      });
      let industryChildren = []
      this.$store.state.industrySet.forEach(element => {
        industryChildren.push(element.children)
      });
      let i = []
      industryChildren.forEach(element => {
        element.forEach(e => {
          i.push(e)
        });
      });
      this.cityJSON = c
      this.hangyeJSON = i
      this.rongziJSON = financing
      this.personNum = companySize
      this.isCityactive = this.cityJSON[0].value
    },
    rongzifn(rongziKey) {
      this.isRongziactive = rongziKey
    },
    cityfn(cityid) {
      //点击切换 变量的值 赋值为 index
      this.isCityactive=cityid;
    },
    back(x){
        if(x>=0){
            return
        }
        x+=1150
        this.x = x
        this.$refs.imgList.style.transform = 'translateX('+x+'px)'
        console.log(x)
    },
    next(x){
        if(x<=-2300){
            return
        }
        x-=1150
        this.x = x
        this.$refs.imgList.style.transform = 'translateX('+x+'px)'
        console.log(x)
    },
    showMore(){
        this.moreClass = 'icon-shangla'
        this.$refs.city.style.height = 'auto'
        this.$refs.company.onmouseover = ()=>{
            this.moreClass = 'icon-shangla'
            this.$refs.city.style.height = 'auto'
        }
    },
    showMore2(){
      if (this.showIndustry) {
        this.close2()
        this.showIndustry = false
      } else {
        this.moreClass2 = 'icon-shangla'
        this.$refs.industry.style.height = 'auto'
        this.$refs.indu.onmouseover = ()=>{
          this.moreClass2 = 'icon-shangla'
          this.$refs.indu.style.height = 'auto'
        }
        this.showIndustry = true
      }
    },
    close(){
        this.moreClass = 'icon-xiala'
        this.$refs.city.style.height = '25px'
        this.$refs.company.onmouseleave = ()=>{
            this.moreClass = 'icon-xiala'
            this.$refs.city.style.height = '25px'
        }
    },
    close2(){
        this.moreClass2 = 'icon-xiala'
        this.$refs.industry.style.height = '25px'
        // this.$refs.indu.onmouseleave = ()=>{
        //     this.moreClass2 = 'icon-xiala'
        //     this.$refs.indu.style.height = '25px'
        // }
    }
  },
}
</script>
<style scoped>
.bg {
  background:#00b38a;
  color:#fff;
}
.container{
    width:1200px;
    margin:0 auto;
}
.container_inner{
    margin-top:35px;
    padding:8px;
    background:#fafafa;
    border:1px solid #ededed;
    border-radius:5px;
    font-size:14px;
}
.container span,.container a{
    color:#555;
}
.items{
    position:relative;
    margin-bottom:10px;
}
.more{
    position:absolute;
    top:0;
    right:0;
    cursor: pointer;
}
.container_inner span{
    float:left;
    font-weight:bold;
    margin-left:15px;
    padding:3px 0;
}
.container_inner ul{
    display: flex;
    overflow: hidden;
    height:25px;
    margin-left:90px;
    max-width:780px;
    flex-wrap: wrap;
}
.container_inner ul li{
    margin-right:10px;
}
.container_inner ul li div{
    display:block;
    padding:3px 10px;
    min-width:30px;
    cursor: pointer;
}
.sel{
    margin-top:-20px;
}
.sel input{
    vertical-align: middle;
    margin-right:3px;
}
.container_inner ul li:first-child a,.container_inner ul li a:hover{
    /* background:#00b38a; */
    /* color:#fff; */
}
.banner{
    width:100%;
    min-width: 1200px;
    height:390px;
    position: relative;
    background:url("../../assets/images/com_bg.jpg") no-repeat center;
    color:#fff;
    text-align: center;
}
.banner h3{
    font-size: 30px;
    color: #fff;
    text-shadow: 0 3px 9px rgba(0,0,0,.25);
    height: 42px;
    padding: 25px 0 20px;
    text-align: center;
    font-weight: 400;
}
.item_list{
    width:1120px;
    padding:0 30px;
    margin:0 auto;
    position:relative;
    overflow: hidden;
}
.item_list ul{
    display: flex;
}
.first{
    width:140px;
    height:220px;
    padding:10px;
    background:rgba(255,255,255,0.6);
    position:relative;
    cursor: pointer;
    
}
.first:hover .second{
   /* display: block; */
   transform: scale(1);
   z-index: 1;
}
.second{
    position:absolute;
    top:0px;
    left:0px;
    width:160px;
    height:240px;
    /* display: none; */
    transform:scale(0);
    transition:.5s;
    z-index: -1;
}
.sec_inner{
    padding:20px;
    font-size:14px;
    height:240px;
    text-align: left;
    color:#666;
    line-height:20px;
    background:rgba(255,255,255,0.9);
}
.sec_btn{
    display: block;
    position: absolute;
    bottom: 15px;
    width: 120px;
    height: 40px;
    text-align: center;
    line-height: 40px;
    margin: 70px auto 10px;
    color: #fff;
    background-color: #00b38a;
}
.first img{
    width:140px;
    height:140px;
}
.banner .back,.banner .next{
    width:45px;
    height:260px;
    line-height:260px;
}
.banner .iconfont{
    font-size:40px;
    cursor:pointer;
}
.banner .back{
    position:absolute;
    left:0;
}
.banner .next{
    position:absolute;
    right:0;
}
.banner_box{
    transition:all 1s ease;
}
.banner_box li{
    padding:10px;
    height:220px;
    margin:10px 0 10px 50px;
}
.banner_box li:first-child{
    margin-left:0;
}
.com_name{
    display: inline-block;
    margin-top:15px;
    color:#000;
}
</style>