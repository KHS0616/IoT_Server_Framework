package kr.inhatc.spring.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.inhatc.spring.board.dto.BoardDto;
import kr.inhatc.spring.board.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	// 주수창에 / 입력시 해당 html 페이지로 이동
	@RequestMapping("/")
	public String hello()
	{
		return "index";
	}
	
	@RequestMapping("/board/boardList.do")
	public String boardList(Model model)
	{
		//DB테이블의 리스트를 불러온뒤 뷰에 적용한다.
		List<BoardDto> list = boardService.boardList();
		System.out.println("===============>" + list.size());
		model.addAttribute("list", list);
		return "board/boardList";
	}
}
