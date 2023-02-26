<template>
  <div class="setting">
    <el-card shadow="hover">
      <el-collapse accordion>
        <el-collapse-item name="1">
          <template #title>
            <el-col :span="5" class="userInfor-title">个人信息</el-col>
            <el-col :span="12" :offset="1" class="userInfor-title">{{
              userInfor.nickname
            }}</el-col>
            <el-col :span="3" :offset="2" class="userInfor-title">修改</el-col>
          </template>
          <div class="userInfor-body">
            <div class="userInfor-avatar">
              <el-upload
                class="avatar-uploader"
                action="https://jsonplaceholder.typicode.com/posts/"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
              >
                <el-avatar
                  v-if="userInfor.avatar"
                  :src="userInfor.avatar"
                  class="avatar"
                />
                <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
              </el-upload>
              <p>180x180像素以上</p>
            </div>
            <el-form label-width="120px" :model="userInfor" class="form">
              <el-form-item label="昵称">
                <el-input v-model="userInfor.nickname"></el-input>
              </el-form-item>
              <el-form-item label="性别">
                <el-radio-group v-model="userInfor.sex">
                  <el-radio label="男" />
                  <el-radio label="女" />
                </el-radio-group>
              </el-form-item>
              <el-form-item label="学校">
                <el-select v-model="userInfor.school" placeholder="请选择学校">
                  <el-option label="武汉理工大学" value="whut" />
                  <el-option label="武汉大学" value="wut" />
                </el-select>
              </el-form-item>
              <el-form-item label="学院">
                <el-select v-model="userInfor.college" placeholder="请选择学院">
                  <el-option label="计算机与人工智障学院" value="whut" />
                  <el-option label="交通与物流学院" value="wut" />
                </el-select>
              </el-form-item>
              <el-form-item label="专业">
                <el-select v-model="userInfor.major" placeholder="请选择专业">
                  <el-option label="计算机科学与技术" value="whut" />
                  <el-option label="软件工程" value="wut" />
                </el-select>
              </el-form-item>
              <el-form-item label="学历">
                <el-select
                  v-model="userInfor.education"
                  placeholder="请选择学历"
                >
                  <el-option label="本科" value="whut" />
                  <el-option label="研究生" value="wut" />
                </el-select>
              </el-form-item>
              <el-form-item type="email" label="电子邮箱">
                <el-input v-model="userInfor.email" />
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="onSubmit">保存</el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-collapse-item>
        <el-collapse-item name="2">
          <template #title>
            <el-col :span="5" class="userInfor-title">手机号</el-col>
            <el-col :span="12" :offset="1" class="userInfor-title">{{
              userInfor.phone
            }}</el-col>
            <el-col :span="3" :offset="2" class="userInfor-title">修改</el-col>
          </template>
          <el-form class="phone-form">
            <el-form-item label="修改手机号码">
              <el-input
                type="tel"
                pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}"
                placeholder="请输入手机号码"
              />
            </el-form-item>
            <el-form-item label="短信验证码">
              <el-input placeholder="请输入短信验证码" />
              <el-button type="primary" @click="发送短信验证码;"
                >获取验证码</el-button
              >
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="绑定;">绑定</el-button>
            </el-form-item>
          </el-form>
        </el-collapse-item>

        <el-collapse-item name="3">
          <template #title>
            <el-col :span="5" class="userInfor-title">密码修改</el-col>
            <el-col :span="12" :offset="1" class="userInfor-title"></el-col>
            <el-col :span="3" :offset="2" class="userInfor-title">修改</el-col>
          </template>
          <el-form class="password-form">
            <el-form-item label="旧密码">
              <el-input type="password"></el-input>
            </el-form-item>
            <el-form-item label="新密码">
              <el-input type="password"></el-input>
            </el-form-item>
            <el-form-item label="确认密码">
              <el-input type="password"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary">修改</el-button>
              <el-button type="primary">重置</el-button>
            </el-form-item>
          </el-form>
        </el-collapse-item>
        <el-collapse-item name="4">
          <template #title>
            <el-col :span="5" class="userInfor-title">第三方账号绑定</el-col>
            <el-col :span="12" :offset="1" class="userInfor-title"></el-col>
            <el-col :span="3" :offset="2" class="userInfor-title">修改</el-col>
          </template>
          <el-form>
            <el-form-item label="QQ">
              <el-input type="text"></el-input>
            </el-form-item>
            <el-form-item label="微信">
              <el-input type="text"></el-input>
            </el-form-item>
            <el-form-item label="微博">
              <el-input type="text"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary">绑定</el-button>
            </el-form-item>
          </el-form>
        </el-collapse-item>
      </el-collapse>
    </el-card>
  </div>
</template>

<script>
import { Plus } from "@element-plus/icons-vue";
export default {
  name: "Setting",
  components: {
    Plus,
  },
  data() {
    return {
      userInfor: {
        id: "",
        avatar:
          "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png",
        nickname: "柯博",
        sex: "",
        email: "",
        school: "",
        phone: "",
      },
    };
  },
};
</script>

<style scoped>
.userInfor-title {
  font-size: 18px;
}
.userInfor-body {
  padding: 0px 50px;
}
.userInfor-avatar {
  margin: 0px 300px;
  align-content: center;
}
.form {
  margin-right: 100px;
}
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}

.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>