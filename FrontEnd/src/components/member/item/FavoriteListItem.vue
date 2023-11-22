<script setup>
import { ref } from "vue";

const props = defineProps({
  attraction: Object,
});

const emit = defineEmits(["onChangeFavorite"]);

const onClick = () => {
  console.log(props.attraction.content_id + " 찜목록에서 제거!!!");
  emit("onChangeFavorite", props.attraction.content_id);
};

const path = ref(window.location.origin);
</script>

<template>
  <div class="col">
    <div class="card h-100 text-center">
      <img
        class="card-img-top"
        :src="attraction.image ? attraction.image : `${path}/src/assets/img/no_image.png`"
        alt="이미지가 없습니다!"
      />
      <div class="card-body">
        <h5 class="card-title">{{ attraction.title }}</h5>
        <p class="card-text">{{ attraction.addr }}</p>
        <img :src="`${path}/src/assets/img/heart-red.png`" :width="20" @click="onClick" />
      </div>
    </div>
  </div>
</template>

<style scoped>
.card-img-top {
  height: 10rem;
  object-fit: contain;
}
img:hover {
  cursor: pointer;
}
</style>
