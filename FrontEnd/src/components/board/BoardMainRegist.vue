<script setup>
import { ref, onMounted } from 'vue';
import { useMemberStore } from "@/stores/member";
import { useRouter } from "vue-router";
import { registArticle } from "@/api/board"

const router = useRouter();
const user = ref(null);

const board = ref({
    article_no: "",
    user_id: "",
    subject: "",
    content: "",
    hit: 0,
    register_time: "",
})

onMounted(() => {
    const memberStore = useMemberStore();
    user.value = memberStore.userInfo;
});



const boardRegist = () => {
    board.user_id = user.value.user_id;

    const fileList = document.querySelector('#file').files;
    registArticle(
        board.value,
        fileList,
        (response) => {
            console.log(response);
            goToBoardMain();
        },
        (error) => {
            console.log(error);

        }
    )
}

const goToBoardMain = () => {
    router.push({ name: "board-main" });
}


</script>

<template>
  <div class="py-5 bg-light" bis_skin_checked="1">
      <div class="container" bis_skin_checked="1">
        <div style='display: flex; justify-content: space-between; align-items: center;'>
            <h3 style='margin: 0;'>여행후기 공유</h3>
      </div>
      <hr>

      <form @submit.prevent="boardRegist">
        <label for="subject">제목</label>
        <input type="text" id='subject' v-model="board.subject" placeholder='제목...'>

        <label for="contents" style='margin-top: 15px;'>내용</label>
        <textarea name="" id="contents" cols="30" rows="10" v-model="board.content" placeholder='내용...'></textarea>

        <label for="upfile" class="form-label">파일:</label>
        <input type="file" class="form-control border" id="file" name="file" multiple="multiple">
        <div id='buttonContainer'>
            <div style='display: flex; justify-content: flex-end; align-items: center; margin-top: 10px;'>
                <button class="float-right btn" type="submit">공유 하기</button>
                <button class="float-right btn" style='margin-left: 10px;' @click.prevent="goToBoardMain">취소</button>
            </div>
        </div>
    </form>
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