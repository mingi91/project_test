package web.info.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.log4j.Log4j;
import web.info.domain.MemberVO;
import web.info.exception.NotFoundMemberException;
import web.info.service.MailSendService;
import web.info.service.MemberService;
import web.info.service.PasswordMisMatchException;

@Controller
@Log4j
public class MemberController {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	private MailSendService mailSendService; 

	@GetMapping("/guest/gusetpage")
	public String guestPage() {
		return "guest/guestpage";
	}
	
	// 회원페이지
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
	@GetMapping({"/mypage", "/mypage/{path}"})
	public String myPage(Model model,Principal principal,
			@PathVariable(required = false) String path ) {
		String memberId = principal.getName();
		if(path==null) {
			MemberVO memberVO = memberService.read(memberId);
			model.addAttribute("vo", memberVO);
			return "member/mypage";
		} 
		return "member/"+path;
	}
	
	//회원 정보수정 처리
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
	@PostMapping("member/modify")
	public String modify(MemberVO memberVO, RedirectAttributes rttr) {
		log.info(memberVO);
		memberService.modify(memberVO);
		rttr.addFlashAttribute("result", "modify");
		return "redirect:/mypage";
	}
	
	// 비밀변호 변경 처리  
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
	@PostMapping(value = "/mypage/changePwd", produces = "application/text; charset=utf-8") // 한글깨짐 발생
	@ResponseBody
	public ResponseEntity<String> changePwd(@RequestParam Map<String, String> memberMap){
		try {
			memberService.changePassword(memberMap);
		} catch (PasswordMisMatchException e) {
			return new ResponseEntity<String>("비밀번호가 일치하지 않음",HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<String>("success",HttpStatus.OK);
	}
	
	// 약관동의 
	@GetMapping("/join/step1")
	public String step1() {
		return "member/step1";
	}

	// 이메일 인증
	@PostMapping("/join/step2")
	public String step2(@RequestParam(defaultValue = "false") List<Boolean> agreement) {
		if(agreement.size()>=2 && agreement.stream().allMatch(v->v) ) {
			return "member/step2";
		}
		return "member/step1";
	}
	
	// 회원가입작성
	@PostMapping("/join/step3")
	public String step3(MemberVO memberVO) {
		return "member/join";
	}
	
	// 회원가입 처리 
	@PostMapping("/member/join")
	public String join(MemberVO memberVO, RedirectAttributes rttr) {
		memberService.join(memberVO);
		return "redirect:/";
	}
	
	@GetMapping({"/join/step2","/join/step3"})
	public String joinForm() {
		return "member/step1";
	} 
	
	// 아이디중복 체크 
	@PostMapping("/member/idCheck")
	@ResponseBody
	public ResponseEntity<Boolean> idDuplicateCheck(String memberId){
		MemberVO vo = memberService.read(memberId);
		return vo == null ? 
			new ResponseEntity<>(Boolean.TRUE,HttpStatus.OK) 
			: new ResponseEntity<>(Boolean.FALSE,HttpStatus.OK);
	} 
	
	@GetMapping("/mailCheck")
	@ResponseBody
	public String mailCheck(String email) {
		return mailSendService.joinEmail(email);
	}
	

	// 관리자 페이지 
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/admin/adminPage")
	public String adminPage(HttpServletRequest request) {
		return "admin/adminPage";
	}
	
	@GetMapping("/accessDenied")
	public String accessDenided() {
		return "accessError";
	}
	
	@RequestMapping("/login")
	public String loginPage(HttpServletRequest request, 
			Authentication authentication, RedirectAttributes rttr, 
			String error, String logout, Model model) {
		String uri = request.getHeader("Referer"); // 로그인 전 사용자가 보던 페이지 
		if(uri!=null && !uri.contains("/login")) {
			request.getSession().setAttribute("prevPage", uri);
		} 
		
		if(authentication!=null) {
			rttr.addFlashAttribute("duplicateLogin", "이미 로그인 중 입니다.");
			if(uri==null)  uri = "/";
			return "redirect:"+uri;
		}
		
		if(logout!=null) model.addAttribute("logout", "로그아웃");
		return "member/login";
	}
	// 아이디 찾기 또는 임시비밀번호 발급 페이지
	@GetMapping("/findMemberInfo")
	public String findMemberInfo() {
		return "member/findMemberInfo";
	}
	
	// 아이디 찾기 메일 전송 
	@PostMapping(value = "/findMemberId", produces = "plain/text; charset=utf-8")
	@ResponseBody
	public ResponseEntity<String> findMemberId(String email){
		String message = null;
		try {
			mailSendService.findIdEmail(email);			
			message = "가입하신 이메일로 전송되었습니다.";
		} catch (NotFoundMemberException e) {
			message = "회원정보를 찾을 수 없습니다.";
			return new ResponseEntity<String>(message,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(message,HttpStatus.OK);
	}
}
