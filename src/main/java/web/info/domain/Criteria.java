package web.info.domain;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class Criteria {
	private int pageNum; // 현재 페이지 
	private int amount; // 한 페이지당 게시물 수
	
	private String type; 
	private String keyword;
	
	public Criteria() {
		this(1,10); // 1페이지, 한 페이지당 게시물 수는 10개 
	}

	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	public int getMaxRow() { // #{maxRow}
		return pageNum*amount;
	}
	
	public int getMinRow() { // #{minRow}
		return (pageNum-1)*amount;
	}
	
	public String[] getTypes() { // collection="types"
		return type == null ? new String[] {} : type.split("");
	}
	
	public String getListLink() {
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
				.queryParam("pageNum",this.pageNum)
				.queryParam("amount",this.amount)
				.queryParam("type",this.type)
				.queryParam("keyword",this.keyword);
		return builder.toUriString();
	}
}
