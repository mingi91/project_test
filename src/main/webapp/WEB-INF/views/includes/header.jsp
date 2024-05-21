<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal.memberVO" var="authInfo"/>
	<sec:authentication property="principal.memberVO.authList" var="authList"/>
</sec:authorize>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ICT</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<script>
let ctxPath = '${ctxPath}'
let duplicateLogin = '${duplicateLogin}'

let csrfHeaderName = "${_csrf.headerName}"; 
let csrfTokenValue = "${_csrf.token}";
let memberId = "${authInfo.memberId}";
let auth = "${authList}"

$(document).ajaxSend(function(e, xhr, options){
	xhr.setRequestHeader(csrfHeaderName,csrfTokenValue);
})

if(duplicateLogin){ // 이미 로그인 한 경우 
	alert(duplicateLogin);
}

function checkExtension(fileName, fileSize){
	let regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");	
	let maxSize = 10485760; // 10MB
	if(fileSize > maxSize) {
		alert('파일크기는 최대 10MB까지 업로드 가능합니다.');
		return false; 
	}
	
	if(regex.test(fileName)) {
		alert('해당 종류의 파일은 업로드 할 수 없습니다.');
		return false; 
	}
	return true;
}	
</script>
</head>
<body>
<nav class="navbar navbar-expand-sm bg-light justify-content-between">
    <ul class="navbar-nav">
        <li class="nav-item">
            <a class="nav-link" href="${ctxPath == '' ? '/': ctxPath}">HOME</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${ctxPath}/board/ict">ICT 신동향</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${ctxPath}/board/list">자유게시판</a>
        </li>
    </ul>
	<ul class="navbar-nav">
		<sec:authorize access="isAnonymous()">
		<li class="nav-item">
			<a class="nav-link" href="${ctxPath}/join/step1">회원가입</a>
		</li>
		<li class="nav-item">
	        <a class="nav-link" href="${ctxPath}/login">로그인</a>
	    </li>
	    </sec:authorize>
		<sec:authorize access="isAuthenticated()">
		<li class="nav-item">
		  <a class="nav-link" href="${ctxPath}/mypage">마이페이지</a>
		</li>
		<li class="nav-item">
		  <a class="nav-link logout" href="${ctxPath}/logout">로그아웃</a>
		</li>
		</sec:authorize>
	</ul>
</nav>

<script>
$(function(){
	$('.logout').click(function(e){
		e.preventDefault();
		let form = $('<form>',{action:$(this).attr('href'), method:'post'});
		form.append($('<input>',{type:'hidden',name:'${_csrf.parameterName}', value:'${_csrf.token}'}))
			.appendTo('body')
			.submit();
	});
})
</script>
