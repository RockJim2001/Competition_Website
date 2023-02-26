/**
 * vuex配置
 */
/*导入vuex*/
import {
    createStore
} from "vuex";

import login from './module/login'
import competitionDetail from "./module/competitionDetail";
import competitionList from './module/competitionList'
import notification from './module/notification'

//
const getters = {
    loading(state) {
        return state.loading;
    },
    error(state) {
        return state.error;
    }
}
//用于响应组件的动作
const actions = {
    updateLoading(context, loading) {
        context.commit("Loading", loading)
    },
    updateError(context, error) {
        context.commit("Error", error)
    }
}
//用于操作store中的数据
const mutations = {
    Loading(state, loading) {
        state.loading = loading;
    },
    Error(state, error) {
        state.error = error;
    }
}
//用于存储数据
const state = {
    loading: {
        type: Boolean,
        default: false
    },
    error: {
        type: String,
        default: null
    },
}
const store = createStore({
    state,
    getters,
    actions,
    mutations,
    modules: {
        login,
        competitionDetail,
        competitionList,
        notification,
    }
})
export default store