package project.boardService.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import project.boardService.dto.CreateMemberDto;
import project.boardService.dto.LoginDto;
import project.boardService.exception.DataNotFoundException;
import project.boardService.service.MemberService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // User Login
    @GetMapping("/member/login")
    public String getLoginForm(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "member/login";
    }

    //로그인 관련 로직은 "/login" spring security 에게 이관한다.

    // Create New User
    @GetMapping("/member/new")
    public String createMemberForm(Model model) {
        model.addAttribute("CreateMemberDto", new CreateMemberDto());
        return "member/createMember";
    }

    @PostMapping("/member/new")
    public String createUser(@ModelAttribute CreateMemberDto createMemberDto, Model model) {

        if (createMemberDto.getName().isEmpty() || createMemberDto.getEmail().isEmpty()) {
            model.addAttribute("createMemberError", "회원가입 양식을 모두 입력해 주십시오.");
            System.out.println("회원가입 양식을 모두 입력해 주십시오.");
            return "member/createMember";
        }

        if (!createMemberDto.getPassword().equals(createMemberDto.getPasswordEqual())) {
            model.addAttribute("createMemberError", "비밀번호가 일치 하지 않습니다.");
            System.out.println("비밀번호가 일치 하지 않습니다.");
            return "member/createMember";
        }

        try {
            memberService.createMember(createMemberDto.getName(), createMemberDto.getPassword(), createMemberDto.getEmail());
        } catch (DataNotFoundException e) {
            model.addAttribute("createMemberError", "이미 등록된 사용자 입니다.");
            System.out.println("이미 등록된 사용자 입니다.");
            return "member/createMember";
        } catch (Exception e) {
            e.printStackTrace();
            return "member/createMember";
        }

        return "redirect:/";
    }

    // User Information
    @GetMapping("/member/private/info")
    public String userInfo(Principal principal, Model model) {
        String memberName = principal.getName();
        model.addAttribute("memberName", memberName);
        return "member/memberInfo";
    }
}
