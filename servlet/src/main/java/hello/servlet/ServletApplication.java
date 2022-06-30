package hello.servlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@ServletComponentScan// 서블릿 자동등록
@SpringBootApplication
public class ServletApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServletApplication.class, args);
	}
	//스프링 부트가 설정정보를 다 가져 와서 자동으로 뷰리졸버를 등록해줌
	/*
	@Bean
	ViewResolver internalResourceViewResolver(){
		//내부 주소 연결 , 컨트롤러 인터페이스 방식과 함께 사용
		return new InternalResourceViewResolver("/WEB-INF/views/",".jsp");
	}*/
}
