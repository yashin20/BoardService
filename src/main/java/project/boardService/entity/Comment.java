package project.boardService.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Comment {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String content; //댓글 내용

    private LocalDateTime createdDateTime; //작성 일자
    private LocalDateTime modifiedDateTime; //수정 일자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; //작성자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post; //댓글이 작성된 게시물


    public Comment(Post post, Member member, String content) {
        this.content = content;

        this.member = member;
        this.post = post;

        this.createdDateTime = LocalDateTime.now();
        this.modifiedDateTime = LocalDateTime.now();
    }

    public Comment(String content) {
        this.content = content;
        this.createdDateTime = LocalDateTime.now();
        this.modifiedDateTime = LocalDateTime.now();
    }

    public void modifiedDateTimeUpdate() {
        this.modifiedDateTime = LocalDateTime.now(); //수정일자 업데이트
    }
}
