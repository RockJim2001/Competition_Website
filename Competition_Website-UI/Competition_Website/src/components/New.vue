<template>
  <div class="newsPage">
    <!-- 竞赛资讯和头条资讯 -->
    <el-row class="competitionsAndNews">
      <el-col class="competitions" :span="16">
        <el-container>
          <el-header class="customize-header">
            <el-row>
              <el-col :span="4">
                <el-h2 class="newTitle">资讯列表</el-h2>
                <el-icon><BottomRight /></el-icon>
              </el-col>
              <el-col :span="16" :offset="2">
                <el-radio-group v-model="radio1">
                  <el-radio-button label="全部资讯" />
                  <el-radio-button label="竞赛发布" />
                  <el-radio-button label="通知公告" />
                  <el-radio-button label="结果公示" />
                </el-radio-group>
              </el-col>
            </el-row>
          </el-header>
          <el-main class="competitions-main">
            <ul>
              <li
                v-for="(notification, index) in notifications"
                :key="notification.id"
              >
                <router-link
                  :to="{
                    path: '/notification',
                    query: { id: notification.id },
                  }"
                >
                  <el-row>
                    <el-col :span="1"> {{ index + 1 }}: </el-col>
                    <el-col :span="20">
                      {{ notification.title }}
                    </el-col>
                    <el-col :span="3">
                      <span style="font-size: 14px; font-weight: 200">{{
                        publishDate(notification.publishDate)
                      }}</span>
                    </el-col>
                  </el-row>
                </router-link>
              </li>
            </ul>
          </el-main>
        </el-container>
      </el-col>
      <!-- 右边的资讯 -->
      <el-col class="MessageNews" :span="7" :offset="1">
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
    <el-row class="competitions-list-pagination">
      <el-col :span="10" :offset="4">
        <el-pagination
          small
          background
          :page-size="4"
          layout="total,prev, pager, next"
          :total="20"
        />
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { BottomRight } from "@element-plus/icons-vue";
import axios from "axios";
import moment from "moment";
export default {
  name: "New",
  components: { BottomRight },
  data() {
    return {
      form: {
        sort: "全部",
        dateRange: "全部",
        state: "全部",
      },
      radio1: "全部资讯",
      competitions: [
        {
          id: "005",
          url: require("../assets/image/carousel/carousel5.png"),
          title: "第十四届浙江省大学生生命科学竞赛",
          state: 0,
          description:
            "浙江省大学生生命科学竞赛旨在培养大学生社会责任感、创新意识、团队精神和实践能力，扩大科学视野，提高综合能浙江省大学生生命科学竞赛旨在培养大学生社会责任感、创新意识、团队精神和实践能力，扩大科学视野，提高综合能",
        },
      ],
      notifications: [],
    };
  },
  created() {
    console.log("created");
    //获取资讯列表
    axios({
      url: "/notifications",
    })
      .then((res) => {
        //成功的回调函数
        this.notifications = res.data.data;
      })
      .catch((res) => {
        //请求失败
        ElMessage({
          type: "error",
          message: "网络异常!!!",
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
  methods: {
    publishDate(date) {
      return moment(new Date(date)).format("YYYY-MM-DD");
    },
  },
};
</script>

<style scoped>
.newsPage {
  min-height: 698px;
  margin: 20px 120px 20px 200px;
  padding-top: 20px;
}
.competitions {
  background-color: white;
}

.competitions-main {
  max-height: 520px;
  overflow: hidden;
}
.competitions-list-pagination {
  margin-top: 20px;
}
.MessageNews-main li {
  text-align: center;
  padding-bottom: 5px;
}
.MessageNews {
  background-color: white;
}
</style>