/**
 * 竞赛详情
 */
import axios from "axios"
import {
    ElMessage
} from "element-plus"
export default {
    namespaced: true,
    state() {
        return {
            competition: {}
        }
    },
    getters: {
        get(state) {
            return state.competition
        }
    },
    actions: {
        getById(context, id) {
            context.commit("get", id)
        },
        reset(context, competition) {
            context.commit("resetCompetition", competition);
        }
    },
    mutations: {
        get(state, id) {
            axios({
                    url: "/detail",
                    params: {
                        id,
                    }
                })
                .then((res) => {
                    //成功的回调
                    if (res.data.success) {
                        state.competition = res.data.data
                        //存store中
                        sessionStorage.setItem("currentCompetition", JSON.stringify(state.competition))

                        ElMessage({
                            type: "success",
                            message: res.data.message,
                        });
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
                })
        },
        resetCompetition(state, competition) {
            state.competition = competition
        }
    }
}