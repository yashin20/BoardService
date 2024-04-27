package project.boardService.controller;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.boardService.entity.Member;
import project.boardService.entity.Post;
import project.boardService.entity.Role;

import java.time.LocalDateTime;

// Test Data 생성
@Profile("local")
@Component
@RequiredArgsConstructor
public class InitMember {

    private final InitUserService initUserService;

    @PostConstruct
    public void init() {
        initUserService.init();
    }


    @Component
    static class InitUserService {
        @PersistenceContext
        private EntityManager em;
        @Autowired
        private PasswordEncoder passwordEncoder;

        @Transactional
        public void init() {
            // User 100ea
            for (int i = 1; i <= 10; i++) {
                Member member = new Member();
                member.setName("Member" + i);
                member.setPassword(passwordEncoder.encode("1234"));
                member.setEmail("Member" + i + "@example.ac.kr");
                member.setRole(Role.USER);
                em.persist(member);
            }

            Member member = new Member();
            member.setName("Member17");
            member.setPassword(passwordEncoder.encode("1234"));
            member.setEmail("Member17@example.ac.kr");
            member.setRole(Role.USER);
            em.persist(member);

            // Admin 1ea
            Member admin = new Member();
            admin.setName("Admin" + 1);
            admin.setPassword(passwordEncoder.encode("1234"));
            admin.setEmail("Admin" + "1" + "@example.ac.kr");
            admin.setRole(Role.ADMIN);
            em.persist(admin);

            // Manager 1ea
            Member manager = new Member();
            manager.setName("Manager" + 1);
            manager.setPassword(passwordEncoder.encode("1234"));
            manager.setEmail("Manager" + "1" + "@example.ac.kr");
            manager.setRole(Role.MANAGER);
            em.persist(manager);

            for (int i = 1; i <= 50; i++) {
                Post post = new Post();
                post.setTitle("Title" + i);
                post.setContent("Content" + i);
                if (i % 3 == 0) {
                    post.setWriter(member);
                } else if (i % 3 == 1) {
                    post.setWriter(admin);
                } else {
                    post.setWriter(manager);
                }
                post.setCreatedDateTime(LocalDateTime.now());
                post.setModifiedDateTime(LocalDateTime.now());
                post.setView(0);
                em.persist(post);
            }

        }
    }
}
