<template>
  <div>
    <div class="view_personalInfo" v-if="!editable">
      <el-button class="edit_acatar el-icon-edit" @click="editable = true" type="text">编辑</el-button>
      <img fit="fill" :src="userInfo.imageUrl" :size="80" class="avatar"></img>
      <div class="view_nickname">
        <span>{{userInfo.nickname}}</span>
      </div>
      <div class="view_role">
        <span>{{userInfo.role.namezh}}</span>
      </div>
      <div class="view_telephone">
        <span>{{userInfo.telephone}}</span>
      </div>
      <div class="view_email">
        <span>{{userInfo.email}}</span>
      </div>
    </div>
    <div class="edit_personalInfo" v-if="editable">
      <el-form
        :rules="rules"
        :inline-message="true"
        ref="personalInfoForm"
        v-loading="loading"
        element-loading-text="正在修改..."
        element-loading-spinner="el-icon-loading"
        element-loading-background="rgba(0, 0, 0, 0.8)"
        :model="personalInfoForm"
        >
        <div class="avatar-uploader">
          <el-button class="edit_back el-icon-back" style="margin-left:110px;" @click="editBack" type="text">返回</el-button>
          <el-form-item prop="headerMask">
            <el-upload
              name="uploadFile"
              action="/common/upload.do"
              :limit="1"
              :data="uploadParam"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :on-error="handleAvatarError"
              :before-upload="beforeAvatarUpload">
              <img fit="fill" :src="userInfo.imageUrl" :size="80" class="avatar"></img>
              <!-- <i v-else class="el-icon-plus avatar-uploader-icon"></i> -->
              <div class="el-upload__tip" slot="tip">点击头像进行上传</div>
            </el-upload>
          </el-form-item>
        </div>
        <div class="edit_nickname">
          <el-form-item prop="nickname">
            <el-input
              style="width:300px;"
              class="input_nickname"
              maxlength="10"
              minlength="1"
              placeholder="请输入常用昵称"
              v-model="personalInfoForm.nickname"
              clearable>
            </el-input>
          </el-form-item>
        </div>
        <div class="submit_button">
          <el-button style="width:100%" type="success" @click="save">保存</el-button>
        </div>
      </el-form>
    </div>

  </div>
</template>

<script>
  import { getUserInfo, getHighestRole, updateUserInfo, deleteFile } from "@/api/index";
  import { Message } from 'element-ui'

  export default {
    // 组件内的守卫
    async beforeRouteLeave (to, from, next) {
      if (this.editable && this.isChanged) {
        let params = {
          fileName: this.personalInfoForm.headerMask,
          remotePath: this.uploadParam.type
        }
        if (params.fileName != null && params.fileName != '' && params.fileName.length != 0) {
          let _result = await deleteFile(params)
        }
        this.$refs.personalInfoForm.resetFields()
        this.editable = false
      }
      next()
    },
    beforeRouteEnter (to, from, next) {
      next(vm => {
        // 通过 `vm` 访问组件实例
        
      })
    },
    async mounted() {
      this.editable = false
      // 获取用户信息
      await this.getUserInfo()
      if (this.userInfo.imageUri == null || this.userInfo.imageUri == '') {
        this.userInfo.imageUrl = 'http://attachment.grtwwh.com:8080/img/yazi.jpeg'
      }
      this.personalInfoForm.nickname = this.userInfo.nickname
    },
    data() {
      return {
        isChanged: false,
        loading: false,
        personalInfoForm: {
          nickname: '',
          headerMask: ''
        },
        // 校验规则
        rules: {
          nickname: [{required: true, message: '请输入昵称', trigger: 'blur'}],
        },
        // 是否编辑
        editable: false,
        userInfo: {
          nickname: '',
          telephone: '',
          email: '',
          // 角色
          role: {
            id: '',
            name: '',
            namezh: ''
          },
          // 头像
          imageUrl: '',
          imageUri: ''
          // imageUrl:'http://www.grtwwh.com:8088/96afad2f-c6ce-4704-ab09-f0ea21acb056',
        },
        uploadParam: {
          type: 'img'
        }
      };
    },
    watch: {
      personalInfoForm: {
        handler(newValue, oldValue) {
          if (newValue.nickname != this.userInfo.nickname || newValue.headerMask != this.userInfo.imageUri) {
            this.isChanged = true
          } else {
            this.isChanged = false
          }
        },
        deep: true
      }
      
    },
    computed: {
      
    },
    methods: {
      async editBack() {
        let params = {
          fileName: this.personalInfoForm.headerMask,
          remotePath: this.uploadParam.type
        }
        if (params.fileName != null && params.fileName != '' && params.fileName.length != 0) {
          let _result = await deleteFile(params)
        }
        this.$refs.personalInfoForm.resetFields()
        this.editable = false
        this.getUserInfo()
      },
      save() {
        this.$refs.personalInfoForm.validate((valid) => {
          if (!this.isChanged) {
            this.editable = false
            return false
          }
          if (valid) {
            this.loading = true
            updateUserInfo(this.personalInfoForm).then(async resp => {
              this.loading = false
              if (resp && resp.status == 1 && resp.msg) {
                Message.error({message: resp.msg})
                let params = {
                  fileName: this.personalInfoForm.headerMask,
                  remotePath: this.uploadParam.type
                }
                if (params.fileName != null && params.fileName != '' && params.fileName.length != 0) {
                  let _result = await deleteFile(params)
                }
              } else if (resp && resp.status == 0 && resp.msg) {
                Message.success({message: resp.msg})
                let params = {
                  fileName: this.userInfo.imageUri,
                  remotePath: this.uploadParam.type
                }
                if (params.fileName != null && params.fileName != '' && params.fileName.length != 0) {
                  let _result = await deleteFile(params)
                }
              } else {
                Message.error({message: "未知错误，请联系管理员！"})
              }
              this.editable = false
              this.getUserInfo()
            })
          }
        })
      },
      async getUserInfo() {
        let baseInfo = await getUserInfo()
        if (baseInfo && baseInfo.status == 0) {
          // 获取成功
          this.userInfo.nickname = baseInfo.data.nickname
          this.userInfo.telephone = baseInfo.data.telephone
          this.userInfo.email = baseInfo.data.email
          this.userInfo.imageUrl = baseInfo.data.headerMask
          let url = baseInfo.data.headerMask.split("/")
          this.userInfo.imageUri = url[(url.length) - 1]
        }
        let roleInfo = await getHighestRole()
        if (roleInfo && roleInfo.status == 0) {
          // 获取成功
          this.userInfo.role = roleInfo.data
        }
      },
      handleAvatarSuccess(res, file) {
        if (res && res.status == 0) {
          this.personalInfoForm.headerMask = res.data.uri
          this.userInfo.imageUrl = URL.createObjectURL(file.raw);
        }
      },
      handleAvatarError(err, file, fileList) {
        Message.error({message:err})
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        return isJPG && isLt2M;
      }
    }
  }
</script>

<style scoped>
  .view_personalInfo, .edit_personalInfo {
    text-align:center;
    width: 50%;
  }
  .edit_acatar, .edit_back {
    /* text-align: left; */
    position: absolute;
    left: 30%;
    top: 2px;
    margin-left: 30px;
    /* padding-left: 20px; */
    line-height: 18px;
    color: #00b38a;
    font-size: 16px;
    /* cursor: pointer; */
  }
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 100px;
    height: 100px;
    border-radius:50%;
  }
  .view_nickname {
    font-size: 18px;
    margin-top: 20px;
    color: #333;
  }

  .view_role, .view_telephone, .view_email {
    color: #555;
    font-size: 14px;
    margin-top: 10px;
  }

  .input_nickname >>> .el-input__inner{
    display: block;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    width: 100%;
    height: 44px;
    margin-top: 0;
    padding: 12px 0;
    font-size: 14px;
    color: #555;
    border: none;
    border-bottom: 1px solid #ededed;
    -webkit-transition: border .2s ease-in 0s;
    transition: border .2s ease-in 0s;
  }
</style>