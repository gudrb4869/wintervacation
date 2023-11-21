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

const originalName = props.board.fileInfos[0].originalFile;
const saveFolder = props.board.fileInfos[0].saveFolder;
const saveFile = props.board.fileInfos[0].saveFile;

const path = ref("");

onMounted(() => {
  getImges();
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
      <div class="col" bis_skin_checked="1" style='height: 500px;'>
        <div class="card shadow-sm" bis_skin_checked="1" @click='goDetail'>
          <img class="bd-placeholder-img card-img-top" :src="path" alt="이미지가 없습니다!">
          <div class="card-body" bis_skin_checked="1">
            <p class="card-text" >{{ board.content }}</p>
            <div class="d-flex justify-content-between align-items-center" bis_skin_checked="1">
              <div class="btn-group text-truncate" style="max-width: 150px;" bis_skin_checked="1">
                <p>{{ board.user_id }}</p>
              </div>
              <small class="text-muted">{{ board.register_time }}</small>
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
</style>