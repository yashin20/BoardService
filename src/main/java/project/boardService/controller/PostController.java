package project.boardService.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.boardService.dto.CreateCommentDto;
import project.boardService.dto.CreatePostDto;
import project.boardService.dto.PostDto;
import project.boardService.entity.Comment;
import project.boardService.entity.Member;
import project.boardService.entity.Post;
import project.boardService.service.CommentService;
import project.boardService.service.MemberService;
import project.boardService.service.PostService;

import java.util.List;

@RequestMapping("/post")
@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final MemberService memberService;
    private final CommentService commentService;


    //게시글 상세 정보
    @GetMapping("/{postId}")
    public String getPost(@PathVariable Long postId, Model model) {
        PostDto post = postService.findByPostDtoById(postId);
        model.addAttribute("post", post);

        //Comments - 댓글 리스트
        List<Comment> comments = postService.getComment(postId);
        model.addAttribute("comments", comments);

        //Create Comment - 댓글 작성 폼
        model.addAttribute("createCommentDto", new CreateCommentDto());

        return "post/postForm";
    }

    @PostMapping("/{postId}")
    public String createComment(@PathVariable Long postId, @ModelAttribute CreateCommentDto createCommentDto,
                                Model model, Authentication authentication)
    {
        String loginName = authentication.getName();
        Member loginMember = memberService.findMemberByName(loginName);

        //댓글 내용이 입력되지 않은 경우
        if (createCommentDto.getContent().isEmpty()) {
            model.addAttribute("createCommentError", "댓글 내용이 입력되지 않았습니다.");
            System.out.println("댓글 내용이 입력되지 않았습니다.");
        }

        commentService.createComment(postId, loginMember.getId(), createCommentDto.getContent());

        return "redirect:/post/{postId}";
    }

    //edit (게시글 작성)
    @GetMapping("/edit")
    public String createPostForm(Model model) {
        model.addAttribute("createPostDto", new CreatePostDto());
        return "post/createPost";
    }

    @PostMapping("/edit")
    public String createPost(@ModelAttribute CreatePostDto createPostDto, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        Member findMember = memberService.findMemberByName(username);

        if (createPostDto.getTitle().isEmpty()) {
            model.addAttribute("createPostError", "게시글 제목이 입력되지 않았습니다.");
            System.out.println("게시글 제목이 입력되지 않았습니다.");
            return "post/createPost";
        }

        if (createPostDto.getContent().isEmpty()) {
            model.addAttribute("createPostError", "게시글 내용이 입력되지 않았습니다.");
            System.out.println("게시글 내용이 입력되지 않았습니다.");
            return "post/createPost";
        }

        postService.createPost(createPostDto.getTitle(), createPostDto.getContent(), findMember.getId());
        return "redirect:/";
    }

    //update (게시글 수정)
    @GetMapping("/{postId}/update")
    public String updatePostForm(@PathVariable Long postId, Model model, Authentication authentication) {
        PostDto post = postService.findByPostDtoById(postId);

        //현재 로그인된 회원이 아니라면, 메인 페이지로 리디렉션
        String loginName = authentication.getName();
        if (!post.getWriterName().equals(loginName)) {
            return "redirect:/";
        }

        model.addAttribute("postDto", post);
        return "post/updatePost";
    }

    //게시글 수정
    @PostMapping("/{postId}/update")
    public String updatePost(@PathVariable Long postId, @ModelAttribute PostDto postDto) {
        postService.updatePost(postId, postDto.getTitle(), postDto.getContent());
        return "redirect:/";
    }


    //게시글 삭제 - API 로 구현
}
