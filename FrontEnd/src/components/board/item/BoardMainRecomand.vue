<script setup>
const props = defineProps({
  festival: Object,
  index: Number,
});
</script>

<template>
  <div class="carousel-item" :class="{ active: index == 0 }">
    <img :src="festival.image" class="d-block w-100" alt="대체 텍스트" style="min-height: 600px" />
    <div class="carousel-caption d-none d-md-block">
      <h6 class="text_under">
        {{ festival.title }}이 있는 {{ festival.sido }} {{ festival.gugun }}(으)로 여행을 떠나보는건
        어때요?
      </h6>
    </div>
  </div>
</template>

<style scoped>
/* .slidercontents {
  height: 480px;
  background-image: url(../public/img/slider.jpg);
  background-repeat: no-repeat;
  background-position: center;
  background-size: cover;
  display: flex;
  align-items: center;
} */

.carousel-caption {
  width: 600px;
  height: 100px;
  position: absolute;
  font-size: 20px;
  top: 70%;
  left: 15%;
  z-index: 10;
  color: #222020;
  text-align: center;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.6);
}

.text_under {
  position: relative;
}

.text_under::after {
  content: "";
  width: 600px;
  height: 23px;
  background-color: #99fee7;
  position: absolute;
  display: inline-block;
  left: 0;
  bottom: 1px;
  z-index: -1;
}
</style>
