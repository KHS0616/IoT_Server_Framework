package kr.inhatc.spring.user.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.NoArgsConstructor;

// 엔티티 선언
// 테이블 이름 설정
// 디폴트 생성자 선언
// 데이터 선언
@Entity
@Table(name = "users")
@NoArgsConstructor
@Data

public class Users {
	
	@Id
	@Column(name="USER_ID")
	private String id;
	private String pw;
	private String name;
	private String email;
	
	// 날짜(시간)을 사용하기 위한 어노테이션
	// Column어노테이션을 통해 세부내용 설정
	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = false, updatable = false, columnDefinition = "date default sysdate")
	private Date joinDate;
	private String enabled;
	private String role;
}
