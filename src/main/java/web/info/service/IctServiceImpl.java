package web.info.service;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import web.info.domain.Criteriaa;
import web.info.domain.IctAttachVO;
import web.info.domain.IctVO;
import web.info.repository.IctAttachRepository;
import web.info.repository.IctRepository;

@Service
@RequiredArgsConstructor
@Log4j
public class IctServiceImpl implements IctService {

	@Autowired
	private IctRepository repository;

	@Autowired
	private IctAttachRepository ictAttachRepository;
	
	@Transactional
	@Override
	public void regedit(IctVO ict) {
	    repository.insertSelectKey(ict);
	    // 첨부파일이 있을 때 ...
	    if (ict.getAttachListt() != null && !ict.getAttachListt().isEmpty()) { 
	        ict.getAttachListt().forEach(attachFile -> {
	            attachFile.setIno(ict.getIno()); // 가져온 ino 값 설정
	            ictAttachRepository.insert(attachFile);
	        });
	    }
	}


	@Override
	public IctVO get(Long ino) {
		return repository.read(ino);
	}

	@Override
	@Transactional
	public boolean modify(IctVO ict) {
	    List<IctAttachVO> attachListt = ict.getAttachListt();
	    if(attachListt != null) {
	        // 기존 첨부 파일 삭제 및 데이터베이스에서 삭제
	        List<IctAttachVO> delList = attachListt.stream()
	                .filter(attach -> attach.getIno() != null)
	                .collect(Collectors.toList());
	        deleteFiles(delList);
	        delList.forEach(vo -> ictAttachRepository.delete(vo.getUuid()));
	        
			// 새로 추가 
	        attachListt.stream()
			.filter(attach -> attach.getIno()==null).forEach(vo->{
				vo.setIno(ict.getIno());
				ictAttachRepository.insert(vo);
			});;
	    }
	    // 게시글 정보 업데이트
	    return repository.update(ict) == 1;
	}


	private void deleteFiles(List<IctAttachVO> delList) {
		delList.forEach(vo->{
			File file = new File("D:/uploads/"+vo.getUploadPath(),vo.getUuid() + "_" + vo.getFileName());
			file.delete();
			if(vo.isFileType()) {
				file = new File("D:/uploads/"+vo.getUploadPath(),"s_"+vo.getUuid() + "_" + vo.getFileName());
				file.delete();
			}
		});
		
	}

	@Override
	public boolean remove(Long ino) {
		return repository.delete(ino)==1;
	}


	@Override
	public List<IctVO> getList(Criteriaa criteriaa) {
		return repository.getList(criteriaa);
	}

	@Override
	public int totalCount(Criteriaa criteriaa) {
		return repository.getTotalCount(criteriaa);
	}

	@Override
	public List<IctAttachVO> getAttachListt(Long ino) {
		return ictAttachRepository.selectByIno(ino);
	}

	@Override
	public IctAttachVO getAttach(String uuid) {
		return ictAttachRepository.selectByUuid(uuid);
	}


	@Override
	public List<IctVO> getIctList() {
		return repository.selectIctList();
	}


}
