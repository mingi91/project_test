<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp"%>
<div class="container">
	<div class="row">
		<div class="col-12">
			<h1 class="page-header text-center">게시판</h1>
		</div>
	</div>
	<div class="row">
		<div class="col-12">
			<div class="card">
				<div class="card-header">
					게시글 수정
				</div>
				<div class="card-body">
					<form class="modifyForm" action="${ctxPath}/board/modify" method="post">
						<div class="form-group">
							<label>글번호</label>	
							<input class="form-control" name="bno" value="${board.bno}" readonly="readonly"/>
						</div>
						<div class="form-group">
							<label>제목</label>
							<input class="form-control" name="title" value="${board.title}" />
						</div>
						<div class="form-group">
							<label>내용</label>
							<textarea class="form-control" rows="10" name="content">${board.content}</textarea>
						</div>
						<div class="form-group">
							<label>작성자</label>
							<input class="form-control" name="writer" value="${board.writer }" readonly="readonly"/>
						</div>
						<div class="form-group">
							<label>작성일</label>
							<input class="form-control" readonly="readonly" name="regDate" 
								value="<tf:formatDateTime value="${board.regDate}" pattern="yyyy년MM월dd일 HH시mm분"/>">
						</div>
						<div class="form-group">
							<label>수정일</label>
							<input class="form-control" readonly="readonly" name="updateDate" 
								value="<tf:formatDateTime value="${board.updateDate}" pattern="yyyy년MM월dd일 HH시mm분"/>">
						</div>
						<button type="button" data-oper='modify' class="btn btn-light">수정</button>
						<button type="button" data-oper='remove' class="btn btn-danger">삭제</button>
						<button type="button" data-oper='list' class="btn btn-info">목록으로</button>
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					</form>						
				</div>
			</div>
		</div>
	</div> <!-- row end -->
	
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
<%@ include file="../includes/footer.jsp" %>

<!-- 원본 이미지 -->
<div class="modal fade" id="showImage">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
	        <div class="modal-header">
	            <h4 class="modal-title">원본 이미지 보기</h4>
	            <button type="button" class="close" data-dismiss="modal">&times;</button>
	        </div>
	        <div class="modal-body"></div>
        </div>
    </div>
</div>

<script>
let formObj = $('.modifyForm');
let type = '${criteria.type}'
let keyword = '${criteria.keyword}'
let addCriteria = function(){
	formObj.append($('<input/>',{type : 'hidden', name : 'pageNum', value : '${criteria.pageNum}'}))
		   .append($('<input/>',{type : 'hidden', name : 'amount', value : '${criteria.amount}'}))
	if(type&&keyword){
		formObj.append($('<input/>',{type : 'hidden', name : 'type', value : '${criteria.type}'}))
			.append($('<input/>',{type : 'hidden', name : 'keyword', value : '${criteria.keyword}'}))
	}
}
</script>
<script src="${ctxPath}/resources/js/modify.js"></script>
