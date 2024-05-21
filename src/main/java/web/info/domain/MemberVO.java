package web.info.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberVO implements Serializable {
	private String memberId;
	private String memberPwd;
	private String memberName;
	private String email;
	private boolean enabled;  
	
	private LocalDateTime regDate; 
	private LocalDateTime updateDate;
	
	private List<AuthVO> authList;	
}
