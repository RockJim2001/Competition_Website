<template>
  <router-view />
</template>

<script>
//引入组件
import Login from "../pages/Login";
import Register from "../pages/Register";
import Home from "../pages/Home";
import { useStore } from "vuex";

export default {
  name: "App",
  setup() {
    const store = useStore();
    return { store };
  },
  components: { Login, Register, Home },
};
</script>

<style scoped lang="css">
</style>
