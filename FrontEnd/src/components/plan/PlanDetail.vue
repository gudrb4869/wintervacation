<script setup>
import { ref, onMounted } from "vue";
import { detailPlan } from "@/api/plan";

import VKakaoMap from "@/components/common/VKakaoMap.vue";

import { useRoute, useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";

const memberStore = useMemberStore();
const userInfo = ref(memberStore.userInfo);

const router = useRouter();
const route = useRoute();

const { plan_no } = route.params;

const attractions = ref([]);
const selectAttraction = ref({});

const days = ref(0);

const plan = ref({
  plan_no: 0,
  user_id: userInfo.value.user_id,
  title: "",
  start_date: "",
  end_date: "",
  user_id: userInfo.value.user_id,
  courses: [],
});

onMounted(() => {
  getPlan();
});

const getDateDiff = (start_date, end_date) => {
  let diff = end_date.getTime() - start_date.getTime();
  diff = Math.ceil(diff / (1000 * 60 * 60 * 24) + 1);
  console.log(diff);
  return diff;
};

const getPlan = () => {
  console.log(plan_no + "번 plan 조회!!!");
  detailPlan(
    plan_no,
    ({ data }) => {
      console.log(data);
      plan.value = data;
      days.value = getDateDiff(new Date(plan.value.start_date), new Date(plan.value.end_date));
      let courses = Array.from(Array(days.value), () => []);
      plan.value.courses.forEach((course) => {
        courses[course.date].push(course.attraction);
      });
      plan.value.courses = courses;
      console.log(plan.value);
    },
    (error) => {
      console.log(error);
    }
  );
};

const moveList = () => {
  router.push({ name: "plan-list" });
};
</script>

<template>
  <form @submit.prevent="onSubmit">
    <div class="mb-3">
      <div class="row" style="height: 700px">
        <div class="col-4 mh-100">
          <div class="mb-3">
            <label for="title" class="form-label">제목 : </label>
            <input
              type="text"
              class="form-control"
              id="title"
              v-model="plan.title"
              placeholder="제목..."
              readonly="readOnly"
            />
          </div>
          <div class="mb-3">
            <label for="start_date" class="form-label">여행 시작일 : </label>
            <input
              type="date"
              class="form-control"
              id="start_date"
              v-model="plan.start_date"
              readonly="readOnly"
            />
          </div>
          <div class="mb-3">
            <label for="end_date" class="form-label">여행 종료일 : </label>
            <input
              type="date"
              class="form-control"
              id="end_date"
              v-model="plan.end_date"
              readonly="readonly"
            />
          </div>
          <div class="mb-3" style="height: 450px">
            <div class="overflow-auto mh-100">
              <template v-for="(course, index) in plan.courses" :key="index">
                <div class="border p-3">
                  <h3>{{ index + 1 }}일차</h3>
                  <ul class="list-group">
                    <li class="list-group-item" v-for="element in course">
                      <h6>{{ element.title }}</h6>
                      <span>{{ element.addr }}</span>
                    </li>
                  </ul>
                </div>
              </template>
            </div>
          </div>
        </div>
        <div class="col-8 p-0 mh-100">
          <v-kakao-map
            :attractions="attractions"
            :selectAttraction="selectAttraction"
          ></v-kakao-map>
        </div>
      </div>
    </div>
    <div class="col-auto text-center">
      <button type="button" class="btn mb-3">수정</button>
      <button type="button" class="btn mb-3 ms-1">삭제</button>
      <button type="button" class="btn mb-3 ms-1" @click="moveList">목록으로이동</button>
    </div>
  </form>
</template>

<style scoped></style>
