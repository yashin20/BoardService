package project.boardService.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.boardService.dto.PostListDto;
import project.boardService.entity.Post;
import project.boardService.service.PostService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;

    //게시글 목록
    @GetMapping("/")
    public String Home(Model model) {
        List<PostListDto> posts = postService.getPosts();

        model.addAttribute("postList", posts);

        return "index";
    }
}
