import { createRouter, createWebHistory } from "vue-router";
import TheMainView from "@/views/TheMainView.vue";
import TheAttractionView from "@/views/TheAttractionView.vue";

import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";

const onlyAuthUser = async (to, from, next) => {
  const memberStore = useMemberStore();
  const { userInfo, isValidToken } = storeToRefs(memberStore);
  const { getUserInfo } = memberStore;

  let token = sessionStorage.getItem("accessToken");

  if (userInfo.value != null && token) {
    await getUserInfo(token);
  }
  if (!isValidToken.value || userInfo.value === null) {
    next({ name: "member-login" });
  } else {
    next();
  }
};

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "main",
      component: TheMainView,
    },
    {
      path: "/attractions",
      name: "attractions",
      component: TheAttractionView,
    },
    {
      path: "/qna",
      name: "qna",
      component: () => import("@/views/TheQnaView.vue"),
      redirect: { name: "qna-board-list" },
      children: [
        {
          path: "list",
          name: "qna-board-list",
          component: () => import("@/components/qna/QnaBoardList.vue"),
        },
        {
          path: ":article_no",
          name: "qna-board-view",
          component: () => import("@/components/qna/QnaBoardDetail.vue"),
        },
        {
          path: "write",
          name: "qna-board-write",
          component: () => import("@/components/qna/QnaBoardWrite.vue"),
        },
        {
          path: "modify/:article_no",
          name: "qna-board-modify",
          component: () => import("@/components/qna/QnaBoardModify.vue"),
        },
      ],
    },
    {
      path: "/plan",
      name: "plan",
      component: () => import("@/views/ThePlanView.vue"),
      redirect: { name: "plan-list" },
      children: [
        {
          path: "list",
          name: "plan-list",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/plan/PlanList.vue"),
        },
        {
          path: "write",
          name: "plan-write",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/plan/PlanWrite.vue"),
        }
      ],
    },
    {
      path: "/board",
      name: "board",
      component: () => import("@/views/TheBoardView.vue"),
      redirect: { name: "board-main" },
      children: [
        {
          path: "main",
          name: "board-main",
          component: () => import("@/components/board/BoardMain.vue"),
        },
        {
          path: "regist",
          name: "board-main-regist",
          component: () => import("@/components/board/BoardMainRegist.vue"),
        },
        {
          path: "list",
          name: "board-list",
          component: () => import("@/components/board/BoardList.vue"),
        },
        {
          path: ":article_no",
          name: "board-view",
          component: () => import("@/components/board/BoardDetail.vue"),
        },
        {
          path: "write",
          name: "board-write",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/board/BoardWrite.vue"),
        },
        {
          path: "modify/:article_no",
          name: "board-modify",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/board/BoardModify.vue"),
        },
      ],
    },
    {
      path : "/member",
      name: "member",
      component: () => import("@/views/TheMemberView.vue"),
      children: [
        {
          path: "mypage",
          name: "member-mypage",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/member/MemberMyPage.vue"),
        },
        {
          path: "login",
          name: "member-login",
          component: () => import("@/components/member/MemberLogin.vue"),
        },
        {
          path: "join",
          name: "member-join",
          component: () => import("@/components/member/MemberJoin.vue"),
        }
      ],
    },
  ],
});

export default router;
