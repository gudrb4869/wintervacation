<script setup>
import { ref } from "vue";

const props = defineProps({
    fileInfos: Object,
});

console.log("로그 ",props.fileInfos);

const originalName = props.fileInfos.originalFile;
const saveFolder = props.fileInfos.saveFolder;
const saveFile = props.fileInfos.saveFile;

const path = ref("");
path.value = "http://localhost/board-api/getImg/" + saveFolder + "/" + originalName + "/" + saveFile;

console.log(path.value);

</script>

<template>
<div class="carousel-item active" data-bs-interval="5000" bis_skin_checked="1">
    <img class="bd-placeholder-img card-img-top" :src="path" alt="이미지가 없습니다!">
</div>
</template>

<style scoped>
img {
    height: 600px;
}
</style>