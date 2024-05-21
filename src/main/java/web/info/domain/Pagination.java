package web.info.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Pagination {

	private Criteria criteria;
	private int startPage; 
	private int endPage; 
	private int tempEndPage; // ?? 
	private int totalCount; // 전체 게시물 수 412개
	private int displayPageNum = 10;
	private boolean prev; 
	private boolean next; 
	
	public Pagination(Criteria criteria, int totalCount) {
		this.criteria = criteria;
		this.totalCount = totalCount; 
		endPage = (int) Math.ceil(criteria.getPageNum()/(double)displayPageNum)*displayPageNum;  
		startPage = endPage-displayPageNum+1; 
		tempEndPage = (int) Math.ceil(totalCount/(double)criteria.getAmount());
		
		prev = startPage != 1;
		next = endPage < tempEndPage; // 마지막페이지를 제외하면 항상 tempEndPage가 endPage보다 크다
		
		if(endPage>tempEndPage) endPage = tempEndPage;
	}
}
