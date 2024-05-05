package project.boardService.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.boardService.entity.Member;
import project.boardService.entity.Role;
import project.boardService.exception.DataAlreadyExistsException;
import project.boardService.exception.DataNotFoundException;
import project.boardService.exception.UnauthorizedAccessException;
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

        //이름 중복 체크
        if (memberRepository.findByName(name).isPresent()){
            throw new DataAlreadyExistsException("이미 존재하는 회원 이름입니다.");
        }

        Member member = new Member();
        member.setName(name);

        //password 암호화 저장
        member.setPassword(passwordEncoder.encode(password));

        member.setEmail(email);
        member.setCreateMemberDateTime(LocalDateTime.now());
        member.setRole(Role.USER); //USER 권한 부여
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
            throw new DataNotFoundException("member not found");
        }
    }


    // 회원 검색 기능 (id 기반)
    public Member findMemberById(Long id) {
        Optional<Member> findMember = memberRepository.findById(id);
        if (findMember.isPresent()) {
            return findMember.get();
        } else {
            throw new DataNotFoundException("member not found");
        }
    }


    /**
     * 회원 삭제 기능
     * @param memberId : 삭제 회원 ID
     * @return : 삭제 회원 ID
     */
    @Transactional
    public Long deleteMember(Long memberId) {
        //1. 삭제 대상 찾기
        Optional<Member> findMember = memberRepository.findById(memberId);

        //*예외처리 : member optional
        if (!findMember.isPresent()) {
            throw new DataNotFoundException("회원을 찾을 수 없습니다.");
        }

        Member member = findMember.get();
        //로그인된 사용자명
        String loggedName = SecurityContextHolder.getContext().getAuthentication().getName();

        //*예외처리 : 삭제 회원 == 로그인 회원
        if (!member.getName().equals(loggedName)) {
            throw new UnauthorizedAccessException("접근 권한이 없습니다.");
        }

        memberRepository.delete(member);
        return memberId;
    }


}
