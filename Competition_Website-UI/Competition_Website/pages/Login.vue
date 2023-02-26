<template>
  <StarBackground />
  <div class="limiter">
    <div class="container-login100">
      <div class="wrap-login100 p-l-55 p-r-55 p-t-65 p-b-54">
        <form class="login100-form validate-form" id="LoginForm">
          <span class="login100-form-title p-b-49">登录</span>
          <div
            class="wrap-input100 validate-input m-b-23"
            data-validate="请输入用户名/手机号/邮箱"
          >
            <span class="label-input100">用户名</span>
            <input
              class="input100"
              type="text"
              id="identifier"
              name="username"
              placeholder="请输入用户名/手机号/邮箱"
              autocomplete="off"
              required
            />
            <span class="focus-input100" data-symbol="&#xf206;"></span>
          </div>

          <div class="wrap-input100 validate-input" data-validate="请输入密码">
            <span class="label-input100">密码</span>
            <input
              class="input100"
              type="password"
              id="credential"
              name="password"
              placeholder="请输入密码"
            />
            <span class="focus-input100" data-symbol="&#xf190;"></span>
          </div>

          <div class="text-right p-t-8 p-b-31">
            <router-link to="/ForgetPassword">忘记密码？</router-link>
          </div>

          <div class="container-login100-form-btn">
            <div class="wrap-login100-form-btn">
              <div class="login100-form-bgbtn"></div>
              <button type="button" class="login100-form-btn" @click="login">
                登 录
              </button>
            </div>
          </div>

          <div class="txt1 text-center p-t-54 p-b-20 line">
            <span>第三方登录</span>
          </div>

          <div class="flex-c-m">
            <a href="#" class="login100-social-item bg1">
              <i class="fa fa-wechat"></i>
            </a>

            <a href="#" class="login100-social-item bg2">
              <i class="fa fa-qq"></i>
            </a>

            <a href="#" class="login100-social-item bg3">
              <i class="fa fa-weibo"></i>
            </a>
          </div>

          <div class="flex-col-c p-t-25">
            <router-link to="/Register">立即注册</router-link>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
/**引入组件 */
import StarBackground from "../src/components/StarBackground.vue";
import { ElMessage } from "element-plus";
/**引入与页面相关的js，css样式 */
import "../src/assets/css/bootstrap.min.css";
import "../src/assets/css/font-awesome.min.css";
import "../src/assets/css/main.css";
import "../src/assets/css/material-design-iconic-font.min.css";
import "../src/assets/css/util.css";

import { useStore } from "vuex";

export default {
  setup() {
    const store = useStore();
    return { store };
  },
  name: "Login",
  beforeMount: function () {
    document.querySelector("body").classList.add("body-bg");
  },
  beforeUnmount() {
    document.querySelector("body").classList.remove("body-bg");
  },
  components: { StarBackground },
  methods: {
    login() {
      var identifier = document.getElementById("identifier").value;
      var credential = document.getElementById("credential").value;
      if (identifier === "") {
        ElMessage({
          type: "warning",
          message: "请输入用户名/邮箱/手机号/使用第三方登录",
        });
      } else if (credential === "") {
        ElMessage({
          type: "warning",
          message: "请输入用户名/邮箱/手机号/使用第三方登录",
        });
      } else {
        this.Axios({
          method: "post",
          url: "login",
          data: {
            identifier,
            credential,
            identityType: "USERNAME",
          },
          headers: {
            "Content-Type": "application/json;charset=UTF-8",
          },
        })
          .then((res) => {
            //请求成功的回调函数
            if (res.data.success) {
              //请求的数据正确
              ElMessage({
                type: "success",
                message: res.data.message,
                duration: 1000,
              });
              //学生用户登录，跳转到网站首页
              if (res.data.data.loginInfo.permission === "STUDENT") {
                //将返回的用户信息放在store中
                this.store.dispatch("login/update", res.data.data.loginInfo);
                this.store.dispatch("login/setToken", res.data.data.token);
                this.$router.replace({ path: "/main" });
              } else {
                ElMessage({
                  type: "error",
                  message: "仅限学生用户登录！",
                });
              }
            } else {
              ElMessage({
                type: "error",
                message: res.data.message,
              });
            }
          })
          .catch((err) => {
            //请求失败的回调函数
            console.log(err);
          });
      }
    },
  },
};
</script>

<style>
.body-bg {
  background-attachment: fixed;
  background-image: url("../src/assets/image/background.jpg");
  background-size: 100%, 100%;
  overflow: hidden;
}
.line {
  padding: 0 10px 0;
  margin: 20px 0;
  line-height: 1px;
  border-left: 100px solid #ddd;
  border-right: 100px solid #ddd;
  text-align: center;
}
.login100-form-title {
  letter-spacing: 20px;
  font-style: inherit;
}
.container-login100 {
  justify-content: left;
  margin-left: 10%;
}
</style>