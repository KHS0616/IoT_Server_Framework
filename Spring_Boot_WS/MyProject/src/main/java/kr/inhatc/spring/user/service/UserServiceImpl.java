package kr.inhatc.spring.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.inhatc.spring.user.entity.Users;
import kr.inhatc.spring.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	
	//유저 정보 불러오기 - 아이디를 기준으로 내림차순
	@Override
	public List<Users> userList() {
		List<Users> list = userRepository.findAllByOrderByIdDesc();
		return list;
	}
	
	//사용자 정보 저장 - 내장함수 사용
	@Override
	public void saveUsers(Users user) {
		userRepository.save(user);
	}

	//사용자 상세 정보 불러오기
	//id를 기준으로 데이터불러오고 그 유무에 따른 예외 처리
	@Override
	public Users userDetail(String id) {
		Optional<Users> optional = userRepository.findById(id);
		if(optional.isPresent()) {
			Users user = optional.get();
			return user;
		} else {
			throw new NullPointerException();
		}
	}

	//사용자 정보 삭제
	@Override
	public void userDelete(String id) {
		userRepository.deleteById(id);
	}
}
