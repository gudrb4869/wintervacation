<script setup>
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import { detailArticle } from "@/api/board";


const router = useRouter();
const route = useRoute();

const { article_no } = route.params;
const board = ref({})

const goDetail = () => {
    router.push({ name: "board-main-detail"})
}

onMounted(() => {
    loadDetail();
})

const loadDetail = () => {
    detailArticle(
        article_no,
        ({ data }) => {
            console.log("boardMainDetail {}",data);
            board.value = data; 
        },
        (error) => {
            console.log(error);
        }
    )
}


</script>

<template>
    <div id="containner">
        <div>
            <div style='display: flex; justify-content: space-between; align-items: center;'>
                <h3 style='margin: 0;'>여행후기 공유</h3>
            </div>
            <hr>
        </div>
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
    <h4 style="margin: 0;">{{ board.user_id }}</h4>
    <div style="display: flex; align-items: center;">
        <h5 style="margin-right: 10px;">조회수 :  {{ board.hit }}  |  </h5>
        <h5>{{ board.register_time }}</h5>
    </div>
</div>  
        <section class="py-5 text-center container">
            <div id="carouselExampleInterval" class="carousel slide" data-bs-ride="carousel" bis_skin_checked="1">
                <div class="carousel-inner" bis_skin_checked="1">
                    <div class="carousel-item active" data-bs-interval="3000" bis_skin_checked="1">
                    <svg class="bd-placeholder-img bd-placeholder-img-lg d-block w-100" width="400" height="400" xmlns="http://www.w3.org/2000/svg" role="img" focusable="false">
                        <title>Placeholder</title>
                        <rect width="100%" height="100%" fill="#777"></rect>
                        <text x="50%" y="50%" fill="#555" dy=".3em">First slide</text>
                    </svg>
                    </div>
                    <div class="carousel-item" data-bs-interval="3000" bis_skin_checked="1">
                    <svg class="bd-placeholder-img bd-placeholder-img-lg d-block w-100" width="400" height="400" xmlns="http://www.w3.org/2000/svg" role="img" focusable="false">
                        <title>Placeholder</title>
                        <rect width="100%" height="100%" fill="#666"></rect>
                        <text x="50%" y="50%" fill="#444" dy=".3em">Second slide</text>
                    </svg>
                    </div>
                    <div class="carousel-item" bis_skin_checked="1" data-bs-interval="3000" >
                    <svg class="bd-placeholder-img bd-placeholder-img-lg d-block w-100" width="400" height="400" xmlns="http://www.w3.org/2000/svg" role="img" focusable="false">
                        <title>Placeholder</title>
                        <rect width="100%" height="100%" fill="#555"></rect>
                        <text x="50%" y="50%" fill="#333" dy=".3em">Third slide</text>
                    </svg>
                    </div>
                </div>
                <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleInterval" data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleInterval" data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>
        </section>

        <div style='margin-bottom: 40px;'>
            {{ board.content }}
        </div>
        <h3>댓글</h3>
        <hr>
        <!-- Comments section-->
        <section class="mb-5">
        <div class="card bg-light">
            <div class="card-body">
                <!-- Comment form-->
                <form class="mb-4"><textarea class="form-control" rows="3" placeholder="Join the discussion and leave a comment!"></textarea></form>
                <!-- Comment with nested comments-->
                <div class="d-flex mb-4">
                    <!-- Parent comment-->
                    <div class="flex-shrink-0"><img class="rounded-circle" src="https://dummyimage.com/50x50/ced4da/6c757d.jpg" alt="..." /></div>
                    <div class="ms-3">
                        <div class="fw-bold">작성자</div>
                        댓글
                    </div>
                </div>
            </div>
        </div>
        </section>
    </div>
</template>

<style scoped>

#containner {
  margin-left: 40px;
  margin-right: 40px;
  padding: 20px; /* Add padding here */
}
</style>