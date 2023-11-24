<script setup>
import { ref, onMounted } from "vue";
import { useMemberStore } from "@/stores/member";
import { useRouter } from "vue-router";
import { registArticle } from "@/api/board";

const router = useRouter();
const memberStore = useMemberStore();

const board = ref({
  article_no: "",
  user_id: "",
  subject: "",
  content: "",
  hit: 0,
  register_time: "",
});

onMounted(() => {
  const profile = ref(null);
  profile.value = memberStore.userInfo;
  board.value.user_id = profile.value.user_id;
});

const boardRegist = () => {
  const imgInfos = document.querySelector("#file").files;
  console.log("!!!!", board.value.user_id);
  const params = {
    boardDto: board.value,
    imgInfos: imgInfos,
  };

  registArticle(
    params,
    (response) => {
      console.log(response);
      goToBoardMain();
    },
    (error) => {
      console.log(error);
    }
  );
};

const goToBoardMain = () => {
  router.push({ name: "board-main" });
};
</script>

<template>
  <div class="py-5 bg-light" bis_skin_checked="1">
    <div class="container" bis_skin_checked="1">
      <div style="display: flex; justify-content: space-between; align-items: center">
        <h3 style="margin: 0">여행후기 공유</h3>
      </div>
      <hr />

      <label for="subject">제목</label>
      <input type="text" id="subject" v-model="board.subject" placeholder="제목..." />

      <label for="contents" style="margin-top: 15px">내용</label>
      <textarea
        name=""
        id="contents"
        cols="30"
        rows="10"
        v-model="board.content"
        placeholder="내용..."
      ></textarea>

      <label for="upfile" class="form-label">파일:</label>
      <input type="file" class="form-control border" id="file" name="file" multiple="multiple" />
      <div id="buttonContainer">
        <div
          style="display: flex; justify-content: flex-end; align-items: center; margin-top: 10px"
        >
          <button class="float-right btn" @click.prevent="boardRegist">공유 하기</button>
          <button class="float-right btn" style="margin-left: 10px" @click.prevent="goToBoardMain">
            취소
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
#subject {
  border: 1px solid lightgray;
  background-color: gainsboro;
  border-radius: 8px;
  width: 100%;
  height: 40px;
  padding-left: 10px;
}

textarea {
  padding: 10px;
  border: 1px solid lightgray;
  background-color: gainsboro;
  border-radius: 8px;
  width: 100%;
  height: 400px;
}
</style>
