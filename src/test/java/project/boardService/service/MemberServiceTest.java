package project.boardService.service;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.boardService.entity.Member;
import project.boardService.repository.MemberRepository;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    @DisplayName("username 기반 user 검색 테스트")
    public void getUserByUsernameTest() throws Exception {
        //given
        Member member = memberService.createMember("회원1", "1234", "example@naver.com");

        //when
        Member findMember = memberService.findMemberByName("회원1");

        //then
        System.out.println("member = " + member);
        System.out.println("findMember = " + findMember);
        Assertions.assertEquals(findMember, member);
    }

    @Test
    @DisplayName("로그인_인증_테스트")
    public void 로그인_인증_테스트() throws Exception {
        //given
        String rawPassword = "1234";
        Member member = memberService.createMember("회원1", rawPassword, "example@naver.com");

        em.flush();
        em.clear();

        Member findMember = memberService.findMemberByName("회원1");

        //입력한 비밀번호 - 암호화된 비밀번호 가 서로 다름을 확인
        Assertions.assertNotEquals(rawPassword, findMember.getPassword());
        System.out.println("rawPassword = " + rawPassword);
        //rawPassword = 1234
        System.out.println("findUser.getPassword() = " + findMember.getPassword());
        //findUser.getPassword() = $2a$10$cF7Hmb5Bo8xqOedYco2PkOK3YC9/Mad3G6GHNLcL1ncK6V2l3SJsm

        // 로그인 시도 시 암호화된 비밀번호와 일치하는지 확인
        assertThat(memberService.authenticate(findMember.getName(), rawPassword)).isTrue();
    }

}