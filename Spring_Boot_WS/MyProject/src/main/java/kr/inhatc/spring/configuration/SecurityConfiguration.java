package kr.inhatc.spring.configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity security) throws Exception {
		// "/"로 된아이는 누구나 다 들어와
		security.authorizeRequests().antMatchers("/").permitAll();

		// MEMBER 권란이 있는 사용자 접근 경로 , hasAnyRole 여러가지 권란 가능
		security.authorizeRequests().antMatchers("/user/**").hasAnyRole("MEMBER", "ADMIN");

		// ADMIN 권란이 있는 사용자 접근 경로 hasRole 특정 권한 가능
		security.authorizeRequests().antMatchers("/board/**").hasRole("ADMIN");
		//쓸때 그냥 MEMBER로 쓰면 여기서 자동으로 ROLE_MEMBER 이런식으로 만들어준다고함


		//크로스 사이트 위조 요청에 대한 설정 1번 영상 41분20초
		//Restfull을 사용하기 위해서는 비활성화
		security.csrf().disable();

		// 로그인 관련 페이지와 성공 시 이동할 페이지 설정
		security.formLogin().loginPage("/login/login").defaultSuccessUrl("/", true);

		// 로그인 실패 상황에 이동할 페이지
		security.exceptionHandling().accessDeniedPage("/login/accessDenied");

		// 로그아웃 요청시 세션을 강제 종료하고 시작 페이지로 이동
		security.logout().logoutUrl("/login/logout").invalidateHttpSession(true).logoutSuccessUrl("/");

	}



}