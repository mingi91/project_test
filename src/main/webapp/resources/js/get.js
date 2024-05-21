$(function(){
	let bnoValue = $('[name="bno"]').val();
	let getForm = $('.getForm');
	let modifyfBtn = getForm.find('.modify');
	let listBtn = getForm.find('.list');
	
	modifyfBtn.click(function(){
		getForm.attr('action',`${ctxPath}/board/modify`)
			.append($('[name="bno"]'))
			.append($('[name="pageNum"]'))
			.append($('[name="amount"]'));
		if(type&&keyword){
			getForm.append($('<input/>',{type:'hidden',name:'type', value: type}))
			   	.append($('<input/>',{type:'hidden',name:'keyword', value: keyword}))	
		}
		getForm.submit();
	});
	
	listBtn.click(function(){
		getForm.attr('action',`${ctxPath}/board/list`)
			   .append($('[name="pageNum"]'))
			   .append($('[name="amount"]'));
		if(type&&keyword){
			getForm.append($('<input/>',{type:'hidden',name:'type', value: type}))
			   	.append($('<input/>',{type:'hidden',name:'keyword', value: keyword}))	
		}
		getForm.submit();
	});
	
	// 첨부파일 목록 조회
	$.getJSON(
		`${ctxPath}/board/getAttachList`,
		{bno : bnoValue},
		function(attachList){
			console.log(attachList);
	});
	
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
		}); // each end
		$('.uploadResultDiv ul').html(fileList);
	}); // getJSON end
	
	// 원본이미지 보기 
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