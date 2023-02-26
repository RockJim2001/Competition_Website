<template>
  <el-header class="header">
    <el-row class="el-row" :gutter="10" type="flex">
      <el-col :span="5" class="logo">
        <router-link to="/main">
          <el-image
            :src="require('../assets/image/logo.png')"
            class="logo"
          ></el-image>
        </router-link>
      </el-col>

      <el-col v-if="true" :span="15" class="navigation">
        <el-menu
          :router="true"
          menu-trigger="click"
          class="el-menu menu navigation"
          active-text-color="#0099FF"
          :default-active="activeIndex"
          mode="horizontal"
        >
          <el-menu-item index="/main">首页</el-menu-item>
          <el-menu-item index="/new">资讯</el-menu-item>
          <el-menu-item index="/competition">竞赛</el-menu-item>
          <el-col :span="5" :offset="2" class="search">
            <!-- <el-input placeholder="竞赛搜索">
              <template #append>
                <el-button type="primary">
                  <el-icon style="vertical-align: middle">
                    <Search />
                  </el-icon>
                  <span style="vertical-align: middle"> Search </span>
                </el-button>
              </template>
            </el-input> -->
            <el-select
              v-model="searchKeyword"
              clearable
              filterable
              remote
              reserve-keyword
              placeholder="请输入关键字"
              :remote-method="search"
              :loading="searchLoading"
              @change="select"
            >
              <el-option
                v-for="item in competitions"
                :key="item.id"
                :label="showCompetiton(item)"
                :value="item.id"
              />
            </el-select>
          </el-col>
        </el-menu>
      </el-col>

      <template v-else>
        <slot></slot>
      </template>

      <el-col :span="4" :offset="0">
        <el-menu
          class="header-loginAndRegister"
          :router="true"
          mode="horizontal"
          active-text-color="#0099FF"
        >
          <template v-if="!user.isLogin">
            <el-space>
              <el-button type="text" @click="login" class="login-button"
                >登录</el-button
              >
              <!-- 分割符 -->
              <el-divider direction="vertical"> </el-divider>
              <!-- 联系方式 -->
              <el-popover
                placement="bottom-end"
                title="联系方式:"
                :width="200"
                trigger="hover"
                content="284027@whut.edu.cn"
              >
                <template #reference>
                  <el-icon> <Headset /></el-icon>
                </template>
              </el-popover>
              <el-button type="text" @click="register" class="register-button"
                >注册</el-button
              >
            </el-space>
          </template>

          <template v-else>
            <el-popover
              placement="bottom-end"
              title="用户信息:"
              :width="200"
              trigger="hover"
              :content="user.nickname + '-' + user.permission"
            >
              <template #reference>
                <el-avatar :src="user.avatar" class="header-avatar" />
              </template>
            </el-popover>
            <el-sub-menu class="header-user" hide-timeout="300">
              <template #title> </template>
              <el-menu-item index="/userCenter/myCompetition">
                <el-icon><Trophy /></el-icon>
                我的竞赛
                <el-badge class="item" :value="1" />
              </el-menu-item>
              <el-menu-item index="/userCenter/honor">
                <el-icon><Document /></el-icon>
                荣誉资质
                <el-badge class="item" :value="2" />
              </el-menu-item>
              <el-menu-item index="/userCenter/setting">
                <el-icon color="#409EFC" class="no-inherit"> <User /> </el-icon
                >个人中心
                <el-badge class="item" :value="3" />
              </el-menu-item>
              <el-menu-item index="/main">
                <el-icon><Back /></el-icon>
                <el-button type="text" @click="logOut">退出登录</el-button>
              </el-menu-item>
            </el-sub-menu>
          </template>
        </el-menu>
      </el-col>
    </el-row>
  </el-header>
</template>

<script>
import {
  Headset,
  Search,
  User,
  Trophy,
  Back,
  Document,
  CaretBottom,
} from "@element-plus/icons-vue";
import { useStore } from "vuex";
import { onBeforeMount } from "@vue/runtime-core";
import axios from "axios";
export default {
  setup() {
    const store = useStore();
    onBeforeMount(() => {
      //页面刷新之前将sessionStorage中的loginInfo读入store
      if (sessionStorage.getItem("loginInfo")) {
        store.dispatch(
          "login/update",
          JSON.parse(sessionStorage.getItem("loginInfo"))
        );
      }
    });
    return { store };
  },
  name: "Header",
  props: {
    activeIndex: { type: String },
  },
  data() {
    return {
      searchKeyword: "",
      searchLoading: false,
      competitions: [],
    };
  },
  components: { Headset, Search, User, Trophy, Back, Document, CaretBottom },
  methods: {
    login() {
      this.$router.push("/login");
    },
    register() {
      this.$router.push("/register");
    },
    logOut() {
      //清除store中所保存的用户信息
      this.store.dispatch("login/logOut");
      console.log("debugger");
      this.$router.push("/main");
    },
    showCompetiton(competition) {
      return competition.title;
    },
    select(value) {
      console.log(value);
      this.$router.push("/detail?id=" + value);
    },
    search(keyword) {
      console.log(keyword);
      this.competitions = this.competitions.splice(0, this.competitions.length);
      if (keyword) {
        this.searchLoading = true;
        setTimeout(() => {}, 1000); // 模拟加载效果
        axios({
          url: "competition",
        })
          .then((res) => {
            if (res.data.success) {
              this.competitions = res.data.data;
              //存在store中
              // sessionStorage.setItem(
              //   "competitionList",
              //   JSON.stringify(state.competitions)
              // );
            } else {
              ElMessage({
                type: "error",
                message: res.data.message,
              });
            }
            this.searchLoading = false;
          })
          .catch((res) => {
            //请求失败
            ElMessage({
              type: "error",
              message: "网络异常!!!",
            });
            this.searchLoading = false;
          });
      }
    },
  },
  computed: {
    user() {
      let isLogin = this.store.getters["login/id"];
      let nickname = this.store.getters["login/nickname"];
      let avatar = this.store.getters["login/avatar"];
      let permission = this.store.getters["login/permission"];
      return { isLogin, avatar, nickname, permission };
    },
  },
};
</script>
<style scoped>
* {
  align-items: center;
}
.header {
  /* padding-left: 3%; */
  justify-content: space-around;
  display: flex;
  width: 100%;
  background-color: white;
}
.navigation {
  padding-left: 49px;
  justify-content: space-around;
}
.header-loginAndRegister {
  height: 60px;
  justify-content: right;
}
.register-button {
  font-size: 16px;
  letter-spacing: 5px;
}
.login-button {
  font-size: 16px;
  letter-spacing: 5px;
}
.el-menu--horizontal > .el-menu-item {
  width: 120px;
  font-size: 28px;
  letter-spacing: 18px;
  font-family: "宋体";
  font-weight: bolder;
}
.header-avatar {
  margin-left: 100px;
}
.item {
  height: 100px;
  width: 20px;
  --el-badge-padding: 0px;
  --el-badge-size: 13px;
  margin-left: 3px;
}
</style>