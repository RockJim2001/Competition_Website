<template>
  <!-- <StarBackground /> -->
  <!-- <StarBackground /> -->
  <!-- 网站首页的头部（包含导航栏） -->
  <div class="home-header">
    <Header :activeIndex_1="'/'" />
  </div>
  <!-- 网站首页的展示区 -->
  <div class="home-content">
    <router-view></router-view>
  </div>
  <!-- 网站首页的底部功能栏 -->
  <div class="home-footer"><Footer /></div>
</template>

<script>
import StarBackground from "@/components/StarBackground";
import Footer from "@/components/Footer.vue";
import Header from "@/components/Header.vue";
export default {
  name: "Home", //主页
  components: { StarBackground, Header, Footer },
};
</script>

<style>
/*********竞赛预览css */
.preview {
  margin: 20px 0px;
  background-color: #fff;
}
.preview-header-more {
  text-align: right;
  padding-right: 10px;
}
.competition-module {
  margin: 10px 10px;
  border-style: ridge;
  box-shadow: 1px 2px 8px 0px rgb(88, 96, 99, 0.94);
  max-height: 200px;
  min-height: 170px;
}
.competition-module-header {
  max-height: 40px;
  --el-header-padding: 0 20px;
  text-align: center;
  padding-top: 25px;
}
.competition-module-description {
  max-height: 90px;
  overflow: hidden;
}
.competition-module-description p {
  text-indent: 2em;
  overflow: hidden;
  white-space: normal;
  text-overflow: ellipsis;
}
.competition-module-footer {
  padding: 0px 5px;
  padding-left: 20px;
  height: 10px;
}
/**竞赛状态 */
.competition-state {
  width: 70px;
  height: 24px;
  background-color: rgb(188, 210, 220);
  text-align: center;
  box-shadow: 1px 2px 8px 0px rgba(208, 237, 146, 0.9);
}
.p-pre {
  color: antiquewhite;
}
.p-start {
  color: red;
}
.p-end {
  color: gray;
}
/**竞赛规模 */
.competition-scale {
  width: 70px;
  height: 24px;
  background-color: rgb(188, 191, 199);
  letter-spacing: 1em;
  text-align: center;
  padding-left: 12px;
  box-shadow: 1px 2px 8px 0px rgba(245, 165, 234, 0.9);
}
.p-country {
  color: red;
}
.p-province {
  color: blue;
}
.p-college {
  color: yellow;
}
/**自定义el-header的用法 */
.customize-header {
  --el-header-height: 35px;
  /* --el-header-padding: 0 10px; */
  border-bottom-style: inset;
  height: var(--el-header-height);
  box-shadow: 1px 2px 8px 1px rgba(227, 227, 227, 0.94);
  /* background-color: rgb(12, 141, 240); */
}
.ul.el-menu.el-menu--horizontal.el-menu {
  padding-right: 0px;
}
.home-footer {
  position: relative;
  bottom: 0;
  width: 100%;
}
.home-content {
  margin-bottom: 35px;
  padding-bottom: 20px;
  background-color: rgb(230, 234, 237);
  min-height: 660px;
}
el-h2 {
  font-size: 22px;
  color: rgb(128, 202, 236);
}
</style>