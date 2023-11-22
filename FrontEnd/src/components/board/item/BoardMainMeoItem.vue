<script setup>
import { ref, watch } from 'vue';
import { useMemberStore } from "@/stores/member";

const props = defineProps({
    article_no: String,
});

const memberStore = useMemberStore();

const article_no = props.article_no;
const user_id = memberStore.userInfo.user_id;

const emits = defineEmits(['registMemo']);


const memo = ref({
  article_no: article_no,
  comment: "",
  memo_no: 0,
  memo_time: "",
  user_id: user_id,
});

const commentErrMsg = ref("");
watch(
  () => memo.value.comment,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 500) {
      commentErrMsg.value = "내용을 확인해 주세요!!!";
    } else commentErrMsg.value = "";
  },
  { immediate: true }
);

const onSubmit = () => {
  if (commentErrMsg.value) {
    alert(commentErrMsg.value);
  } else {
    console.log('댓글 쓰러 가자!!');
    console.log(memo.value);
    emits('registMemo', memo.value);
    memo.value.comment = '';
  }
};

</script>

<template>
    <form class="mb-4" @submit.prevent="onSubmit">
        <textarea class="form-control" rows="3" v-model='memo.comment' placeholder="댓글을 입력하세요!">
        </textarea>
        <button type="submit" class="btn mb-3">등록</button>
    </form>
</template>

<style scoped>
button {
    margin-top: 20px;
}
</style>