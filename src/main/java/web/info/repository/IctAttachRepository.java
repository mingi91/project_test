package web.info.repository;

import java.util.List;

import web.info.domain.IctAttachVO;

public interface IctAttachRepository {

	void insert(IctAttachVO vo);
	
	void delete(String uuid);
	
	List<IctAttachVO> selectByIno(Long ino);
	
	IctAttachVO selectByUuid(String uuid);
	
	void deleteAll(Long ino);
	
	List<IctAttachVO> pastFiles(); 
}	
