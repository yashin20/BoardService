package project.boardService.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false)
    private Integer view = 0; //조회수 - 기본값 : 0 설정

    private LocalDateTime createdDateTime; //작성일자
    private LocalDateTime modifiedDateTime; //수정일자

    // 게시글 삭제 시 - 해당 게시글의 댓글도 삭제
    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id asc")
    private List<Comment> comments = new ArrayList<>();


    //생성자
    public Post(String title, String content, Member writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;

        this.createdDateTime = LocalDateTime.now(); //작성일자 설정
        this.modifiedDateTime = LocalDateTime.now(); //마지막 수정일자 설정
    }

    //수정일자 업데이트
    public void modifiedDateTimeUpdate() {
        this.modifiedDateTime = LocalDateTime.now(); //수정일자 업데이트
    }


}
