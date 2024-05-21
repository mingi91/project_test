package web.info.exception;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j;
import web.info.domain.AuthVO;
import web.info.domain.MemberVO;
import web.info.repository.AuthRepository;
import web.info.repository.MemberRepository;
import web.info.service.MemberService;
import web.info.service.PasswordMisMatchException;

@Service
@Log4j
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberRepository memberRepository; 
	
	@Autowired
	private AuthRepository authRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Transactional
	@Override
	public void join(MemberVO vo) {
		vo.setMemberPwd(passwordEncoder.encode(vo.getMemberPwd()));
		AuthVO authVO = new AuthVO(vo.getMemberId(),"ROLE_MEMBER");
		memberRepository.insert(vo);
		authRepository.insert(authVO);
	}

	@Override
	public void modify(MemberVO vo) {
		memberRepository.update(vo);
	}

	@Override
	public MemberVO read(String memberId) {
		return memberRepository.selectById(memberId);
	}

	@Transactional
	@Override
	public void changePassword(Map<String, String> memberMap) {
		String memberId = memberMap.get("memberId");
		String newPwd = memberMap.get("newPwd");
		String currentPwd = memberMap.get("currentPwd");
		MemberVO vo = memberRepository.selectById(memberId);
		if(!passwordEncoder.matches(currentPwd, vo.getMemberPwd())) {
			throw new PasswordMisMatchException(); 
		}
		memberRepository.updatePassword(
				memberId,passwordEncoder.encode(newPwd));
	}
}
