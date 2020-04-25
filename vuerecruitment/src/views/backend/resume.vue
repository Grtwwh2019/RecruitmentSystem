<!-- resume -->
<template>
<div class='resume'>
    <div class="container">
        <div class="resume_l l1">
            <div class="my_resume">
                <div class="basic">
                    <div class="my_img">
                      <el-upload
                        name="uploadFile"
                        action="/common/upload.do"
                        :limit="1"
                        :data="{type: 'img'}"
                        :show-file-list="false"
                        :file-list="avatarFilelist"
                        :on-success="handleAvatarSuccess"
                        :on-error="handleAvatarError"
                        :before-upload="beforeAvatarUpload"
                        :on-exceed="handleExceed">
                        <img fit="fill" :src="headermaskimg" :size="80" class="avatar"></img>
                        <!-- <i v-else class="el-icon-plus avatar-uploader-icon"></i> -->
                        <div class="el-upload__tip" slot="tip">点击上传新头像</div>
                      </el-upload>
                    </div>
                    <div class="my_info">
                         <h3>{{mineData.baseInfo.realname}}</h3>
                         <i v-show="mineData.baseInfo.gender == 1" class="el-icon-male"></i>
                         <i v-show="mineData.baseInfo.gender == 2" class="el-icon-female"></i>
                         <p v-if="mineData.educationExpList.length > 0">{{mineData.baseInfo.education.schoolName}} / {{degree[mineData.baseInfo.education.degree]}}</p>
                         <p class="tel">
                             <span><i class="iconfont icon-shouji"></i>{{mineData.baseInfo.telephone}}</span>
                             <span><i class="iconfont icon-youxiang"></i>{{mineData.baseInfo.email}}</span>
                         </p>
                    </div>
                    <el-button class="edit edit_btn el-icon-edit" @click="showDialog('basic')" type="text">编辑</el-button>
                    <!-- <router-link to="" class="edit"><i class="iconfont icon-bianji-copy"></i>编辑</router-link> -->
                </div>

                <!-- 编辑基本信息 -->
                <resumeBasicDialog v-slot:title :showVisiable.sync="dialogFormVisible" :baseInfo.sync="dialogData" @handleShowBasicDialog = "handleShowBasicDialog">
                  <p style="font-size: 18px;color: #333;">{{dialogData.title}}</p>
                  <!-- <p style="font-size: 18px;color: #333;">个人优势</p> -->
                </resumeBasicDialog>

                <div class="my_tml">
                    <ul>
                        <li>
                            <div class="my_title" >
                                <h4>个人优势</h4>
                            </div>
                            <!-- <div class="my_tags">
                                <span v-for="(item, index) in mineData.myself.tags" :key="index">{{item}}</span>
                            </div> -->
                            <div style="padding: 8px 0 8px 15px;position: relative;min-height: 40px;">
                              <div class="my_intro" style="word-break:break-all;width: 90%;margin-top: 13px;display: flex;font-size: 14px;color: #666;line-height: 30px;">
                                <p>{{mineData.baseInfo.advantage}}</p>
                              </div>
                              <div class="editor-tool" style="margin-left: 83%">
                                <el-button class="edit_btn el-icon-edit" @click="showDialog('advantage')" type="text">编辑</el-button>
                              </div>
                            </div>

                            <!-- 编辑个人优势 -->
               



                            
                        </li>
                        <li>
                             <div class="my_title">
                                <h4>教育经历</h4>
                                <div class="add r1">
                                    <i class="iconfont icon-tianjia" @click="showDialog('addEdu')"></i>添加
                                </div>
                             </div>
                             <!-- <div class="school_img">
                                <img :src="mineData.edu.img">
                             </div> -->
                             <div class="school_info" style="padding: 8px 0 8px 15px;position: relative;min-height: 40px;" v-for="(school, index) in mineData.educationExpList" :key="index">
                                 <div class="sc_in">
                                     <h3>{{school.schoolName}}</h3>
                                     <p class="r1">{{school.startdate}} 至 {{school.enddate}}</p>
                                 </div>
                                 <p style="margin-bottom:20px">{{degree[school.degree]}} {{schoolType[school.schoolType]}} / {{school.major}}</p>
                                <div class="editor-tool" style="margin-top:-30px; margin-left: 90%">
                                  <el-button class="edit_btn el-icon-edit" @click="showDialog('editEdu', index)" type="text">编辑</el-button>
                                </div>
                             </div>
                        </li>
                        <li>
                            <div class="my_title">
                                <h4>工作经历</h4>
                                <div class="add r1">
                                    <i class="iconfont icon-tianjia" @click="showDialog('addWork')"></i>添加
                                </div>
                             </div>
                             <ul class="work-exp_group" v-for="(workExp,index) in mineData.workExpList" :key="index">
                               <li class="work-exp_list" style="padding: 8px 0 8px 15px;position: relative;min-height: 40px;">
                                <div class="primary-info" style="padding: 7px 0;color: #61687c;white-space: pre-wrap;overflow: hidden;word-break: break-all;">
                                  <div class="info-text">
                                    <h4 class="name" style="max-width: 420px;word-break: break-all;float: left;color: #414a60;">
                                      <span>{{workExp.cname}}</span>
                                      <span v-if="workExp.department">／ {{workExp.department}}</span>
                                    </h4>
                                    <span class="gray period" style="font-size: 12px;padding-left: 30px;float: left;position: relative;top: 2px;">
                                      {{workExp.startdate}} 至 {{workExp.enddate}}
                                    </span>
                                  </div>
                                  <h4 style="font-size: 15px;font-weight: 700;margin-top:20px">
                                    <span class="prev-line" style="max-width: 250px;">{{workExp.positionTypeZh}}</span>
                                  </h4>
                                  <div class="info-text" style="padding: 7px 0;color: #61687c;white-space: pre-wrap;overflow: hidden;word-break: break-all;">
                                    <span class="text-type" style="font-weight: 700;">内容：
                                    </span>{{workExp.workcontent}}
                                  </div>
                                  <div class="keywords" style="padding: 7px 0 5px;">
                                    <span v-for="tag in workExp.skillTags" v-if="tag"
                                     style="display: inline-block;font-size: 12px;line-height: 20px;color: #9fa3b0;padding: 0 14px;margin-right: 10px;border: 1px solid #cfd1d7;border-radius: 50px;">
                                      {{tag.skillTag}}
                                    </span>
                                  </div>
                                </div>
                                <div class="work-operator">
                                  <el-button class="edit_btn el-icon-edit" 
                                    style="display: inline-block;line-height: 24px;padding: 0 5px;" 
                                    @click="showDialog('editWork',index)"
                                    type="text">编辑
                                  </el-button>

                                  <el-button class="edit_btn el-icon-delete" 
                                    style="display: inline-block;line-height: 24px;padding: 0 5px;" 
                                    @click="del('work', workExp.id)" 
                                    type="text">删除
                                  </el-button>
                                </div>
                               </li>
                             </ul>
                             
                        </li>
                        <li>
                            <div class="my_title">
                                <h4>项目经历</h4>
                                <div class="add r1">
                                    <i class="iconfont icon-tianjia" @click="showDialog('addProject')"></i>添加
                                </div>
                            </div>
                            <ul class="project-exp" v-for="(project, index) in mineData.projectExpList" :key="index">
                               <li class="work-exp_list" style="padding: 8px 0 8px 15px;position: relative;min-height: 40px;">
                                <div class="primary-info" style="padding: 7px 0;color: #61687c;white-space: pre-wrap;overflow: hidden;word-break: break-all;">
                                  <div class="info-text">
                                    <h4 class="name" style="max-width: 420px;word-break: break-all;float: left;color: #414a60;">
                                      <span>{{project.projectName}}</span>
                                    </h4>
                                    <span class="gray period" style="font-size: 12px;padding-left: 30px;float: left;position: relative;top: 2px;">
                                      {{project.starttime}} 至 {{project.endtime}}
                                    </span>
                                  </div>
                                  <h4 style="font-size: 15px;font-weight: 700;margin-top:20px;margin-bottom:20px">
                                    <span class="prev-line" style="max-width: 250px;">{{project.role}}</span>
                                  </h4>
                                  <div class="info-text" style="padding: 7px 0;color: #61687c;white-space: pre-wrap;overflow: hidden;word-break: break-all;">
                                    <span class="text-type" style="font-weight: 700;">内容：
                                    </span>{{project.description}}
                                  </div>
                                  
                                </div>
                                <div class="work-operator">
                                  <el-button class="edit_btn el-icon-edit" 
                                    style="display: inline-block;line-height: 24px;padding: 0 5px;" 
                                    @click="showDialog('editProject',index)"
                                    type="text">编辑
                                  </el-button>

                                  <el-button class="edit_btn el-icon-delete" 
                                    style="display: inline-block;line-height: 24px;padding: 0 5px;" 
                                    @click="del('project', project.id)"  
                                    type="text">删除
                                  </el-button>
                                </div>
                               </li>
                             </ul>
                            
                          
                        </li>
                        <li>
                            <div class="my_title">
                                <h4>资格证书</h4>
                                <div class="add r1">
                                    <i class="iconfont icon-tianjia" @click="showDialog('addCertification')"></i>添加
                                </div>
                            </div>
                            <ul class="certification">
                              <li class="certificate-list" style="padding: 8px 0 8px 15px;position: relative;min-height: 40px;">
                                <div class="primary-info">
                                  <div class="visible-wrap" style="position: relative;padding-right: 70px;max-height: 66px;overflow: hidden;">
                                    <div>
                                      <span class="resume-cert-tag"
                                        style="display: inline-block;
                                              font-size: 12px;
                                              font-weight: 400;
                                              color: #61687c;
                                              white-space: nowrap;
                                              height: 23px;
                                              line-height: 22px;
                                              border-radius: 2px;
                                              border: 1px solid #d0d1d6;
                                              padding: 0 10px;
                                              margin: 5px 10px 5px 0;
                                              overflow: hidden;
                                              vertical-align: top;
                                              position: relative;"
                                        v-for="(item, index) in mineData.certificationList">
                                        {{item.certificationName}}
                                      </span>
                                      
                                    </div>
                                  </div>
                                </div>
                                <div class="work-operator">
                                  <el-button class="edit_btn el-icon-edit" 
                                    style="display: inline-block;line-height: 24px;padding: 0 5px;" 
                                    @click="showDialog('addCertification')"
                                    type="text">编辑
                                  </el-button>
                                </div>
                              </li>
                            </ul>
                              
                        </li>
                        <li>
                            <div class="my_title">
                                <h4>社交主页</h4>
                                <div v-if="!mineData.baseInfo.socialpage" class="add r1">
                                    <i class="iconfont icon-tianjia" @click="toAddOrEditSocialPage = true" ></i>添加
                                </div>
                            </div>
                             <div class="social-page" 
                              v-if="mineData.baseInfo.socialpage"
                              style="padding: 8px 0 8px 15px;
                              position: relative;
                              min-height: 40px;">
                              <div>
                                <img :src="ftp_server_http_prefix + 'img/' + 'socialPage.png'" class="social-img">
                                <span>{{mineData.baseInfo.socialpage}}</span>
                              </div>
                             <div class="work-operator" v-if="mineData.baseInfo.socialpage && !toAddOrEditSocialPage">
                              <el-button class="edit_btn el-icon-edit" 
                                style="display: inline-block;line-height: 24px;padding: 0 5px;" 
                                @click="toAddOrEditSocialPage = true" 
                                type="text">编辑
                              </el-button>

                              <el-button class="edit_btn el-icon-delete" 
                                style="display: inline-block;line-height: 24px;padding: 0 5px;" 
                                @click="updateSocialpage(mineData.baseInfo, 'del')" 
                                type="text">删除
                              </el-button>
                            </div>
                             </div>
                             <div v-if="toAddOrEditSocialPage" style="margin-top:-90px;padding: 20px 30px;margin: 0 -30px;background-color: #f8f9fb;">
                              <h3 v-if="mineData.baseInfo.socialpage" class="title" style="color: #24272e;font-size: 16px;font-weight: 500;margin: 0 0 16px 30px;">编辑社交主页</h3>
                              <h3 v-if="!mineData.baseInfo.socialpage" class="title" style="color: #24272e;font-size: 16px;font-weight: 500;margin: 0 0 16px 30px;">添加社交主页</h3>
                              
                              <el-form style="padding: 0 30px;" ref="socialForm" :model="socialForm" :rules="rules" >
                                <el-form-item prop="socialpage">
                                  <el-input v-model="socialForm.socialpage" placeholder="请输入内容" @keydown.enter.native="updateSocialpage(mineData.baseInfo, 'update')"></el-input>
                                </el-form-item>
                              
                              </el-form>
                              <div style="text-align: right;padding-right: 30px;margin-top:15px">
                                <el-button style="margin-right: 20px;"  
                                  @click="toAddOrEditSocialPage = false">取消
                                </el-button>

                                <el-button
                                  type="success" 
                                  @click="updateSocialpage(mineData.baseInfo, 'update')">完成
                                </el-button>
                              </div>
                             </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="resume_r r1">
            <div class="res_top">
               <div class="resume_r_head">
                <h3>求职意向</h3>
                <div class="add r1">
                  <el-button class="edit_btn el-icon-edit" @click="showDialog('expected')" type="text">编辑</el-button>
                  <!-- <i class="iconfont icon-bianji-copy" @click="showDialog('expected')"></i>编辑 -->
                </div>
               </div>
            <ul class="res_list">
                <li><i class="iconfont icon-H"></i>{{mineData.baseInfo.expectposition}}</li>
                <!-- <li><i class="iconfont icon-shijian"></i>全职</li> -->
                <li><i class="iconfont icon-dingwei"></i>{{mineData.baseInfo.city}}</li>
                <li><i class="iconfont icon-wallet"></i>{{mineData.baseInfo.lowsalary}} - {{mineData.baseInfo.highsalary}}</li>
                <li><i class="iconfont icon-icon_dingtalk_line"></i>{{mineData.baseInfo.industryDesc}}</li>
            </ul>
            </div>
            <div class="upload">
              <el-upload
                name="uploadFile"
                class="upload-demo"
                ref="upload"
                action="/common/upload.do"
                :data="uploadParam"
                :limit="1"
                accept=".doc,.docx,.pdf"
                :file-list="fileList"
                :before-upload="beforeAttachmentUpload"
                :on-success="handleAttachmentSuccess"
                :on-error="handleAttachmentError"
                :on-preview="handleAttachmentOnPreview"
                :before-remove="handleAttachmentBeforeRemove"
                :on-exceed="handleExceed">
                <!-- <el-button slot="trigger" size="small" type="primary" plain style="width:534px;">选取文件</el-button> -->
                <el-button class="el-icon-paperclip" slot="trigger" >我要上传附件简历</el-button>
                <div slot="tip" class="el-upload__tip">只能上传doc/docx/pdf文件</div>
              </el-upload>
                
            </div>
            
        </div>
    </div>
     <el-backtop></el-backtop>
    <!-- <el-backtop target=".container"><i class="el-icon-caret-top"></i></el-backtop> -->
    <addOrUpdateResumeDialog :showVisiable.sync="showResumeDialogVisiable" :addOrUpdateData.sync="dialogData" @handleResumeDialogClose="handleResumeDialogClose"></addOrUpdateResumeDialog>
</div>
</template>

<script>
// import {mineData} from '../../../static/js/resumenJSON' 数据
import { getResumeInfo, updateUserInfo, updateResumeBaseInfo, updateResumeEducationExpInfo, updateResumeWorkExpInfo, deleteResumeWorkExpInfo, updateResumeProjectExpInfo, deleteResumeProjecExpInfo, updateResumeCertificationListInfo, deleteAttachment, deleteFile } from "@/api/index";
import { degree, schoolType } from "@/const/index";
import resumeBasicDialog from "@/components/dialog/resumeBasicDialog";
import addOrUpdateResumeDialog from "@/components/dialog/addOrUpdateResumeDialog";
import { Message } from 'element-ui'
export default {
  name: 'resume',
  components: {
    resumeBasicDialog,
    addOrUpdateResumeDialog
  },
  mounted() {
    this.getResumeInfo()
    this.degree = degree
    this.schoolType = schoolType
  },
  data() {
    return {
      avatarFilelist: [],
      fileList: [],
      // 上传文件时附带的参数
      uploadParam: {
        type: 'attachment'
      },
      rules:{
        socialpage: [{required: true, message: '必填', trigger: 'blur'}]
      },
      socialForm: {
        socialpage: ''
      },
      toAddOrEditSocialPage: false,
      headermaskimg: '',
      dialogData: {},
      // 显示嵌套表单
      dialogFormVisible: false,
      // 经历的表单
      showResumeDialogVisiable: false,
      schoolType:{},
      degree: {},
      // ftp 服务器地址
      ftp_server_http_prefix: 'http://attachment.grtwwh.com:8080/',
      // 简历数据
      mineData: {
        // 基本信息
        baseInfo: {
          education: {
          }
        },
        workExpList: {},
        projectExpList: {},
        educationExpList:{},
        certificationList: {}
      }
    }
  },
  methods: {
    compare(prop, type) {
      return function (obj1, obj2) {
        var val1 = obj1[prop];
        var val2 = obj2[prop];
        if (!isNaN(Number(val1)) && !isNaN(Number(val2))) {
          val1 = Number(val1);
          val2 = Number(val2);
        }
        // 1:降序
        if (type == 1) {
          if (val1 > val2) {
            return -1;
          } else if (val1 < val2) {
            return 1;
          } else {
            return 0;
          }  
          // 2.升序
        } else if (type == 2) {
          if (val1 < val2) {
            return -1;
          } else if (val1 > val2) {
            return 1;
          } else {
            return 0;
          }  
        }
        
      } 
    },
    handleAvatarSuccess(res, file) {
      if (res && res.status == 0) {
        this.mineData.baseInfo.expectpositionid = this.mineData.baseInfo.expectpositionId 
        let industryid = []
        this.mineData.baseInfo.industries.forEach(element => {
          industryid.push(element.industryId)
        });
        this.mineData.baseInfo.industryid = industryid
        this.mineData.baseInfo.headermask = res.data.uri
        this.headermaskimg = URL.createObjectURL(file.raw);
        this.updateResumeBaseInfo(this.mineData.baseInfo)
      }
    },
    handleAvatarError(err, file, fileList) {
      Message.error({message:err})
    },
    beforeAvatarUpload(file) {
      let params = {
        fileName: this.mineData.baseInfo.headermask,
        remotePath: "img"
      }
      if (params.fileName != null && params.fileName != '' && params.fileName.length != 0) {
        deleteFile(params).then(resp => {
          if (resp && resp.status == 0) {
            console.log(resp)
          } else {
            Message.error({message: resp.msg ? resp.msg : "未知错误，请联系管理员"})
          }
        })
      }
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
      
    },
    // 文件超出个数限制时的钩子
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    },
    // on-preview	点击文件列表中已上传的文件时的钩子
    handleAttachmentOnPreview(file) {
      this.$confirm('确认下载文件吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          closeOnClickModal: false,
          closeOnPressEscape: false,
          type: 'warning'
      }).then(() => {
        window.location.href=file.url
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消下载'
        })
        return false
      })
      
    },
    // on-remove	文件列表移除文件时的钩子
    handleAttachmentBeforeRemove(file, fileList) {
      return this.$confirm('删除后不可恢复，确认删除吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          closeOnClickModal: false,
          closeOnPressEscape: false,
          type: 'warning'
      }).then(() => {
        let param = {type: "resume"}
        deleteAttachment(param).then(resp => {
          if (resp && resp.status == 0) {
            Message.success({message: "删除成功, 删除文件为！"+ file.name})
            this.getResumeInfo()
          }else {
            Message.error({message: resp.msg ? resp.msg : "未知错误，请联系管理员"})
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
        reject(false)
      })
      
    },
    // 上传文件之前
    beforeAttachmentUpload(file) {
      if (!file) {
        Message.error({message: '请上传文件'})
      }
      var testmsg=file.name.substring(file.name.lastIndexOf('.')+1)
      const extension = testmsg === 'doc'
      const extension2 = testmsg === 'docx'
      const extension3 = testmsg === 'pdf'
      // const isLt2M = file.size / 1024 / 1024 < 10
      if(!extension && !extension2) {
        this.$message({
            message: '上传文件只能是 doc、docx、pdf格式!',
            type: 'warning'
        });
      }
      // if(!isLt2M) {
      //     this.$message({
      //         message: '上传文件大小不能超过 10MB!',
      //         type: 'warning'
      //     });
      // }
      // return (extension || extension2) && isLt2M
      return (extension || extension2)
    },
    // 文件上传成功时的钩子
    handleAttachmentSuccess(response, file, fileList) {
      if (response && response.status === 0) {
        this.mineData.baseInfo.resumeattachment = response.data.uri
        this.mineData.baseInfo.expectpositionid = this.mineData.baseInfo.expectpositionId 
        let industryid = []
        this.mineData.baseInfo.industries.forEach(element => {
          industryid.push(element.industryId)
        });
        this.mineData.baseInfo.industryid = industryid
        // 上传成功再去保存
        this.updateResumeBaseInfo(this.mineData.baseInfo)
      } else {
        Message.error({message: response.msg})
      }
    },
    // 文件上传失败时钩子
    handleAttachmentError(err, file, fileList) {
      console.log(err)
      return false
    },
    del(type, param) {
      this.$confirm('确定删除本条信息？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          closeOnClickModal: false,
          closeOnPressEscape: false,
          type: 'warning'
      }).then(() => {
        switch(type){
          case "work":
            deleteResumeWorkExpInfo(param).then(resp => {
              if (resp && resp.status == 0) {
                Message.success({message: "删除成功！"})
                this.getResumeInfo()
              }else {
                Message.error({message: resp.msg ? resp.msg : "未知错误，请联系管理员"})
              }
            })
            break
          case "project":deleteResumeProjecExpInfo
            deleteResumeProjecExpInfo(param).then(resp => {
              if (resp && resp.status == 0) {
                Message.success({message: "删除成功！"})
                this.getResumeInfo()
              }else {
                Message.error({message: resp.msg ? resp.msg : "未知错误，请联系管理员"})
              }
            })
            break
        }
      }).catch(() => {
          this.$message({
              type: 'info',
              message: '已取消删除'
          });
      });
      
    },
    handleResumeDialogClose(params, type) {
      // debugger
      switch(type) {
        case "addEdu":
          if (!params.id) {
            params.id = ''
          }
          updateResumeEducationExpInfo(params, params.id).then(resp => {
            if (resp.status == 0) {
              Message.success({message: "保存成功！"})
              this.getResumeInfo()
            }else {
              Message.error({message: resp.msg ? resp.msg : "未知错误，请联系管理员"})
            }
          })
          break
        case "addWork":
          if (!params.id) {
            params.id = ''
          }
          updateResumeWorkExpInfo(params, params.id).then(resp => {
            if (resp.status == 0) {
              Message.success({message: "保存成功！"})
              this.getResumeInfo()
            }else {
              Message.error({message: resp.msg ? resp.msg : "未知错误，请联系管理员"})
            }
          })
          break
        case "addProject":
          if (!params.id) {
            params.id = ''
          }
          updateResumeProjectExpInfo(params, params.id).then(resp => {
            if (resp.status == 0) {
              Message.success({message: "保存成功！"})
              this.getResumeInfo()
            }else {
              Message.error({message: resp.msg ? resp.msg : "未知错误，请联系管理员"})
            }
          })
          break
        case "addCertification":
          updateResumeCertificationListInfo(params.certificationList).then(resp => {
            if (resp.status == 0) {
              Message.success({message: "保存成功！"})
              this.getResumeInfo()
            }else {
              Message.error({message: resp.msg ? resp.msg : "未知错误，请联系管理员"})
            }
          })
          break
      }
      this.showResumeDialogVisiable = false
    },
    delSocialpage() {
      this.$confirm('删除后不可恢复，确认删除吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          closeOnClickModal: false,
          closeOnPressEscape: false,
          type: 'warning'
      }).then(() => {
        this.mineData.baseInfo.socialpage = ''
        this.updateResumeBaseInfo()
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      })
    },
    updateSocialpage(resumebaseInfo, type) {
      let param = {
        headermask: resumebaseInfo.headermask,
        realname: resumebaseInfo.realname,
        gender: resumebaseInfo.gender,
        birthday: resumebaseInfo.birthday,
        currentstatus: resumebaseInfo.currentstatus,
        advantage: resumebaseInfo.advantage,
        otherattachment: resumebaseInfo.otherattachment,
        resumeattachment: resumebaseInfo.resumeattachment,
        expectpositionid: resumebaseInfo.expectpositionId,
        lowsalary: resumebaseInfo.lowsalary,
        highsalary: resumebaseInfo.highsalary,
      }
      let industryid = []
      resumebaseInfo.industries.forEach(element => {
        industryid.push(element.industryId)
      });
      param.industryid = industryid

      if (type == "del") {
        this.$confirm('删除后不可恢复，确认删除吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          closeOnClickModal: false,
          closeOnPressEscape: false,
          type: 'warning'
        }).then(() => {
          param.socialpage = ''
          this.updateResumeBaseInfo(param)
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        })
      } else if (type == "update") {
        this.$refs.socialForm.validate((valid => {
          if (valid) {
            param.socialpage = this.socialForm.socialpage
            this.updateResumeBaseInfo(param)
            this.toAddOrEditSocialPage = false
          }
        }))
      }
    },
    updateResumeBaseInfo(resumebaseInfo) {
      updateResumeBaseInfo(resumebaseInfo).then(resp => {
        if (resp && resp.status == 0) {
          // success
          Message.success({message: "保存成功！"})
          this.getResumeInfo()
        } else {
          Message.error({message: resp.msg ? resp.msg : "未知错误，请联系管理员"})
        }
      })
    },
    // 处理弹框关闭
    async handleShowBasicDialog(resumebaseInfo, userInfo, type) {
      if (type == "save") {
        let result = await updateUserInfo(userInfo)
        if (result.status == 0) {
          this.updateResumeBaseInfo(resumebaseInfo)
        } else {
          Message.error({message: result.msg ? result.msg : "未知错误，请联系管理员"})
        }
      }
      
      this.dialogFormVisible = false
    },
    showDialog(type, param) {
      let data = {}
      let dialogType ={}
      let params = {params: {industryId: 0}}
      switch(type) {
        case "basic":
          dialogType= {dialogType : type, title: "基本信息"}
          this.dialogData = Object.assign(data, this.mineData.baseInfo, dialogType)
          this.dialogFormVisible = true
          break
        case "expected":
          this.mineData.baseInfo.expectpositionid = this.mineData.baseInfo.expectpositionId
          dialogType= {dialogType : type, title: "编辑求职意向"}
          this.dialogData = Object.assign(data, this.mineData.baseInfo, dialogType)
          this.dialogFormVisible = true
          break
        case "advantage":
          dialogType = {dialogType : type, title:"个人优势"}
          this.dialogData = Object.assign(data, this.mineData.baseInfo, dialogType)
          this.dialogFormVisible = true
          break
        case "addEdu":
          dialogType= {dialogType : type, title: "添加教育经历"}
          this.dialogData = Object.assign(data, params, dialogType)
          this.showResumeDialogVisiable = true
          // this.dialogData = Object.assign(data, {params:this.mineData.educationExpList[0]}, dialogType)
          break
        case "editEdu":
          dialogType= {dialogType : type, title: "编辑教育经历"}
          this.dialogData = Object.assign(data, {params:this.mineData.educationExpList[param]}, dialogType)
          this.showResumeDialogVisiable = true
          break
        case "addWork":
          dialogType= {dialogType : type, title: "添加工作经历"}
          this.dialogData = Object.assign(data, params, dialogType)
          this.showResumeDialogVisiable = true
          break
        case "editWork":
          dialogType= {dialogType : type, title: "编辑工作经历"}
          this.dialogData = Object.assign(data, {params:this.mineData.workExpList[param]}, dialogType)
          this.showResumeDialogVisiable = true
          break
        case "addProject":
          dialogType= {dialogType : type, title: "添加项目经历"}
          this.dialogData = Object.assign(data, params, dialogType)
          this.showResumeDialogVisiable = true
          break
        case "editProject":
          dialogType= {dialogType : type, title: "编辑工作经历"}
          this.dialogData = Object.assign(data, {params:this.mineData.projectExpList[param]}, dialogType)
          this.showResumeDialogVisiable = true
          break
        case "addCertification":
          dialogType= {dialogType : type, title: "资格证书"}
          let arr = []
          this.mineData.certificationList.forEach(element => {
            arr.push(element.id)
          });
          params = {
            params: {
                certificationList: arr
              }
          }
          this.dialogData = Object.assign(data, params, dialogType)
          this.showResumeDialogVisiable = true
          break
      }
    },
    getResumeInfo() {
      getResumeInfo().then(resp => {
        if (resp && resp.status == 0) {
          this.mineData.baseInfo = resp.data.resumeBaseInfoVo
          this.mineData.workExpList = resp.data.workExpListVos
          this.mineData.projectExpList = resp.data.projectExpListVos
          this.mineData.educationExpList = resp.data.educationExpListVos
          this.mineData.certificationList = resp.data.certificationListVos
          
          this.socialForm.socialpage = resp.data.resumeBaseInfoVo.socialpage
          // let url = baseInfo.data.headerMask.split("/")
          // this.userInfo.imageUri = url[(url.length) - 1]
          this.headermaskimg = (this.mineData.baseInfo.headermask == null || this.mineData.baseInfo.headermask == '') ? this.ftp_server_http_prefix + 'img/yazi.jpeg' : this.ftp_server_http_prefix + 'img/' + this.mineData.baseInfo.headermask
          if (this.mineData.educationExpList.length > 1) {
            // let educationExpList = []
            let tempDegree = 0
            this.mineData.educationExpList.forEach(element => {
              if (element.degree > tempDegree) {
                tempDegree = element.degree
              }
            });
            let i = 0
            this.mineData.educationExpList.forEach(element => {
              if (element.degree == tempDegree) {
                this.mineData.baseInfo.education = element
              }
            })
            this.fileList = []
            if (this.mineData.baseInfo.resumeattachment && this.mineData.baseInfo.resumeattachment != null) {
              let file = {name: this.mineData.baseInfo.resumeattachment, url: this.ftp_server_http_prefix + this.uploadParam.type + "/" + this.mineData.baseInfo.resumeattachment}
              this.fileList.push(file)
            }
            this.mineData.educationExpList.sort(this.compare("degree", 1))
            this.mineData.workExpList.sort(this.compare("id", 1))
            this.mineData.projectExpList.sort(this.compare("id", 1))
            this.mineData.certificationList.sort(this.compare("id", 2))
          } else {
            this.mineData.baseInfo.education = this.mineData.educationExpList[0]
          }
        } else {
          Message.error({message: resp.msg})
        }
      })
    }
  },
}
</script>
<style scoped>
.iconfont{
    vertical-align: middle;
}
.container{
    width:1024px;
    margin:35px auto;
}
.null{
    width:100%;
    min-height:100px;
}
.resume_l{
    width:720px;
    margin-bottom:80px;
}
.my_resume{
    border: 1px solid #E3EBE9;
    border-radius: 5px;
}
.add{
    font-size:14px;
    color:#00b38a;
    cursor: pointer;
}
.icon-tianjia{
    margin-right:5px;
}
.basic{
    position:relative;
    padding:50px;
    background:#fafafa;
    border-radius: 5px 5px 0 0;
    border-bottom: 1px solid #E3EBE9;
}
.edit{
    position:absolute;
    top:40px;
    right:50px;
    color:#00b38a;
}
.my_img,h3,h4,.add,.school_img,.school_info{
    display: inline-block;
}
.icon-nan{
    margin-left:10px;
    color:#00b38a;
}
.my_img img{
    width:80px;
    height:80px;
    border-radius: 50%;
}
.tel span{
    margin-right:20px;
}
.my_info{
    display: inline-block;
    margin-left:20px;
}
.my_info p{
    margin:10px 0;
    font-size:14px;
    color:#333;
}
.my_tml{
    padding:50px;
}
.sc_in{
    width:550px;
    margin-top: 10px;
    margin-bottom:12px;
}
.school_info p{
    font-size:14px;
    color:#666;
}
.my_tml li,.my_title{
    margin-bottom:30px;
}
.my_title{
    border-left: 3px solid #00b38a;
    padding-left: 5px;
}
.my_intro{
    margin:20px 0;
    font-size:14px;
    color:#666;
    line-height:30px;
}
.my_tags span{
    display: inline-block;
    border:1px solid #efefef;
    border-radius: 30px;
    color:#999;
    margin-right:10px;
    font-size:12px;
    padding:3px 10px;
}
.school_img img{
    width:46px;
    height:46px;
    border-radius: 50%;
    margin-right:10px;
}
.school_info h3{
    font-size: 14px;
    color:#333;
}
.upload{
    background:#fafafa;
    text-align: center;  
}
.upload .iconfont{
    margin-right:5px;
}
.upload a{
    font-size:14px;
    color:#333;
}
.res_top,.upload{
    width:240px;
    border:1px solid #f2f2f2;
    border-radius: 2px;
    padding:20px;
    margin-bottom:20px;
}
.resume_r_head h3{
   font-size:16px;
   font-weight:600;
   color:#333;
}
.res_list li{
    color:#666;
    margin-top:15px;
}
.res_list .iconfont{
    margin-right:10px;
}
.editRes{
    float:right;
    color:#00b38a;
}
.edit_btn {
    color: #00B38A;
    font-size: 14px;
    display: flex;
    align-items: center;
    cursor: pointer;
    line-height: 1;
}
.work-exp .work-exp_list .exp-list_top .exp-list_right {
    display: flex;
    flex-direction: column;
    flex: 1;
    height: 46px;
    justify-content: space-between;
}
.work-exp .work-exp_list .exp-list_top .exp-list_right strong {
    font-weight: 600;
    display: flex;
}
.work-exp .work-exp_list .exp-list_top .exp-list_right p, .work-exp .work-exp_list .exp-list_top .exp-list_right strong {
    font-size: 14px;
    color: #333;
}
.tagboard .tagboard-tag {
    font-size: 12px;
    color: #999;
    line-height: 18px;
    padding: 3px 10px;
    border: 1px solid #EAEDEC;
    border-radius: 100px;
    margin-right: 10px;
}
.work-exp .work-exp_list .tagboard {
    margin: 20px 0 10px;
}
.work-exp .work-exp_list {
    position: relative;
    margin-bottom: 30px;
}
.work-exp .work-exp_list .exp-list_time {
    font-size: 14px;
    color: #666;
    position: absolute;
    right: 0;
}
.resume-box .item-primary li {
    padding: 8px 0 8px 15px;
    position: relative;
    min-height: 40px;
}
.resume-box .item-primary li .primary-info .info-text {
    padding: 7px 0;
    color: #61687c;
    white-space: pre-wrap;
    overflow: hidden;
    word-break: break-all;
}
h4.name {
    max-width: 420px;
    word-break: break-all;
    float: left;
    color: #414a60;
}
.prev-line {
    display: inline-block;
    vertical-align: middle;
    max-width: 140px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
.info-text {
    padding: 7px 0;
    color: #61687c;
    white-space: pre-wrap;
    overflow: hidden;
    word-break: break-all;
}
.work-operator {
  /* display: none; */
  bottom: unset;
  top: 8px;
  right: 3px;
  position: absolute;
}
.social-img {
    width: 25px;
    height: 25px;
    border-radius: 100%;
    margin-right: 15px;
    vertical-align: middle;
}
</style>