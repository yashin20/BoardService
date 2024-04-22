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
import project.boardService.entity.Role;

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
            for (int i = 0; i < 100; i++) {
                Member member = new Member();
                member.setName("Member" + i);
                member.setPassword(passwordEncoder.encode("1234"));
                member.setEmail("Member" + i + "@naver.com");
                member.setRole(Role.USER);
                em.persist(member);
            }

            // Admin 1ea
            Member admin = new Member();
            admin.setName("Admin" + 1);
            admin.setPassword(passwordEncoder.encode("1234"));
            admin.setEmail("Admin" + "1" + "@naver.com");
            admin.setRole(Role.ADMIN);
            em.persist(admin);

            // Manager 1ea
            Member manager = new Member();
            manager.setName("Manager" + 1);
            manager.setPassword(passwordEncoder.encode("1234"));
            manager.setEmail("Manager" + "1" + "@naver.com");
            manager.setRole(Role.MANAGER);
            em.persist(manager);
        }
    }
}
