package project.boardService.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.boardService.dto.PostDto;
import project.boardService.dto.PostListDto;
import project.boardService.entity.Member;
import project.boardService.entity.Post;
import project.boardService.exception.DataNotFoundException;
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
    public PostDto findByPostDtoById(Long postId) {
        Optional<Post> findPost = postRepository.findPostById(postId);
        if (findPost.isPresent()) {
            Post post = findPost.get();
            return new PostDto(post.getId(), post.getTitle(), post.getContent(), post.getWriter().getName(),
                    post.getView(), post.getCreatedDateTime(), post.getModifiedDateTime());
        } else {
            throw new DataNotFoundException("post not found");
        }
    }

    //post 삭제 (deletePost)
    @Transactional
    public Long deletePost(Post post) {
        Long deleteId = post.getId();
        postRepository.deletePost(post);
        return deleteId;
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

}
