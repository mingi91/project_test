package web.info.service;

import web.info.domain.Criteria;
import web.info.domain.ReplyPageDTO;
import web.info.domain.ReplyVO;

public interface ReplyService {
	
	int register(ReplyVO vo);
	
	ReplyVO get(Long rno);
	
	int modify(ReplyVO vo);
	
	int remove(Long rno);
	
	ReplyPageDTO getList(Criteria criteria, Long bno);
}
