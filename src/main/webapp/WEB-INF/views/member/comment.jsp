<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp"%>

<div class="container">
	<ul class="list-group list-group-horizontal justify-content-center mt-3">
		<li class="list-group-item">
			<a href="${ctxPath}/mypage">회원정보 변경</a>
		</li>
		<li class="list-group-item">
			<a href="${ctxPath}/mypage/article">내가 쓴 글</a>
		</li>
		<li class="list-group-item">
			<a href="${ctxPath}/mypage/comment">내가 쓴 댓글</a>
		</li>
	</ul>	
	
	<div class="d-flex justify-content-center">
		<div class="w-50 my-5">
			<div class="jumbotron text-center">
				<h3>내가 쓴 댓글</h3>
			</div>
			<div class="userImage d-flex justify-content-center my-3">
				<label>
					<img class="rounded-circle" src="${ctxPath}/resources/images/userImage.png" style="width: 120px">
				</label>
				<input type="file" name="userImage" id="imageUpload" style=" display: none;width: 100%;height: 100%">
			</div>
<%@ include file="../includes/footer.jsp"%>