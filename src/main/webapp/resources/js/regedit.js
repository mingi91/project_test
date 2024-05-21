$(function(){
	let uploadResultList = [];
	let showUploadResult = function(attachListt){
		let fileList = '';
		$.each(attachListt,function(i,e){
			fileList += `
			<li class="list-group-item" data-uuid="${e.uuid}">
				<div class="float-left">`
			if(e.fileType){ // 이미지 파일인 경우 섬네일 표시
				let filePath = e.uploadPath+"/s_"+e.uuid+"_"+e.fileName; 
				let encodingFilePath = encodeURIComponent(filePath);
				fileList +=`
					<div class="thumnail d-inline-block mr-3">
						<img alt="" src="${ctxPath}/file/display?fileName=${encodingFilePath}">	
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
						<a href="#">${e.fileName}</a>
					</div>
				</div>
				<div class="float-right">
					<a href="#" class="delete">삭제</a>
				</div>
			</li>`				
		});
		$('.uploadResultDiv ul').append(fileList);
	}
	
	// 게시물 목록
	$('.ict').click(function(){
		let form = $('<form/>')
		let type = $('[name="type"]');
		let keyword = $('[name="keyword"]');
		if(type.val()&&keyword.val()){
			form.append(type).append(keyword);				
		}
		form.attr('action','${ctxPath}/board/ict')
			.append($('[name="pageNum"]'))
			.append($('[name="amount"]'))
			.appendTo('body')
			.submit();
	})
	
	// 파일 업로드 이벤트 
	$('input[type="file"]').change(function(){
		let formData = new FormData(); 
		let files = this.files;
		
		for(let f of files){
			if(!checkExtension(f.name, f.size)) {
				$(this).val('');
				return;
			} 
			formData.append('uploadFile', f);
		}
		
		$.ajax({
			url : `${ctxPath}/file/upload`, 
			type : 'post', 
			processData : false, 
			contentType : false, 
			data : formData, 
			dataType : 'json', 
			success : function(attachListt){
				$.each(attachListt,function(i,e){
					uploadResultList.push(e);
				})
				showUploadResult(attachListt);
			}
		});
	});
	
	// 게시글 등록
	$('.regedit').click(function(e){
		e.preventDefault();
		let form = $('form');
		console.log(uploadResultList);
		if(uploadResultList.length>0){ // 첨부파일이 있으면 
			$.each(uploadResultList, function(i,e){
				let uuid = $('<input/>',{type:'hidden', name:`attachListt[${i}].uuid`, value:`${e.uuid}`})
				let fileName = $('<input/>',{type:'hidden', name:`attachListt[${i}].fileName`, value:`${e.fileName}`})
				let fileType = $('<input/>',{type:'hidden', name:`attachListt[${i}].fileType`, value:`${e.fileType}`})
				let uploadPath = $('<input/>',{type:'hidden', name:`attachListt[${i}].uploadPath`, value:`${e.uploadPath}`})
				
				form.append(uuid)
					.append(fileName)
					.append(fileType)
					.append(uploadPath)
			})
		}
		alert("등록완료");
		form.submit();	
	});
	
	// 첨부파일 삭제 
	$('.uploadResultDiv ul').on('click','.delete',function(e){
		e.preventDefault(); 
		let uuid = $(this).closest('li').data('uuid');
		let targetFileIdx = -1;
		let targetFile = null; 
		
		$.each(uploadResultList,function(i,e){
			if(e.uuid == uuid){
				targetFileIdx = i;
				targetFile = e; 
				return;
			}		
		})
		
		if(targetFileIdx!=-1) uploadResultList.splice(targetFileIdx,1);
		console.log(uploadResultList);
		
		console.log('삭제 대상 파일 객체 :');
		console.log(targetFile);
		
		$.ajax({
			type : 'post',
			url : `${ctxPath}/file/deleteFile`, 
			data : targetFile, 
			success : function(result){
				console.log(result);
			} 
		});
		
		$(this).closest('li').remove();
	});
	
})