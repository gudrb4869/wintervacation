<script setup>
import { ref, onMounted, watchEffect } from 'vue';
import BoardMainItem from "./item/BoardMainItem.vue";
import { useMemberStore } from "@/stores/member";
import { useRouter } from "vue-router";
import { listArticle } from "@/api/board";

const router = useRouter();
const memberStore = useMemberStore();
const profile = ref(null);

const goToRegist = () => {
  router.push({ name : "board-main-regist" })
}

const boardList = ref([]);
const param = ref({
  key: "",
  work: "",
  sort: "recent", // 초기 값은 "최신순 정렬"
});

const getBoardList = () => {
  listArticle(
    param.value,
    ({ data }) => {
      console.log(data);
      boardList.value = data;
    }, 
    (error) => {
      console.log(error);
    }
  );
};

// param.sort 값이 변경될 때마다 getBoardList 호출
watchEffect(() => {
  getBoardList();
});

onMounted(() => {
  profile.value = memberStore.userInfo;
  getBoardList();
});
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
          <div class="d-flex">
            <div class="btn-group" role="group" aria-label="Basic radio toggle button group" style='margin-right: 20px;'>
              <div class="form-check form-check-inline">
                <input v-model="param.sort" class="form-check-input" type="radio" id="inlineRadio1" value="recent">
                <label class="form-check-label" for="inlineRadio1">최신순 정렬</label>
              </div>

              <div class="form-check form-check-inline">
                <input v-model="param.sort" class="form-check-input" type="radio" id="inlineRadio2" value="hit">
                <label class="form-check-label" for="inlineRadio2">조회순 정렬</label>
              </div>
            </div>
            <div v-if="profile !== null">
              <!-- TODO : click 이벤트 -->
              <button class="float-right btn" @click="goToRegist"> 나도 공유 하기</button>
            </div>
          </div>

      </div>
      <hr>

    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3" bis_skin_checked="1">
        <BoardMainItem v-for="board in boardList" :board="board" :key="board.article_no">

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