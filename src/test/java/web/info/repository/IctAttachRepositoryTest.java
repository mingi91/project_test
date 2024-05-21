package web.info.repository;

import java.util.UUID;

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
import web.info.domain.IctAttachVO;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class, ServletConfig.class} )
@WebAppConfiguration
@Log4j
public class IctAttachRepositoryTest {

	@Autowired
	IctAttachRepository ictAttachRepository;
	
	@Test

	public void testInsert() {
		IctAttachVO vo = new IctAttachVO();
		vo.setIno(1L);
		vo.setFileName("test02.txt");
		vo.setFileType(false);
		vo.setUploadPath("D:/DDD");
		String uuid = UUID.randomUUID().toString();
		vo.setUuid(uuid);
		ictAttachRepository.insert(vo);
	}

	@Test
	@Ignore
	public void testSelectByBno() {
		ictAttachRepository.selectByIno(1L)
			.forEach(file -> log.info(file));
	}
	
	@Test
	@Ignore
	public void testDelete() {
		// 데이터베이스에 저장된 uuid값을 참고
		String uuid = "593d31d7-404c-4cc5-845b-66159a7d97ac";
		ictAttachRepository.delete(uuid);
	}
}
