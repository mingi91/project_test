package web.info.repository;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import lombok.extern.log4j.Log4j;
import web.info.config.RootConfig;
import web.info.config.ServletConfig;
import web.info.domain.BoardVO;
import web.info.domain.Criteria;
import web.info.domain.IctVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class, ServletConfig.class} )
@WebAppConfiguration
@Log4j
public class BoardRepositoryTest {

	@Autowired
	private BoardRepository boardRepository; 
	
	@Test
	@Ignore
	public void testGetList() {
		Criteria criteria = new Criteria(); 
		criteria.setPageNum(3);
		List<BoardVO> list = boardRepository.getList(criteria); // 1페이지(1~10)
		log.info(list.size());
		list.forEach(b-> log.info(b));
	}
	
	@Test
	public void testInsert() {
		BoardVO vo = BoardVO.builder()
				.title("새로 작성하는 글...")
				.content("새로 작성하는 글 내용")
				.writer("관리자")
				.build();
		boardRepository.insert(vo);
		log.info(vo);
	}
	
	@Test
	@Ignore
	public void testInsertSelectKey() {
		BoardVO vo = BoardVO.builder()
				.title("새로 작성하는 글...!!!!")
				.content("새로 작성하는 글 내용!!!!")
				.writer("운영자")
				.build();
		boardRepository.insertSelectKey(vo);
		log.info(vo);
	}
	
	@Test
	@Ignore
	public void testRead() {
		BoardVO read = boardRepository.read(4L);
		log.info(read);
	}
	
	@Test
	@Ignore
	public void testUpdate() {
		BoardVO vo = BoardVO.builder()
				.bno(1L)
				.title("수정 작성하는 글...!!!!")
				.content("수정 작성하는 글 내용!!!!")
				.writer("관리자")
				.build();
		int count = boardRepository.update(vo);
		log.info("업데이트 된 행의 개수 : " + count);
	}
	
	@Test
	@Ignore
	public void testDelete() {
		int count = boardRepository.delete(1L);
		log.info("삭제 된 행의 개수 : " + count);
	}
	
	@Test
	@Ignore
	public void testSearch() {
		Criteria criteria = new Criteria(); 
		criteria.setType("TC");
		criteria.setKeyword("자바");
		List<BoardVO> list = boardRepository.getList(criteria);
		list.forEach(b->System.out.println(b));
	}

	@Test
	@Ignore
	public void totalCountTest() {
		Criteria criteria = new Criteria(); 
		criteria.setType("TC");
		criteria.setKeyword("자바");
		int totalCount = boardRepository.getTotalCount(criteria);
		log.info(totalCount);
	}
	
	@Test
	public void test() {
		for(int i=1;i<=108;i++) {
			BoardVO vo = BoardVO.builder()
					.title("제목 : 페이징 처리 " + i)
					.content("내용 : 페이징 처리 " + i)
					.writer("작성자" + (i%5))
					.build();
			boardRepository.insert(vo);			
		}
}
}