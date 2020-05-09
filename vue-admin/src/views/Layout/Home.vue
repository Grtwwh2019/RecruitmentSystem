<template>
  <el-container>
    <el-header class="homeHeader">
        <div class="title">校园招聘后台管理系统</div>
        <div>
            <el-button icon="el-icon-bell" type="text" style="margin-right: 8px;color: #000000;" size="normal"></el-button>
            <el-dropdown class="userInfo" @command="commandHandler">
                <span class="el-dropdown-link" style="color: #fff;">
                {{user.nickname}}<i><img :src="user.headerMask" alt=""></i>
                </span>
                <el-dropdown-menu slot="dropdown">
                    <!-- <el-dropdown-item command="userinfo">个人中心</el-dropdown-item>
                    <el-dropdown-item command="setting">设置</el-dropdown-item> -->
                    <el-dropdown-item command="logout" divided>注销登录</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
    </el-header>
    <el-container>
        <el-aside width="200px">
            <el-menu router unique-opened :default-active="activeMenu" active-text-color="#00b38a" style="border-right:2px #00b38a solid;">
                <div v-for="(item,index) in this.$router.options.routes" :key="index">
                    <div v-if="!item.hidden">
                        <el-menu-item :index="child.path" v-for="(child,indexj) in item.children" :key="indexj" style="margin-top:10px;margin-bottom:10px;">
                            <i style="color: #00b38a;margin-right: 5px" :class="child.meta.icon"></i>
                            {{child.meta.title}}
                        </el-menu-item>

                        <!-- <template v-if="!item.children || item.children.length == 1 || item.children.length == 0">
                            <el-menu-item :index="item.path + '/' + item.children[0].path"  v-if="item.children && item.children.length == 1">
                                {{item.children[0].meta.title}}
                            </el-menu-item>
                            <el-menu-item :index="item.path" v-else>
                                {{item.meta.title}}
                            </el-menu-item>
                        </template>

                        <el-submenu :index="index+''" v-else>
                            <template slot="title">
                                <i style="color: #409eff;margin-right: 5px" :class="item.meta.icon"></i>
                                <span>{{item.meta.title}}</span>
                            </template>
                            <el-menu-item :index="child.path" v-for="(child,indexj) in item.children" :key="indexj">
                                {{child.meta.title}}
                            </el-menu-item>
                        </el-submenu> -->
                    </div>
                </div>
            </el-menu>
        </el-aside>
        <el-main>
            <el-breadcrumb separator-class="el-icon-arrow-right" v-if="this.$router.currentRoute.path!='/home'">
                <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item>{{this.$router.currentRoute.meta.title}}</el-breadcrumb-item>
            </el-breadcrumb>
            <!-- <div class="welcomeLogo">
                <el-image :src="ftp_prefix + 'img/' + welcomeLogo"></el-image>
            </div> -->

            <div class="homeWelcome" v-if="this.$router.currentRoute.path=='/home'">
                欢迎来到校园招聘后台管理系统！
            </div>
            
            <router-view class="homeRouterView"/>
        </el-main>
    </el-container>
    </el-container>
</template>

<script>
export default {
    name: "Home",
    mounted() {
        console.log(this.$router.options.routes)
    },
    computed: {
        activeMenu() {
            const route = this.$route
            const { meta, path } = route
            // if set path, the sidebar will highlight the path you set
            if (meta.activeMenu) {
                return meta.activeMenu
            }
            return path
        }
    },
    data() {
        return {
            // welcomeLogo: 'blz.jpg',
            // ftp_prefix: 'http://attachment.grtwwh.com:8080/',
            user: JSON.parse(window.sessionStorage.getItem("user"))
        }
    },
    methods: {
        commandHandler(cmd) {
            if (cmd == 'logout') {
                this.$confirm('此操作将注销登录, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.post("/user/logout.do");
                    window.sessionStorage.removeItem("user")
                    this.$router.replace("/");
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消操作'
                    });
                });
            }
        }
    }

}
</script>

<style scoped>
    .homeRouterView {
        margin-top: 10px;
    }

    .homeWelcome {
        text-align: center;
        font-size: 30px;
        font-family: 华文行楷;
        color: #00b38a;
        padding-top: 50px;
    }

    .homeHeader {
        background-color: #00b38a;
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 0px 15px;
        box-sizing: border-box;
    }

    .homeHeader .title {
        font-size: 30px;
        font-family: 华文行楷;
        color: #ffffff
    }

    .homeHeader .userInfo {
        cursor: pointer;
    }

    .el-dropdown-link img {
        width: 48px;
        height: 48px;
        border-radius: 24px;
        margin-left: 8px;
    }

    .el-dropdown-link {
        display: flex;
        align-items: center;
    }
</style>