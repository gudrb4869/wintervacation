<script setup>
import { ref, onMounted } from "vue";
import { useMemberStore } from "@/stores/member";

const memberStore = useMemberStore();
const profile = ref(null);

const props = defineProps({
  memo: Object,
});

const { memo } = props;

const emits = defineEmits(["deleteMemo"]);

const deleteMemo = () => {
  console.log(memo.memo_no + "번 댓글 삭제 및 부모 컴포넌트 호출");
  emits("deleteMemo", memo.memo_no);
};

onMounted(() => {
    console.log(memberStore.userInfo);
    profile.value = memberStore.userInfo;
    console.log(profile.value);
})

</script>

<template>
    <div class="d-flex mb-4">
        <!-- Parent comment-->
        <div class="flex-shrink-0"><img class="rounded-circle" src="https://dummyimage.com/50x50/ced4da/6c757d.jpg" alt="..." /></div>
        <div class="ms-3">
            <div class="fw-bold">{{ memo.user_id }} | {{ memo.memo_time }}</div> 
            
            {{ memo.comment }}
        </div>
        <div class="ms-3" v-if="profile && memo.user_id === profile.user_id">
            <p @click="deleteMemo">삭제</p>
        </div>

    </div>
</template>

<style scoped>

</style>