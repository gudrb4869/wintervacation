<script setup>
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import { detailArticle, deleteArticle } from "@/api/board";
import { memoList, writeMemo, deleteMemo } from "@/api/memo";
import BoardMainDetailItem from "./item/BoardMainDetailItem.vue";
import BoardMainMeoItem from "./item/BoardMainMeoItem.vue";
import BoardMainMemoList from "./item/BoardMainMemoList.vue";
import { useMemberStore } from "@/stores/member";

const memberStore = useMemberStore();
const profile = ref(null);

const router = useRouter();
const route = useRoute();

const { article_no } = route.params;
const board = ref({});
const memos = ref([]);
const memoCount = ref(0);

onMounted(() => {
  loadDetail();
  getMemoList();
  profile.value = memberStore.userInfo;
});

const loadDetail = () => {
  detailArticle(
    article_no,
    ({ data }) => {
      console.log("boardMainDetail {}", data);
      board.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
};

const moveList = () => {
  router.push({ name: "board-main" });
};

const onDeleteArticle = () => {
  deleteArticle(
    article_no,
    (response) => {
      console.log("게시글 삭제!");
      alert("게시글이 삭제 되었습니다!");
      moveList();
    },
    (error) => {
      console.log(error);
    }
  );
};

const getMemoList = () => {
  console.log(article_no + "번 qna게시글의 댓글들 조회!!!!");
  // API 호출
  memoList(
    article_no,
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

const registMemo = (memo) => {
  // API 호출
  writeMemo(
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
  <div id="containner">
    <div>
      <div style="display: flex; justify-content: space-between; align-items: center">
        <h3 style="margin: 0">여행후기 공유</h3>
      </div>
      <hr />
    </div>
    <div
      style="
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 20px;
      "
    >
      <h4 style="margin: 0">{{ board.user_id }}</h4>
      <div style="display: flex; align-items: center">
        <h5 style="margin-right: 10px">조회수 : {{ board.hit }} |</h5>
        <h5>{{ board.register_time }}</h5>
      </div>
    </div>
    <section class="my-5 text-center container">
      <div
        id="carouselExampleInterval"
        class="carousel slide"
        style="max-width: 800px; margin: auto"
        data-bs-ride="carousel"
      >
        <div class="carousel-inner">
          <BoardMainDetailItem
            v-for="(fileInfos, index) in board.fileInfos"
            :fileInfos="fileInfos"
            :index="index"
            :key="fileInfos.idx"
          >
          </BoardMainDetailItem>
        </div>
        <button
          class="carousel-control-prev"
          type="button"
          data-bs-target="#carouselExampleInterval"
          data-bs-slide="prev"
        >
          <span class="carousel-control-prev-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Previous</span>
        </button>
        <button
          class="carousel-control-next"
          type="button"
          data-bs-target="#carouselExampleInterval"
          data-bs-slide="next"
        >
          <span class="carousel-control-next-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Next</span>
        </button>
      </div>
    </section>

    <div style="margin-bottom: 40px">
      {{ board.content }}
    </div>
    <div style="display: flex; justify-content: flex-end">
      <button type="button" class="btn mb-3" @click="moveList">목록</button>
      <button type="button" class="btn mb-3 ms-1" @click="onDeleteArticle">삭제</button>
    </div>

    <h3>댓글 ({{ memoCount }})</h3>
    <hr />
    <!-- Comments section-->
    <section class="mb-5">
      <div class="card bg-light">
        <div class="card-body">
          <!-- Comment form-->
          <div v-if="profile !== null">
            <BoardMainMeoItem :article_no="article_no" @regist-memo="registMemo">
            </BoardMainMeoItem>
          </div>
          <!-- Comment with nested comments-->
          <BoardMainMemoList
            v-for="memo in memos"
            :key="memo.memo_no"
            :memo="memo"
            @delete-memo="onDeleteMemo"
          >
          </BoardMainMemoList>
        </div>
      </div>
    </section>
  </div>
</template>

<style scoped>
#containner {
  margin-left: 40px;
  margin-right: 40px;
  padding: 20px; /* Add padding here */
}

.card:hover {
  filter: brightness(1) !important;
}
</style>
