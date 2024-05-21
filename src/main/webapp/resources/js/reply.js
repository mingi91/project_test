$(function(){
	
	let bnoValue = $('[name="bno"]').val()
	let replyContainer = $('.chat');
	let pageNum = 1; // 기본 페이지 번호
	let paginationWrap = $('.pagination_wrap');  
	let showReplyPage = function(replyCount){
		let endNum = Math.ceil(pageNum/10.0)*10;
		let startNum = endNum - 9;
		let tempEndNum = Math.ceil(replyCount/10.0);
		
		let prev = startNum !=1; 
		let next = endNum < tempEndNum;
		if(endNum>tempEndNum) endNum = tempEndNum;
		
		let pagination = '<ul class="pagination">';
		
		if(prev){ // 이전 버튼 
			pagination += `<li class="page-item">
					<a class="page-link" href="${startNum-1}">이전</a></li>`
		}
		for(let pageLink=startNum; pageLink<= endNum ; pageLink++){ // 페이지 버튼
			let active = (pageNum==pageLink) ? 'active':''; // 현재페이지버튼 활성화
			pagination += `<li class="page-item ${active}">
					<a class="page-link" href="${pageLink}">${pageLink}</a></li>`
		}
		if(next){ // 다음 버튼 
			pagination += `<li class="page-item">
					<a class="page-link" href="${endNum+1}">다음</a></li>`
		}
		pagination += '</ul>'
		paginationWrap.html(pagination);
	} 
	
	let showList = function(page){
		let param = {bno:bnoValue, page : page||1};
		replyService.getList(param,function(replyCount,list){
			
			if(page == -1){ // 글 작성후 마지막 페이지 호출
				pageNum = Math.ceil(replyCount/10.0);
				showList(pageNum); 
				return;
			}
			if(list==null||list.length==0) {
				replyContainer.html('등록된 댓글이 없습니다.');
				return;
			};
		
			let replyList='';
			$.each(list,function(idx,elem){
				replyList += `<li class="list-group-item" data-rno="${elem.rno}" >
					<div class="d-flex justify-content-between">
					  <div class="d-flex">
					    <div class="user_image mr-3" style="width: 75px">
					      <img class="rounded-circle" src="${ctxPath}/resources/images/userImage.png" style="width: 100%">
					    </div>
					    <div class="comment_wrap">
					      <div class="comment_info">
					        <span class="userName badge badge-pill badge-info mr-2">${elem.replyer}</span>
					        <span class="badge badge-dark">${elem.replyDate}</span>
					      </div>
					      <div class="comment_content py-2">${elem.reply}</div>
					    </div>
					  </div>`
					  if(memberId==elem.replyer || auth.includes('ROLE_ADMIN')){
						  replyList += `<div class="reply_modify">
						    <button type="button" class="btn btn-light dropdown-toggle" data-toggle="dropdown">변경</button>
						    <div class="dropdown-menu">						   
						      <a class="dropdown-item" href="modify">수정</a>
						      <a class="dropdown-item" href="delete">삭제</a>
						    </div>
						  </div>`					  
					  }
					replyList+= `</div>
					</li>`				
			});
			replyContainer.html(replyList);
			showReplyPage(replyCount);
		});
	} // showList function end
	showList(1);
	
	// 페이지 이동 이벤트
	paginationWrap.on('click','li a', function(e){
		e.preventDefault();
		let targetPageNum = $(this).attr('href');
		pageNum = targetPageNum;
		showList(pageNum);
	});

	// 댓글 추가 
	$('.submit button').click(function(){
		let reply = { // 입력 데이터 객체  
			bno : bnoValue, // 게시물 번호 
			reply : $('.replyContent').val(), // 내용
			replyer : $('.replyer').html()	// 작성자
		}
		
		replyService.add(reply,function(result){ // 댓글 추가 처리 
			if(result=='success'){
				alert('댓글을 등록하였습니다.');
			} else {
				alert('댓글 등록 실패');
			}
			$('.replyContent').val(''); // 댓글입력창 초기화 
			showList(-1); // 목록 갱신		
		});
	});	
	
	// 댓글 수정 및 삭제 처리 
	$('.chat').on('click','.reply_modify a',function(e){
		e.preventDefault();// a태그  기본동작 금지
		let rno = $(this).closest('li').data('rno'); // 댓글 번호 가져오기
		let operation = $(this).attr('href');// 수정/삭제 동작 결정
		let replyer = $(this).closest('li').find('.userName').text();

		// 작성자가 일치하지 않거나 관리자가 아니면
		if(replyer!=memberId && !auth.includes('ROLE_ADMIN')){
			return;
		}
		
		if(operation=='delete'){ // 삭제 처리 
			replyService.remove(rno,function(result){
				if(result=='success'){
					alert(rno+'번 댓글을 삭제하였습니다.');
					showList(pageNum); // 목록 갱신
				} else {
					alert('댓글 삭제 실패');
				}
			});
			return; 
		}
	
		if(operation=='modify'){ // 수정처리 
					
			let replyUpdateForm = $('.replyWriterForm').clone(); // 댓글쓰기폼 복사.
			replyUpdateForm.attr('class','replyUpdateForm'); // 클래스명 변경
			let updateBtn = replyUpdateForm.find('.submit button').html('수정'); // 수정처리 버튼
			
			let listTag = $(this).closest('li'); // 현재 댓글의 li 태그를 찾음 
			let replyUpdateFormLength = listTag.find('.replyUpdateForm').length; // 댓글수정폼 존재 여부
			if(replyUpdateFormLength>0) { // 댓글수정폼이 추가되어있다면 
				listTag.find('.replyUpdateForm').remove(); // 기존의 수정폼 삭제
				$(this).html('수정'); // 취소 버튼을 수정버튼으로 
				$(this).next().show(); // 삭제 버튼 다시 보이게 
				return;
			} 
			
			// 조회 메소드 호출 :  댓글 조회 후 수정폼에 나타냄 
			replyService.get(rno,function(data){
				replyUpdateForm.find('.replyContent').val(data.reply);
				replyUpdateForm.find('.replyer').html(data.replyer);
			})	
			
			$(this).closest('li').append(replyUpdateForm); // 아래에 추가
			$(this).html('취소'); // 수정 버튼을 취소버튼으로 변경
			$(this).next().hide(); // 삭제 버튼 숨김
			
			updateBtn.click(function(){ // 수정 처리 이벤트 
				let replyVO = { // 수정 처리 메소드 매개값
					rno :  rno,
					reply : replyUpdateForm.find('.replyContent').val() 
				} 
				// 수정 처리  메소드 호출
				replyService.update(replyVO, function(result){
					alert(result);
					showList(pageNum); // 목록 갱신
				});
			})			
			return; 
		}
	}); // 댓글 수정 및 삭제 처리 끝
	
	$.getJSON(`${ctxPath}/board/getAttachList`,{bno:bnoValue},function(attachList){
		let fileList = '';
		$(attachList).each(function(i,e){
			fileList += `<li class="list-group-item" data-uuid="${e.uuid}">
				<div class="float-left">`
			if(e.fileType){ // 이미지 파일인 경우 섬네일 표시
				let filePath = e.uploadPath+"/s_"+e.uuid+"_"+e.fileName; 
				let encodingFilePath = encodeURIComponent(filePath);
				fileList +=`
					<div class="thumnail d-inline-block mr-3">
						<img alt="" src="${ctxPath}/files/display?fileName=${encodingFilePath}">	
					</div>				
				`
			} else {
				fileList +=` 
					<div class="thumnail d-inline-block mr-3" style="width:40px">
						<img alt="" src="${ctxPath}/resources/images/attach.png" style="width: 100%">
					</div>`
			}
			fileList +=		
				`<div class="d-inline-block">
					${e.fileName}
				</div>
				</div>
				<div class="float-right">`
			if(e.fileType){
				fileList += `<a href="${e.uploadPath+"/"+e.uuid+"_"+e.fileName}" class="showIamge">원본보기</a>`
			}else{
				fileList += `<a href="${e.uploadPath+"/"+e.uuid+"_"+e.fileName}" class="download">다운로드</a>`
			} 
			fileList += `		
				</div>
			</li>`			
		});
		$('.uploadResultDiv ul').html(fileList);
	});
	
	// 원본보기 
	$('.uploadResultDiv ul').on('click','.showIamge',function(e){
		e.preventDefault();
		let filePath = $(this).attr('href');
		let imgSrc = `${ctxPath}/files/display?fileName=${filePath}`
		$('#showImage').find('.modal-body').html($('<img>',{src : imgSrc, class : 'img-fluid'}));
		$('#showImage').modal();
	});
	
	// 파일 다운로드 
	$('.uploadResultDiv ul').on('click','.download',function(e){
		e.preventDefault();
		self.location = `${ctxPath}/files/download?fileName=${$(this).attr('href')}`
	});
	
	
});