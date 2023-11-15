<script setup>
import { ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { registArticle, modifyArticle, detailArticle } from "@/api/board";

const router = useRouter();
const route = useRoute();

const props = defineProps({
  type: String,
});

const isUseId = ref(false);

const article = ref({
  article_no: 0,
  user_id: "",
  subject: "",
  content: "",
  hit: 0,
  register_time: "",
});

if (props.type === "modify") {
    let { article_no } = route.params;
    console.log("BoardFromItem article_no : " + article_no);
  detailArticle(
    article_no,
    ({ data }) => {
      article.value = data;
    },
    (error) => {
      console.log(error);
    }
  );

  isUseId.value = true;
}

const subjectErrMsg = ref("");
const contentErrMsg = ref("");
watch(
  () => article.value.subject,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 30) {
      subjectErrMsg.value = "제목을 확인해 주세요!!!";
    } else subjectErrMsg.value = "";
  },
  { immediate: true }
);
watch(
  () => article.value.content,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 500) {
      contentErrMsg.value = "내용을 확인해 주세요!!!";
    } else contentErrMsg.value = "";
  },
  { immediate: true }
);

const onSubmit = () => {
  if (subjectErrMsg.value) {
    alert(subjectErrMsg.value);
  } else if (contentErrMsg.value) {
    alert(contentErrMsg.value);
  } else {
    props.type === "regist" ? writeArticle() : updateArticle();
  }
};

const writeArticle = () => {
  console.log("여행후기 게시글 등록!!!", article.value);
  registArticle(
    article.value,
    (response) => {
      console.log(response);
      moveList();
    },
    (error) => {
      console.log(error);
    }
  );
};

const updateArticle = () => {
  console.log(article.value.article_no + "번 여행후기 게시글 수정!!!");
  modifyArticle(
    article.value,
    (response) => {
      console.log(response);
      moveList();
    },
    (error) => {
      console.log(error);
    }
  );
};

const moveList = () => {
  router.push({ name: "board-list" });
};
</script>

<template>
  <form @submit.prevent="onSubmit">
    <div class="mb-3">
      <label for="userid" class="form-label">작성자 ID : </label>
      <input
        type="text"
        class="form-control"
        v-model="article.user_id"
        :disabled="isUseId"
        placeholder="작성자ID..."
      />
    </div>
    <div class="mb-3">
      <label for="subject" class="form-label">제목 : </label>
      <input type="text" class="form-control" v-model="article.subject" placeholder="제목..." />
    </div>
    <div class="mb-3">
      <label for="content" class="form-label">내용 : </label>
      <textarea class="form-control" v-model="article.content" rows="7"></textarea>
    </div>
    <div class="col-auto text-center">
      <button type="submit" class="btn mb-3" v-if="type === 'regist'">글작성</button>
      <button type="submit" class="btn mb-3" v-if="type === 'modify'">글수정</button>
      <button type="button" class="btn mb-3 ms-1" @click="moveList">목록으로이동</button>
    </div>
  </form>
</template>

<style scoped></style>
