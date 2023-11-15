<script setup>
const props = defineProps({
  memo: Object,
});

const { memo } = props;

const emits = defineEmits(["deleteMemo"]);

const deleteMemo = () => {
  console.log(memo.memo_no + "번 댓글 삭제 및 부모 컴포넌트 호출");
  emits("deleteMemo", memo.memo_no);
};
</script>

<template>
  <div class="card mb-4">
    <div class="card-body">
      <img
        class="avatar me-2 float-md-start bg-light p-2"
        src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
      />
      <span class="fw-bold">{{ memo.user_id }}</span> <br />
      <span class="text-secondary fw-light">
        {{ memo.memo_time }}
      </span>
      <button type="button" class="btn ms-1" @click="deleteMemo">삭제</button>
      <div class="text-secondary">{{ memo.comment }}</div>
    </div>
  </div>
</template>

<style scoped>
.card:hover {
  filter: brightness(0.99) !important;
}
</style>
