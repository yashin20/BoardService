package project.boardService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostListDto {

    private Long postId;

    private String title;
    private String content;
    private String writer;

    private int view;
}
