package com.cos.blog.service;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


// Spring이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌 -> IOC를 해줌
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional          // 회원가입 메서드가 하나의 transaction이 되어서 성공하면 commit 실패하면 rollback이 된다. ( 롤백 로직은 따로 짜야함 )
    public void 회원가입(User user) {
       // try {
            userRepository.save(user);
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("UserService : 회원가입() : " + e.getMessage() );
//        }
//        return -1;
    }

    @Transactional(readOnly = true)     // Select할 때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료 (정합성 유지)
    public User 로그인(User user) {
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

}
























