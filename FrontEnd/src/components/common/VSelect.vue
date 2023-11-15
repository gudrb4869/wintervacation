<script setup>
import { ref, watch } from "vue";
const props = defineProps({ selectOption: Array });
const emit = defineEmits(["onKeySelect"]);

const key = ref("");

const onSelect = () => {
  console.log(key.value + "선택!!!");
  emit("onKeySelect", key.value);
};

watch(
  () => props.selectOption.value,
  () => {
    key.value = "";
  },
  { deep: true }
);
</script>

<template>
  <select v-model="key" class="form-select form-select-sm ms-5 me-1 w-50" @change="onSelect">
    <option
      v-for="option in selectOption"
      :key="option.value"
      :value="option.value"
      :disabled="option.value === '' ? true : false"
    >
      {{ option.text }}
    </option>
  </select>
</template>

<style scoped></style>
