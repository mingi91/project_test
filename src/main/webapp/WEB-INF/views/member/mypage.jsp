<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp"%>

<div class="container">
	<ul class="list-group list-group-horizontal justify-content-center mt-3">
		<li class="list-group-item">
			<a href="${ctxPath}/mypage">회원정보변경</a>
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
				<h3>회원정보 변경</h3>
			</div>
			<div class="userImage d-flex justify-content-center my-3">
				<label>
					<img class="rounded-circle" src="${ctxPath}/resources/images/userImage.png" style="width: 120px">
				</label>
				<input type="file" name="userImage" id="imageUpload" style=" display: none;width: 100%;height: 100%">
			</div>
			<form action="${ctxPath}/member/modify" method="post">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<div class="form-group">ID
					<input type="text" name="memberId" class="form-control form-control-lg" readonly="readonly" value="${vo.memberId}">
				</div>
				<div class="form-group">이름
					<input type="text" name="memberName" class="form-control form-control-lg"  value="${vo.memberName}">
				</div>
				<div class="form-group">EMAIL
					<input type="text" name="email" class="form-control form-control-lg"  value="${vo.email}">
				</div>
				<div class="form-group">
					<button class="btn btn-outline-primary btn-lg form-control">정보수정</button>
				</div>
				<div class="form-group">
					<button type="button" class="btn btn-outline-info btn-lg form-control chnagePwdForm">비밀번호변경</button>
				</div>
			</form>
		</div>
	</div>
	
</div> <!--  container end -->

<!-- 비밀번호 변경창 -->
<div class="modal" id="changePwdModal">
    <div class="modal-dialog">
        <div class="modal-content">

            <!-- Modal Header -->
            <div class="modal-header">
            <h4 class="modal-title">비밀번호 변경</h4>
            <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
            	<form>
            		<input type="text" autocomplete="username" style="display: none"/>
		           	<div class="form-group">
		               	현재 비밀번호 : <input type="password" class="form-control currentPwd" autocomplete="new-password">
	               </div>
	               <div class="form-group">
	               		변경할 비밀번호 : <input type="password" class="form-control newPwd" autocomplete="new-password">
	               </div>
               </form>
            </div>
            <div class="modal-footer">
            	<button class="btn btn-outline-info showPwd">비밀번호보기</button>
            	<button class="btn btn-outline-danger chnagePwd">변경</button>
            </div>
        </div>
    </div>
</div>

<%@ include file="../includes/footer.jsp"%>

<script>
let result = "${result}";
$(function(){
	
	console.log(result);
	if(result=='modify'){
		alert('회원정보를 수정하였습니다.')
	}
	
	// 비밀번호 변경 모달
	$('.chnagePwdForm').click(function(){
		$('#changePwdModal').find('input').val('');
		$('#changePwdModal').modal();
	});
	
	// 비밀번호 변경 처리 
	$('.chnagePwd').click(function(){
		$.ajax({
			type : 'post', 
			url : '${ctxPath}/mypage/changePwd',
			data : {
				memberId : $('[name="memberId"]').val(),
				currentPwd : $('.currentPwd').val(),
				newPwd: $('.newPwd').val()
			},
			success : function(result){
				alert(result);
				$('#changePwdModal').modal('hide');
			},
			error : function(xhr, status, er){
				alert(xhr.responseText)
			}
		});
	});
	
})
</script>
<script>
$(function(){
    // 비밀번호 보이기/가리기 기능 추가
    $('.showPwd').click(function(){
        // 현재 비밀번호 입력 필드
        let currentPwdField = $('.currentPwd');
        // 변경할 비밀번호 입력 필드
        let newPwdField = $('.newPwd');
        
        // 현재 비밀번호 입력 필드의 type을 확인하여 보이기/가리기를 토글
        if (currentPwdField.attr('type') === 'password') {
            currentPwdField.attr('type', 'text');
        } else {
            currentPwdField.attr('type', 'password');
        }
        
        // 변경할 비밀번호 입력 필드의 type을 확인하여 보이기/가리기를 토글
        if (newPwdField.attr('type') === 'password') {
            newPwdField.attr('type', 'text');
        } else {
            newPwdField.attr('type', 'password');
        }
        
        // 버튼 텍스트 변경 (보이는/가리는)
        let buttonText = $(this).text();
        if (buttonText === '비밀번호보기') {
            $(this).text('비밀번호가리기');
        } else {
            $(this).text('비밀번호보기');
        }
    });
});
</script>