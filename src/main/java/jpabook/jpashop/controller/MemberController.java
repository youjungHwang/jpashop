package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController { //회원 등록 컨트롤러

    private final MemberService memberService; //Controller는 주로 Service 주입받음

    @GetMapping("/members/new")
    public String createForm(Model model) { //Controlle에서 view로 갈때 model에 데이터를 실어서 넘김
        model.addAttribute("memberForm",new MemberForm()); //MemberForm() 빈 껍데기 객체 가지고 view로 넘어감 -> 화면에서 MemberForm()객체 접근 가능해짐
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) { //@Valid하면 MemberForm에 이는 Validation 해줌(@NotEmpty..)
        //Member가 아닌 MemberForm 쓴 이유는 작성폼과 Member가 완벽히 fit하지 않으므로 새로 Form만들어서 사용

        //BindingResult사용 시 오류가 담겨서 나머지 코드가 실행 됨, createMemberForm에 오류메세지 넘어감
        if(result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/"; //첫 페이지로 리다이렉트
    }

    @GetMapping("/members")
    public String list(Model model) {
        //화면에 뿌리는 게 Member엔티티의 필드와 완전 일치했기에 반환했지만, 실무에서는 엔티티가 아닌 DTO 반환 권장
        //단, API만들 때 절대 엔티티를 외부로 반환하면 안된다.
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
