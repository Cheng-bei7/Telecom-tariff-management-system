import {createRouter, createWebHashHistory} from "vue-router";
import Home from "../views/Home.vue";

const routes = [
    {
        path: '/',
        redirect: '/login'
    }, {
        path: "/",
        name: "Home",
        component: Home,
        children: [
            {
                path: "/homepage",
                name: "homepage",
                meta: {
                    title: '系统首页'
                },
                component: () => import ( /* webpackChunkName: "dashboard" */ "../views/Homepage.vue")
            },{
                path: "/cost",
                name: "cost",
                meta: {
                    title: '资费管理'
                },
                component: () => import ( /* webpackChunkName: "dashboard" */ "../views/Cost.vue")
            },{
                path: "/account",
                name: "account",
                meta: {
                    title: '账务信息'
                },
                component: () => import ( /* webpackChunkName: "dashboard" */ "../views/Account.vue")
            }, {
                path: "/service",
                name: "service",
                meta: {
                    title: '业务信息'
                },
                component: () => import ( /* webpackChunkName: "dashboard" */ "../views/Service.vue")
            }, {
                path: "/order",
                name: "order",
                meta: {
                    title: '账单管理'
                },
                component: () => import ( /* webpackChunkName: "dashboard" */ "../views/Order.vue")
            },
            {
                path: "/role",
                name: "role",
                meta: {
                    title: '角色管理'
                },
                component: () => import ( /* webpackChunkName: "dashboard" */ "../views/Role.vue")
            },{
                path: "/admin",
                name: "admin",
                meta: {
                    title: '人员管理'
                },
                component: () => import ( /* webpackChunkName: "dashboard" */ "../views/Admin.vue")
            },{
                path: '/403',
                name: '403',
                meta: {
                    title: '没有权限'
                },
                component: () => import (/* webpackChunkName: "403" */ '../views/errorInterface/403.vue')
            }
        ]
    }, {
        path: "/login",
        name: "Login",
        meta: {
            title: '登录'
        },
        component: () => import ( /* webpackChunkName: "login" */ "../views/Login.vue")
    }  , {
        path: '/404',
        name: '404',
        meta: {
            title: '找不到页面'
        },
        component: () => import (/* webpackChunkName: "404" */ '../views/errorInterface/404.vue'),
        hidden: true
    },
    {
        path: '/403',
        name: '403',
        meta: {
            title: '权限不足'
        },
        component: () => import (/* webpackChunkName: "404" */ '../views/errorInterface/403.vue'),
        hidden: true
    },
    {
        path:  '/:catchAll(.*)',
        hidden: true,
        redirect: { path: '/404' }
    }
];

const router = createRouter({
    history: createWebHashHistory(),
    routes
});

router.beforeEach((to, from, next) => {
    document.title = `${to.meta.title} | vue-manage-system`;
    const role = JSON.parse(localStorage.getItem("admin"));
    next();
    if (!role && to.path !== '/login') {
        next('/login');
    } else if (to.meta.permission) {
        // 如果是管理员权限则可进入，这里只是简单的模拟管理员权限而已
        role === 'admin'
            ? next()
            : next('/403');
    } else {
        next();
    }
});

export default router;
