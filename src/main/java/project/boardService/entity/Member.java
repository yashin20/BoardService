package project.boardService.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String name; //회원명 == 회원 ID
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role; //회원 권한

    private LocalDateTime createMemberDateTime; //회원가입 일자
}
