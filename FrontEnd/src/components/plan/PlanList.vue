<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';

import PlanListItem from '@/components/plan/item/PlanListItem.vue';
import VPageNavigation from '@/components/common/VPageNavigation.vue';
import { listPlan } from '../../api/plan';

import { useMemberStore } from "@/stores/member";

const memberStore = useMemberStore();

const userInfo = ref(memberStore.userInfo);

const router = useRouter();

const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;

const plans = ref([]);
const currentPage = ref(1);
const totalPage = ref(0);
const param = ref({
    user_id: userInfo.value.user_id,
    pgno: currentPage.value,
    spp: VITE_ARTICLE_LIST_SIZE,
    key: "",
    word: "",
});

onMounted(() => {
    getPlanList();
})

const getPlanList = () => {
    console.log("Plan 얻어오자!!!")
    console.log(param.value);
    listPlan(
        param.value,
        ({ data }) => {
            console.log(data);
        }, (error) => {
            console.log(error);
        }
    )
}

const onPageChange = (val) => {
    console.log(val + "번 페이지로 이동 준비 끝!!!");
    currentPage.value = val;
    param.value.pgno = val;
    getArticleList();
};

const moveWrite = () => {
    router.push({ name: "plan-write" });
};
</script>

<template>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-12">
                <h2 class="my-3 py-3 shadow-sm bg-light text-center">
                    <mark class="sky">나의 여행 계획</mark>
                </h2>
            </div>
            <div class="col-12">
                <div class="row align-self-center mb-2">
                    <div class="col-md-2 text-start">
                        <button type="button" class="btn btn-sm" @click="moveWrite">Plan 만들기</button>
                    </div>
                    <!-- <div class="col-md-7 offset-3">
                        <form class="d-flex">
                            <v-select :selectOption="selectOption" @onKeySelect="changeKey"></v-select>
                            <div class="input-group input-group-sm">
                                <input type="text" class="form-control" v-model="param.word" placeholder="검색어..." />
                                <button class="btn btn-sm" type="button" @click="getArticleList">검색</button>
                            </div>
                        </form>
                    </div> -->
                </div>
                <div class="row">
                    <plan-list-item v-for="plan in plans" :key="plan.plan_no" :plan='plan'></plan-list-item>
                </div>
                <!-- <table class="table table-hover">
                    <thead class="table-dark">
                        <tr class="text-center">
                            <th scope="col">글번호</th>
                            <th scope="col">제목</th>
                            <th scope="col">작성자</th>
                            <th scope="col">조회수</th>
                            <th scope="col">작성일</th>
                        </tr>
                    </thead>
                    <tbody>
                        <qna-board-list-item v-for="article in articles" :key="article.article_no"
                            :article="article"></qna-board-list-item>
                    </tbody>
                </table> -->
            </div>
            <v-page-navigation :current-page="currentPage" :total-page="totalPage"
                @pageChange="onPageChange"></v-page-navigation>
        </div>
    </div>
</template>

<style scoped></style>