package kr.inhatc.spring.board.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.board.dto.BoardDto;
import kr.inhatc.spring.board.dto.FileDto;
import kr.inhatc.spring.board.mapper.BoardMapper;
import kr.inhatc.spring.utils.FileUtils;

//어노테이션을 통해 서비스임을 알린다. 클래스에 올려야된다
@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardMapper boardMapper;
	
	private FileUtils fileUtils = new FileUtils();
	
	@Override
	public List<BoardDto> boardList() {
		// TODO Auto-generated method stub
		return boardMapper.boardList();
	}

	@Override
	public void boardInsert(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) {
		// TODO Auto-generated method stub
		boardMapper.boardInsert(board);
		
		// 파일 저장
		List<FileDto> list = fileUtils.parseFileInfo(board.getBoardIdx(), multipartHttpServletRequest);

		// DB에 파일 저장
		if(!CollectionUtils.isEmpty(list)) {
			boardMapper.boardFileInsert(list);
		}
	}

	@Override
	public BoardDto boardDetail(int boardIdx) {
		// TODO Auto-generated method stub
		BoardDto board = boardMapper.boardDetail(boardIdx);
		
		//파일 정보를 불러오고 보드에 설정 후 반환
		List<FileDto> fileList = boardMapper.selectBoardFileList(boardIdx);
		board.setFileList(fileList);
		
		//조회수 업데이트
		boardMapper.updateHit(boardIdx);
		return board;
	}

	@Override
	public void boardUpdate(BoardDto board) {
		boardMapper.boardUpdate(board);
		
	}

	@Override
	public void boardDelete(int boardIdx) {
		boardMapper.boardDelete(boardIdx);
	}

	
	@Override
	public FileDto selectFileInfo(int idx, int boardIdx) {
		FileDto boardFile = boardMapper.selectFileInfo(idx, boardIdx);
		return boardFile;
	}

}
