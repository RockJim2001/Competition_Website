/**
 * loginInfo登录用户信息的变量
 */
export default {
    namespaced: true,
    state() {
        return {
            loginInfo: {
                id: "",
                nickname: "",
                avatar: "",
                permission: ""
            },
            token: "sldflkdjflsjjs6+s5ff8we4f5s46we4r65w466465qw64e@dsfs6"
        }
    },
    mutations: {
        save(state, loginInfo) {

            state.loginInfo = loginInfo
            console.log(state);
            sessionStorage.setItem(
                "loginInfo",
                JSON.stringify(state.loginInfo))
        },
        clear(state) {
            state.loginInfo = {
                id: "",
                nickname: "",
                avatar: "",
                permission: ""
            }
        },
        SET_TOKEN(state, token) {
            state.token = token;

        },
    },
    actions: {
        update(context, loginInfo) {
            context.commit("save", loginInfo)
        },
        logOut(context) {
            sessionStorage.removeItem("loginInfo")
            context.commit("clear")
        },
        setToken(context, token) {
            context.commit("SET_TOKEN", token)
        }
    },
    getters: {
        //获取id
        id(state) {
            return state.loginInfo.id
        },
        //获取昵称
        nickname(state) {
            return state.loginInfo.nickname
        },
        //获取头像地址
        avatar(state) {
            return state.loginInfo.avatar
        },
        //获取权限
        permission(state) {
            return state.loginInfo.permission
        },
        //获取identifier
        identifier(state) {
            return state.loginInfo.identifier
        },
        //获取loginInfo
        loginInfo(state) {
            return state.loginInfo
        },
        token(state) {
            return state.token
        }
    }
}