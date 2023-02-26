<template>
  <div class="competitions">
    <!-- 顶部筛选框 -->
    <div class="selection">
      <el-form :model="form" label-width="120px">
        <el-form-item label="分  类：">
          <el-radio-group v-model="form.sort">
            <el-radio-button label="全部" />
            <el-radio-button label="未开始" />
            <el-radio-button label="进行中" />
            <el-radio-button label="已结束" />
          </el-radio-group>
        </el-form-item>
        <el-form-item label="时  间：">
          <el-row>
            <el-col :span="13">
              <el-radio-group v-model="form.dateRange">
                <el-radio-button label="全部" />
                <el-radio-button label="一天之内" />
                <el-radio-button label="一周之内" />
                <el-radio-button label="一月之内" />
              </el-radio-group>
            </el-col>
            <el-col :span="5" :offset="6">
              <el-config-provider :locale="zhCn">
                <el-date-picker
                  v-model="form.dateRange"
                  type="monthrange"
                  unlink-panels
                  range-separator="至"
                  start-placeholder="开始月份"
                  end-placeholder="结束月份"
                  :shortcuts="shortcuts"
                />
              </el-config-provider>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item label="状  态：">
          <el-radio-group v-model="form.state">
            <el-radio-button label="全部" />
            <el-radio-button label="国赛" />
            <el-radio-button label="省赛" />
            <el-radio-button label="校赛" />
          </el-radio-group>
        </el-form-item>
      </el-form>
    </div>
    <!-- 显示列表 -->
    <div class="competitions-list">
      <el-container>
        <el-header class="customize-header">
          <el-row>
            <el-col :span="3">
              <el-h2 class="newTitle">竞赛列表</el-h2>
              <el-icon><BottomRight /></el-icon>
            </el-col>
            <el-col :span="15">
              <el-menu
                :router="true"
                :default-active="1"
                mode="horizontal"
                class="competitions-list-menu"
              >
                <el-menu-item
                  index="1"
                  class="competitions-list-header-menu-item"
                  >综合排序</el-menu-item
                >
                <el-menu-item
                  index="2"
                  class="competitions-list-header-menu-item"
                  >最新发布</el-menu-item
                >
                <el-menu-item
                  index="3"
                  class="competitions-list-header-menu-item"
                  >热门竞赛</el-menu-item
                >
              </el-menu>
            </el-col>
          </el-row>
        </el-header>
        <el-main class="competitions-list-main">
          <ul>
            <li
              v-for="competition in competitions"
              :key="competition.id"
              class="competition-module"
            >
              <el-container>
                <el-aside>
                  <router-link
                    v-if="competition.url === null"
                    :to="{ path: '/detail', query: { id: competition.id } }"
                  >
                    <el-image :src="competition.poster"></el-image>
                  </router-link>
                  <a :href="competition.url" v-else target="_blank">
                    <el-image :src="competition.poster"></el-image>
                  </a>
                </el-aside>
                <el-container>
                  <el-header class="competition-module-header">
                    <!-- 点击竞赛题目进行跳转 -->
                    <router-link
                      v-if="competition.url === null"
                      :to="{ path: '/detail', query: { id: competition.id } }"
                    >
                      <h4>{{ competition.title }}</h4>
                    </router-link>
                    <a :href="competition.url" v-else target="_blank">
                      <h4>{{ competition.title }}</h4>
                    </a>
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
    </div>
    <el-row class="competitions-list-pagination">
      <el-col :span="10" :offset="7">
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
import { ElConfigProvider } from "element-plus";
import zhCn from "element-plus/lib/locale/lang/zh-cn";
import { BottomRight } from "@element-plus/icons-vue";
import { useStore } from "vuex";
import axios from "axios";
export default {
  name: "Competition",
  components: { [ElConfigProvider.name]: ElConfigProvider, BottomRight },
  setup() {
    const store = useStore();
    return { store };
  },
  data() {
    return {
      form: {
        sort: "全部",
        dateRange: "全部",
        state: "全部",
      },
      zhCn,
      competitions: [
        {
          id: "",
          poster: "",
          url: "",
          title: "",
          summary: "",
          status: 0,
          scale: 0,
          registrationNumber: 0,
          startDate: "",
          endDate: "",
        },
      ],
    };
  },
  created() {},
  mounted() {
    console.log("mounted");
    axios({
      url: "competition",
    })
      .then((res) => {
        //请求成功
        if (res.data.success) {
          //成功，回调函数
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
      })
      .catch((res) => {
        //请求失败
        ElMessage({
          type: "error",
          message: "网络异常!!!",
        });
      });
  },
};
</script>
<style scoped>
.competitions {
  min-height: 698px;
  /* background-color: rgb(59, 69, 76); */
  padding-top: 10px;
}
.selection {
  margin: 20px 200px;
  box-shadow: 1px 2px 8px 0px rgba(209 192 192, 0.9);
  background-color: rgb(255, 255, 255);
  border-width: 1px;
  border-style: outset;
  border-radius: 2%;
  padding: 20px 20px;
}
.competitions-list {
  margin: 20px 200px;
  box-shadow: 1px 2px 8px 0px rgba(209 192 192, 0.9);
  background-color: rgb(255, 255, 255);
  border-width: 1px;
  border-style: outset;
  border-radius: 2%;
}
.competitions-list-header
  .competitions-list-menu
  .competitions-list-header-menu-item {
  max-height: 35px;
}
.competitions-list-menu {
  --el-menu-item-height: 30px;
}
.competitions-list-header-menu-item {
  font-size: 20px;
}

.competitions-list-main {
  min-height: 300px;
}

.competitions-list-pagination {
  margin: 20px 200px;
  align-items: center;
}
</style>