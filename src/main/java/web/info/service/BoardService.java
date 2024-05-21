package web.info.service;

import java.util.List;

import web.info.domain.BoardAttachVO;
import web.info.domain.BoardVO;
import web.info.domain.Criteria;

public interface BoardService {

	List<BoardVO> getList(Criteria criteria); // 목록
	
	void register(BoardVO board); //등록

	BoardVO get(Long bno); // 조회

	boolean modify(BoardVO board); // 수정

	boolean remove(Long bno); // 삭제
	
	//게시물수
	int totalCount(Criteria criteria);
	
	List<BoardAttachVO> getAttachList(Long bno);
	
	BoardAttachVO getAttach(String uuid);
}
