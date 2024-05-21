<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="java.io.BufferedReader, java.io.FileReader, java.io.FileNotFoundException, java.io.IOException" %>

<div class="container">
	<div class="row my-5">
		<div class="col-2">
			<ul class="list-group">
				<li class="list-group-item active">이용약관</li>
				<li class="list-group-item">이메일 인증</li>
				<li class="list-group-item">회원가입작성</li>
			</ul>		
		</div>
		<div class="col-10">
			<form action="${ctxPath}/join/step2" method="post" id="termForm">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<div class="form-group">
					<h4>이용약관</h4>
					<textarea rows="6" class="form-control my-2 bg-light border border-primary" readonly="readonly">
<%
BufferedReader reader1 = null;
try{
    String filePath = application.getRealPath("/resources/text/text1.txt"); 
    reader1 = new BufferedReader(new FileReader(filePath));
    while(true){
        String str = reader1.readLine();
        if(str==null) break;
        out.print(str+"\n");
    }
}
catch(FileNotFoundException fnfe){
    out.print("파일이 존재 하지 않습니다.");
}
catch(IOException ioe){
    out.print("파일을 읽을수 없습니다.");
}
finally{
    try {
        if(reader1 != null) 
            reader1.close();
    }
    catch(Exception e){}
}
%>
					</textarea>
					<label class="mt-3">
						<input type="checkbox" name=agreement>
						<span>위 이용약관에 동의합니다.</span>
					</label>
				</div>
				<div class="form-group">
					<h4>개인정보 수집 및 이용에 대한 안내</h4>
					<textarea rows="6" class="form-control my-2 bg-light border border-primary" readonly="readonly">
<%
BufferedReader reader2 = null;
try{
    String filePath = application.getRealPath("/resources/text/text2.txt"); 
    reader2 = new BufferedReader(new FileReader(filePath));
    while(true){
        String str = reader2.readLine();
        if(str==null) break;
        out.print(str+"\n");
    }
}
catch(FileNotFoundException fnfe){
    out.print("파일이 존재 하지 않습니다.");
}
catch(IOException ioe){
    out.print("파일을 읽을수 없습니다.");
}
finally{
    try {
        if(reader2 != null) 
            reader2.close();
    }
    catch(Exception e){}
}
%>
					</textarea>
					<label class="mt-3">
						<input type="checkbox" name=agreement>
						<span>위의 개인정보 수집 및 이용에 대한 안내에 동의합니다</span>
					</label>
				</div>
				
				<div class="border border-primary my-5 py-3 text-center">
					<label>					
						<input type="checkbox" class=checkAll>
						<span>위의 이용약관 및 개인정보 수집 및 이용에 대한 안내에 동의합니다.</span>
					</label>	
				</div>
					
				<div class="my-3 text-center">
					<button type="button" class="btn btn-outline-primary nextBtn col-3">다음</button>
					<button type="button" class="btn btn-outline-danger cancelBtn col-3">취소</button>
				</div>
			</form>
		</div> <!-- col-10 end -->
	</div> <!-- row end -->
	
</div> <!-- container end -->

<script>
$(function(){
	let termForm = $('#termForm');
	let agreementCheck = $('[name="agreement"]');
	// 모두 동의
	$('.checkAll').change(function(){
		if($(this).is(':checked')){
			$('[name="agreement"]').prop('checked',true);
		} else {
			$('[name="agreement"]').prop('checked',false);	
		}
	});
	
	// 다음단계로 이동
	$('.nextBtn').click(function(){
		let checkFlag = [];
		$(agreementCheck).each(function(index,element){
			checkFlag.push($(element).is(':checked'));
		})
		
		if(!checkFlag[0]){
			alert('이용약관에 동의해주세요');
			return;
		}
		
		if(!checkFlag[1]){
			alert('개인정보 수집 및 이용에 대한 안내에 동의해주세요');
			return;	
		}
		termForm.submit();
	});
})
</script>
