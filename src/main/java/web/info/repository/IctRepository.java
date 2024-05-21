package web.info.repository;


import java.util.List;

import web.info.domain.BoardVO;
import web.info.domain.Criteriaa;
import web.info.domain.IctVO;

public interface IctRepository {
	void insert(IctVO vo);

	Integer insertSelectKey(IctVO vo);
	
	IctVO read(Long ino);
	
	int delete(Long ino);
	
	int update(IctVO vo);

	List<IctVO> getList(Criteriaa criteriaa);  // #{amount});
	
	// 전체 게시물 수
	int getTotalCount(Criteriaa criteriaa);
	
	List<IctVO> selectIctList();
}
