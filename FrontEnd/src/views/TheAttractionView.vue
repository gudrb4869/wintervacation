<script setup>
import { ref, onMounted } from "vue";
import { listAttractions } from "@/api/map";
import festivals from "@/assets/festival.json";

import VKakaoMap from "@/components/common/VKakaoMap.vue";

const { VITE_OPEN_API_SERVICE_KEY } = import.meta.env;

let festivalList = [];

const festival = ref({});

const getFestivals = (month) => {
  return festivals.filter((f) => (f.month - month + 12) % 12 < 4);
};

const courses = ref([]);

const attractions = ref([]);
const selectAttraction = ref({});

// const param = ref({
//   serviceKey: VITE_OPEN_API_SERVICE_KEY,
//   pageNo: 1,
//   numOfRows: 20,
//   zscode: 0,
// });

onMounted(() => {
  randomRecommend();
});

const param = ref({
  sido_code: 0,
  gugun_code: 0,
  // content_type_id: [12, 14, 15, 25, 28, 32, 38, 39]
  content_type_id: [12, 32, 39],
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
  console.log(festivalList);
  let length = festivalList.length;
  console.log("길이: " + length);
  let idx = parseInt(Math.random() * length);
  console.log("인덱스: " + idx);
  let f = festivalList[idx];
  param.value.sido_code = f.sido_code;
  param.value.gugun_code = f.gugun_code;
  festival.value = f;
};

const getRecommendAttractions = () => {
  console.log("관광지 정보 api 호출! 22222222222222");
  listAttractions(
    param.value,
    ({ data }) => {
      attractions.value = data;
      console.log(attractions.value);
    },
    (err) => {
      console.log(err);
    }
  );
};

const onChangeAttractions = (val) => {
  attractions.value = val;
};
</script>

<template>
  <div class="text-center mt-3">
    <div class="alert alert-warning" role="alert" @click="getRecommendAttractions">
      <img :src="festival.image" style="width: 100px" />
      {{ festival.title }}이 있는 {{ festival.sido }} {{ festival.gugun }}(으)로 여행을 떠나보는건
      어때요? 해당 지역으로 이동하려면 여기를 클릭하세요.
    </div>
    <VKakaoMap
      :search="true"
      :courses="courses"
      :attractions="attractions"
      :selectAttraction="selectAttraction"
      @onChangeAttractions="onChangeAttractions"
    />
  </div>
</template>

<style scoped>
mark.purple {
  background: linear-gradient(to top, #c354ff 20%, transparent 30%);
}
</style>
