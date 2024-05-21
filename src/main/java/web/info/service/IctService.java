package web.info.service;

import java.util.List;

import web.info.domain.BoardAttachVO;
import web.info.domain.Criteria;
import web.info.domain.Criteriaa;
import web.info.domain.IctAttachVO;
import web.info.domain.IctVO;

public interface IctService {
	List<IctVO> getList(Criteriaa criteriaa);

	void regedit(IctVO ict);

	IctVO get(Long ino);

	boolean modify(IctVO board);

	boolean remove(Long ino);
	
	int totalCount(Criteriaa criteriaa);
	
	List<IctAttachVO> getAttachListt(Long ino);

	IctAttachVO getAttach(String uuid);
	
	List<IctVO> getIctList();
}
