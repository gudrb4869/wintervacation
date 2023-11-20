<script setup>
import PlanFormItem from "@/components/plan/item/PlanFormItem.vue";
</script>

<template>
  <div class="container-fluid">
    <div class="row justify-content-center">
      <div class="col-12">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">여행 Plan 등록하기</mark>
        </h2>
      </div>
      <div class="col-12 text-start">
        <plan-form-item type="regist"></plan-form-item>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
