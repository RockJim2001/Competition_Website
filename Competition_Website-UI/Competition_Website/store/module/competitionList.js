/**
 * 竞赛配置
 */
import axios from "axios"
import {
    ElMessage
} from 'element-plus'
export default {
    namespaced: true,
    state() {
        return {
            competitions: []
        }
    },
    getters: {
        getAll(state) {

            return state.competitions;
        },
    },
    actions: {
        getAll(context) {
            context.commit("getAllCompetition")
        },
        reset(context, competitions) {
            context.commit("resetCompetitions", competitions);
        }
    },
    mutations: {
        getAllCompetition(state) {
            axios({
                    url: "competition",
                })
                .then((res) => {
                    //请求成功
                    if (res.data.success) {
                        //成功，回调函数
                        state.competitions = res.data.data
                        //存在store中
                        sessionStorage.setItem("competitionList", JSON.stringify(state.competitions))
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
        resetCompetitions(state, competitions) {
            state.competitions = competitions
        }
    }
}