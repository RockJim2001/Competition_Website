<template>
  <div class="mainPage">
    <!-- 跑马灯 -->
    <div class="block carousel">
      <el-carousel indicator-position="outside" type="card" height="300px">
        <el-carousel-item
          v-for="carousel__item in carouselData"
          :key="carousel__item"
        >
          <el-image :src="carousel__item.url" alt="" fit="fill"></el-image>
        </el-carousel-item>
      </el-carousel>
    </div>
    <!-- 其他部分 -->
    <div class="other">
      <!-- 头条和通知 -->
      <el-row class="newsAndNotification">
        <el-col class="news">
          <el-container>
            <el-header class="customize-header">
              <el-row>
                <el-col :span="20">
                  <el-h2 class="newTitle">头条资讯</el-h2>
                  <el-icon><BottomRight /></el-icon>
                </el-col>
                <el-col :span="4">
                  <router-link to="/new">查看更多</router-link>
                </el-col>
              </el-row>
            </el-header>
            <el-main class="newAndCompetition-main">
              <el-carousel
                indicator-position="outside"
                height="160px"
                initial-index="1"
                interval="1500"
              >
                <el-carousel-item
                  v-for="item in newList.slice(0, 3)"
                  :key="item"
                >
                  <router-link
                    :to="{
                      path: '/detail',
                      query: { id: item.id },
                    }"
                  >
                    <el-image :src="item.poster" alt="" fit="fill"></el-image>
                  </router-link>
                </el-carousel-item>
              </el-carousel>
            </el-main>
          </el-container>
        </el-col>
        <el-col class="notification">
          <el-container>
            <el-header class="customize-header">
              <el-row align="center">
                <el-col class="notification-header" :span="22">
                  <el-menu
                    :router="true"
                    :default-active="1"
                    mode="horizontal"
                    class="notification-menu"
                  >
                    <el-menu-item
                      index="1"
                      class="notification-header-menu-item"
                      >竞赛资讯</el-menu-item
                    >
                    <el-menu-item
                      index="2"
                      class="notification-header-menu-item"
                      >赛事通知</el-menu-item
                    >
                    <el-menu-item
                      index="3"
                      class="notification-header-menu-item"
                      >结果公示</el-menu-item
                    >
                  </el-menu>
                </el-col>
                <el-col :span="2"
                  ><router-link to="/competition">查看更多</router-link></el-col
                >
              </el-row>
            </el-header>
            <el-main class="newAndCompetition-main">
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
      </el-row>

      <!-- 竞赛预览 -->
      <el-container class="preview">
        <el-header class="customize-header">
          <el-row>
            <el-col :span="22"
              ><el-h2 class="newTitle">竞赛预览</el-h2>
              <el-icon><BottomRight /></el-icon
            ></el-col>
            <el-col :span="2" class="preview-header-more"
              ><router-link to="/competition">查看更多</router-link></el-col
            >
          </el-row>
        </el-header>
        <el-main class="preview-main">
          <ul
            v-for="competition in competitions.slice(0, 3)"
            :key="competition.id"
            class="competition-module"
          >
            <li>
              <el-container>
                <el-aside>
                  <router-link
                    :to="{ path: '/detail', query: { id: competition.id } }"
                  >
                    <el-image :src="competition.poster" alt=""></el-image>
                  </router-link>
                </el-aside>
                <el-container>
                  <el-header class="competition-module-header">
                    <router-link
                      :to="{ path: '/detail', query: { id: competition.id } }"
                    >
                      <h4>{{ competition.title }}</h4>
                    </router-link>
                  </el-header>
                  <el-main class="competition-module-description"
                    ><p>{{ competition.summary }}</p></el-main
                  >
                  <el-footer class="competition-module-footer">
                    <!-- 状态 -->
                    <el-col :span="2">
                      <p
                        v-if="competition.status === 0"
                        class="competition-state p-pre"
                      >
                        报名中
                      </p>
                      <p
                        v-else-if="competition.status === 1"
                        class="competition-state p-start"
                      >
                        正在进行
                      </p>
                      <p
                        v-else-if="competition.status === 2"
                        class="competition-state p-end"
                      >
                        已经结束
                      </p>
                    </el-col>
                    <!-- 级别 -->
                    <el-col :span="2" :offset="1">
                      <p
                        v-if="competition.scale === 2"
                        class="competition-scale p-country"
                      >
                        国赛
                      </p>
                      <p
                        v-else-if="competition.scale === 1"
                        class="competition-scale p-province"
                      >
                        省赛
                      </p>
                      <p
                        v-else-if="competition.scale === 0"
                        class="competition-scale p-college"
                      >
                        校赛
                      </p>
                    </el-col>
                  </el-footer>
                </el-container>
              </el-container>
            </li>
          </ul>
        </el-main>
      </el-container>

      <!-- 友情链接 -->
      <el-container class="friendLink">
        <el-header class="customize-header">
          <el-row>
            <el-col :span="24"
              ><el-h2 class="newTitle">友情链接</el-h2>
              <el-icon><BottomRight /></el-icon
            ></el-col>
          </el-row>
        </el-header>
        <el-main class="friendLink-main">
          <ul v-for="friendLink in friendLinkData" :key="friendLink.id">
            <li>
              <a :href="friendLink.url">{{ friendLink.name }}</a>
            </li>
          </ul>
        </el-main>
      </el-container>
    </div>
  </div>
</template>

<script>
import { BottomRight } from "@element-plus/icons-vue";
import moment from "moment";
import axios from "axios";
export default {
  name: "Main",
  components: {
    BottomRight,
  },
  data() {
    return {
      carouselData: [
        { url: require("../assets/image/carousel/carousel1.jpg") },
        { url: require("../assets/image/carousel/carousel2.jpg") },
        { url: require("../assets/image/carousel/carousel3.jpg") },
        { url: require("../assets/image/carousel/carousel4.jpg") },
        { url: require("../assets/image/carousel/carousel5.png") },
      ],
      notifications: [],
      competitions: [],
      newList: [],
      friendLinkData: [
        {
          id: "001",
          url: "http://www.moe.gov.cn/",
          name: "中华人民共和国教育部",
        },
        {
          id: "002",
          url: "https://www.cahe.edu.cn/",
          name: "中国高等教育协会",
        },
        { id: "003", url: "https://www.whut.edu.cn/", name: "武汉理工大学" },
        {
          id: "004",
          url: "https://cst.whut.edu.cn/",
          name: "武汉理工大学计算机与人工智能学院",
        },
      ],
    };
  },
  created() {
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
        this.newList = res.data.data;
      })
      .catch((res) => {
        //请求失败
        ElMessage({
          type: "error",
          message: "网络异常!!!",
        });
      });

    //获取部分预览竞赛
    axios({
      url: "competition",
    })
      .then((res) => {
        //请求成功
        if (res.data.success) {
          //成功，回调函数
          this.competitions = res.data.data;
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
  methods: {
    publishDate(date) {
      return moment(new Date(date)).format("YYYY-MM-DD");
    },
  },
};
</script>

<style scoped>
/***********友情链接 */
.friendLink {
  margin: 20px 0px;
  background-color: #fff;
}
.friendLink-main {
  display: flex;
  max-height: 60px;
  overflow: hidden;
}
.friendLink-main li {
  width: 250px;
  padding: 0px 10px;
  text-align: left;
}
/*********全局css */
.mainPage {
  overflow: scroll-y;
  background-color: rgba(247, 237, 237, 0.5);
}
.el-col-24 {
  max-height: 250px;
}

/*********跑马灯css */
.el-carousel__item {
  /* color: #475669; */
  font-size: 14px;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
  text-align: center;
}

/*********mainPage 中除了跑马灯之外的其他设置 */
.other {
  background-color: rgb(230, 234, 237);
  padding: 0px 200px;
  height: 1100px;
}

/*********头条资讯和竞赛管理css */
.newsAndNotification {
  padding-top: 20px;
}
.newsTitle {
  display: flex;
}
.el-row {
  align-items: center;
}
.newAndCompetition-main {
  --el-main-padding: 5px;
  height: 200px;
  overflow: hidden;
}

.news {
  max-width: 35%;
  background-color: rgb(255, 255, 255);
}
.notification {
  max-width: 64%;
  background-color: rgb(255, 255, 255);
  margin-left: 1%;
}
.notification-header .notification-menu .notification-header-menu-item {
  max-height: 35px;
}
.notification-menu {
  --el-menu-item-height: 30px;
}
</style>