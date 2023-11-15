<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { listArticle } from "@/api/board";

import VSelect from "@/components/common/VSelect.vue";
import BoardListItem from './item/BoardListItem.vue';
import PageNavigation from "@/components/common/PageNavigation.vue";

const router = useRouter();

const selectOption = ref([
  { text: "검색조건", value: "" },
  { text: "글번호", value: "article_no" },
  { text: "제목", value: "subject" },
  { text: "작성자아이디", value: "user_id" },
]);

const articles = ref([]);
const currentPage = ref(1);
const totalPage = ref(0);
const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;
const param = ref({
  pgno: currentPage.value,
  spp: VITE_ARTICLE_LIST_SIZE,
  key: "",
  word: "",
});

onMounted(() => {
  getArticleList();
});

const changeKey = (val) => {
  console.log("BoarList에서 선택한 조건 : " + val);
  param.value.key = val;
};

const getArticleList = () => {
  console.log("글 얻어오자!!!");
  console.log(param.value);
  listArticle(
    param.value,
    ({ data }) => {
      console.log(data);
      articles.value = data.boards;
      currentPage.value = data.currentPage;
      totalPage.value = data.totalPageCount;
    },
    (error) => {
      console.log(error);
    }
  );
};

const onPageChange = (val) => {
  console.log(val + "번 페이지로 이동 준비 끝!!!");
  currentPage.value = val;
  param.value.pgno = val;
  getArticleList();
};

const moveWrite = () => {
  router.push({ name: "board-write" });
};
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-8 col-md-10 col-sm-12">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">여행 후기 게시판</mark>
        </h2>
      </div>
      <div class="col-lg-8 col-md-10 col-sm-12">
        <div class="row align-self-center mb-2">
          <div class="col-md-2 text-start">
            <button type="button" class="btn btn-sm" @click="moveWrite">글쓰기</button>
          </div>
          <div class="col-md-7 offset-3">
            <form class="d-flex">
              <v-select :selectOption="selectOption" @onKeySelect="changeKey"></v-select>
              <div class="input-group input-group-sm">
                <input type="text" class="form-control" v-model="param.word" placeholder="검색어..." />
                <button class="btn btn-sm" type="button" @click="getArticleList">검색</button>
              </div>
            </form>
          </div>
        </div>
        <table class="table table-hover">
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
            <board-list-item v-for="article in articles" :key="article.article_no"
              :article="article"></board-list-item>
          </tbody>
        </table>
      </div>
      <page-navigation :current-page="currentPage" :total-page="totalPage" @pageChange="onPageChange"></page-navigation>
    </div>
  </div>
</template>

<style scoped></style>
