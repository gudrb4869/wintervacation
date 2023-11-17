<script setup>
import { ref, onMounted } from "vue";
import { listSido, listGugun, listAttractions } from "@/api/map";

import VKakaoMap from "@/components/common/VKakaoMap.vue";
import VSelect from "@/components/common/VSelect.vue";
import VSwitch from "@/components/common/VSwitch.vue";

const { VITE_OPEN_API_SERVICE_KEY } = import.meta.env;

const sidoList = ref([]);
const gugunList = ref([{ text: "구군선택", value: "" }]);
const contentTypeList = ref([
  // { text: "모두", value: "0" },
  { text: "관광지", value: "12" },
  { text: "문화시설", value: "14" },
  { text: "축제공연행사", value: "15" },
  { text: "여행코스", value: "25" },
  { text: "레포츠", value: "28" },
  { text: "숙박", value: "32" },
  { text: "쇼핑", value: "38" },
  { text: "음식점", value: "39" },
]);
const attractions = ref([]);
const selectAttraction = ref({});

// const param = ref({
//   serviceKey: VITE_OPEN_API_SERVICE_KEY,
//   pageNo: 1,
//   numOfRows: 20,
//   zscode: 0,
// });

const param = ref({
  sido_code: 0,
  gugun_code: 0,
  content_type_id: [],
});

onMounted(() => {
  getSidoList();
});

const getSidoList = () => {
  listSido(
    ({ data }) => {
      let options = [];
      options.push({ text: "시도선택", value: "" });
      data.forEach((sido) => {
        options.push({ text: sido.sido_name, value: sido.sido_code });
      });
      sidoList.value = options;
    },
    (err) => {
      console.log(err);
    }
  );
};

const onChangeSido = (val) => {
  listGugun(
    { sido_code: val },
    ({ data }) => {
      let options = [];
      options.push({ text: "구군선택", value: "" });
      data.forEach((gugun) => {
        options.push({ text: gugun.gugun_name, value: gugun.gugun_code });
      });
      gugunList.value = options;
    },
    (err) => {
      console.log(err);
    }
  );
};

const onChangeGugun = (val) => {
  console.log("구군 변경!");
  // getAttractions();
};

const onChangeContentType = (val) => {
  console.log("콘텐츠 변경!");
  console.log(val);
  param.value.content_type_id = val;
  console.log(param.value);
  getAttractions();
};

const getAttractions = () => {
  console.log("관광지 정보 api 호출!");
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

const viewAttraction = (attraction) => {
  console.log("클릭한 관광지로 지도 이동!");
  selectAttraction.value = attraction;
  console.log(selectAttraction.value);
};
</script>

<template>
  <div class="container text-center mt-3">
    <div class="alert alert-success" role="alert">지역별 여행지</div>
    <div class="d-flex justify-content-center mb-3">
      <VSelect v-model="param.sido_code" :selectOption="sidoList" @onKeySelect="onChangeSido" />
      <VSelect v-model="param.gugun_code" :selectOption="gugunList" @onKeySelect="onChangeGugun" />
      <!-- <VSelect
        v-model="param.content_type_id"
        :selectOption="contentTypeList"
        @onKeySelect="onChangeContentType"
      ></VSelect> -->
    </div>
    <div class="d-flex justify-content-evenly mb-3">
      <VSwitch :switchOption="contentTypeList" @onChangeSwitch="onChangeContentType"></VSwitch>
    </div>
    <VKakaoMap :attractions="attractions" :selectAttraction="selectAttraction" />
    <table class="table table-hover table-striped mt-3">
      <thead>
        <tr class="text-center">
          <th scope="col">관광지명</th>
          <th scope="col">관광지유형</th>
          <th scope="col">이미지</th>
          <th scope="col">위도</th>
          <th scope="col">경도</th>
          <th scope="col">주소</th>
        </tr>
      </thead>
      <tbody>
        <tr
          class="text-center"
          v-for="attraction in attractions"
          :key="attraction.content_id"
          @click="viewAttraction(attraction)"
        >
          <th>{{ attraction.title }}</th>
          <td>{{ attraction.content_type_id }}</td>
          <td>{{ attraction.image }}</td>
          <td>{{ attraction.latitude }}</td>
          <td>{{ attraction.longitude }}</td>
          <td>{{ attraction.addr }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
mark.purple {
  background: linear-gradient(to top, #c354ff 20%, transparent 30%);
}
</style>
