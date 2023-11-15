<script setup>
import { ref, onMounted } from "vue";

import VKakaoMap from "@/components/common/VKakaoMap.vue";
import VSelect from "@/components/common/VSelect.vue";

const { VITE_OPEN_API_SERVICE_KEY } = import.meta.env;

const sidoList = ref([]);
const gugunList = ref([{ text: "구군선택", value: "" }]);
const chargingStations = ref([]);
const selectStation = ref({});

const param = ref({
  serviceKey: VITE_OPEN_API_SERVICE_KEY,
  pageNo: 1,
  numOfRows: 20,
  zscode: 0,
});
</script>

<template>
  <div class="container text-center mt-3">
    <div class="alert alert-success" role="alert">전기차 충전소</div>
    <div class="row mb-2">
      <!-- <div class="col d-flex flex-row-reverse">
        <VSelect :selectOption="sidoList" @onKeySelect="onChangeSido" />
      </div>
      <div class="col"><VSelect :selectOption="gugunList" @onKeySelect="onChangeGugun" /></div> -->
    </div>
    <VKakaoMap :stations="chargingStations" :selectStation="selectStation" />
    <table class="table table-hover">
      <thead>
        <tr class="text-center">
          <th scope="col">충전소명</th>
          <th scope="col">충전소ID</th>
          <th scope="col">충전기상태</th>
          <th scope="col">위치</th>
          <th scope="col">위도</th>
          <th scope="col">경도</th>
        </tr>
      </thead>
      <tbody>
        <tr
          class="text-center"
          v-for="station in chargingStations"
          :key="station.statId + station.chgerId"
          @click="viewStation(station)"
        >
          <th>{{ station.statNm }}</th>
          <td>{{ station.statId }}</td>
          <td>{{ station.stat }}</td>
          <td>{{ station.addr }}</td>
          <td>{{ station.lat }}</td>
          <td>{{ station.lng }}</td>
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
