<script setup>
import { ref, watch, onMounted } from "vue";
import { listAttractions } from "@/api/map";

import VKakaoMap from "@/components/common/VKakaoMap.vue";

import { useRoute, useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
import draggable from 'vuedraggable';

const memberStore = useMemberStore();
const userInfo = ref(memberStore.userInfo);

const router = useRouter();
const route = useRoute();

const props = defineProps({
    type: String,
});

const attractions = ref([])
const selectAttraction = ref({})

const days = ref(0);

const plan = ref({
    plan_no: 0,
    user_id: userInfo.value.user_id,
    title: "",
    start_date: "",
    end_date: "",
});

const courses = ref([])

const isDisabled = ref(true);

onMounted(() => {
})

watch(
    () => plan.value.start_date,
    (value) => {

        if (value === '') {
            plan.value.end_date = '';
            days.value = 0;
            isDisabled.value = true;
            return;
        }

        if (isDisabled.value) {
            isDisabled.value = false;
            return;
        }

        let start_date = new Date(value);
        let end_date = new Date(plan.value.end_date);
        days.value = getDateDiff(start_date, end_date);
        console.log('days: ' + days.value);
    }
)

const getAttractions = () => {
    console.log("관광지 정보 api 호출!");
    listAttractions(
        {sido_code: 5, gugun_code: 1, content_type_id: 39},
        ({ data }) => {
            attractions.value = data;
            console.log(attractions.value);
        },
        (err) => {
            console.log(err);
        }
    );
};

watch(
    () => plan.value.end_date,
    (newValue, oldValue) => {

        if (newValue === '') {
            days.value = 0;
            return;
        }
        let start_date = new Date(plan.value.start_date);
        let end_date = new Date(newValue);
        console.log('시작일')
        console.log(start_date)
        console.log('종료일')
        console.log(end_date)

        console.log(start_date > end_date);
        if (start_date > end_date) {
            alert("여행 종료일은 여행 시작일보다 빠를 수 없습니다.");
            plan.value.end_date = oldValue;
        } else {
            console.log('여행 일정 생성 성공')
            days.value = getDateDiff(start_date, end_date);
            courses.value = Array.from(Array(days.value), () => [{id: 1, content_id: "zzz"}]);
            console.log('days: ' + days.value);
            console.log(courses.value);
        }
    }
)

const getDateDiff = (start_date, end_date) => {
    let diff = end_date.getTime() - start_date.getTime()
    diff = Math.ceil(diff / (1000 * 60 * 60 * 24) + 1)
    console.log(diff);
    return diff;
}

// const subjectErrMsg = ref("");
// const contentErrMsg = ref("");
// watch(
//   () => article.value.subject,
//   (value) => {
//     let len = value.length;
//     if (len == 0 || len > 30) {
//       subjectErrMsg.value = "제목을 확인해 주세요!!!";
//     } else subjectErrMsg.value = "";
//   },
//   { immediate: true }
// );
// watch(
//   () => article.value.content,
//   (value) => {
//     let len = value.length;
//     if (len == 0 || len > 500) {
//       contentErrMsg.value = "내용을 확인해 주세요!!!";
//     } else contentErrMsg.value = "";
//   },
//   { immediate: true }
// );

const onSubmit = () => {
    //   if (subjectErrMsg.value) {
    //     alert(subjectErrMsg.value);
    //   } else if (contentErrMsg.value) {
    //     alert(contentErrMsg.value);
    //   } else {
    props.type === "regist" ? writePlan() : updatePlan();
    //   }
};

const writePlan = () => {
    console.log("Plan 등록!!!", plan.value);
    //   registArticle(
    //     article.value,
    //     (response) => {
    //       console.log(response);
    //       moveList();
    //     },
    //     (error) => {
    //       console.log(error);
    //     }
    //   );
};

const updatePlan = () => {
    console.log(plan.value.article_no + "번 Plan 수정!!!");
    //   modifyArticle(
    //     article.value,
    //     (response) => {
    //       console.log(response);
    //       moveList();
    //     },
    //     (error) => {
    //       console.log(error);
    //     }
    //   );
};

const moveList = () => {
    router.push({ name: "plan-list" });
};
</script>

<template>
    <form @submit.prevent="onSubmit">
        <div class="mb-3">
            <label for="title" class="form-label">제목 : </label>
            <input type="text" class="form-control" id='title' v-model="plan.title" placeholder="제목..." />
        </div>
        <div class="mb-3">
            <div class='row'>
                <div class='col-3'>
                    <div class='mb-3'>
                        <label for="start_date" class="form-label">여행 시작일 : </label>
                        <input type="date" class="form-control" id="start_date" v-model="plan.start_date" />
                    </div>
                    <div class="mb-3">
                        <label for="end_date" class="form-label">여행 종료일 : </label>
                        <input type="date" class="form-control" id="end_date" v-model="plan.end_date"
                            :disabled='isDisabled' @change='getAttractions'/>
                    </div>
                    <template v-for="day in days" :key="day">
                        <h1>{{ day }}일차</h1>
                        <draggable class='dragArea list-group' v-model="courses[day - 1]" group='course' item-key='content_id'>
                            <template #item='{element, index}'>
                                <div class='list-group-item'>{{ element.content_id }} {{ index }}</div>
                            </template>
                        </draggable>
                    </template>
                </div>
                <div class='col-6'>
                    <v-kakao-map :attractions="attractions" :selectAttraction="selectAttraction"></v-kakao-map>
                </div>
                <div class="col-3">
                    <h1>여행지 검색 목록</h1>
                    <!-- <draggable class='dragArea list-group' v-model="attractions" group="" item-key='content_id'>
                        <template #item='{element, index}'>
                            <div class='list-group-item'>{{ element.title }} {{element.addr}} {{ index }}</div>
                        </template>
                    </draggable> -->
                </div>
            </div>
        </div>
        <div class="col-auto text-center">
            <button type="submit" class="btn mb-3" v-if="type === 'regist'">등록</button>
            <button type="submit" class="btn mb-3" v-if="type === 'modify'">수정</button>
            <button type="button" class="btn mb-3 ms-1" @click="moveList">목록으로이동</button>
        </div>
    </form>
</template>

<style scoped></style>