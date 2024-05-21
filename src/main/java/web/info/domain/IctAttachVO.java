package web.info.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class IctAttachVO {
	private Long ino; 
    private String uuid; 
    private String uploadPath; 
    private String fileName; 
    private boolean fileType;
}
