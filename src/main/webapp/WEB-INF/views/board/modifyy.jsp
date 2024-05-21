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
					<form class="modifyForm" action="${ctxPath}/ict/modifyy" method="post">
						<div class="form-group">
							<label>글번호</label>	
							<input class="form-control" name="ino" value="${ict.ino}" readonly="readonly"/>
						</div>
						<div class="form-group">
							<label>제목</label>
							<input class="form-control" name="title" value="${ict.title}" />
						</div>
						<div class="form-group">
							<label>내용</label>
							<textarea class="form-control" rows="10" name="content">${ict.content}</textarea>
						</div>
						<div class="form-group">
							<label>작성자</label>
							<input class="form-control" name="writer" value="${ict.writer }" readonly="readonly"/>
						</div>
						<div class="form-group">
							<label>작성일</label>
							<input class="form-control" readonly="readonly"  name="regDate"
								value="<tf:formatDateTime value="${ict.regDate }" pattern="yyyy년MM월dd일 HH시mm분"/>">
						</div>
						<div class="form-group">
							<label>수정일</label>
							<input class="form-control" readonly="readonly" name="updateDate"  
								value="<tf:formatDateTime value="${ict.updateDate }" pattern="yyyy년MM월dd일 HH시mm분"/>">
						</div>
						<button type="button" data-oper='modifyy' class="btn btn-light">수정</button>
						<button type="button" data-oper='removee' class="btn btn-danger">삭제</button>
						<button id="modifyButton" type="button" data-oper='ict' class="btn btn-info">목록</button>
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					</form>						
				</div>
			</div>
		</div>
	</div>
</div>

<%@ include file="../includes/footer.jsp" %>

<script>
$(function(){
	let formObj = $('form')
	$('button').click(function(){
		let operation =$(this).data('oper')
		formObj.append($('<input/>',{type : 'hidden', name : 'pageNum', value : '${criteriaa.pageNum}'}))
			   .append($('<input/>',{type : 'hidden', name : 'amount', value : '${criteriaa.amount}'}))
		if(operation=='removee'){
			formObj.attr('action','${ctxPath}/board/removee')
			alert("삭제완료")
		} else if (operation=='ict'){
			formObj.attr('action','${ctxPath}/board/ict')
				   .attr('method','get');
		} else if (operation=='modifyy'){
			formObj.attr('action','${ctxPath}/board/modifyy')
			alert("수정완료")
		}		
		formObj.submit();
	});	
})
</script>
