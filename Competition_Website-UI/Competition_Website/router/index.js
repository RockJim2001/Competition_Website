/**
 * 路由配置
 */
//1.导入路由
import {
    nextTick
} from 'vue';
import {
    createRouter,
    createWebHashHistory
} from 'vue-router'
// //2.引入组件

const VueRouter = createRouter({
    history: createWebHashHistory(),
    routes: [{
            path: '/login',
            name: "login",
            component: () => import("../pages/Login.vue"),
            meta: {
                title: "登录"
            }
        },
        {
            path: '/',
            redirect: '/main',
            name: "home",
            component: () => import("../pages/Home.vue"),
            meta: {
                title: '首页'
            },
            children: [{
                    path: 'new',
                    component: () => import("@/components/New.vue")
                },
                {
                    path: 'main',
                    component: () => import("@/components/Main.vue")
                },
                {
                    path: 'competition',
                    component: () => import("@/components/Competition.vue")
                },
                {
                    path: 'notification',
                    name: 'notification',
                    component: () => import("@/components/Notification.vue"),
                    meta: {
                        title: '竞赛通知'
                    }
                },
                {
                    path: 'detail',
                    name: 'detail',
                    component: () => import("@/components/CompetitionDetail.vue"),
                    meta: {
                        title: '竞赛详情'
                    }
                },

                {
                    path: 'userCenter',
                    name: 'userCenter',
                    component: () => import("@/components/UserCenter.vue"),
                    meta: {
                        title: '用户中心'
                    },
                    children: [{
                            path: 'myCompetition',
                            name: 'myCompetition',
                            component: () => import("@/components/MyCompetition"),
                            meta: {
                                title: '我的竞赛'
                            },
                        },
                        {
                            path: 'honor',
                            name: 'honor',
                            component: () => import("@/components/Honor.vue"),
                            meta: {
                                title: '我的竞赛'
                            },
                        },
                        {
                            path: 'setting',
                            name: 'setting',
                            component: () => import("@/components/Setting"),
                            meta: {
                                title: '我的竞赛'
                            },
                        }
                    ]
                },
            ]
        },
        {
            path: '/register',
            name: "register",
            component: () => import("../pages/Register.vue"),
            meta: {
                title: '注册'
            }
        },
        {
            path: '/forgetPassword',
            name: "forgetPassword",
            component: () => import("../pages/ForgetPassword.vue"),
            meta: {
                title: '忘记密码'
            }
        },
    ]
})

// VueRouter.beforeEach((to, from, next) => {
//     if (to.path === "/detail") {
//         console.log("a" + to.path);
//         console.log("a" + to.query.id);
//     }
//     if (from.query.id === to.query.id) {
//         next()
//     } else {
//         next({
//             path: to.path,
//             query: to.query
//         })
//     }
// })
export default VueRouter