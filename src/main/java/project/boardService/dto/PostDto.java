package project.boardService.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PostDto {

    private Long postId;

    private String title;
    private String content;
    private String writerName;

    private Integer view;
    private LocalDateTime createdDateTime;
    private LocalDateTime modifiedDateTime;

}
