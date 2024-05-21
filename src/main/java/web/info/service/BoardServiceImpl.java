package web.info.service;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import web.info.domain.BoardAttachVO;
import web.info.domain.BoardVO;
import web.info.domain.Criteria;
import web.info.repository.BoardAttachRepository;
import web.info.repository.BoardRepository;

@Service
@Log4j
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private BoardAttachRepository boardAttachRepository;

	@Override
	public List<BoardVO> getList(Criteria criteria) {
		return boardRepository.getList(criteria);
	}

	@Transactional
	@Override
	public void register(BoardVO board) {
		boardRepository.insertSelectKey(board);
		// 첨부파일이 있을 때 ...
		if(board.getAttachList()!=null && !board.getAttachList().isEmpty()) { 
			board.getAttachList().forEach(attachFile->{
				attachFile.setBno(board.getBno());
				boardAttachRepository.insert(attachFile);
			});
		}
	}

	@Override
	public BoardVO get(Long bno) {
		return boardRepository.read(bno);
	}

	@Override
	public boolean modify(BoardVO board) {
		List<BoardAttachVO> attachList = board.getAttachList();
		if(attachList!=null) {
			// 삭제 
			List<BoardAttachVO> delList = attachList.stream()
					.filter(attach -> attach.getBno()!=null).collect(Collectors.toList());
			deleteFiles(delList);
			delList.forEach(vo->{
				boardAttachRepository.delete(vo.getUuid());
			});
			
			// 새로 추가 
			attachList.stream()
			.filter(attach -> attach.getBno()==null).forEach(vo->{
				vo.setBno(board.getBno());
				boardAttachRepository.insert(vo);
			});;
		}
		return boardRepository.update(board)==1;
	}

	@Transactional
	@Override
	public boolean remove(Long bno) {
		List<BoardAttachVO> attachList = getAttachList(bno);
		if(attachList!=null) {
			deleteFiles(attachList);
			boardAttachRepository.deleteAll(bno);
		}
		return boardRepository.delete(bno)==1;
	}

	@Override
	public int totalCount(Criteria criteria) {
		return boardRepository.getTotalCount(criteria);
	}

	@Override
	public List<BoardAttachVO> getAttachList(Long bno) {
		return boardAttachRepository.selectByBno(bno);
	}

	@Override
	public BoardAttachVO getAttach(String uuid) {
		return boardAttachRepository.selectByUuid(uuid);
	}
	
	private void deleteFiles(List<BoardAttachVO> delList) {
		delList.forEach(vo->{
			File file = new File("C:/storage/"+vo.getUploadPath(),vo.getUuid() + "_" + vo.getFileName());
			file.delete();
			if(vo.isFileType()) {
				file = new File("C:/storage/"+vo.getUploadPath(),"s_"+vo.getUuid() + "_" + vo.getFileName());
				file.delete();
			}
		});
	}
}
