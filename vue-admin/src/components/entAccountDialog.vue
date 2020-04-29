<template>
  <el-dialog class="dialog" :title="dialogDetail.title" width="30%" :visible.sync="showVisiable" :close-on-click-modal="false" :close-on-press-escape="false" @open="handleOpen" :before-close="handleClose">
      <div class="dialog-content">
          <el-form  ref="data" :model='dataSource' label-width="80px" style="margin-right:80px;">
            <el-form-item prop="id" label="Id">
                <el-input
                readonly
                v-model="dataSource.id" 
                >
                </el-input>
            </el-form-item>

            <el-form-item prop="nickname" label="昵称">
                <el-input
                readonly
                v-model="dataSource.nickname" 
                >
                </el-input>
            </el-form-item>

            <el-form-item prop="username" label="登录账号">
                <el-input
                readonly
                v-model="dataSource.username" 
                >
                </el-input>
            </el-form-item>

            <el-form-item prop="license" label="营业执照">
                <el-image
                    style="width: 100px; height: 100px;border: 1px solid #DCDFE6"
                    :src="url"
                    fit="fill"
                    :preview-src-list="srcList">
                </el-image>
                <span class="demonstration" style="margin-left:10px;color:#00b38a;text-decoration:underline;">tips：点击图片放大</span>
            </el-form-item>

            <el-form-item label="状态">
                <div style="border: 1px solid #DCDFE6; padding-left:15px;border-radius:5px;" v-if="dialogDetail.btn && dialogDetail.btn != ''">
                    <el-radio v-model="auth" :label="1">通过认证</el-radio>
                    <el-radio v-model="auth" :label="2">拒绝认证</el-radio>
                </div>

                <div v-else style="border: 1px solid #DCDFE6; padding-left:15px;border-radius:5px;">
                    {{authInfo[dataSource.authentication]}}
                </div>
            </el-form-item>


          </el-form>
      </div>
      <div style="text-align:center;">
        <el-button v-if="dialogDetail.btn && dialogDetail.btn != ''" type="primary" size="small" style="margin-right:30px;background-color:#00b38a;border-color: #00b38a;" @click="operator" >{{dialogDetail.btn}}</el-button>
        <el-button style="margin-left:30px;" size="small" @click="handleClose">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { auth } from "@/const/index";
export default {
    mounted() {
        this.init()
    },
    props: {
        dialogDetail: {type: Object},
        showVisiable: {type: Boolean},
        dataSource: {type: Object}
    },
    data() {
        return {
            authInfo: {},
            auth: 1,
            url: '',
            srcList: [],
            ftp_prefix: 'http://attachment.grtwwh.com:8080/'
        }
    },
    methods: {
        init() {
            this.authInfo = auth
            this.auth = 1
            this.url = ''
            this.srcList = []

        },
        operator() {
            this.$confirm('该操作将'+ this.dialogDetail.btn +'此招聘信息，是否继续？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                closeOnClickModal: false,
                closeOnPressEscape: false,
                type: 'warning'
            }).then(() => {
                this.$emit('handleDialogClose', this.dataSource.id, this.auth)
                this.init()
            }).catch(() => {
                this.$message({
                type: 'info',
                message: '已取消操作'
                })
            })
        },
        handleOpen() {
            this.url = this.ftp_prefix + 'img/' + this.dataSource.license
            this.srcList.push(this.url)
        },
        handleClose() {
            this.$emit('handleDialogClose')
            this.init()
        }
    }
}
</script>

<style>

</style>