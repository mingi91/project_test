package web.info.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.log4j.Log4j;
import web.info.domain.BoardAttachVO;
import web.info.domain.BoardVO;
import web.info.domain.Criteria;
import web.info.domain.Criteriaa;
import web.info.domain.IctAttachVO;
import web.info.domain.IctVO;
import web.info.domain.Pagination;
import web.info.domain.Paginationn;
import web.info.service.BoardService;
import web.info.service.IctService;

@Controller
@RequestMapping("/board")
@Log4j
public class BoardController {
	
	@Autowired
	private BoardService boardService; 
	
	@Autowired
	private IctService ictService;
	
	@GetMapping("/list")
	public void list(Model model, Criteria criteria) {
		model.addAttribute("list", boardService.getList(criteria));
		model.addAttribute("p", new Pagination(criteria, boardService.totalCount(criteria)));
	}
	
	@GetMapping("/getAttachList")
	@ResponseBody
	public ResponseEntity<List<BoardAttachVO>> getAttachList(Long bno){
		return new ResponseEntity<List<BoardAttachVO>>(boardService.getAttachList(bno), HttpStatus.OK);
	}
	@GetMapping("/getAttachListt")
	@ResponseBody
	public ResponseEntity<List<IctAttachVO>> getAttachListt(Long ino){
		return new ResponseEntity<List<IctAttachVO>>(ictService.getAttachListt(ino), HttpStatus.OK);
	}
	
	@GetMapping("/getAttachFileInfo")
	@ResponseBody
	public ResponseEntity<BoardAttachVO> getAttachFileInfo(String uuid){
		return new ResponseEntity<>(boardService.getAttach(uuid), HttpStatus.OK); 
	}
	@GetMapping("/getAttachFileInfoo")
	@ResponseBody
	public ResponseEntity<IctAttachVO> getAttach(String uuid){
		return new ResponseEntity<>(ictService.getAttach(uuid),HttpStatus.OK); 
	}
	
	@GetMapping("/get")
	public void get(Long bno, Model model, Criteria criteria) {
		model.addAttribute("board", boardService.get(bno));
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify")
	public String modify(Long bno, Model model, Criteria criteria, Authentication auth) {
		BoardVO vo = boardService.get(bno);
		String username = auth.getName(); // 인증된 사용자 계정
		if (!vo.getWriter().equals(username) &&  // 글작성자가 아닌 경우
			!auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) { // 관리자가 아닌 경우
				throw new AccessDeniedException("Access denied"); // 접근 금지 
	    }
		model.addAttribute("board", vo);
		return "board/modify";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/register")
	public void register() {}	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/register")
	public String register(BoardVO vo, RedirectAttributes rttr) {
		log.info(vo.getAttachList());
		boardService.register(vo);
		rttr.addFlashAttribute("result", vo.getBno()); // ${result}
		rttr.addFlashAttribute("operation", "register");
		return "redirect:/board/list";
	}
	// ict 게시글 추가
	@GetMapping("/regedit")
	public void regedit() {}
	@PostMapping("/regedit")
	public String regedit(IctVO vo, RedirectAttributes rttr) {
		log.info(vo.getAttachListt());
		ictService.regedit(vo);
		rttr.addFlashAttribute("result", vo.getIno());
		rttr.addFlashAttribute("operation", "regedit");
		return "redirect:/board/ict";
	}
	
	@PreAuthorize("isAuthenticated() and principal.username== #vo.writer or hasRole('ROLE_ADMIN')")
	@PostMapping("/modify")
	public String modify(BoardVO vo, RedirectAttributes rttr, Criteria criteria) {
		if(boardService.modify(vo)) {
			rttr.addFlashAttribute("result", vo.getBno());
			rttr.addFlashAttribute("operation", "modify");
		}
		return "redirect:/board/list"+criteria.getListLink();
	}
	
	@PreAuthorize("isAuthenticated() and principal.username== #writer or hasRole('ROLE_ADMIN')")
	@PostMapping("/remove")
	public String remove(Long bno, RedirectAttributes rttr, Criteria criteria, String writer) {
		if(boardService.remove(bno)) {
			rttr.addFlashAttribute("result", bno);
			rttr.addFlashAttribute("operation", "remove");
		}
		return "redirect:/board/list"+criteria.getListLink();
	}
	
	// ict 기능 추가
	@GetMapping("/ict")
	public void ict(Model model, Criteriaa criteriaa) {
	    model.addAttribute("ictList", ictService.getList(criteriaa));
	    model.addAttribute("p", new Paginationn(criteriaa, ictService.totalCount(criteriaa)));
	}

	
	// ict 게시글 조회
	@GetMapping({"/geter","/modifyy"})
	public void get(Long ino, Model model, Criteriaa criteriaa) {
		model.addAttribute("ict", ictService.get(ino));
	}
	
	// ict 게시글 수정
	@PostMapping("/modifyy")
	public String modifyy(IctVO vo, RedirectAttributes rttr, Criteriaa criteriaa) {
		if(ictService.modify(vo)) {
			rttr.addFlashAttribute("result", vo.getIno());
			rttr.addFlashAttribute("operation", "modify"); 
		}
		rttr.addAttribute("pageNum",criteriaa.getPageNum());
		rttr.addAttribute("amount",criteriaa.getAmount());
		return "redirect:/board/ict";
	}
	// ict 게시글 삭제
	@PostMapping("/removee")
	public String remove(Long ino, RedirectAttributes rttr,Criteriaa criteriaa) {
		if(ictService.remove(ino)) {
			rttr.addFlashAttribute("result", ino);
			rttr.addFlashAttribute("operation", "remove"); 
		}		
		rttr.addAttribute("pageNum",criteriaa.getPageNum());
		rttr.addAttribute("amount",criteriaa.getAmount());
		return "redirect:/board/ict"; 
	}
	
	 @GetMapping("/ictt")
	    public String list(Model model) {
	        List<IctVO> ictList = ictService.getIctList();
	        model.addAttribute("ictList", ictList);
	        return "board/ict";
	    }
	}
