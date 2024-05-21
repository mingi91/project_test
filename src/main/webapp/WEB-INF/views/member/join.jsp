<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp"%>

<div class="container">
	<div class="row my-5">
		<div class="col-2">
			<ul class="list-group">
				<li class="list-group-item">이용약관</li>
				<li class="list-group-item">이메일 인증</li>
				<li class="list-group-item active">회원가입작성</li>
			</ul>		
		</div>
		<div class="col-6 mx-auto">
			<form:form action="${ctxPath}/member/join" modelAttribute="memberVO">
				<h1 class="text-center py-3">회원가입</h1>
				<div class="form-group row">
					<div class="col-9">
						<form:input class="form-control" path="memberId" placeholder="아이디"/>
					</div>
					<div class="col-3">
						<button type="button" class="btn btn-outline-info form-control idCheck">ID중복확인</button>
					</div>
				</div>
				<div class="form-group">
					<form:input class="form-control"  path="memberName" placeholder="이름"/>
				</div>
				<div class="form-group">
					<form:input class="form-control" path="email" placeholder="이메일" readonly="true"/>
				</div>
				<div class="form-group">
					<form:password class="form-control"  path="memberPwd" placeholder="비밀번호"/>
				</div>
				<button type="button" class="form-control btn btn-outline-primary join" >회원가입</button>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			</form:form>
		</div>
	</div>
</div>
<script>
$(function(){
	let idCheckFlag = false; 
	$('.idCheck').click(function(){
		let idInput = $('#memberId')
		let memberId = $('#memberId').val();
		
		if(idInput.attr('readonly')){ // 이미 값이 입력된 경우 
			idInput.attr('readonly',false);
			idInput.focus();
			$(this).html('ID중복확인');
			idCheckFlag = false;
			return; 
		}
		
		if(memberId=='') {
			alert('아이디를 입력하세요')
			return; 			
		}
		
		$.ajax({ // 중복검사 
			type : 'post', 
			url : '${ctxPath}/member/idCheck',
			data : { memberId : memberId },
			async : false,
			success : function(result){
				if(result){ // 사용할 수 있는 경우
					alert('사용할 수 있는 아이디 입니다.')
					idCheckFlag = true;
					$('.idCheck').html('변경');
					idInput.attr('readonly',true);
				} else { // 중복되는 경우 
					alert('사용할수 없는 아이디입니다.');
					idInput.focus();
				}
			}
		});
	});
	
	$('.join').click(function(){
		if(!idCheckFlag){
			alert('ID 중복체크 바람');
			return;
		} 
		$('#memberVO').submit(); 
	});
});

</script>