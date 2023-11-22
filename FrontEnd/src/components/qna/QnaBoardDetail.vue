<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { detailArticle, deleteArticle } from "@/api/qna-board";
import { listMemo, registMemo, deleteMemo } from "@/api/qna-memo";
import QnaMemoFormItem from "@/components/qna/item/QnaMemoFormItem.vue";
import QnaMemoListItem from "@/components/qna/item/QnaMemoListItem.vue";

const route = useRoute();
const router = useRouter();

const { article_no } = route.params;

const article = ref({});
const memos = ref([]);
const memoCount = ref(0);
const currentPage = ref(1);
const totalPage = ref(0);
const { VITE_MEMO_LIST_SIZE } = import.meta.env;
const param = ref({
  article_no: article_no,
  pgno: currentPage.value,
  spp: VITE_MEMO_LIST_SIZE,
});

onMounted(() => {
  getArticle();
  getMemoList();
});

const getArticle = () => {
  console.log(article_no + "번 qna게시글 조회!!!");
  // API 호출
  detailArticle(
    article_no,
    ({ data }) => {
      article.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
};

const getMemoList = () => {
  console.log(article_no + "번 qna게시글의 댓글들 조회!!!!");
  // API 호출
  listMemo(
    param.value,
    ({ data }) => {
      console.log(data);
      memos.value = data;
      memoCount.value = memos.value.length;
    },
    (error) => {
      console.log(error);
    }
  );
};

const moveList = () => {
  router.push({ name: "qna-board-list" });
};

const moveModify = () => {
  console.log(article_no + "번 qna 게시글 수정 페이지로 이동!!!");
  router.push({ name: "qna-board-modify", params: { article_no } });
};

const onDeleteArticle = () => {
  console.log(article_no + "번 qna 게시글 삭제!!!");
  // API 호출
  deleteArticle(
    article_no,
    (response) => {
      console.log(response);
      moveList();
    },
    (error) => {
      console.log(error);
    }
  );
};

const writeMemo = (memo) => {
  console.log("부모 컴포넌트에서 댓글 작성 요청 받음!");
  // API 호출
  registMemo(
    memo,
    (response) => {
      console.log(response);
      console.log("댓글 작성 성공!");
      getMemoList();
    },
    (error) => {
      console.log(error);
    }
  );
};

const onDeleteMemo = (memo_no) => {
  console.log(memo_no + "번 댓글 삭제 요청 부모 컴포넌트에서 받음!");
  // API 호출
  deleteMemo(
    memo_no,
    (response) => {
      console.log(response);
      console.log(memo_no + "번 댓글 삭제 성공!");
      getMemoList();
    },
    (error) => {
      console.log(error);
    }
  );
};
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-8 col-md-10 col-sm-12">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">Q&A 글보기</mark>
        </h2>
      </div>

      <div class="col-lg-8 col-md-10 col-sm-12">
        <div class="row my-2">
          <h2 class="text-secondary px-5">{{ article.article_no }}. {{ article.subject }}</h2>
        </div>
        <div class="row">
          <div class="col-md-8">
            <div class="clearfix align-content-center">
              <img
                class="avatar me-2 float-md-start bg-light p-2"
                src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
              />
              <p>
                <span class="fw-bold">{{ article.user_id }}</span> <br />
                <span class="text-secondary fw-light">
                  {{ article.register_time }} 조회 : {{ article.hit }}
                </span>
              </p>
            </div>
          </div>
          <div class="col-md-4 align-self-center text-end">댓글 : {{ memoCount }}</div>
          <div class="divider mb-3"></div>
          <div class="text-secondary">{{ article.content }}</div>
          <div class="divider mt-3 mb-3"></div>
          <div class="d-flex justify-content-end">
            <button type="button" class="btn mb-3" @click="moveList">목록</button>
            <button type="button" class="btn mb-3 ms-1" @click="moveModify">수정</button>
            <button type="button" class="btn mb-3 ms-1" @click="onDeleteArticle">삭제</button>
          </div>
        </div>
        <div class="row">
          <h3>
            <mark class="sky">댓글 ({{ memoCount }})</mark>
          </h3>
          <qna-memo-form-item
            :article_no="article_no"
            @regist-memo="writeMemo"
          ></qna-memo-form-item>
          <qna-memo-list-item
            v-for="memo in memos"
            :key="memo.memo_no"
            :memo="memo"
            @delete-memo="onDeleteMemo"
          ></qna-memo-list-item>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
