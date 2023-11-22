<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { getImg } from "@/api/board";

const router = useRouter();

const props = defineProps({
  board: Object,
});

const articleNo = props.board.article_no;

const goDetail = () => {
  router.push({ name: "board-main-detail", params: { article_no: articleNo }})
}

let originalName = null;
let saveFolder = null;
let saveFile = null;

if (props.board.fileInfos.length !== 0) {
  originalName = props.board.fileInfos[0].originalFile;
  saveFolder = props.board.fileInfos[0].saveFolder;
  saveFile = props.board.fileInfos[0].saveFile;
}

const path = ref("");

onMounted(() => {
  if (props.board.fileInfos.length !== 0) {
    getImges();
  } else {
    path.value = window.location.origin + "/src/assets/img/no_img.jpg";
  }
})

const getImges = () => {
  getImg(
    saveFolder,
    originalName,
    saveFile,
    ({ data }) => {
      path.value = "http://localhost/board-api/getImg/" +saveFolder +"/"+originalName +"/"+saveFile;
    },
    (error) => {
      console.log(error);
    }
  );
};

</script>

<template>
      <div class="col" bis_skin_checked="1" style='height: 450px;'>
        <div class="card shadow-sm h-100" bis_skin_checked="1" @click='goDetail'>
          <img class="bd-placeholder-img card-img-top" :src="path" alt="이미지가 없습니다!" >
          <div class="card-body" bis_skin_checked="1">
            <p class="card-text" style='font-size: 25px;'>{{ board.subject }}</p>
            <p class="card-text text-truncate">{{ board.content }}</p>
            <div class="d-flex justify-content-between align-items-center" bis_skin_checked="1">
              <div class="btn-group text-truncate" style="max-width: 150px;" bis_skin_checked="1">
                <p>{{ board.user_id }}</p>
              </div>
              <div class='d-flex gap-3'>
                <h5>조회수 : {{ board.hit }}</h5>
                <h5>{{ board.register_time }}</h5>
              </div>
            </div>
          </div>
        </div>
      </div>
</template>

<style scoped>
img {
  width: auto;
  height: 300px;
}

p {
  margin-top: -5px;
}
</style>