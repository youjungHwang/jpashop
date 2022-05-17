package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.transaction.Transactional;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class) //스프링과 테스트 통합
@SpringBootTest //스프링 부트 띄우고 테스트(이게 없으면 @Autowired 다 실패)
@Transactional //데이터변경,(커밋 안하고)롤백(영속성컨텍스트 flush 안 함) -> 커밋하고 싶으면 @Rollback(false)하면 insert문을 DB로 날림
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        //given ~주어졌을 때
        Member member = new Member();
        member.setName("kim");

        //when ~실행 하면
        Long savedId = memberService.join(member);

        //then ~된다
        assertEquals(member, memberRepository.findOne(savedId)); //JPA - Transactional 안에서 id값 같으면 같은 영속성컨텍스트에서 하나로 관리
      }

      @Test(expected = IllegalStateException.class)
      public void 중복_회원_예외() throws Exception {
          //given
          Member member1 = new Member();
          member1.setName("kim");

          Member member2 = new Member();
          member2.setName("kim");

          //when
          memberService.join(member1);
          memberService.join(member2);

          //then
          fail("예외가 발생해야 한다."); //여기로 오면 안되므로 fail문 작성
        }

}