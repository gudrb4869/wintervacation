<script setup>
import { ref, onMounted, watchEffect } from 'vue';
import BoardMainItem from "./item/BoardMainItem.vue";
import BoardMainRecomand from "./item/BoardMainRecomand.vue";
import { useMemberStore } from "@/stores/member";
import { useRouter } from "vue-router";
import { listArticle } from "@/api/board";
import festivals from "@/assets/festival.json";

const router = useRouter();
const memberStore = useMemberStore();
const profile = ref(null);

const goToRegist = () => {
  router.push({ name : "board-main-regist" })
}


const getFestivals = (month) => {
  return festivals.filter((f) => (f.month - month + 12) % 12 < 4);
};

let festivalList = [];

const boardList = ref([]);
const param = ref({
  key: "",
  work: "",
  sort: "recent", // 초기 값은 "최신순 정렬"
});

const getBoardList = () => {
  console.log(param.value.sort);
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


onMounted(() => {
  profile.value = memberStore.userInfo;
  getBoardList();
  randomRecommend();
});


const randomRecommend = () => {
  let current = new Date();
  console.log(current);

  let monthDate = [0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365];

  // 2월 4일 (입춘) => 34
  // 5월 5일 (입하) => 124
  // 8월 7일 (입추) => 218
  // 11월 7일 (입동) => 310
  let currentDate = monthDate[current.getMonth()] + (current.getDate() - 1);
  console.log("현재날짜의 값: " + currentDate);

  if (currentDate >= 34 && currentDate < 124) {
    festivalList = getFestivals(2);
  } else if (currentDate >= 124 && currentDate < 218) {
    festivalList = getFestivals(5);
  } else if (currentDate >= 218 && currentDate < 310) {
    festivalList = getFestivals(8);
  } else {
    festivalList = getFestivals(11);
  }

  let length = festivalList.length;
  let recommendedFestivals = [];

  // 랜덤으로 3번 선택
  for (let i = 0; i < 3; i++) {
    let idx = parseInt(Math.random() * length);
    console.log("인덱스 " + (i + 1) + ": " + idx);
    let f = festivalList[idx];
    recommendedFestivals.push(f);
  }

  // 추천된 축제 리스트를 저장
  festivalList = recommendedFestivals;

};

// param.sort 값이 변경될 때마다 getBoardList 호출
watchEffect(() => {
  getBoardList();
});


</script>

<template>
<main>

  <div id="carouselExampleCaptions" class="carousel slide py-5 bg-light custom-carousel-container h-100" data-bs-ride="carousel">
      <div class="carousel-indicators">
        <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
        <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="1" aria-label="Slide 2"></button>
        <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="2" aria-label="Slide 3"></button>
      </div>
      <div class="carousel-inner">
        <BoardMainRecomand v-for='festival in festivalList' :festival='festival' :key='festival.id'>

        </BoardMainRecomand>
      </div>
      <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
      </button>
      <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
      </button>
  </div>

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

.custom-carousel-container {
    max-width: 800px; /* 원하는 최대 너비로 설정 */
    margin: auto; /* 중앙 정렬을 위해 margin을 auto로 설정 */
  }

.carousel-indicators [data-bs-target] {
box-sizing: content-box;
flex: 0 1 auto;
width: 10px;
height: 10px;
border-radius:5px;
padding: 0;
margin-right: 3px;
margin-left: 3px;
text-indent: -999px;
cursor: pointer;
background-color: white;
background-clip: padding-box;
border: 0;
opacity: .8;
transition: opacity .6s ease;

}

.carousel-caption {
  width : 300px;
  height : 100px;
  position: absolute;
  font-size: 20px;
  top: 70%;
  left: 32%;
    color: #222020;
    text-align: center;
    text-shadow: 0 1px 2px rgba(0, 0, 0, .6);
}



.carousel-indicators .active{
width: 40px;
height: 10px;
background-color: #35c5f0;

}

.wrapText {
  margin-left: 12%;
}
</style>