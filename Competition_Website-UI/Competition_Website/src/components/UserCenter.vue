<template>
  <div class="UserCenter">
    <div class="UserCenter-image">
      <el-image
        :src="require('../assets/image/user_center_bg.jpg')"
        alt=""
      ></el-image>
    </div>
    <div class="UserCenter-content">
      <el-row>
        <el-col :span="3" class="UserCenter-content-menu">
          <el-menu
            default-active="2"
            class="el-menu-vertical-demo"
            :router="true"
            @open="handleOpen"
            @close="handleClose"
          >
            <el-menu-item index="/userCenter/myCompetition">
              <el-icon><Trophy /></el-icon>
              <span>我的竞赛</span>
            </el-menu-item>
            <el-menu-item index="/userCenter/honor">
              <el-icon><Document /></el-icon>
              <span>荣誉资质</span>
            </el-menu-item>
            <el-menu-item index="/userCenter/setting">
              <el-icon><Setting /></el-icon>
              <span>个人设置</span>
            </el-menu-item>
          </el-menu>
        </el-col>
        <el-col :span="20" :offset="1" class="UserCenter-content-main"
          ><router-view></router-view>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import { Setting, Document, Trophy } from "@element-plus/icons-vue";
export default {
  name: "UserCenter",
  components: { Setting, Document, Trophy },
};
</script>

<style scoped>
.UserCenter {
  min-height: 645px;
  background-color: white;
}
.UserCenter-content {
  margin: 0px 100px;
  margin-top: 30px;
}
.UserCenter-content-menu span {
  font-size: 20px;
  text-align: center;
}
.UserCenter-content-main {
  background-color: white;
}
.UserCenter-content-menu {
  padding-top: 50px;
}
</style>