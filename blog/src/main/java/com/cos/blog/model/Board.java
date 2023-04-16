package com.cos.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100)
    private String title;
    @Lob    // 대용량 데이터
    private String content; // 섬머노트 라이브러리 <html>태그가 섞여서 디자인이 됨
    @ColumnDefault("0")
    private long count;     // 조회수
    @ManyToOne(fetch = FetchType.EAGER)          // Many = Board , User = One        한명의 유저는 여러개의 게시글을 쓸 수 있다.
    @JoinColumn(name = "userId")
    private User user;  // DB는 오브젝트를 저장할 수 없다. FK,자바는 오브젝트를 저장할 수 있다.

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER) // 하나의 게시글은 여러개의 답변을 가질 수 있다. , mappedBy는 연관관계의 주인이 아니다.(난FK가 아니라는 뜻 = DB에 컬럼을 만들지 말라는 뜻.)
    private List<Reply> reply;
    @CreationTimestamp
    private Timestamp createDate;
}
