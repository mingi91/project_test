package web.info.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Paginationn {
	private Criteriaa criteriaa;
	private int startPage; 
	private int endPage; 
	private int tempEndPage;  
	private int totalCount; // 전체 게시물 수 412개
	private int displayPageNum = 10;
	private boolean prev; 
	private boolean next;
	
	public Paginationn(Criteriaa criteriaa, int totalCount) {
		this.criteriaa = criteriaa;
		this.totalCount = totalCount;
		endPage = (int) Math.ceil(criteriaa.getPageNum()/(double)displayPageNum)*displayPageNum;
		startPage = endPage-displayPageNum+1;
		tempEndPage = (int) Math.ceil(totalCount/(double)criteriaa.getAmount());
		
		prev = startPage != 1;
		next = endPage < tempEndPage;
		
		if(endPage>tempEndPage) endPage = tempEndPage;
	} 
}
