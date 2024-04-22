package project.boardService.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.boardService.entity.Member;
import project.boardService.exception.DataNotFoundException;
import project.boardService.repository.MemberRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입 기능
    @Transactional
    public Member createMember(String name, String password, String email) {
        Member member = new Member();
        member.setName(name);

        //password 암호화 저장
        member.setPassword(passwordEncoder.encode(password));

        member.setEmail(email);
        member.setCreateMemberDateTime(LocalDateTime.now());
        memberRepository.save(member);
        return member;
    }

    // 인증 (로그인시 인증)
    public boolean authenticate(String name, String password) {
        Optional<Member> findMember = memberRepository.findByName(name);
        if (findMember.isPresent()) {
            return passwordEncoder.matches(password, findMember.get().getPassword());
        }
        return false;
    }


    // 회원 검색 기능 (username 기반)
    public Member findMemberByName(String name) {
        Optional<Member> findMember = memberRepository.findByName(name);
        if (findMember.isPresent()) {
            return findMember.get();
        } else {
            throw new DataNotFoundException("user not found");
        }
    }


    // 회원 검색 기능 (id 기반)
    public Member findMemberById(Long id) {
        Optional<Member> findMember = memberRepository.findById(id);
        if (findMember.isPresent()) {
            return findMember.get();
        } else {
            throw new DataNotFoundException("user not found");
        }
    }


}
