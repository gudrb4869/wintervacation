<script setup>
import { ref } from "vue";

const props = defineProps({
  fileInfos: Object,
  index: Number,
});

const originalName = props.fileInfos.originalFile;
const saveFolder = props.fileInfos.saveFolder;
const saveFile = props.fileInfos.saveFile;

const path = ref("");
path.value =
  "http://localhost/board-api/getImg/" + saveFolder + "/" + originalName + "/" + saveFile;
</script>

<template>
  <div class="carousel-item" :class="{ active: index == 0 }">
    <img
      class="d-block w-100"
      style="height: 600px; object-fit: fill"
      :src="path"
      alt="이미지가 없습니다!"
    />
  </div>
</template>

<style scoped></style>
