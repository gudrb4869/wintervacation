<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <%@ include file="/WEB-INF/views/include/head.jsp" %>
    </head>

    <body>
      <%@ include file="/WEB-INF/views/include/nav.jsp" %>

        <!-- 중앙 content start -->
        <div class="container">
          <!-- <div style="height: 70px"></div> -->
          <div class="row">
            <div class="col-md-12 ">
              <div class='d-flex justify-content-center'>
                <h2>지역별 여행지</h2>
              </div>
              <!-- 관광지 검색 start -->
              <form class="d-flex my-3" onsubmit="return false;" role="search">
                <select id="search-area" class="form-select me-2">
                  <option value="0" selected>지역</option>
                </select>
                <select id="gugun" class="form-select me-2">
                  <option value="">구군선택</option>
                </select>
                <select id="search-content-id" class="form-select me-2">
                  <option value="0" selected>관광지 유형</option>
                  <option value="12">관광지</option>
                  <option value="14">문화시설</option>
                  <option value="15">축제공연행사</option>
                  <option value="25">여행코스</option>
                  <option value="28">레포츠</option>
                  <option value="32">숙박</option>
                  <option value="38">쇼핑</option>
                  <option value="39">음식점</option>
                </select>
                <button id="btn-search" class="btn text-black" type="button">검색</button>
              </form>

              <!-- kakao map start -->
              <div id="map" class="mt-3" style="width: 100%; height: 400px"></div>
              <!-- kakao map end -->
              <div class="row mt-3">
                <table class="table table-striped table-hover" style="display: none">
                  <thead class="table-dark">
                    <tr class="text-center">
                      <th>대표이미지</th>
                      <th>관광지명</th>
                      <th>주소</th>
                      <th title="하루 전 오후 12시 기준입니다.">기온</th>
                      <th title="하루 전 오후 12시 기준입니다.">강수량</th>
                    </tr>
                  </thead>
                  <tbody id="trip-list"></tbody>
                </table>
              </div>
              <!-- 관광지 검색 end -->
            </div>
          </div>
          <!-- 중앙 content end -->

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
        </div>

        <script type="text/javascript"
          src="//dapi.kakao.com/v2/maps/sdk.js?appkey=156332de7fc90f3e70ad6f9b9d13c3df&libraries=services,clusterer,drawing"></script>

        <script type="text/javascript">

          // index page 로딩 후 전국의 시도 설정.      
          fetch(`${root}/map/sido`)
            .then((response) => response.json())
            .then((data) => makeSido(data));

          function makeSido(sidos) {
            let sel = document.getElementById("search-area");

            sidos.forEach((sido) => {
              let opt = document.createElement("option");
              opt.setAttribute("value", sido.sido_code); // 옵션 element 에 value 부여 
              opt.appendChild(document.createTextNode(sido.sido_name));
              sel.appendChild(opt);
            })
          }

          // 시/도 클릭 이벤트 생성
          document.querySelector("#search-area").addEventListener("change", function () {
            document.querySelector("#gugun").length = 1;
            let sido_code = document.querySelector("#search-area").value; // 선택한 시도 값

            fetch(`${root}/map/gugun?sido_code=` + sido_code)
              .then((response) => response.json())
              .then((data) => makeGugun(data));

            function makeGugun(guguns) {
              let sel = document.querySelector("#gugun");
              guguns.forEach((gugun) => {
                let opt = document.createElement("option");
                opt.setAttribute("value", gugun.gugun_code);
                opt.appendChild(document.createTextNode(gugun.gugun_name));
                sel.appendChild(opt);
              });
            }
          });

          // 검색 버튼을 누르면..
          // 지역(시, 군구) 유형 얻기.
          // 위 데이터를 가지고 공공데이터에 요청.
          // 받은 데이터를 이용하여 화면 구성.

          document.getElementById("btn-search").addEventListener("click", () => {
            let sido_code = document.getElementById("search-area").value; // 지역코드
            let gugun_code = document.getElementById("gugun").value; // 구군 
            let content_type_id = document.getElementById("search-content-id").value;

            let searchUrl = `${root}/map/search?sido_code=`;
            searchUrl += sido_code;
            searchUrl += "&gugun_code=";
            searchUrl += gugun_code;
            searchUrl += "&content_type_id=";
            searchUrl += content_type_id;

            fetch(searchUrl)
              .then((response) => response.json())
              .then((data) => makeData(data));

          });

          async function makeData(trips) { // 하단 테이블 생성 
            document.querySelector("table").setAttribute("style", "display:");
            let weather = await getWeather(trips);
            makeList(trips, weather); // 지역 정보, 날씨 정보를 이용하여 리스트 생성 
          }

          async function getWeather(trips) {
            const serviceKey = "QaEXVgdd1N4MXpntkk1e5sjwWXt%2FGGhjeapJpkMXQqbZDASWHsFOVGPzNSW0NSJDIK0vKM%2B268HaoK2adiseXg%3D%3D";

            var currentDate = new Date(); // 현재 날짜와 시간을 가져옵니다.
            currentDate.setDate(currentDate.getDate() - 1); // 하루 전 날짜 사용 

            // 년, 월, 일을 추출합니다.
            var year = currentDate.getFullYear();
            var month = (currentDate.getMonth() + 1).toString().padStart(2, '0'); // 월은 0부터 시작하므로 1을 더하고, 두 자리 숫자로 포맷팅합니다.
            var day = currentDate.getDate().toString().padStart(2, '0'); // 일을 두 자리 숫자로 포맷팅합니다.

            // "yyyyMMdd" 형식으로 날짜를 조합합니다.
            var formattedDate = year + month + day;
            let json = "";
            let weatherUrl = "https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst?serviceKey=" +
              serviceKey + "&pageNo=1&numOfRows=30&dataType=json&base_date=" + formattedDate + "&base_time=1100&nx=";
            let weatherUrl2 = "&ny=";

            let fetchPromises = [];

            // 위도, 경도 를 정수로 변환 
            let nx, ny, url;
            let flag = 0;

            nx = parseInt(trips[0].latitude);
            ny = parseInt(trips[0].longitude);
            url = weatherUrl + nx + weatherUrl2 + ny;

            fetchPromises.push(
              fetch(url, { method: "GET" })
                .then((response) => response.json())
                .then(data => {
                  let rain = JSON.stringify(data.response.body.items.item[12].fcstValue);
                  let temperature = JSON.stringify(data.response.body.items.item[24].fcstValue);

                  json += "{\"rain\":" + rain + ",";
                  json += "\"temperature\":" + temperature + "},";
                })
            );

            return await Promise.all(fetchPromises)
              .then(() => {
                json = json.slice(0, -1);
                return JSON.parse(json);
              })
          }

          var positions; // marker 배열.
          // 지역 정보, 날씨 정보를 이용하여 리스트 생성 
          function makeList(trips, weather) {
            let tripList = ``;
            positions = [];

            trips.forEach((trip) => {
              tripList += "<tr onclick=\"moveCenter(";
              tripList += trip.latitude + ",";
              tripList += trip.longitude
              tripList += ");\"";
              tripList += ">";
              tripList += "<td>";
              tripList += "<img src=\"";
              tripList += trip.first_image ? trip.first_image : `${root}/assets/img/no_image.png`;
              tripList += "\"";
              tripList += " width=\"100px\"></td>";
              tripList += "<td>";
              tripList += trip.title
              tripList += "</td>";
              tripList += "<td>";
              tripList += trip.addr1;
              tripList += " ";
              tripList += trip.addr2;
              tripList += "</td>";
              tripList += "<td>";
              tripList += weather.temperature;
              tripList += "</td>";
              tripList += "<td>";
              tripList += weather.rain;
              tripList += "</td></tr>";

              let markerInfo = {
                title: trip.title,
                latlng: new kakao.maps.LatLng(trip.latitude, trip.longitude),
              };
              positions.push(markerInfo);
            });

            document.getElementById("trip-list").innerHTML = tripList;
            displayMarker();
          }


          // 카카오지도
          var mapContainer = document.getElementById("map"), // 지도를 표시할 div
            mapOption = {
              center: new kakao.maps.LatLng(37.500613, 127.036431), // 지도의 중심좌표
              level: 5, // 지도의 확대 레벨
            };

          // 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
          var map = new kakao.maps.Map(mapContainer, mapOption);

          let markers = [];
          
          function displayMarker() {
        	  markers.forEach((marker) => {
        		  marker.setMap(null);
        	  })
        	  markers = [];
            // 마커 이미지의 이미지 주소입니다
            var imageSrc = `${root}/assets/img/marker.png`;

            positions.forEach((position) => {
              // 마커 이미지의 이미지 크기 입니다
              var imageSize = new kakao.maps.Size(30, 35);

              // 마커 이미지를 생성합니다
              var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

              // 마커를 생성합니다
              var marker = new kakao.maps.Marker({
                map: map, // 마커를 표시할 지도
                position: position.latlng, // 마커를 표시할 위치
                title: position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
                image: markerImage, // 마커 이미지
              });
              
              markers.push(marker);

              // 마커에 표시할 인포윈도우를 생성합니다 
              var infowindow = new kakao.maps.InfoWindow({
                content: '<div style="padding:5px;">' + position.title + '</div>' // 인포윈도우에 표시할 내용
              });

              // 마커에 mouseover 이벤트와 mouseout 이벤트를 등록합니다
              // 이벤트 리스너로는 클로저를 만들어 등록합니다 
              // for문에서 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
              kakao.maps.event.addListener(marker, 'mouseover', makeOverListener(map, marker, infowindow));
              kakao.maps.event.addListener(marker, 'mouseout', makeOutListener(infowindow));

            });

            // 첫번째 검색 정보를 이용하여 지도 중심을 이동 시킵니다
            map.setCenter(positions[0].latlng);
          }

          // 인포윈도우를 표시하는 클로저를 만드는 함수입니다 
          function makeOverListener(map, marker, infowindow) {
            return function () {
              infowindow.open(map, marker);
            };
          }

          // 인포윈도우를 닫는 클로저를 만드는 함수입니다 
          function makeOutListener(infowindow) {
            return function () {
              infowindow.close();
            };
          }

          function moveCenter(lat, lng) {
            map.setCenter(new kakao.maps.LatLng(lat, lng));
          }
        </script>

        <%@ include file="/WEB-INF/views/include/footer.jsp" %>