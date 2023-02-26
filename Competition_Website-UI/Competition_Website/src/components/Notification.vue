<template>
  <div v-if="loading" class="loading">Loading...</div>

  <div v-else-if="error" class="error">{{ error }}</div>

  <div v-else-if="notification" class="content">
    <!-- 竞赛详情和头条资讯 -->
    <div class="notificationDetail">
      <div class="notificationTitle">
        <el-row>
          <el-col :span="20" :offset="3">
            <h3 class="notification-title">{{ notification.title }}</h3>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="2" :offset="3"
            ><span class="newTitle">{{
              notification.classification
            }}</span></el-col
          >
          <el-col :span="4" :offset="1">
            <span>{{ publishDate }}</span></el-col
          >
          <el-col :span="3" :offset="1"
            ><span>阅读量：{{ notification.readingNumber }}</span></el-col
          >
        </el-row>
      </div>
      <el-row class="notificationDetail-content">
        <!-- 左边的竞赛详情 -->
        <el-col class="notifications" :span="15" :offset="2">
          <!-- 竞赛详情展示区 -->
          <el-row class="notification-detail-show">
            <v-md-preview-html
              :html="html"
              preview-class="vuepress-markdown-body"
              class="preview-html"
            ></v-md-preview-html>
          </el-row>
          <!-- 附件下载区 -->
          <el-row class="notification-appendix">
            <el-col :span="24" :offset="0" class="notification-appendix-header">
              <span>附件下载：</span>
            </el-col>
          </el-row>
          <el-row class="notification-appendix-file">
            <el-col>
              <ul v-for="file in files" :key="file">
                <li>
                  <a :href="file.url" :download="file.name"
                    >{{ file.id }}:{{ file.name }}</a
                  >
                </li>
              </ul>
            </el-col>
          </el-row>
        </el-col>
        <!-- 右边的资讯 -->
        <el-col class="MessageNews" :span="5" :offset="1">
          <el-container>
            <el-header class="customize-header">
              <el-row>
                <el-col :span="24">
                  <el-h2 class="newTitle">头条资讯</el-h2>
                  <el-icon><BottomRight /></el-icon>
                </el-col>
              </el-row>
            </el-header>
            <el-main class="MessageNews-main">
              <ul
                v-for="competition in competitions.slice(0, 3)"
                :key="competition"
              >
                <li>
                  <router-link
                    :to="{ path: '/detail', query: { id: competition.id } }"
                  >
                    <el-image :src="competition.poster" alt=""></el-image>
                  </router-link>
                  <router-link
                    :to="{ path: '/detail', query: { id: competition.id } }"
                  >
                    <p>{{ competition.title }}</p>
                  </router-link>
                </li>
              </ul>
            </el-main>
          </el-container>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import VueMarkdownEditor, { xss } from "@kangc/v-md-editor";
import { ElMessage } from "element-plus";
import { BottomRight } from "@element-plus/icons-vue";
import moment from "moment";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import axios from "axios";

export default {
  name: "Notification",
  setup(props) {
    const store = useStore();
    const router = useRouter();
    return { store, router };
  },
  components: { ElMessage, BottomRight },
  data() {
    return {
      loading: true,
      error: null,
      notification: {
        id: "",
        publishId: "",
        title: "",
        publishDate: "",
        readingNumber: 0,
        classification: "",
        context: "",
      },
      files: [
        {
          id: "001",
          name: "测试文档.pdf",
          // url: require("../../public/files/test.pdf"),
          url: "/files/test.pdf",
        },
      ],
      competitions: [],
    };
  },
  beforeMount() {
    // debugger;
    console.log("beforeMount" + new Date());
  },
  mounted() {
    // debugger;
    console.log("mounted" + new Date());
  },
  beforeUpdate() {
    //// debugger;
    console.log("beforeUpdate" + new Date());
  },
  updated() {
    //// debugger;
    console.log("updated" + new Date());
    axios({
      url: "/notification",
      method: "patch",
      params: { id: this.notification.id },
    });
  },
  beforeCreate() {
    //// debugger;
    console.log("beforeCreate" + new Date());
  },

  created() {
    console.log("created" + new Date());
    axios({
      url: "/notification",
      params: { id: this.$route.query.id },
    })
      .then((res) => {
        //成功的回调
        if (res.data.success) {
          this.notification = res.data.data;
          this.loading = false;
          ElMessage({
            type: "success",
            message: res.data.message,
          });
        }
      })
      .catch((res) => {
        ElMessage({
          type: "error",
          message: res.data.message,
        });
      });

    //获取头条资讯
    axios({
      url: "/newsList",
    })
      .then((res) => {
        //成功的回调函数
        this.competitions = res.data.data;
      })
      .catch((res) => {
        //请求失败
        ElMessage({
          type: "error",
          message: "网络异常!!!",
        });
      });
  },
  beforeUnmount() {
    //// debugger;
    console.log("beforeUnmount" + new Date());
  },
  unmounted() {
    //// debugger;
    console.log("unmounted" + new Date());
  },

  methods: {
    fetchData() {
      this.loading = false;
    },
  },
  computed: {
    html() {
      return xss.process(
        VueMarkdownEditor.vMdParser.themeConfig.markdownParser.render(
          this.notification.context
        )
      );
    },
    publishDate() {
      return moment(new Date(this.notification.publishDate)).format(
        "YYYY-MM-DD hh:mm:ss"
      );
    },
  },
};
</script>

<style scoped>
.notificationTitle {
  max-height: 100px;
  width: 100%;
  /* text-align: center; */
  padding: 20px 200px;
  padding-top: 20px;
  background-color: rgb(231, 228, 228);
}
.notification-title {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 10px;
}
.newTitle {
  color: rgb(128, 202, 236);
}
.notificationTitle span {
  font-size: 15px;
  font-weight: 400;
}
.notificationDetail-content {
  margin-top: 15px;
}
.MessageNews-main li {
  text-align: center;
  padding-bottom: 5px;
}
.MessageNews {
  background-color: white;
  max-height: 620px;
  margin-top: 50px;
}
/* .notifications {
  background-color: rgb(245, 240, 240);
} */
.notification-appendix {
  margin: 20px 0px;
  font-size: 16px;
}
.notification-appendix-header {
  background-color: darkgrey;
  text-indent: 2em;
}
.preview-html {
  width: 1000px;
}
.preview-html .vuepress-markdown-body {
  min-height: 330px;
}
.notification-appendix-file {
  background-color: white;
  min-height: 0px;
  margin-top: 20px;
  padding-left: 10px;
}
.notification-appendix-file li {
  text-indent: 2em;
  background-color: white;
  font-size: 20px;
  color: red;
}
</style>