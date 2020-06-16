package kr.inhatc.spring.user.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.inhatc.spring.user.entity.Users;
import kr.inhatc.spring.user.service.UserService;

@Controller
public class UserController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserService userService;
	
	// 주수창에 / 입력시 userList실행
	@RequestMapping("/")
	public String hello()
	{
		return "index";
	}
	
	//RESTFUL 기반  API
	//RESTFUL 기반으로 API서비스를 하는 시스템 사이의 규칙
	//통상적인 WEB 서버에서는 GET, POST 만으로도 해결하는 경우도 있다.
	//GET(read), POST(create), PUT(update), DELETE(delete)
	@RequestMapping(value = "/user/userList", method=RequestMethod.GET)
	public String userList(Model model)
	{
		//DB테이블의 리스트를 불러온뒤 모델의 속성에 저장한다.
		List<Users> list = userService.userList();
		model.addAttribute("list", list);
		return "user/userList";
	}
	
	//사용자 등록
	@RequestMapping(value = "/user/userInsert", method=RequestMethod.GET)
	public String userWrite()
	{
		return "user/userWrite";
	}
	
	//사용자 DB저장
	@RequestMapping(value = "/user/userInsert", method=RequestMethod.POST)
	public String userInsert(Users user)
	{
		userService.saveUsers(user);
		return "redirect:/user/userList";
	}
	
	//사용자 상세조회
	@RequestMapping(value = "/user/userDetail/{id}", method=RequestMethod.GET)
	public String userDetail(@PathVariable("id") String id, Model model)
	{
		Users user = userService.userDetail(id);
		model.addAttribute("user", user);
		return "/user/userDetail";
	}
	
	//사용자 정보 수정
	@RequestMapping(value = "/user/userUpdate/{id}", method=RequestMethod.POST)
	public String userUpdate(@PathVariable("id") String id, Users user)
	{
		//아이디 값이 null로 오기때문에 URL을 통해 직접 전달
		//아이디 설정
		user.setId(id);
		userService.saveUsers(user);
		return "redirect:/user/userList";
	}
	
	//사용자 정보 삭제
	@RequestMapping(value = "/user/userDelete/{id}", method=RequestMethod.GET)
	public String userDelete(@PathVariable("id") String id)
	{
		userService.userDelete(id);
		return "redirect:/user/userList";
	}
}
