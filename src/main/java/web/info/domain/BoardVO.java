package web.info.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Alias("board")
public class BoardVO {
	private Long bno;
	private String title;
	private String content;
	private String writer;
	
	private int replyCnt; 
	
	@DateTimeFormat(pattern = "yyyy년MM월dd일 HH시mm분")
	private LocalDateTime regDate;
	
	@DateTimeFormat(pattern = "yyyy년MM월dd일 HH시mm분")
	private LocalDateTime updateDate;

	private List<BoardAttachVO> attachList; 
}
