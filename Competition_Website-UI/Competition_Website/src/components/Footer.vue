<template>
  <el-footer class="me-area">
    <div class="me-footer">
      <p>
        Designed by
        <strong>
          <router-link to="/" class="me-login-design-color"
            >RockJim</router-link
          >
        </strong>
      </p>
    </div>
  </el-footer>
</template>

<script>
export default {
  name: "Footer",
};
</script>

<style scoped>
.el-footer {
  min-width: 100%;
  box-shadow: 0 -2px 3px hsla(0, 0%, 7%, 0.1), 0 0 0 1px hsla(0, 0%, 7%, 0.1);
  position: absolute;
  bottom: 0;
  left: 0;
  z-index: 1024;
  height: 35px;
}
.me-footer {
  text-align: center;
  line-height: 60px;
  font-family: "Open Sans", sans-serif;
  font-size: 18px;
}
.me-login-design-color {
  color: #9fd115 !important;
}
</style>