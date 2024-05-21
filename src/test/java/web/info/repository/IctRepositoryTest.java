package web.info.repository;

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
import web.info.domain.IctVO;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class, ServletConfig.class} )
@WebAppConfiguration
@Log4j
public class IctRepositoryTest {

	@Autowired
	private IctRepository repository;
	
	@Test
	@Ignore
	public void testInsert() {
		IctVO vo = IctVO.builder()
				.title("새로 작성하는 글...")
				.content("새로 작성하는 글 내용")
				.writer("관리자")
				.build();
		repository.insert(vo);
		log.info(vo);
	}
	
	@Test
	@Ignore
	public void testRead() {
		// 존재하는 게시물 번호로 테스트
		IctVO board = repository.read(2L);
		log.info(board);
	}
	
	@Test
	@Ignore
	public void testDelete() {
		log.info("DELETE COUNT: " + repository.delete(5L));
	}

	@Test
	@Ignore
	public void testUpdate() {
		IctVO board = new IctVO();
		// 실행전 존재하는 번호인지 확인할 것
		board.setIno(2L);
		board.setTitle("수정된 제목");
		board.setContent("수정된 내용");
		board.setWriter("user00");

		int count = repository.update(board);
		log.info("UPDATE COUNT: " + count);
	}
	@Test
	public void test() {
		for(int i=1;i<=108;i++) {
			IctVO vo = IctVO.builder()
					.title("제목 : 페이징 처리 " + i)
					.content("내용 : 페이징 처리 " + i)
					.writer("작성자" + (i%5))
					.build();
			repository.insert(vo);			
		}
}
}