package kr.inhatc.spring.board.dto;
// DTO : Data Transfer Object

import lombok.Data;


@Data
public class BoardDto {
	
	private int boardIdx;
	private String title;
	private String contents;
	private int hitCnt;
	private String creatorId;
	private String createDatetime;
	
}
