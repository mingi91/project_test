package web.info.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Setter
@Getter
@ToString
public class ReplyPageDTO {
	private int replyCount; // 댓글 수 
	private List<ReplyVO> list; // 댓글 목록
}
