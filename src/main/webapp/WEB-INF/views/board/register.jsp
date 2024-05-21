<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:set var="ctxPath" value="${pageContext.request.contextPath}" />
<%@ include file="../includes/header.jsp"%>
<div class="container">
	<div class="row">
		<div class="col-12">
			<h1 class="page-header">게시판</h1>
		</div>
	</div>
	
	<div class="row">
		<div class="col-12">
			<div class="card">
				<div class="card-header">게시글작성</div>
				<div class="card-body">
					<form action="${ctxPath}/board/register" method="post">
						<div class="form-group">
							<label>제목</label>
							<input class="form-control" name="title"/>
						</div>
						<div class="form-group">
							<label>내용</label>
							<textarea class="form-control" rows="15" name="content"></textarea>
						</div>
						<div class="form-group">
							<label>작성자</label>
							<input class="form-control" name="writer" value="${authInfo.memberId}" readonly="readonly"/>
						</div>
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<button type="button" class="btn btn-outline-primary register">등록하기</button>
						<button type="button" class="btn btn-outline-info" onclick="location.href='${ctxPath}/board/list?pageNum=1&amount=10'">게시판 목록</button>             			
					</form>
				</div>
			</div>
		</div>
	</div> <!-- row end-->
	
	<div class="row my-5">
		<div class="col-lg-12">
			<div class="card">
				<div class="card-header">
					<h4>파일 첨부</h4>
				</div>
				<div class="card-body">
					<div class="uploadDiv">
						<input type="file" name="uploadFile" multiple="multiple">
					</div>
					<div class="uploadResultDiv mt-3"> <!-- 파일업로드 결과 보여주기  -->
						<ul class="list-group"></ul>
					</div>
				</div> <!-- card-body -->
			</div> <!-- card end -->
		</div> <!-- col end -->
	</div><!-- row end -->
</div>

<input type="hidden" name="pageNum" value="${param.pageNum }" >
<input type="hidden" name="amount" value="${param.amount }" >
<input type="hidden" name="type" value="${param.type }" >
<input type="hidden" name="keyword" value="${param.keyword }" >

<script src="${ctxPath}/resources/js/register.js"></script>

<%@ include file="../includes/footer.jsp" %>