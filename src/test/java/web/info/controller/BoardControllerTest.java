package web.info.controller;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.WebApplicationContext;

import lombok.extern.log4j.Log4j;
import web.info.config.RootConfig;
import web.info.config.ServletConfig;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class, ServletConfig.class} )
@WebAppConfiguration
@Log4j
public class BoardControllerTest {

	@Autowired
	private WebApplicationContext ctx;
	
	private MockMvc mockMvc; 
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	@Test
	@Ignore
	public void testList() throws Exception {
		ModelMap modelMap = mockMvc.perform(MockMvcRequestBuilders.get("/board/ict"))
			.andReturn()
			.getModelAndView()
			.getModelMap();
		log.info(modelMap);
	}

	@Test
	@Ignore
	public void testRegister() throws Exception {
		String resultPage = mockMvc.perform(MockMvcRequestBuilders
					.post("/board/regedit")
						.param("title", "테스트 새글 제목")
						.param("content", "테스트 새글 내용")
						.param("writer", "user00"))
				.andReturn()
				.getModelAndView()
				.getViewName();
		log.info(resultPage);
	}
	@Test
	public void testGet() throws Exception {
		ModelMap modelMap = mockMvc.perform(MockMvcRequestBuilders
				.get("/board/geter")
					.param("ino", "2"))
			.andReturn()
			.getModelAndView()
			.getModelMap();
		log.info(modelMap);
	}
	@Test
	@Ignore
	public void testModify() throws Exception{
		String resultPage = mockMvc.perform(MockMvcRequestBuilders
				.post("/board/modifyy")
					.param("ino", "1")
					.param("title", "zzz")
					.param("content", "수정된 테스트 글 내용")
					.param("writer", "user00"))
			.andReturn()
			.getModelAndView()
			.getViewName();
		log.info(resultPage);
	}
	@Test
	public void testRemove() throws Exception{
		String resultPage = mockMvc.perform(MockMvcRequestBuilders
				.post("/board/removee")
					.param("ino", "3")) // 게시물 존재 여부 확인
			.andReturn()
			.getModelAndView()
			.getViewName();
		log.info(resultPage);
	}
	
}