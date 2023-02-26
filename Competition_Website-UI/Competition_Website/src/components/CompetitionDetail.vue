<template>
  <div v-if="loading" class="loading">Loading...</div>

  <div v-if="error" class="error">{{ error }}</div>
  <div class="competition-detail" v-if="competition">
    <div class="competition-detail-header">
      <el-row>
        <el-container>
          <el-aside class="competition-detail-header-image">
            <el-image :src="competition.poster" alt=""></el-image>
          </el-aside>
          <el-main class="competition-detail-header-main">
            <el-container>
              <el-header class="competition-detail-header-main-header">
                <h4>{{ competition.title }}</h4>
              </el-header>
              <el-main class="competition-detail-header-main-main">
                <p>{{ competition.summary }}</p>
              </el-main>
              <el-footer class="competition-detail-header-main-footer">
                <el-row>
                  <el-col :span="4" :offset="1">
                    <el-button
                      type="primary"
                      @click="applyCompetition"
                      :disabled="!(activeStep === 1)"
                      >立即报名</el-button
                    >
                    <el-dialog v-model="applyFormVisible" title="竞赛申请">
                      <el-form size="large">
                        <el-form-item label="竞赛名称：" label-width="auto">
                          <el-input
                            :v-model="competition.title"
                            :placeholder="competition.title"
                            disabled
                            autocomplete="off"
                          />
                        </el-form-item>
                        <el-row>
                          <el-col :span="10">
                            <el-form-item
                              label="申请人："
                              :label-width="formLabelWidth"
                            >
                              <el-input
                                v-model="user.nickname"
                                autocomplete="off"
                                disabled
                              />
                            </el-form-item>
                          </el-col>
                          <el-col :span="10" :offset="2">
                            <el-form-item
                              label="学历："
                              :label-width="formLabelWidth"
                            >
                              <el-input
                                v-model="user.education"
                                type="email"
                                autocomplete="off"
                                disabled
                              />
                            </el-form-item>
                          </el-col>
                        </el-row>
                        <el-row>
                          <el-col :span="10">
                            <el-form-item
                              label="就读学校："
                              :label-width="formLabelWidth"
                            >
                              <el-input
                                type="text"
                                v-model="user.school"
                                disabled
                              />
                            </el-form-item>
                          </el-col>
                        </el-row>
                        <el-form-item
                          label="附件："
                          :label-width="formLabelWidth"
                        >
                          <el-upload
                            class="upload-demo"
                            drag
                            action="#"
                            :on-change="handleChange"
                            :fileList="applyFileList"
                            :http-request="handleUpload"
                            :before-upload="beforeUpload"
                            accept=".txt,.docx,.md,.pdf"
                          >
                            <el-icon class="el-icon--upload"
                              ><UploadFilled
                            /></el-icon>
                            <div class="el-upload__text">
                              拖拽文件至此或者<em>点击上传</em>
                            </div>
                            <template #tip>
                              <div style="text-align: center">
                                支持上传.txt、.md、.pdf、.docx格式
                              </div>
                            </template>
                          </el-upload>
                        </el-form-item>
                      </el-form>
                      <template #footer>
                        <span class="dialog-footer">
                          <el-row>
                            <el-col :span="4" :offset="6">
                              <el-button @click="applyFormVisible = false"
                                >取消</el-button
                              >
                            </el-col>
                            <el-col :span="4">
                              <el-button type="primary" @click="submitApplyForm"
                                >申请</el-button
                              >
                            </el-col>
                          </el-row>
                        </span>
                      </template>
                    </el-dialog>
                  </el-col>
                  <el-col :span="4" :offset="1">
                    <el-button
                      type="primary"
                      @click="submitWorks"
                      :disabled="!(activeStep > 1 && activeStep < 4)"
                      >提交作品</el-button
                    >
                    <el-dialog v-model="worksFormVisible" title="作品提交">
                      <el-form
                        :model="worksForm"
                        size="large"
                        :rules="rules"
                        ref="worksForm"
                      >
                        <el-form-item label="竞赛名称：" label-width="auto">
                          <el-input
                            v-model="competition.title"
                            :disabled="true"
                            :placeholder="competition.title"
                            autocomplete="off"
                          />
                        </el-form-item>
                        <el-form-item
                          label="作品名称："
                          label-width="auto"
                          prop="title"
                        >
                          <el-input
                            v-model="worksForm.title"
                            placeholder="请输入作品名称"
                            type="text"
                          />
                        </el-form-item>

                        <el-form-item
                          label="作品介绍："
                          label-width="auto"
                          prop="introduction"
                        >
                          <el-input
                            v-model="worksForm.introduction"
                            placeholder="请输入作品介绍"
                            type="textarea"
                          />
                        </el-form-item>

                        <el-form-item
                          label="附件："
                          :label-width="formLabelWidth"
                          prop="worksFileList"
                        >
                          <el-upload
                            class="upload-demo"
                            drag
                            action="#"
                            :on-change="handleChange2"
                            :fileList="worksForm.worksFileList"
                            :http-request="handleUpload2"
                            :before-upload="beforeUpload2"
                            accept=".jpeg,.jpg,.gif,.png,.zip,.rar,.ppt,.pptx,.xls,.xlsx,.txt,.md,.pdf,.docx,.mp4"
                          >
                            <el-icon class="el-icon--upload"
                              ><UploadFilled
                            /></el-icon>
                            <div class="el-upload__text">
                              拖拽文件至此或者<em>点击上传</em>
                            </div>
                            <template #tip>
                              <div style="text-align: center">
                                (.jpeg、.jpg、.gif、.png、.zip、.rar、.ppt、.pptx、.xls、.xlsx、.txt、.md、.pdf、.docx、.mp4)
                              </div>
                            </template>
                          </el-upload>
                        </el-form-item>
                      </el-form>
                      <template #footer>
                        <span class="dialog-footer">
                          <el-row>
                            <el-col :span="4" :offset="6">
                              <el-button @click="worksFormVisible = false"
                                >取消</el-button
                              >
                            </el-col>
                            <el-col :span="4">
                              <el-button
                                type="primary"
                                @click="submitWorksForm('worksForm')"
                                >提交</el-button
                              >
                            </el-col>
                          </el-row>
                        </span>
                      </template>
                    </el-dialog>
                  </el-col>
                  <el-col :span="4" :offset="1">
                    <el-button @click="shareCompetition"
                      ><el-icon><Share /></el-icon>
                      &emsp;分&nbsp;享
                    </el-button>
                  </el-col>
                  <el-col :span="5" :offset="4">
                    <p>报名人数：{{ competition.registrationNumber }}</p>
                  </el-col>
                </el-row>
              </el-footer>
            </el-container>
          </el-main>
        </el-container>
      </el-row>
      <el-row class="competition-detail-header-timeLine">
        <el-col :span="20" :offset="2">
          <el-steps :active="activeStep" finish-status="finish" align-center>
            <el-step
              :title="item.name"
              :description="publishDate(item.datePoint)"
              v-for="(item, index) in competition.dateLine"
              :key="index"
            />
          </el-steps>
        </el-col>
      </el-row>
    </div>
    <!-- 竞赛详细内容展示区 -->
    <el-row class="competition-detail-content">
      <el-col :span="24">
        <el-tabs :v-model="0" class="competition-detail-content-tabs">
          <el-tab-pane
            :label="detail_tabs.title"
            :name="index"
            v-for="(detail_tabs, index) in competition.content"
            :key="detail_tabs"
          >
            <v-md-preview-html
              :html="html(detail_tabs.context)"
              preview-class="vuepress-markdown-body"
              class="preview-html"
            >
            </v-md-preview-html>
          </el-tab-pane>
        </el-tabs>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { ElMessage, ElNotification } from "element-plus";
import { UploadFilled } from "@element-plus/icons-vue";
import { Share } from "@element-plus/icons-vue";
import VueMarkdownEditor, { xss } from "@kangc/v-md-editor";
import moment from "moment";
import { useStore } from "vuex";
import axios from "axios";
import { applyCompetition, submitWorks } from "@/api/competition";
import { useRouter } from "vue-router";

export default {
  name: "CompetitionDetail",
  components: { Share, UploadFilled },
  setup() {
    const store = useStore();
    const router = useRouter();
    return { store };
  },
  created() {
    console.log("created");
  },
  mounted() {
    console.log("mounted");
    this.queryInfo();
  },
  unmounted() {},
  data() {
    return {
      loading: false,
      error: null,
      competition: {
        id: "",
        title: "",
        poster: "",
        summary: "",
        registrationNumber: 0,
        dateLine: [],
        content: [],
      },
      //报名申请附件
      applyFileList: [],
      //作品提交
      worksForm: {
        competition_id: "",
        user_id: "",
        title: "",
        introduction: "",
        worksFileList: [],
      },
      applyFormVisible: false,
      worksFormVisible: false,
      rules: {
        title: [{ required: true, message: "请输入作品名称", trigger: "blur" }],
        introduction: [
          { required: true, message: "请输入作品描述", trigger: "blur" },
        ],
        worksFileList: [
          { required: true, message: "请上传作品", trigger: "change" },
        ],
      },
    };
  },
  computed: {
    activeStep() {
      const nowDate = Date.parse(new Date());
      let activeStep = 0;
      this.competition.dateLine.forEach((date, index) => {
        if (nowDate - Date.parse(date.datePoint) > 0) {
          activeStep = index;
        }
      });
      console.log("activeStep", activeStep);
      return activeStep;
    },
    html: function () {
      return function (context) {
        return xss.process(
          VueMarkdownEditor.vMdParser.themeConfig.markdownParser.render(context)
        );
      };
    },
    user() {
      let user = this.store.getters["login/loginInfo"];
      return user;
    },
  },
  methods: {
    //请求
    queryInfo() {
      axios({
        url: "/detail",
        params: {
          id: this.$route.query.id,
        },
      })
        .then((res) => {
          //成功的回调
          if (res.data.success) {
            this.competition = res.data.data;
            ElMessage({
              type: "success",
              message: res.data.message,
            });
          } else {
            ElMessage({
              type: "error",
              message: res.data.message,
            });
          }
        })
        .catch((res) => {
          //请求失败
          ElMessage({
            type: "error",
            message: "网络异常!!!",
          });
        });
    },

    applyCompetition() {
      if (this.user.id === "") {
        ElMessage({
          type: "warning",
          message: "请先登录账号！",
        });
      } else {
        this.applyFileList = [];
        this.applyFormVisible = true;
      }
    },
    submitWorks() {
      //没登录不许提交
      if (this.user.id === "") {
        ElMessage({
          type: "warning",
          message: "请先登录账号！",
        });
      } else {
        //没报名不许提交
        axios({
          url: "/isRegister",
          method: "get",
          params: {
            user_id: this.user.id,
            competition_id: this.competition.id,
          },
        })
          .then((res) => {
            if (res.data) {
              this.worksFormVisible = true;
            } else {
              ElMessage({
                type: "warning",
                message: res.message,
              });
            }
          })
          .catch((err) => {});
      }
    },
    shareCompetition() {
      const url = window.location.href;
      if (window.clipboardData) {
        window.clipboardData.setData("text", url);
      } else {
        (function (url) {
          document.oncopy = function (e) {
            e.clipboardData.setData("text", url);
            e.preventDefault();
            document.oncopy = null;
          };
        })(url);
        document.execCommand("Copy");
      }
      ElMessage("已复制到剪切板！");
    },
    publishDate(date) {
      return moment(new Date(date)).format("YYYY-MM-DD");
    },
    //提交申请
    handleChange2(file, fileList) {
      if (fileList.length > 0) {
        this.worksForm.worksFileList = [fileList[fileList.length - 1]]; //这一步，是 展示最后一次选择文件
      }
      console.log("新增一个文件：" + file.name);
    },
    handleUpload2(params) {
      console.log("提交附件！" + params);
    },
    beforeUpload2(file) {
      console.log("上传之前:" + file.type);
    },

    //作品提交
    handleChange(file, fileList) {
      if (fileList.length > 0) {
        this.applyFileList = [fileList[fileList.length - 1]]; //这一步，是 展示最后一次选择文件
      }
      console.log("新增一个文件：" + file.name);
    },
    handleUpload(params) {
      console.log("提交附件！" + params);
    },
    beforeUpload(file) {
      console.log("上传之前:" + file.type);
    },
    //显示提示消息
    showNotication(type, message) {
      ElNotification({
        type: type,
        message: message,
        duration: 2000,
      });
    },
    //提交申请
    async submitApplyForm() {
      let applyForm = new FormData();
      applyForm.append("userId", this.user.id);
      applyForm.append("competitionId", this.competition.id);
      applyForm.append(
        "file",
        this.applyFileList[0].raw,
        this.applyFileList[0].name
      );
      try {
        let res = await applyCompetition(applyForm);
        if (res.success) {
          //报名成功
          ElMessage({
            type: "success",
            message: res.message,
            duration: 2000,
          });
          this.applyFormVisible = false;
        }
      } catch (error) {
        console.log("捕获的异常为：" + error);
      }
    },
    //提交作品
    submitWorksForm(worksForm) {
      this.$refs[worksForm].validate((valid) => {
        if (valid) {
          let formData = new FormData();
          formData.append(
            "file",
            this.worksForm.worksFileList[0].raw,
            this.worksForm.worksFileList[0].name
          );
          let works = {
            userId: this.store.getters["login/loginInfo"].id,
            competitionId: this.competition.id,
            title: this.worksForm.title,
            introduction: this.worksForm.introduction,
            worksUrl: "",
            score: "",
            submitDate: new Date(),
          };
          //将works转为json
          let json = JSON.stringify(works);
          //将json转为Blob
          let blob = new Blob([json], {
            type: "application/json",
          });
          formData.append("works", blob);
          //发送数据
          submitWorks(formData).then((res) => {
            if (res.success) {
              this.worksForm.worksFileList = [];
              this.worksForm = {};
              this.worksFormVisible = false;
              ElNotification({
                type: "success",
                message: res.message,
                duration: 2000,
              });
            } else {
              ElNotification({
                type: "warning",
                message: res.message,
                duration: 2000,
              });
            }
          });
        } else {
          ElMessage({
            type: "info",
            message: "请按照表单填写完成信息",
          });
        }
      });
    },
  },
  watch: {
    $route: {
      immediate: true,
      handler() {
        if (this.$route.query.id) {
          this.queryInfo();
        }
      },
    },
  },
};
</script>

<style scoped>
.competition-detail {
  padding: 0px 200px;
  padding-top: 20px;
}
.competition-detail-header {
  background-color: white;
  border-style: ridge;
  box-shadow: 1px 2px 8px 0px rgb(100, 100, 100, 0.9);
  padding: 20px 20px;
}
.competition-detail-header-image {
  --el-aside-width: 500px;
  max-height: 300px;
}
.competition-detail-header-timeLine {
  margin-top: 20px;
}
.competition-detail-header-main-header {
  text-align: center;
  border-bottom-style: inset;
  --el-header-height: 40px;
}
.competition-detail-header-main-footer {
  margin-top: 10px;
  --el-footer-height: 30px;
}
.competition-detail-header-main-main {
  height: 183px;
  overflow: hidden;
  text-indent: 2em;
}
.competition-detail-content {
  margin-top: 30px;
  min-height: 300px;
  width: 100%;
  padding: 10px 20px;
  background-color: white;
}
</style>