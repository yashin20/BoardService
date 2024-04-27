package project.boardService.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
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

    //삭제 로직
    public void deletePost(Post post) {
        em.remove(post);
    }

    //전체 검색
    public List<Post> findPosts() {
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }

}
