package web.info.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan("web.info")
public class ServletConfig implements WebMvcConfigurer{
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/views/",".jsp");
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**")
				.addResourceLocations("/resources/");
	}
	
	@Bean
	public MultipartResolver multipartResolver() {
		 return new StandardServletMultipartResolver();
	}
	
	@Bean
	public MessageSource messageSource() { // 빈id를 반드시 messageSource로 지정해야함
		ResourceBundleMessageSource ms = new ResourceBundleMessageSource(); 
		ms.setBasenames("message.label"); // classpath:message/label.properties
		ms.setDefaultEncoding("UTF-8");
		return ms;
	}
	
}
