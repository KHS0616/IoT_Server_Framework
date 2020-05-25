package kr.inhatc.spring.board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import kr.inhatc.spring.board.dto.BoardDto;
import kr.inhatc.spring.board.dto.FileDto;
import kr.inhatc.spring.board.service.BoardService;

@Controller
public class BoardController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BoardService boardService;
	
//	// 주수창에 / 입력시 해당 html 페이지로 이동
//	@RequestMapping("/")
//	public String hello()
//	{
//		return "index";
//	}
	
	@RequestMapping("/board/boardList")
	public String boardList(Model model)
	{
		//DB테이블의 리스트를 불러온뒤 뷰에 적용한다.
		List<BoardDto> list = boardService.boardList();
		log.debug("===============>" + list.size());
		model.addAttribute("list", list);
		return "board/boardList";
	}
	
	@RequestMapping("/board/boardWrite")
	public String boardWrite()
	{
		return "board/boardWrite";
	}
	
	// redirect를 이용해 웹피이지로 단순 이동이 아니라 설정한 서비스를 다시 시작하는것이 가능하다.
	// 파일도 함께 받아오면 처리하도록 설정
	@RequestMapping("/board/boardInsert")
	public String boardInsert(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest)
	{
		boardService.boardInsert(board, multipartHttpServletRequest);
		return "redirect:/board/boardList";
	}
	
	@RequestMapping("/board/boardDetail")
	public String boardDetail(@RequestParam int boardIdx, Model model)
	{
		//글 번호를 매개변수로, 서비스 호출 후 결과 저장
		BoardDto board = boardService.boardDetail(boardIdx);
		//모델을 통해 데이터 전달
		model.addAttribute("board", board);
		return "board/boardDetail";
	}
	
	@RequestMapping("/board/boardUpdate")
	public String boardUpdate(BoardDto board)
	{
		boardService.boardUpdate(board);
		return "redirect:/board/boardList";
	}
	
	@RequestMapping("/board/boardDelete")
	public String boardDelete(@RequestParam("boardIdx") int boardIdx)
	{
		boardService.boardDelete(boardIdx);
		return "redirect:/board/boardList";
	}
	
	//파일 다운로드
	@RequestMapping("/board/downloadBoardFile")
	public void downloadBoardFile(
			@RequestParam("idx") int idx,
			@RequestParam("boardIdx") int boardIdx,
			HttpServletResponse response) throws Exception
	{
		FileDto boardFile = boardService.selectFileInfo(idx, boardIdx);
		
		if(ObjectUtils.isEmpty(boardFile) == false)
		{
			String fileName = boardFile.getOriginalFileName();
			byte[] files = FileUtils.readFileToByteArray(new File(boardFile.getStoredFilePath()));
			
			// response 헤더에 설정
			response.setContentType("application/octet-stream");
			response.setContentLength(files.length);
			response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\";");
			response.setHeader("Content-Transfer-Encoding", "binart");
			
			//파일 작성
			response.getOutputStream().write(files);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}
}
