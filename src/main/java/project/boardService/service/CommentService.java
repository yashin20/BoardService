package project.boardService.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.boardService.entity.Comment;
import project.boardService.entity.Member;
import project.boardService.entity.Post;
import project.boardService.exception.DataNotFoundException;
import project.boardService.exception.UnauthorizedAccessException;
import project.boardService.repository.CommentRepository;
import project.boardService.repository.MemberRepository;
import project.boardService.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;


    //댓글 생성 (소속 게시글_ID(postId), 작성자_ID(memberId), 댓글_내용(content))
    @Transactional
    public Comment createComment(Long postId, Long memberId, String content) {
        Optional<Post> findPost = postRepository.findPostById(postId);
        Optional<Member> findMember = memberRepository.findById(memberId);

        if (findPost.isPresent() && findMember.isPresent()) {
            Comment comment = new Comment(findPost.get(), findMember.get(), content);
            commentRepository.save(comment);
            return comment;
        } else {
            throw new DataNotFoundException("게시글 또는 작성자 가 존재하지 않습니다.");
        }
    }


    /**
     * 댓글 수정 기능
     * @param commentId : 수정 대상 댓글 ID
     * @param content : 새로 수정한 내용
     * @return : 수정된 댓글
     */
    @Transactional
    public Comment updateComment(Long commentId, String content) {
        Optional<Comment> findComment = commentRepository.findById(commentId);

        // 수정하고자 하는 댓긓이 존재하지 않는 경우
        if (!findComment.isPresent()) {
            throw new DataNotFoundException("수정할 댓글을 찾을 수 없습니다.");
        }

        Comment comment = findComment.get();
        //로그인된 사용자명
        String loggedName = SecurityContextHolder.getContext().getAuthentication().getName();

        // Comment 작성자 != 로그인된 사용자
        if (!comment.getMember().getName().equals(loggedName)) {
            throw new UnauthorizedAccessException("댓글 수정 권한이 없습니다.");
        }

        comment.setContent(content);
        return comment;
    }

    /**
     * 삭제 기능
     * @param commentId : 삭제 대상 댓글 ID
     * @return : 삭제된 댓글의 ID
     */
    @Transactional
    public Long deleteComment(Long commentId) {

        Optional<Comment> findComment = commentRepository.findById(commentId);

        // 삭제 대상 이 존재하지 않음.
        if (!findComment.isPresent()) {
            throw new DataNotFoundException("삭제할 댓글을 찾을 수 없습니다.");
        }

        Comment comment = findComment.get();
        //로그인된 사용자명
        String loggedName = SecurityContextHolder.getContext().getAuthentication().getName();

        // 작성자 != 로그인된 사용자
        if (!comment.getMember().getName().equals(loggedName)) {
            throw new UnauthorizedAccessException("이 댓글을 삭제할 권한이 없습니다.");
        }

        commentRepository.delete(comment);
        return comment.getId();
    }


}
