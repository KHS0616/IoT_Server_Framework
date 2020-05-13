package kr.inhatc.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;

// 첨푸파일과 관련된 자동 구성을 사용하지 않도록 설정
// 추가로 설정한 업로드 관련 설정 파일을 사용하도록 하기 위함
@SpringBootApplication(exclude = {MultipartAutoConfiguration.class})
public class MyProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyProjectApplication.class, args);
	}

}
