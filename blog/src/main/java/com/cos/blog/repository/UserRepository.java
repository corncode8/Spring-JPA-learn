package com.cos.blog.repository;

import com.cos.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Id;

// DAO
// 자동으로 Bean으로 등록이 된다. @Repository 생략 가능하다.
public interface UserRepository extends JpaRepository<User, Integer> {      // User 테이블이 관리하는 Repository고 User 테이블의 PK는 Integer다.
//로그인을 위한 함수 JPA Naming 전략
    // findByUsernameAndPassword가 SELECT * From user WHERE username = ?1 AND password = ?2
    User findByUsernameAndPassword(String username, String password);

    // 위랑 같은 결과를 도출해준다.
//    @Query(value = "SELECT * From user WHERE username = ?1 AND password = ?2", nativeQuery = true)
//    User login(String username, String password);


}
