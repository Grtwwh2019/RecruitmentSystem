<template>
  <el-dialog class="dialog" :title="dialogDetail.title" width="30%" :visible.sync="showVisiable" :close-on-click-modal="false" :close-on-press-escape="false" @open="handleOpen" :before-close="handleClose">
        <div class="dialog-content">
            <el-form v-if="dialogDetail.type == 'announce'" ref="data" :model="data" label-width="80px" hide-required-asterisk :rules="rules" style="margin-right:80px;">
                <el-form-item prop="title" label="标题">
                    <el-input
                    v-model="data.title" 
                    maxlength=128
                    :show-word-limit="true"
                    placeholder="请输入标题"
                    clearable>
                    </el-input>
                </el-form-item>

                <el-form-item prop="type" label="类型">
                    <el-select style="width:100%;" v-model="data.type" placeholder="请选择类型">
                        <el-option
                        v-for="(value, key) in announceType"
                        :key="key"
                        :label="value"
                        :value="Number(key)">
                        </el-option>
                    </el-select>
                </el-form-item>

                <el-form-item prop="content" label="内容" >
                    <el-input
                    type="textarea"
                    :rows="8"
                    placeholder="请输入内容"
                    resize="none"
                    v-model="data.content">
                    </el-input>
                </el-form-item>

                <el-form-item label="接收人" prop="receiver">
                    <el-select
                        style="width:100%;"
                        v-model="data.receiver"
                        :filterable="true"
                        :remote="true"
                        :reserve-keyword="false"
                        placeholder="请输入接收人"
                        :remote-method="remoteMethod"
                        :loading="loading"
                        clearable
                        >
                        <el-option
                        v-for="(item, index) in options"
                        :key="index"
                        :label="item.label"
                        :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>

            </el-form>

            <el-form v-if="dialogDetail.type == 'company'" ref="data" :model='data' label-width="80px" hide-required-asterisk :rules="rules" style="margin-right:80px;">
                
                <el-form-item prop="logo" label="公司Logo" style="text-align:center;">
                    <el-upload
                    style="border: 1px #00b38a solid;"
                    name="uploadFile"
                    ref="logo"
                    action="/common/upload.do"
                    :limit="1"
                    :data="{type: 'img'}"
                    :show-file-list="false"
                    :on-success="handleLogoSuccess"
                    :on-error="handleLogoError"
                    :before-upload="beforeLogoUpload"
                    :on-exceed="handleExceed"
                    :file-list="logoList"
                    >
                    <el-image fit="fill" :src="ftp_prefix + 'img/' + companyLogo" style="width: 100px; height: 100px" class="logo"></el-image>
                    <!-- <i v-else class="el-icon-plus Logo-uploader-icon"></i> -->
                    <div class="el-upload__tip" slot="tip" >点击Logo进行上传</div>
                    </el-upload>
                </el-form-item>

                <el-form-item prop="photo" label="公司环境" style="text-align:center;">
                    <el-upload
                        class="upload-demo"
                        style="border: 1px #00b38a solid;"
                        name="uploadFile"
                        ref="photo"
                        action="/common/upload.do"
                        :data="{type: 'img'}"
                        :on-change="handlePhotoOnChange"
                        :on-success="handlePhotoSuccess"
                        :before-upload="beforePhotoUpload"
                        :before-remove="handlePhotoBeforeRemove"
                        :on-remove="handlePhotoOnRemove"
                        :on-error="handleLogoError"
                        :file-list="photoList"
                        list-type="picture"
                        multiple
                        >
                        <!-- :auto-upload="false" -->
                        <el-button size="small" type="primary" style="background-color:#00b38a">点击上传</el-button>
                        <div slot="tip" class="el-upload__tip">只能上传jpg文件，且不超过500kb</div>
                    </el-upload>
                </el-form-item>

                <el-form-item prop="cname" label="公司名称">
                    <el-input
                    v-model="data.cname" 
                    maxlength=255
                    :show-word-limit="true"
                    placeholder="公司名称"
                    clearable>
                    </el-input>
                </el-form-item>

                <el-form-item prop="fullname" label="公司全称">
                    <el-input
                    v-model="data.fullname" 
                    maxlength=255
                    :show-word-limit="true"
                    placeholder="公司全称"
                    clearable>
                    </el-input>
                </el-form-item>

                <el-form-item prop="ccityId" label="所在城市" style="">
                    <el-cascader style="width:100%;" :options="this.$store.state.citySet" v-model="data.ccityId" :props="uaualCascaderProp" :show-all-levels="false" placeholder="城市" clearable></el-cascader>
                </el-form-item>

                <el-form-item prop="industry" label="行业">
                    <el-cascader style="width:100%;" :options="this.$store.state.industrySet" v-model="data.industryid" :props="uaualCascaderProp" :show-all-levels="false" placeholder="请选择行业" clearable></el-cascader>
                </el-form-item>

                <el-form-item prop="csize" label="规模">
                    <el-select style="width:100%;" v-model="data.csize" placeholder="请选择规模" clearable>
                        <el-option
                        v-for="(value, key) in companySize"
                        :key="key"
                        :label="value"
                        :value="Number(key)">
                        </el-option>
                    </el-select>
                </el-form-item>

                <el-form-item prop="financing" label="融资">
                    <el-select style="width:100%;" v-model="data.financing" placeholder="请选择融资阶段" clearable>
                        <el-option
                        v-for="(value, key) in financing"
                        :key="key"
                        :label="value"
                        :value="Number(key)">
                        </el-option>
                    </el-select>
                </el-form-item>

                <el-form-item prop="assets" label="资产">
                    <el-input
                    v-model="data.assets" 
                    maxlength=18
                    placeholder="资产"
                    clearable>
                     <i slot="suffix" style="font-style:normal;margin-right: 10px;">万元（￥）</i>
                    </el-input>
                </el-form-item>

                <el-form-item prop="representative" label="法人代表">
                    <el-input
                    v-model="data.representative" 
                    maxlength=128
                    :show-word-limit="true"
                    placeholder="法人代表"
                    clearable>
                    </el-input>
                </el-form-item>

                <el-form-item prop="establishtime" label="成立时间">
                    <el-date-picker
                    style="width:100%;"
                    v-model="data.establishtime"
                    value-format="yyyy-MM-dd"
                    type="date"
                    placeholder="成立时间">
                    </el-date-picker>
                </el-form-item>

                <el-form-item prop="cintroduction" label="公司介绍">
                    <el-input
                    type="textarea"
                    :rows="8"
                    placeholder="公司介绍"
                    resize="none"
                    v-model="data.cintroduction">
                    </el-input>
                </el-form-item>



            </el-form>

            
        </div>
        <div style="text-align:center;">
            <el-button type="primary" size="small" style="margin-right:30px;background-color:#00b38a;border-color: #00b38a;" @click="save" >{{dialogDetail.btn}}</el-button>
            <el-button style="margin-left:30px;" size="small" @click="handleClose">取 消</el-button>
        </div>
    </el-dialog>
</template>

<script>
const announceType = {
    '1': '系统通知',
    '2': '公司通知'
}
import { companySize, financing } from "@/const/index";
export default {
    beforeRouteLeave(to, from, next) {
        if (this.data.logo &&  this.data.logo != '' && this.companyLogo != this.data.logo) {
            this.deleteFile(this.companyLogo)
        }
        // 删除photo
        this.photoList.forEach(file => {
            if (file.status == 'success') {
                if (file.response && file.response.status == 0) {
                    this.deleteFile(file.response.data.uri)
                }
            }
        });
        next()
    },
    mounted() {
        this.companySize = companySize
        this.financing = financing
        this.announceType = announceType
    },
    props: {
        dialogDetail: {type: Object},
        showVisiable: {type: Boolean},
        dataSource: {type: Object}
    },
    data() {
        return {
            data: {},
            rules: {
                // announce
                title: [{required: true, message: '必填', trigger: 'blur'}],
                type: [{required: true, message: '必填', trigger: 'blur'}],
                content: [{required: true, message: '必填', trigger: 'blur'}],
                receiver: [{required: true, message: '必填', trigger: 'blur'}]
            },
            // announce
            loading: false,
            receivers: [],
            options: [],
            announceType: {},
            // company
            uaualCascaderProp: {
                emitPath : false
            },
            companyLogo: '',
            newList:[],
            deleteList: [],
            photoList: [],
            logoList: [],
            ftp_prefix: 'http://attachment.grtwwh.com:8080/',
            companySize: {},
            financing: {}
        }
    },
    methods: {
        init () {
            // company
            this.companyLogo= ''
            this.newList = []
            this.deleteList= []
            this.photoList= []
            this.logoList= []
            // announce
            this.receivers= []
            this.options= []
        },
        handleOpen() {
            this.data = Object.assign({}, this.dataSource)
            if (this.dialogDetail.type == 'announce') {
                let receiver = {
                    label: this.data.receiverName,
                    value: this.data.receiver
                }
                this.options.push(receiver)
            }
            if (this.dialogDetail.type == 'company') {
                this.data.ccityId = (Number) (this.data.ccityId)
                this.companyLogo = (this.data.logo == null || this.data.logo == '') ? 'yazi.jpeg' :this.data.logo
                if (this.data.photo && this.data.photo != '') {
                    let photos = this.data.photo.split(',')
                    photos.forEach(photo => {
                        let file = {
                            name: photo,
                            url: this.ftp_prefix + 'img/' + photo
                        }
                        this.photoList.push(file)
                    });
                }
            }
        },
        save(){
            this.$refs.data.validate(valid => {
                if (valid) {
                    switch(this.dialogDetail.type) {
                        case 'company':
                            if ( this.data.logo &&  this.data.logo != '' && this.companyLogo != this.data.logo) {
                                let deleteLogo = this.data.logo
                                this.data.logo = this.companyLogo
                                this.deleteFile(deleteLogo)
                                // this.deleteFile(this.data.logo).then(resp => {
                                //     if (resp) {
                                //         this.data.logo = this.companyLogo
                                //     }
                                // })
                            }
                            // 保存按钮
                            // 删除照片
                            if (this.deleteList.length > 0) {
                                this.deleteList.forEach(element => {
                                    this.deleteFile(element.name)
                                });
                            }
                            // 记录photo
                            let p = ''
                            this.photoList.forEach(file => {
                                if (file.status == 'success') {
                                    if (file.response && file.response.status == 0) {
                                        p += (',' + file.response.data.uri)
                                    }
                                    if (!file.response) {
                                        p += (',' + file.name)
                                    }
                                }
                            });
                            p = p.replace(',', '')
                            this.data.photo = p
                            break
                    }
                    this.init()
                    this.$emit('handleDialogClose',this.data)
                    
                }
            })
        },
        handleClose() {
            this.$confirm('离开修改内容将不会保存，是否继续？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                closeOnClickModal: false,
                closeOnPressEscape: false,
                type: 'warning'
            }).then(() => {
                if (this.dialogDetail.type == 'company') {
                    if (this.data.logo &&  this.data.logo != '' && this.companyLogo != this.data.logo) {
                        this.deleteFile(this.companyLogo)
                    }
                    // 删除photo
                    this.photoList.forEach(file => {
                        if (file.status == 'success') {
                            if (file.response && file.response.status == 0) {
                                this.deleteFile(file.response.data.uri)
                            }
                        }
                    });
                }
                this.init()
                this.$refs.data.resetFields()
                this.$emit('handleDialogClose', false)
            }).catch(() => {
                this.$message({
                type: 'info',
                message: '已取消操作'
                })
            })
        },
        // company
        sendFile() {
            // const loading = this.$loading({
            //     lock: true,
            //     text: '文件上传中请稍等...',
            //     spinner: 'el-icon-loading',
            //     background: 'rgba(0, 0, 0, 0.7)'
            // })
            // this.postJson('/common/upload.do', data).then(resp => {
            //     loading.close()
            //     if (resp) {
            //         // 上传成功
            //         let p = this.data.photo
            //         p += (',' + resp.data.uri)
            //         p = p.indexOf(',') == 0 ? p.replace(',', '') : p
            //         this.data.photo = p
            //     }
            // })
        },
        handlePhotoOnChange(file, fileList) {
            // console.log('change', file)
            this.photoList = fileList
            // this.newList.push(file.raw)
        },
        async deleteFile(fileName) {
            let params = {
                fileName: fileName,
                remotePath: 'img'
            }
            let resp = await this.post('/common/deleteFile.do', params)
            return resp
        },
        handlePhotoSuccess(response, file, fileList) {
            this.photoList = fileList
        },
        beforePhotoUpload(file) {
            const isJPG = file.type === 'image/jpeg';
            const isLt2M = file.size / 1024 / 1024 < 2;
            if (!isJPG) {
                this.$message.error('上传Logo图片只能是 JPG 格式!');
            }
            if (!isLt2M) {
                this.$message.error('上传Logo图片大小不能超过 2MB!');
            }
            return isJPG && isLt2M;
        },
        handlePhotoOnRemove(file, fileList) {
            this.photoList = fileList
            this.deleteList.push(file)
        },
        handlePhotoBeforeRemove(file, fileList) {
            return this.$confirm('删除后不可恢复，确认删除吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                closeOnClickModal: false,
                closeOnPressEscape: false,
                type: 'warning'
            }).then(() => {
                
            }).catch(() => {
                this.$message({
                type: 'info',
                message: '已取消删除'
                })
                reject(false)
            })
        },
        handleLogoSuccess(res, file) {
            if (res) {
                this.companyLogo = res.data.uri
            }
        },
        handleLogoError(err, file, fileList) {
            Message.error({message:err})
        },
        beforeLogoUpload(file) {
            const isJPG = file.type === 'image/jpeg';
            const isLt2M = file.size / 1024 / 1024 < 2;
            if (!isJPG) {
                this.$message.error('上传Logo图片只能是 JPG 格式!');
            }
            if (!isLt2M) {
                this.$message.error('上传Logo图片大小不能超过 2MB!');
            }
            return isJPG && isLt2M;
        },
        // 文件超出个数限制时的钩子
        handleExceed(files, fileList) {
            this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
        },
        // announce
        async remoteMethod(query) {
            // query为当前输入值
            if (query !== '') {
                let resp = await this.get('/admin/getUserListByUserName.do/' + query)
                this.receivers = []
                if (resp && resp.status == 0) {
                    let result = resp.data
                    result.map(item=>{
                        this.receivers.push({
                            label: item.nickName,
                            value: item.id
                        })
                    })
                }
                this.loading = true
                setTimeout(() => {
                    this.loading = false
                    this.options = this.receivers.filter(item => {
                        return item.label.indexOf(query) > -1
                    })
                }, 200)
            } else {
                this.options = []
            }
        },
    }

}
</script>

<style>

</style>