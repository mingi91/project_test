<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="includes/header.jsp"%>
<style>
	  /* 각각의 사진 슬라이드 스타일 */
	  .mySlides {
	    display: none;
	  }
	  /* 이전/다음 버튼 스타일 */
		.prev, .next {
		  cursor: pointer;
		  position: absolute;
		  top: 50%;
		  width: auto;
		  padding: 16px;
		  margin-top: -22px;
		  color: #444; /* 텍스트 색상 회색으로 변경 */
		  background-color: rgba(0, 0, 0, 0.3); /* 배경색 회색 투명도 추가 */
		  font-weight: bold;
		  font-size: 18px;
		  transition: 0.6s ease;
		  border-radius: 3px;
		  user-select: none;
		  display: block; /* 버튼 보이게 수정 */
		}

	/* 이전 버튼 위치 조정 */
	.prev {
	  left: 5%; /* 페이지 왼쪽 중간으로 이동 */
	}
	
	/* 다음 버튼 위치 조정 */
	.next {
	  right: 5%; /* 페이지 오른쪽 중간으로 이동 */
	}
	
	  /* 버튼 hover 시 배경색 변경 */
	  .prev:hover, .next:hover {
	    background-color: rgba(0, 0, 0, 0.8);
	  }
	</style>
	</head>
	<body>
	<div class="slideshow-container">
	  <!-- 사진 슬라이드 -->
	  <div class="mySlides">
	    <img src="resources/images/slide1.jpg" width="900" height="500" style="margin: 0 auto; display: block;"/>
	  </div>
	
	  <div class="mySlides">
	    <img src="resources/images/slide2.jpg" width="900" height="500" style="margin: 0 auto; display: block;"/>
	  </div>
	
	  <div class="mySlides">
	    <img src="resources/images/slide3.png" width="900" height="500" style="margin: 0 auto; display: block;"/>
	  </div>
	
	  <!-- 이전/다음 버튼 -->
	  <a class="prev" onclick="plusSlides(-1)">&#10094;</a>
	  <a class="next" onclick="plusSlides(1)">&#10095;</a>
	</div>
	
	<script>
	  var slideIndex = 0;
	  showSlides();
	
	  function showSlides() {
	    var i;
	    var slides = document.getElementsByClassName("mySlides");
	    for (i = 0; i < slides.length; i++) {
	      slides[i].style.display = "none";
	    }
	    slideIndex++;
	    if (slideIndex > slides.length) {slideIndex = 1}
	    slides[slideIndex-1].style.display = "block";
	    setTimeout(showSlides, 3000); // 3초마다 슬라이드 변경
	  }
	
	  function plusSlides(n) {
	    showSlides(slideIndex += n);
	  }
	</script>
	</body>
</html>
<%@ include file="includes/footer.jsp"%>