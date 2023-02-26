<template>
  <StarBackground />
  <div class="limiter">
    <div class="container-login100">
      <div class="wrap-login100 p-l-55 p-r-55 p-t-65 p-b-54">
        <form class="login100-form validate-form">
          <span class="login100-form-title p-b-49">注册</span>

          <!-- 用户名 -->
          <div
            class="wrap-input100 validate-input m-b-23"
            data-validate="请输入用户名"
          >
            <span class="label-input100">用户名</span>
            <input
              class="input100"
              type="text"
              name="username"
              id="username"
              placeholder="请输入用户名"
              autocomplete="off"
            />
            <span class="focus-input100" data-symbol="&#xf206;"></span>
          </div>

          <!-- 电子邮箱 -->
          <div
            class="wrap-input100 validate-input"
            data-validate="请输入电子邮箱"
          >
            <span class="label-input100">电子邮箱</span>
            <input
              class="input100"
              type="email"
              name="email"
              id="email"
              placeholder="请输入电子邮箱"
            />
            <span class="focus-input100" data-symbol="&#xf15a;"></span>
          </div>

          <!-- 密码 -->
          <div class="wrap-input100 validate-input" data-validate="请输入密码">
            <span class="label-input100">密码</span>
            <input
              class="input100"
              type="password"
              name="password"
              id="credential"
              placeholder="请输入密码"
            />
            <span class="focus-input100" data-symbol="&#xf190;"></span>
          </div>

          <div class="container-login100-form-btn">
            <div class="wrap-login100-form-btn">
              <div class="login100-form-bgbtn"></div>
              <button class="login100-form-btn" type="button" @click="register">
                <span>注 册</span>
              </button>
            </div>
          </div>
          <!-- 注册 -->
          <div class="flex-col-c p-t-25">
            <!-- <a href="javascript:" class="txt2">已有账号,去登录</a> -->
            <router-link to="/login">已有账号,去登录</router-link>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script scoped>
import StarBackground from "../src/components/StarBackground.vue";
import { useStore } from "vuex";
import axios from "axios";
import { ElMessage } from "element-plus";
export default {
  name: "Register", //注册界面
  setup() {
    const store = useStore();
    return { store };
  },
  beforeMount() {
    document.querySelector("body").classList.add("body-bg");
  },
  beforeUnmount() {
    document.querySelector("body").classList.remove("body-bg");
  },
  components: { StarBackground, ElMessage },
  methods: {
    register() {
      let username = document.getElementById("username").value;
      let credential = document.getElementById("credential").value;
      let email = document.getElementById("email").value;
      if (username === "") {
        ElMessage({
          type: "warning",
          message: "用户名不能为空",
          duration: 1000,
        });
      } else if (credential === "") {
        ElMessage({
          type: "warning",
          message: "密码不能为空",
          duration: 1000,
        });
      } else if (email === "") {
        ElMessage({
          type: "warning",
          message: "邮箱不能为空",
          duration: 1000,
        });
      } else {
        axios({
          url: "register",
          method: "post",
          data: {
            username,
            credential,
            email,
          },
          headers: {
            "Content-Type": "application/json;charset=UTF-8",
          },
        })
          .then((res) => {
            //成功的回调函数
            if (res.data.success) {
              ElMessage({
                type: "success",
                message: res.data.message,
                duration: 1000,
              });
              //自动跳转到登录界面
              this.$router.replace("/login");
            } else if (!res.data.success) {
              ElMessage({
                type: "error",
                message: res.data.message,
              });
              document.getElementById("username").value = "";
              document.getElementById("password").value = "";
              document.getElementById("email").value = "";
            }
          })
          .catch((res) => {
            //失败的回调函数
            ElMessage({
              type: "error",
              message: "请求失败！",
            });
          });
      }
    },
  },
};
</script>

<style scoped>
.body-bg {
  background-attachment: fixed;
  background-image: url("../src/assets/image/background.jpg");
  background-size: 100%, 100%;
  overflow: hidden;
}
.p-b-49 {
  padding-bottom: 30px;
}
.p-t-65 {
  padding-top: 40px;
}
.container-login100-form-btn {
  padding-top: 35px;
}
</style>