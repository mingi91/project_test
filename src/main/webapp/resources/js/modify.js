$(function(){
	
	let bnoValue = $('[name="bno"]').val();
	let uploadResultList = []; // 업로드 파일 목록 
	let toBeDelList = [] // 삭제 대상 파일 목록;  
	
	let showUploadResult = function(attachList){
		let fileList = '';
		$.each(attachList,function(i,e){
			uploadResultList.push(e);
			fileList += `
			<li class="list-group-item" data-uuid="${e.uuid}">
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
	
	// 첨부파일 목록 불러오기 
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
					<div class="form-check-inline ml-2">
						<label class="form-check-label">
	    					<input type="checkbox" class="form-check-input toBeDelFile">
	    					<span>삭제</span>
						</label>		
					</div>
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
	
	// 파일 업로드 
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
			url : `${ctxPath}/files/upload`, 
			type : 'post', 
			processData : false, 
			contentType : false, 
			data : formData, 
			dataType : 'json', 
			success : function(attachList){
				showUploadResult(attachList);
				console.log(uploadResultList);
			}
		});
	});
	
	// 새로 첨부한 파일 삭제 
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
		
		$.ajax({
			type : 'post',
			url : `${ctxPath}/files/deleteFile`, 
			data : targetFile, 
			success : function(result){
				console.log(result);
			} 
		});
		
		$(this).closest('li').remove();
	});
	
	// 기존 업로드된 파일 삭제
	$('.uploadResultDiv ul').on('change','.toBeDelFile',function(e){
		let listTag = $(this).closest('li');
		let uuid = listTag.data('uuid');
		if($(this).is(':checked')){
			$.ajax({
				type : 'get', 
				url : `${ctxPath}/board/getAttachFileInfo`, 
				data : {uuid : uuid}, 
				success : function(boardAttachVO){
					toBeDelList.push(boardAttachVO)
				}
			});
		} else {
			toBeDelList = toBeDelList.filter(e=> e.uuid != uuid);
		}
		console.log(toBeDelList); // 삭제 대상 파일 
		
	});
	
	$('.modifyForm button').click(function(){
		let operation =$(this).data('oper')
		addCriteria();
		if(operation=='remove'){
			formObj.attr('action',`${ctxPath}/board/remove`);
		} else if (operation=='list'){
			formObj.empty();
			addCriteria();
			formObj.attr('action',`${ctxPath}/board/list`)
				   .attr('method','get');
		} else { // 수정 처리 
			let idx = 0; 
			if(toBeDelList.length>0){ // 삭제 대상 첨부파일이 있으면 
				$.each(toBeDelList, function(i,e){
					let bno = $('<input/>',{type:'hidden', name:`attachList[${i}].bno`, value:`${e.bno}`})
					let uuid = $('<input/>',{type:'hidden', name:`attachList[${i}].uuid`, value:`${e.uuid}`})
					let fileName = $('<input/>',{type:'hidden', name:`attachList[${i}].fileName`, value:`${e.fileName}`})
					let fileType = $('<input/>',{type:'hidden', name:`attachList[${i}].fileType`, value:`${e.fileType}`})
					let uploadPath = $('<input/>',{type:'hidden', name:`attachList[${i}].uploadPath`, value:`${e.uploadPath}`})
					formObj.append(bno)
						.append(uuid)
						.append(fileName)
						.append(fileType)
						.append(uploadPath);
					console.log(i);
					idx = ++i; 
				}); // each end
			} // if end	
			
			if(uploadResultList.length>0){ // 새로 추가한 첨부파일이 있으면  
				$.each(uploadResultList, function(i,e){
					console.log(idx+i)
					let uuid = $('<input/>',{type:'hidden', name:`attachList[${i+idx}].uuid`, value:`${e.uuid}`})
					let fileName = $('<input/>',{type:'hidden', name:`attachList[${i+idx}].fileName`, value:`${e.fileName}`})
					let fileType = $('<input/>',{type:'hidden', name:`attachList[${i+idx}].fileType`, value:`${e.fileType}`})
					let uploadPath = $('<input/>',{type:'hidden', name:`attachList[${i+idx}].uploadPath`, value:`${e.uploadPath}`})
					formObj
						.append(uuid)
						.append(fileName)
						.append(fileType)
						.append(uploadPath)
				}); // each end
			} // if end	
			
		} // else end
		formObj.submit();
	});
})