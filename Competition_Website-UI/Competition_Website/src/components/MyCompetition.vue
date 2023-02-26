<template>
  <div class="app-container">
    <div class="app-body">
      <el-table
        :data="tableData"
        border
        fit
        height="300"
        style="width: 100%"
        current-row-key="id"
        size="mini"
      >
        <el-table-column label="序号" type="index" width="55" align="center">
          <template #default="scope">
            <span>{{ scope.$index + 1 }}</span>
          </template>
        </el-table-column>

        <el-table-column
          prop="competitionTitle"
          align="center"
          label="竞赛名称"
          width="200"
          show-overflow-tooltip
        >
        </el-table-column>
        <el-table-column
          prop="scale"
          align="center"
          label="类型"
          width="80"
          sortable
        >
          <template #default="scope">
            <el-tag
              effect="light"
              v-if="scope.row.scale === 0"
              type="info"
              round
              >{{ scaleOptions[scope.row.scale] }}</el-tag
            >
            <el-tag
              type="success"
              effect="light"
              v-if="scope.row.scale === 1"
              round
              >{{ scaleOptions[scope.row.scale] }}</el-tag
            >
            <el-tag
              type="danger"
              effect="light"
              v-if="scope.row.scale === 2"
              round
              >{{ scaleOptions[scope.row.scale] }}</el-tag
            >
          </template>
        </el-table-column>

        <el-table-column
          prop="title"
          align="center"
          label="报名表"
          show-overflow-tooltip
        >
          <template #default="scope">
            <a
              :href="preview(scope.row.registrationFormUrl)"
              :download="报名表"
              target="_blank"
              >查看报名表</a
            >
          </template>
        </el-table-column>

        <el-table-column
          prop="title"
          align="center"
          label="参赛作品"
          show-overflow-tooltip
        >
          <template #default="scope">
            <a
              v-if="scope.row.worksUrl !== null"
              :href="preview(scope.row.worksUrl)"
              :download="参赛作品"
              target="_blank"
              >{{ scope.row.worksTitle }}</a
            >
            <span v-else>尚未提交作品</span>
          </template>
        </el-table-column>

        <el-table-column
          prop="star"
          label="发布/报名/提交/评审/结果"
          align="center"
          width="230"
          sortable
        >
          <template #default="scope">
            <el-rate
              :model-value="calStep(scope.row)"
              disabled
              :icons="icons"
              :void-icon="CaretRight"
              :colors="['#409eff', '#67c23a', '#FF9900', '#FFF']"
              size="large"
            >
            </el-rate>
          </template>
        </el-table-column>

        <el-table-column prop="score" label="得分" align="center" width="90">
          <template #default="scope">
            <span v-if="scope.row.score === 0 || scope.row.score === null"
              >暂未公布</span
            >
            <span v-else>{{ scope.row.score + "分" }}</span>
          </template>
        </el-table-column>

        <el-table-column label="操作" align="center" width="230">
          <template #default="scope">
            <!-- <el-button size="mini" text="true" @click="handleEdit(scope.row)"
              >去参赛</el-button
            > -->
            <router-link
              v-if="scope.row.competitionUrl === null"
              :to="{ path: '/detail', query: { id: scope.row.id } }"
            >
              <span>去提交信息</span>
            </router-link>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <el-dialog
      :title="dialogTitleMap[dialogStatus]"
      v-model="dialogFormVisible"
      :close-on-click-modal="false"
    >
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="temp"
        label-position="left"
        label-width="70px"
        style="width: 400px; margin-left: 50px"
      >
        <el-form-item label="标题" prop="title">
          <el-input v-model="temp.title" />
        </el-form-item>
        <el-form-item label="作者" prop="author">
          <el-input v-model="temp.author" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="temp.status" placeholder="Please select">
            <el-option label="审核中" :value="0"></el-option>
            <el-option label="上架" :value="1"></el-option>
            <el-option label="下架" :value="2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="评分" prop="star">
          <el-rate
            v-model="temp.star"
            :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
            :max="5"
            style="margin-top: 8px"
          />
        </el-form-item>
        <el-form-item label="阅读数" prop="viewCount">
          <el-input v-model="temp.viewCount" type="number" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogFormVisible = false"> 取消 </el-button>
        <el-button
          type="primary"
          @click="dialogStatus === 'create' ? createData() : updateData()"
        >
          确定
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { reactive, ref, toRefs } from "vue";
import { ElNotification } from "element-plus";
import { getCompetitionInfoById } from "@/api/user";
import { formatDate } from "@/utils/date";
import store from "../../store";
import { preview } from "@/utils/preview";
export default {
  name: "complex-table",
  setup() {
    const form = ref(null);

    const state = reactive({
      pagination: {
        pageNo: 1,
        pageSize: 10,
      },
      tableData: [],
      temp: {},
      dialogTitleMap: {
        update: "Edit",
        create: "Create",
      },
      dialogStatus: "",
      dialogFormVisible: false,
      statusOptions: {
        0: "审核中",
        1: "上架",
        2: "下架",
      },
      scaleOptions: {
        0: "校赛",
        1: "省赛",
        2: "国赛",
      },
      rules: {
        title: [{ required: true, message: "标题必须", trigger: "change" }],
        author: [{ required: true, message: "作者必须", trigger: "change" }],
      },
    });

    // 重置
    const resetForm = () => {
      form.value.resetFields();
    };

    // 列表数据
    const queryList = async () => {
      let payload = state.pagination;
      payload.id = store.getters["login/loginInfo"].id;

      const { success, data } = await getCompetitionInfoById(payload);
      if (success) {
        const { rows, ...pagination } = data.resultObject;
        state.tableData = rows;
        state.pagination = pagination;
      }
    };

    // 刷新
    const refresh = () => {
      queryList();
    };
    // 格式化时间
    const handleFormatDate = (time) => {
      return formatDate(time);
    };

    const handleDelete = (index) => {
      state.tableData.splice(index, 1);
    };

    const handleEdit = (row) => {
      state.temp = Object.assign({}, row);
      state.dialogStatus = "update";
      state.dialogFormVisible = true;
    };

    const resetTemp = () => {
      state.temp = {
        id: undefined,
        author: "",
        create_at: undefined,
        star: "",
        status: undefined,
        title: "",
        viewCount: undefined,
      };
    };
    //计算步骤
    const calStep = (data) => {
      const nowDate = Date.parse(new Date());
      let activeStep = 0;
      if (nowDate > Date.parse(data.releaseDate)) {
        //已经发布了
        activeStep = 1;
      }
      if (nowDate > Date.parse(data.registrationDate)) {
        //可以报名了
        activeStep = 2;
      }
      if (nowDate > Date.parse(data.submitDate)) {
        //可以提交作品
        activeStep = 3;
      }
      if (nowDate > Date.parse(data.reviewDate)) {
        //可以评审了
        activeStep = 4;
      }
      if (nowDate > Date.parse(data.resultDate)) {
        //发布结果
        activeStep = 5;
      }
      return activeStep;
    };
    const updateData = () => {
      form.value.validate((valid) => {
        if (valid) {
          const index = state.tableData.findIndex(
            (v) => v.id === state.temp.id
          );
          state.tableData.splice(index, 1, state.temp);
          state.dialogFormVisible = false;
          ElNotification({
            title: "Success",
            message: "更新完成",
            type: "success",
            duration: 2000,
          });
        }
      });
    };
    queryList();

    return {
      ...toRefs(state),
      form,
      resetForm,
      refresh,
      handleFormatDate,
      handleDelete,
      handleEdit,
      updateData,
      resetTemp,
      preview,
      calStep,
    };
  },
};
</script>

<style scoped>
</style>