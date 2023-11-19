<script setup>
import { ref, onMounted } from 'vue';
import BoardMainItem from "./item/BoardMainItem.vue";
import { useMemberStore } from "@/stores/member";
import { useRouter } from "vue-router";

const router = useRouter();
const memberStore = useMemberStore();
const profile = ref(null);

onMounted( () => {
    profile.value = memberStore.userInfo;
    console.log(profile.value);
});

const goToRegist = () => {
    router.push({ name : "board-main-regist" })
}

</script>

<template>
<main>

    <section class="py-5 text-center container">
        <div id="carouselExampleInterval" class="carousel slide" data-bs-ride="carousel" bis_skin_checked="1">
      <div class="carousel-inner" bis_skin_checked="1">
        <div class="carousel-item active" data-bs-interval="3000" bis_skin_checked="1">
          <svg class="bd-placeholder-img bd-placeholder-img-lg d-block w-100" width="800" height="400" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: First slide" preserveAspectRatio="xMidYMid slice" focusable="false"><title>Placeholder</title><rect width="100%" height="100%" fill="#777"></rect><text x="50%" y="50%" fill="#555" dy=".3em">First slide</text></svg>
        </div>
        <div class="carousel-item" data-bs-interval="3000" bis_skin_checked="1">
          <svg class="bd-placeholder-img bd-placeholder-img-lg d-block w-100" width="800" height="400" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: Second slide" preserveAspectRatio="xMidYMid slice" focusable="false"><title>Placeholder</title><rect width="100%" height="100%" fill="#666"></rect><text x="50%" y="50%" fill="#444" dy=".3em">Second slide</text></svg>
        </div>
        <div class="carousel-item" bis_skin_checked="1" data-bs-interval="3000" >
          <svg class="bd-placeholder-img bd-placeholder-img-lg d-block w-100" width="800" height="400" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: Third slide" preserveAspectRatio="xMidYMid slice" focusable="false"><title>Placeholder</title><rect width="100%" height="100%" fill="#555"></rect><text x="50%" y="50%" fill="#333" dy=".3em">Third slide</text></svg>
        </div>
      </div>
      <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleInterval" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
      </button>
      <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleInterval" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
      </button>
    </div>
  </section>

  <div class="album py-5 bg-light" bis_skin_checked="1">
      <div class="container" bis_skin_checked="1">
        <div style='display: flex; justify-content: space-between; align-items: center;'>
            <h3 style='margin: 0;'>여행후기 공유</h3>
            <div v-if="profile !== null">
                <!-- TODO : click 이벤트 -->
                <button class="float-right btn" @click="goToRegist"> 나도 공유 하기</button>
            </div>
      </div>
      <hr>

    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3" bis_skin_checked="1">
        <BoardMainItem>

        </BoardMainItem>
        <BoardMainItem>

        </BoardMainItem>
        <BoardMainItem>

        </BoardMainItem>
        <BoardMainItem>

        </BoardMainItem>

    </div>
  </div>
</div>

</main>
</template>

<style scoped>
.slidercontents {
  height: 480px;
  background-image: url(../public/img/slider.jpg);
  background-repeat: no-repeat;
  background-position: center;
  background-size: cover;
  display: flex;
  align-items: center;
}

.wrapText {
  margin-left: 12%;
}
</style>