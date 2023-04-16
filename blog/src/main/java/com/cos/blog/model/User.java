package com.cos.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;

// ORM -> JAVA or (모든언어) Object를 테이블로 매핑해주는 기술
// 나는 Object를 만들면 JPA가 테이블로 만들어준다.
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
//@DynamicInsert // insert할때 null 인 필드 제외
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // 시퀀스, auto_inecrement
    @Column(nullable=false, length=30, unique = true)
    private String username; // 아이디
    @Column(nullable = false, length=100)
    private String password;
    @Column(nullable = false, length=50)
    private String email;

    // DB에는 RoleType이라는게 없다.
    //@ColumnDefault("user")
    @Enumerated(EnumType.STRING) // enum타입이라고 알려줌
    private RoleType role; // Enum을 쓰는게 좋다. // admin,user,manager 권한 부여 하는것 (도메인 설정)
    @CreationTimestamp // 시간 자동 입력
    private Timestamp creatDate;
}
