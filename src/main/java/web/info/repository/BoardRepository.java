package web.info.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import web.info.domain.BoardVO;
import web.info.domain.Criteria;

public interface BoardRepository {
	
	List<BoardVO> getList(Criteria criteria); 
	
	void insert(BoardVO vo);
	
	Integer insertSelectKey(BoardVO vo);
	
	BoardVO read(Long bno);
	
	int delete(Long bno);
	
	int update(BoardVO vo);
	
	int getTotalCount(Criteria criteria);
	
	// amount = {댓글 추가 : 1,  댓글 감소 : -1}
	void updateReplyCnt(@Param("bno") Long bno, @Param("amount") int amount);
	
	// 추천 수
		void updateLikeCnt(@Param("bno") Long bno, @Param("amount") int amount);
}
