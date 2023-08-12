import { createRouter, createWebHistory } from 'vue-router'
import store from "@/store";
import {notification} from "ant-design-vue";

const routes = [
  {
    path: '/',
    component: () => import('../views/main.vue'),
    children: [{
      path: 'welcome',
      component: () => import("../views/main/welcome.vue")
    }, {
      path: '/about',
      component: () => import('../views/main/about.vue')
    }, {
      path: "base/",
      children: [
        {
          path: 'station',
          component: () => import('../views/main/base/station.vue')
        }, {
          path: 'train',
          component: () => import('../views/main/base/train.vue')
        }, {
          path: 'train-station',
          component: () => import('../views/main/base/train-station.vue')
        }, {
          path: 'train-carriage',
          component: () => import('../views/main/base/train-carriage.vue')
        }, {
          path: 'train-seat',
          component: () => import('../views/main/base/train-seat.vue')
        }
      ]
    }, {
      path: 'batch/',
      children: [
        {
          path: 'job',
          component: () => import('../views/main/batch/job.vue')
        }
      ]
    }, {
      path: 'business/',
      children: [
        {
          path: 'daily-train',
          component: () => import('../views/main/business/daily-train.vue')
        },
        {
          path: 'daily-train-station',
          component: () => import('../views/main/business/daily-train-station.vue')
        },
        {
          path: 'daily-train-carriage',
          component: () => import('../views/main/business/daily-train-carriage.vue')
        }
      ]
    }]
  }, {
    path: '',
    redirect: '/welcome'
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

// 路由登录拦截
router.beforeEach((to, from, next) => {
  // 要不要对meta.loginRequire属性做监控拦截
  if (to.matched.some(function (item) {
    console.log(item, "是否需要登录校验：", item.meta.loginRequire || false);
    return item.meta.loginRequire
  })) {
    const _member = store.state.member;
    console.log("页面登录校验开始：", _member);
    if (!_member.token) {
      console.log("用户未登录或登录超时！");
      notification.error({ description: "未登录或登录超时" });
      next('/login');
    } else {
      next();
    }
  } else {
    next();
  }
});

export default router
