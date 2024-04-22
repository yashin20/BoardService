package project.boardService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMemberDto {

    private String name;
    private String password;
    private String passwordEqual;
    private String email;

}
