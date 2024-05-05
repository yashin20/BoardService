package project.boardService.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.boardService.dto.PostDto;
import project.boardService.dto.PostListDto;
import project.boardService.entity.Comment;
import project.boardService.entity.Member;
import project.boardService.entity.Post;
import project.boardService.exception.DataNotFoundException;
import project.boardService.exception.UnauthorizedAccessException;
import project.boardService.repository.MemberRepository;
import project.boardService.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    //게시글 전체 검색
    public List<PostListDto> getPosts() {
        List<Post> posts = postRepository.findPosts();
        List<PostListDto> postListDtoList = new ArrayList<>();
        for (Post post : posts) {
            PostListDto postListDto = new PostListDto(post.getId(), post.getTitle(), post.getContent(), post.getWriter().getName(), post.getView());
            postListDtoList.add(postListDto);
        }

        return postListDtoList;
    }

    //게시글 소속 댓글 검색
    public List<Comment> getComment(Long postId) {
        Optional<Post> post = postRepository.findPostById(postId);
        if (post.isPresent()) {
            return postRepository.findComments(post.get());
        } else {
            throw new DataNotFoundException("게시글을 찾을 수 없습니다.");
        }
    }


    //post 생성 (createPost)
    @Transactional
    public Post createPost(String title, String content, Long memberId) {
        Optional<Member> findMember = memberRepository.findById(memberId);

        if (findMember.isPresent()) {
            Post post = new Post(title, content, findMember.get());
            postRepository.save(post);
            return post;
        } else {
            throw new DataNotFoundException("member not found");
        }
    }

    //post 검색 (findPostById)
    public Post findByPostById(Long postId) {
        Optional<Post> findPost = postRepository.findPostById(postId);
        if (findPost.isPresent()) {
            return findPost.get();
        } else {
            throw new DataNotFoundException("post not found");
        }
    }


    //post 검색 (findPostById) 별도 반환 DTO
    @Transactional //조회수가 증가되어야 해서, 트랜젝셔널이 필요!
    public PostDto findByPostDtoById(Long postId) {
        Optional<Post> findPost = postRepository.findPostById(postId);
        if (findPost.isPresent()) {
            Post post = findPost.get();

            //조회수 증가
            postRepository.incrementViewCount(postId);

            //DTO 반환
            return new PostDto(post.getId(), post.getTitle(), post.getContent(), post.getWriter().getName(),
                    post.getView(), post.getCreatedDateTime(), post.getModifiedDateTime());
        } else {
            throw new DataNotFoundException("post not found");
        }
    }

    //post 삭제 (deletePost) + 삭제 조건
    @Transactional
    public Long deletePost(Long postId) {
        //1. Post 찾기
        Optional<Post> findPost = postRepository.findPostById(postId);

        //*예외처리1 : Post 검색 실패
        if (!findPost.isPresent()) {
            throw new DataNotFoundException("게시글을 찾을 수 없습니다.");
        }

        Post post = findPost.get();
        //로그인된 사용자명
        String loggedName = SecurityContextHolder.getContext().getAuthentication().getName();
        //*예외처리2 : 작성자 != 로그인 회원
        if (!post.getWriter().getName().equals(loggedName)) {
            throw new UnauthorizedAccessException("이 댓글을 삭제할 권한이 없습니다.");
        }

        //post 삭제
        postRepository.deletePost(post);
        return postId;
    }

    //post 수정 (updatePost)
    @Transactional
    public Post updatePost(Long id, String title, String content) {
        Optional<Post> findPost = postRepository.findPostById(id);
        if (findPost.isPresent()) {
            Post post = findPost.get();
            post.setTitle(title);
            post.setContent(content);
            post.modifiedDateTimeUpdate();
            postRepository.save(post);
            return post;
        } else {
            throw new DataNotFoundException("post not found");
        }
    }

    //Member's Post Search
    public List<Post> findPostByMember(Long memberId) {
        //1. Member 찾기(id 기반)
        Optional<Member> findMember = memberRepository.findById(memberId);
        if (findMember.isPresent()) {
            return postRepository.findPostByMember(findMember.get());
        } else {
            throw new DataNotFoundException("존재하지 않는 회원 입니다.");
        }
    }


}
