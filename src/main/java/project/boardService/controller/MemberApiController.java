package project.boardService.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.boardService.service.MemberService;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /**
     * 회원 삭제(탈퇴) API
     * @param memberId : 삭제 회원 ID
     * @return : 응답
     */
    @DeleteMapping("/{memberId}")
    public ResponseEntity<?> deleteMember(@PathVariable Long memberId,
                                          HttpServletRequest request, HttpServletResponse response) {
        memberService.deleteMember(memberId);
        logoutUser(request, response);
        return ResponseEntity.ok().build();
    }

    // 회원 로그아웃 (세션 정리를 통한)
    private void logoutUser(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, null);
        SecurityContextHolder.clearContext(); //세션을 정리
    }
}
