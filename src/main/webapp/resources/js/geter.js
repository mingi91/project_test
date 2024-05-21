$(function(){
    let inoValue = $('[name="ino"]').val();
    let getForm = $('.getForm');
    let modifyfBtn = getForm.find('.modify');
    let ictBtn = getForm.find('.ict');
	let getAttachListt = [];
    modifyfBtn.click(function(){
        getForm.attr('action',`${ctxPath}/board/modifyy`)
            .append($('[name="ino"]'))
            .append($('[name="pageNum"]'))
            .append($('[name="amount"]'));
        if(type&&keyword){
            getForm.append($('<input/>',{type:'hidden',name:'type', value: type}))
                .append($('<input/>',{type:'hidden',name:'keyword', value: keyword}))    
        }
        getForm.submit();
    });

    ictBtn.click(function(){
        getForm.attr('action',`${ctxPath}/board/ict`)
               .append($('[name="pageNum"]'))
               .append($('[name="amount"]'));
        if(type&&keyword){
            getForm.append($('<input/>',{type:'hidden',name:'type', value: type}))
                .append($('<input/>',{type:'hidden',name:'keyword', value: keyword}))    
        }
        getForm.submit();
    });
    $.getJSON(
        `${ctxPath}/board/getAttachListt`,
        {ino : inoValue},
        function(attachListt){
            console.log(attachListt);
    });
    $.getJSON(`${ctxPath}/board/getAttachListt`,{ino:inoValue},function(attachListt){
        let fileList = '';
        $(attachListt).each(function(i,e){
            fileList += `<li class="list-group-item" data-uuid="${e.uuid}">
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
                    ${e.fileName}
                </div>
                </div>
                <div class="float-right">
                </div>
            </li>`            
        });
        $('.uploadResultDiv ul').html(fileList);
    });
});
