<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp"%>
<div class="container">
	<div class="row">
		<div class="col-12">
			<h1 class="page-header text-center">ICT신동향</h1>
		</div>
	</div>
	<div class="row">
		<div class="col-12">
			<div class="card">
				<div class="card-header">
					게시물
				</div>
				<div class="card-body">
					<div class="form-group">
					<label>글번호</label>	
						<input class="form-control" name="ino" value="${ict.ino}" readonly="readonly"/>
					</div>
					<div class="form-group">
						<label>제목</label>
						<input class="form-control" name="title" value="${ict.title}" readonly="readonly"/> 
					</div>
					<div class="form-group">
						<label>내용</label>
						<textarea class="form-control" rows="10" name="content" readonly="readonly">${ict.content}</textarea>
					</div>
					<div class="form-group">
						<label>작성자</label>
						<input class="form-control" name="writer" value="${ict.writer}" readonly="readonly"/>
					</div>
					<div class="getBtns">
						<sec:authorize access="isAuthenticated() and principal.username== #ict.writer or hasRole('ROLE_ADMIN')">
							<button data-oper='modify' class="btn btn-light modifyy">수정하기</button>
						</sec:authorize>
						<button data-oper='ict' class="btn btn-info ict">목록으로</button>
					</div>						
				</div>
			</div>
		</div>
	</div>
	
	<div class="row my-5">
		<div class="col-lg-12">
			<div class="card">
				<div class="card-header">
					<h4>첨부 파일</h4>
				</div>
				<div class="card-body">
					<div class="uploadResultDiv mt-3"> <!-- 파일업로드 결과 보여주기  -->
						<ul class="list-group"></ul>
					</div>
				</div> <!-- card-body -->
			</div> <!-- caard end -->
		</div> <!-- col end -->
	</div><!-- row end -->
</div> <!-- end container -->

<form>
	<input type="hidden" name="ino" id="ino" value="${ict.ino }">	
</form>

<%@ include file="../includes/footer.jsp" %>

<script>
console.log('geter.js');
$(function(){
	
	// 목록 or 수정 페이지로
	let form = $('form')
	$('.getBtns button').click(function(){
		let operration = $(this).data('oper');
		let type = '${criteriaa.type}'
		let keyword = '${criteriaa.keyword}'
		
		form.append($('<input/>',{type : 'hidden', name : 'pageNum', value : '${criteriaa.pageNum}'}))
			.append($('<input/>',{type : 'hidden', name : 'amount', value : '${criteriaa.amount}'}))
			.attr('method','get')
			
		if(type&&keyword){
			form.append($('<input/>',{type : 'hidden', name : 'type', value : '${criteriaa.type}'}))
				.append($('<input/>',{type : 'hidden', name : 'keyword', value : '${criteriaa.keyword}'}))
		}
			
		if(operration=='ict'){
			form.find('#ino').remove();
			form.attr('action','${ctxPath}/board/ict')
		} else if(operration=='modify'){
			form.attr('action','${ctxPath}/board/modifyy')
		}
		form.submit();
	});
});
</script>
<script src="${ctxPath}/resources/js/geter.js"></script>
