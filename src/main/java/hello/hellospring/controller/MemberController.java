package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {
    //이렇게도 가능하지만 스프링이 관리하면 다 컨테이너에 등록하고 컨테이너로부터 객체 받아서 사용
    //new 하면 다른 컨트롤러들이 MemberService를 가져다 사용할 수 있다. 공용으로 사용하는게 좋음
    //@Autowired private MemberService memberService;//필드 주입(별로 좋은 방법이 아님)

    private MemberService memberService;

    //생성자에 Autowired 있으면 Spring Container에 있는 MemberService를 가져다가 연결을 시켜줌
    @Autowired
    public MemberController(MemberService memberService) {//생성자 DI
        this.memberService = memberService;
        //setter DI 단점 : 아무 개발자나 호출 가능해진다.
        //개발은 최대한 호출하지 않아야 될 메서드가 호출되면 안된다.
        //memberService.setMemberRepository();
        System.out.println("memberService = " + memberService.getClass());
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        System.out.println("member = " + member.getName());

        memberService.join(member);

        return "redirect:/";//회원가입이 끝나면 home 화면으로 보내기
    }

    @GetMapping("/members")
    public String list(Model model) {
        //Ctrl + Alt + V : 자동 변수 생성
        List<Member> members = memberService.findMembers();
        //member의 리스트를 model에 담아서 뷰에 넘김(request.setAttribute() 역할)
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
