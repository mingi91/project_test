<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp"%>

<div class="container">
	<div class="mt-5">
		<h3>아이디 찾기</h3>
		<div class="form-inline">
			<input type="email" class="form-control col-5 mr-3" placeholder="이메일을 입력하세요">
			<button class="findIdBtn btn btn-dark col-3">확인</button>
		</div>
	</div>
	
	<div class="mt-5">
		<h3>임시 비밀번호 발급</h3>
		<div class="form-inline">
			<input type="email" class="form-control col-5 mr-3" placeholder="이메일을 입력하세요">
			<button class="findPwd btn btn-dark col-3">확인</button>
		</div>
	</div>	 
</div>

<script>
$(function(){
	$('.findPwd').click(function(){
		let email = $(this).prev().val();
		if(email==''){
			alert('이메일을 입력하세요')
			return 
		}
		
		$.ajax({
			url : '${ctxPath}/findMemberPwd', 
			type : 'post',
			data : {email : email}, 
			success : function(result){
				alert(result)
			}, 
			error : function(xhr,error){
				alert(xhr.responseText)
			}
		})
	})
})
</script>