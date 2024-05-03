package project.boardService.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.boardService.entity.Comment;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;


    //save
    public void save(Comment comment) {
        em.persist(comment);
    }

    public Optional<Comment> findById(Long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    public void delete(Comment comment) {
        em.remove(comment);
    }

}
