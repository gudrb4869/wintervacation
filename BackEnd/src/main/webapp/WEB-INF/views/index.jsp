<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<link rel="stylesheet" href="${root}/assets/css/index.css" />
</head>
<body>
   	<%@ include file="/WEB-INF/views/include/nav.jsp" %>

    <!-- Carousel -->
    <div class="container-fluid">
      <div class="row">
        <div id="demo" class="carousel slide" data-bs-ride="carousel">
          <!-- Indicators/dots -->
          <div class="carousel-indicators">
            <button
              type="button"
              data-bs-target="#demo"
              data-bs-slide-to="0"
              class="active"
            ></button>
            <button type="button" data-bs-target="#demo" data-bs-slide-to="1"></button>
            <button type="button" data-bs-target="#demo" data-bs-slide-to="2"></button>
            <button type="button" data-bs-target="#demo" data-bs-slide-to="3"></button>
          </div>

          <!-- The slideshow/carousel -->
          <div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-inner">
              <div class="carousel-item active">
                <img src="${root}/assets/img/sea.jpg" class="d-block w-100" alt="sea" />
                <div class="carousel-caption text-black">
                  <h3>
                    코끝을 자극하는 짭잘한 냄새 맡으며 시원한 바닷바람을 맞으니, <br />
                    이 더운 여름도 지금 만큼은 싫지 않아요
                  </h3>
                  <!-- <p>We had such a great time in Porto!</p> -->
                </div>
              </div>
              <div class="carousel-item">
                <img src="${root}/assets/img/forest.jpg" alt="forest" class="d-block w-100" />
                <div class="carousel-caption">
                  <h3>
                    산이 시원한 이유는 나무가 많아서라고 아빠가 알려주셨어요. <br />
                    동물들이 산에 꼭꼭 숨어사는 이유를 알 것 같아요!
                  </h3>
                  <!-- <p>We had such a great time in Porto!</p> -->
                </div>
              </div>
              <div class="carousel-item">
                <img src="${root}/assets/img/camping.jpg" alt="camping" class="d-block w-100" />
                <div class="carousel-caption">
                  <h3>
                    귀를 간지럽히는 풀벌레 소리를 들으며 <br />
                    새빨간 모닥불을 보고 있으니, 다른곳에 온 것 같아요
                  </h3>
                  <!-- <p>We had such a great time in Porto!</p> -->
                </div>
              </div>
              <div class="carousel-item">
                <img src="${root}/assets/img/sky.jpg" alt="sky" class="d-block w-100" />
                <div class="carousel-caption">
                  <h3>
                    도시에서는 안보이던 별들은 도대체 어디 숨어있다가 나타난걸까요?<br />
                    은은하게 빛나는 밤하늘을 보고있자니 빨려 들어갈것 같아요!
                  </h3>
                  <!-- <p>We had such a great time in Porto!</p> -->
                </div>
              </div>
            </div>
            <button
              class="carousel-control-prev"
              type="button"
              data-bs-target="#carouselExampleIndicators"
              data-bs-slide="prev"
            >
              <span class="carousel-control-prev-icon" aria-hidden="true"></span>
              <span class="visually-hidden">Previous</span>
            </button>
            <button
              class="carousel-control-next"
              type="button"
              data-bs-target="#carouselExampleIndicators"
              data-bs-slide="next"
            >
              <span class="carousel-control-next-icon" aria-hidden="true"></span>
              <span class="visually-hidden">Next</span>
            </button>
          </div>
        </div>
      </div>

      <!-- main second -->
      <div class="row contentBox" style="margin: 20px 10px">
        <div class="d-flex justify-content-center">
          <h2 style="margin-top: 8px">이번엔 어디로 가볼까요?</h2>
        </div>
        <div class="row" style="margin: 5px 5px 5px 5px; padding: 0px">
          <div class="col-md-3">
            <div class="card">
              <img src="${root}/assets/img/tower2.jpg" class="card-img-top custom-img" alt="..." />
              <!-- <div class="card-body">
                  <h5 class="card-title">Card title</h5>
                  <p class="card-text">This is a longer card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.</p>
                </div> -->
            </div>
          </div>
          <div class="col-md-3">
            <div class="card">
              <img src="${root}/assets/img/경주.jpg" class="card-img-top custom-img" alt="..." />
              <!-- <div class="card-body">
                  <h5 class="card-title">Card title</h5>
                  <p class="card-text">This is a short card.</p>
                </div> -->
            </div>
          </div>
          <div class="col-md-3">
            <div class="card">
              <img src="${root}/assets/img/제주.jpg" class="card-img-top custom-img" alt="..." />
              <!-- <div class="card-body">
                  <h5 class="card-title">Card title</h5>
                  <p class="card-text">This is a longer card with supporting text below as a natural lead-in to additional content.</p>
                </div> -->
            </div>
          </div>
          <div class="col-md-3">
            <div class="card">
              <img src="${root}/assets/img/부산.jpg" class="card-img-top custom-img" alt="..." />
              <!-- <div class="card-body">
                  <h5 class="card-title">Card title</h5>
                  <p class="card-text">This is a longer card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.</p>
                </div> -->
            </div>
          </div>
        </div>
        <div class="row justify-content-center">
          <button
            id="btn-search"
            class="btn text-black btn-auto-width"
            type="button"
            onclick="location.href='${root}/map/map.jsp'"
          >
            여행가기
          </button>
        </div>
      </div>
    </div>

    <!-- 하단 footer start -->
    <!-- navbar navbar-expand-lg bg-light navbar-light shadow fixed-top -->
    <footer class="navbar navbar-expand-lg navbar-light bg-light shadaw justify-content-center">
      <div class="row">
        <ul class="navbar-nav">
          <li><a href="#" class="nav-link text-secondary">개인정보처리방침</a></li>
          <li><a href="#" class="nav-link text-secondary">이용약관</a></li>
          <li><a href="#" class="nav-link text-secondary">오시는길</a></li>
          <li><label class="nav-link text-secondary">&copy; SSAFY Corp.</label></li>
        </ul>
      </div>
      <!-- 하단 footer end -->
    </footer>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
    