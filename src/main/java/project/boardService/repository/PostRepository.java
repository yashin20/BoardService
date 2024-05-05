package project.boardService.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.boardService.entity.Comment;
import project.boardService.entity.Member;
import project.boardService.entity.Post;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    //저장 로직
    public void save(Post post) {
        em.persist(post);
    }

    //검색 로직 (id 기반)
    public Optional<Post> findPostById(Long id) {

        return Optional.ofNullable(em.find(Post.class, id));
    }

    //검색 로직 - 회원이 작성한 Posts
    public List<Post> findPostByMember(Member member) {

        return em.createQuery("select p from Post p where p.writer.id =: memberId", Post.class)
                .setParameter("memberId", member.getId())
                .getResultList();
    }


    //삭제 로직
    public void deletePost(Post post) {
        em.remove(post);
    }

    //전체 검색
    public List<Post> findPosts() {
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }

    //게시글 댓글 목록
    public List<Comment> findComments(Post post) {
        return post.getComments();
    }


    // Post.view 1 증가
    public void incrementViewCount(Long postId) {
        em.createQuery("update Post p set p.view = p.view + 1 where p.id = :id")
                .setParameter("id", postId)
                .executeUpdate();
    }

}
