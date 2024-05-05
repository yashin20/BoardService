package project.boardService.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.boardService.entity.Member;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    //저장 로직
    public void save(Member member) {
        em.persist(member);
    }

    //검색 로직 (id 기반)
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(em.find(Member.class, id));
    }

    //검색 로직 (name 기반)
    public Optional<Member> findByName(String name) {
        List findMembers = em.createQuery("select m from Member m where m.name = :name")
                .setParameter("name", name)
                .getResultList();
        return findMembers.stream().findFirst();
    }

    //삭제 로직
    public void delete(Member member) {
        em.remove(member);
    }
}
