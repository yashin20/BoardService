package project.boardService.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Post {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title; //제목
    private String content; //내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member writer; //작성자

    private Integer view; //조회수

    private LocalDateTime createdDateTime; //작성일자
    private LocalDateTime modifiedDateTime; //수정일자


    //생성자
    public Post(String title, String content, Member writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;

        this.createdDateTime = LocalDateTime.now(); //작성일자 설정
        this.modifiedDateTime = LocalDateTime.now(); //마지막 수정일자 설정
        this.view = 0;
    }

    //수정일자 업데이트
    public void modifiedDateTimeUpdate() {
        this.modifiedDateTime = LocalDateTime.now(); //수정일자 업데이트
    }

    //조회수 업데이트
    public void viewUpdate() {
        this.view += 1;
    }
}
