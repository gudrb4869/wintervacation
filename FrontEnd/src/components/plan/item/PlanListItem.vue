<script setup>
defineProps({
  plan: Object,
});
</script>

<template>
  <div class="col-md-4">
    <div class="card mb-4 shadow-sm">
      <div class="card-body">
        <router-link
          :to="{ name: 'plan-view', params: { plan_no: plan.plan_no } }"
          class="text-decoration-none"
        >
          <h5 class="card-title context">{{ plan.title }}</h5>
        </router-link>
        <p class="card-text">{{ plan.user_id }}</p>
        <div class="d-flex justify-content-between align-items-center">
          <small class="text-muted">{{ plan.start_date }} ~ {{ plan.end_date }}</small>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
