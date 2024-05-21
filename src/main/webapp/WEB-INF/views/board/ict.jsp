<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../includes/header.jsp"%>
<div class="container">
    <div class="row">
        <div class="col-12">
            <h1 class="page-header text-center">ICT 신동향</h1>
        </div>
    </div>
    <div class="row">
        <div class="col-12">
            <div class="card">
                <div class="card-header">
                    <ul class="pagination justify-content-center">
                        <c:if test="${p.prev}">
                            <li class="page-item">
                                <a class="page-link" href="${p.startPage-p.displayPageNum}">이전페이지</a>
                            </li>
                        </c:if>
                        <c:forEach begin="${p.startPage}" end="${p.endPage}" var="pagelink">
                            <li class="page-item ${pagelink == p.criteriaa.pageNum ? 'active':''}">
                                <a href="${pagelink}" class="page-link ">${pagelink}</a>
                            </li>
                        </c:forEach>
                        <c:if test="${p.next}">
                            <li class="page-item">
                               	<a class="page-link" href="${p.endPage+1}">다음페이지</a>
                            </li>
                        </c:if>
                    </ul>
                    <form id="listForm" action="${ctxPath}/board/ict" method="get">
						<input type="hidden" name="pageNum" value="${p.criteriaa.pageNum}">
						<input type="hidden" name="amount" value="${p.criteriaa.amount}">
					</form>
                    
                    <div class="row justify-content-center mt-3">
                        <div class="col-3 d-flex justify-content-center">
                            <a href="${ctxPath}/board/regedit" class="btn btn-outline-primary">등록하기</a>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <c:forEach items="${ictList}" var="ict">
                        <div class="col-lg-4 col-md-6 mb-4">
                            <div class="card h-100">
                            <!-- 이미지 추가 -->
						    <c:if test="${not empty ict.imageList}">
						        <div class="image_wrap" data-ino="${ict.ino}" data-path="${ict.imageList[0].uploadPath}" data-uuid="${ict.imageList[0].uuid}">
						            <img class="card-img-top" alt="..." src="${ctxPath}/${ict.imageList[0].uploadPath}/${ict.imageList[0].uuid}">
						        </div>
						    </c:if>
						
						    <c:if test="${empty ict.imageList}">
						        <div>
						            <img class="card-img-top" src="${ctxPath}/resources/images/noimage.jpg" alt="">
						        </div>
						    </c:if>
							<!-- 디버깅용 출력 -->
							<c:out value="${ict.imageList}" />

                                <div class="card-body">
                                    <td>
                                    	<a class="move" href="${ict.ino}">${ict.title}</a>
                                    </td>    
                                    <p class="card-text">${ict.content}</p>
                                </div>
                                <div class="card-footer">
                                    <small class="text-muted">${ict.writer}</small>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="../includes/footer.jsp"%>

<!-- Modal -->
<div class="modal fade" id="ictModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <!-- Modal Header -->
            <div class="modal-header">
                <h4 class="modal-title">처리 결과</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <!-- Modal body -->
            <div class="modal-body">
                처리가 완료되었습니다.
            </div>
            <!-- Modal footer -->
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal">닫기</button>
            </div>
        </div>
    </div>
</div>

<!-- jQuery 로드 -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<script>
$(function(){
    let result = "${result}";
    let ictForm = $('#listForm'); // 페이지 폼

    // 글쓰기 페이지로 이동
    $('#btnn').click(function(){
        searchCondition(); // 필요한 경우 주석 해제하여 사용
        ictForm.attr('action', '${ctxPath}/board/regedit').submit(); // 필요한 경우 주석 해제하여 사용
        checkModal(result);
    });
    
    // 페이지 이동 
    $('.pagination a').click(function(e){
        e.preventDefault();
        let pageNum = $(this).attr('href');
        ictForm.find('input[name="pageNum"]').val(pageNum)
        ictForm.submit();
    });
    
    // 조회 페이지로 이동 
    $('.move').click(function(e){
        e.preventDefault();
        $('input[name="ino"]').remove();
        let inoValue = $(this).attr('href');
        ictForm.append($('<input/>',{type : 'hidden', name : 'ino', value : inoValue}))
                .attr('action','${ctxPath}/board/geter')
                .submit();
    });
});

</script>
