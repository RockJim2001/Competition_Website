/**
 * 通知存储配置
 */

import axios from 'axios'
import {
    ElMessage
} from 'element-plus'
export default {
    namespaced: true,
    state() {
        return {
            notifications: [],
        }
    },
    getters: {
        //根据id获取notification
        getNotificationById: (state) => (id) => {
            // console.log(state.notifications);
            return state.notifications.find(notification => notification.id === id)
        },
        //获取所有的notifications
        getAllNotification(state) {
            return state.notifications;
        }
    },
    actions: {
        //获取所有的notifications
        getAllNotification(context) {
            context.commit("getAll")
        },
        //初始化notification
        reset(context, notifications) {
            context.commit("resetNotification", notifications);
        }
    },
    mutations: {
        //获取所有
        getAll(state) {
            axios({
                    url: "notification",
                })
                .then((res) => {
                    //请求成功
                    if (res.data.code == 200) {
                        state.notifications = res.data.data;
                        console.log(state.notification);
                        sessionStorage.setItem('notifications', JSON.stringify(state.notifications))
                        console.log("sessionStorage:" + new Date());
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
                        message: "网络异常!!!！",
                    });
                });
        },
        resetNotification(state, notifications) {
            state.notifications = notifications
        }
    }
}