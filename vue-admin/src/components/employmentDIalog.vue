<template>
  <el-dialog class="dialog" :title="dialogDetail.title" width="30%" :visible.sync="showVisiable" :close-on-click-modal="false" :close-on-press-escape="false" @open="handleOpen" :before-close="handleClose">
        <div class="dialog-content">
            <el-form  ref="data" :model='dataSource' label-width="80px" style="margin-right:80px;">
                <el-form-item prop="ename" label="职位名称">
                    <el-input
                    readonly
                    v-model="dataSource.ename" 
                    >
                    </el-input>
                </el-form-item>

                <el-form-item prop="etypeid" label="职位类型">
                    <el-input
                    readonly
                    v-model="dataSource.etypeid" 
                    >
                    </el-input>
                </el-form-item>

                <el-form-item prop="ecityid" label="工作城市">
                    <!-- <el-cascader readonly :options="this.$store.state.citySet" :show-all-levels="false" v-model="dataSource.ecityid"></el-cascader> -->
                    <el-input
                    readonly
                    v-model="dataSource.ecityid"
                    >
                    </el-input>
                </el-form-item>

                <el-form-item prop="esalaryfloor" label="最低薪资">
                    <el-input
                    readonly
                    v-model="dataSource.esalaryfloor" 
                    >
                    </el-input>
                </el-form-item>


                <el-form-item prop="esalaryceilie" label="最高薪资">
                    <el-input
                    readonly
                    v-model="dataSource.esalaryceilie" 
                    >
                    </el-input>
                </el-form-item>


                <el-form-item prop="educationrequire" label="学历要求">
                    <el-input
                    readonly
                    v-model="dataSource.educationrequire" 
                    >
                    </el-input>
                </el-form-item>

                <el-form-item prop="eresponsibility" label="岗位职责">
                    <el-input
                    type="textarea"
                    :rows="8"
                    resize="none"
                    readonly
                    v-model="dataSource.eresponsibility" 
                    >
                    </el-input>
                </el-form-item>

                <el-form-item prop="erequirement" label="岗位要求">
                    <el-input
                    readonly
                    type="textarea"
                    :rows="8"
                    resize="none"
                    v-model="dataSource.erequirement" 
                    >
                    </el-input>
                </el-form-item>

                <el-form-item prop="publisher" label="发布人">
                    <el-input
                    readonly
                    v-model="dataSource.publisher" 
                    >
                    </el-input>
                </el-form-item>

                <el-form-item prop="createtime" label="发布时间">
                    <el-input
                    readonly
                    v-model="dataSource.createtime" 
                    >
                    </el-input>
                </el-form-item>

                <el-form-item prop="estatus" label="状态">
                    <el-input
                    readonly
                    v-model="dataSource.estatus" 
                    >
                    </el-input>
                </el-form-item>



            </el-form>    
        </div>
    <div style="text-align:center;">
        <el-button type="primary" size="small" style="margin-right:30px;background-color:#00b38a;border-color: #00b38a;" @click="operator" >{{dialogDetail.btn}}</el-button>
        <el-button style="margin-left:30px;" size="small" @click="handleClose">取 消</el-button>
    </div>
</el-dialog>
</template>

<script>
import { degree } from "@/const/index";
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
            degree: {}
        }
    },
    methods: {
        init() {
            this.degree = degree
        },
        operator() {
            this.$confirm('该操作将'+ this.dialogDetail.btn +'此招聘信息，是否继续？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                closeOnClickModal: false,
                closeOnPressEscape: false,
                type: 'warning'
            }).then(() => {
                this.$emit('handleDialogClose', this.dataSource.id, this.dataSource.estatus == '封禁' ? 1 : 0)
            }).catch(() => {
                this.$message({
                type: 'info',
                message: '已取消操作'
                })
            })
        },
        handleOpen() {
            console.log(this.$store.state.positionTypeSet)
            this.dataSource.educationrequire = this.degree[this.dataSource.educationrequire]
            // this.dataSource.ecityid = (Number)(this.dataSource.ecityid)
            this.$store.state.citySet.forEach(children => {
                children.children.forEach(element => {
                    if (element.value == (Number)(this.dataSource.ecityid)) {
                        this.dataSource.ecityid = element.label
                        return
                    }
                });
            });
            this.$store.state.positionTypeSet.forEach(element => {
                element.children.forEach(element => {
                    element.children.forEach(element => {
                        if (element.value == (Number)(this.dataSource.etypeid)){
                            this.dataSource.etypeid = element.label
                            return
                        }
                    })
                });
            });
            this.dataSource.estatus = this.dataSource.estatus == 0 ? '封禁' : '正常'
        },
        handleClose() {
            this.$emit('handleDialogClose')
        }
    }
}
</script>

<style>

</style>