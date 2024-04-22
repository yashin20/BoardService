package project.boardService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MemberInfoDto {

    private String name;
    private String email;
    private LocalDateTime createMemberDateTime;

}
