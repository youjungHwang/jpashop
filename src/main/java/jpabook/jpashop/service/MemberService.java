package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service //@Component 스캔대상이므로 자동으로 스프링 빈에 등록되어짐
@Transactional(readOnly = true) //JPA에서 모든 데이터변경이나 로직은 @Transactional안에서 해야함
@RequiredArgsConstructor // final 필드에 대해 생성자를 만들어주는 lombok의 annotation.
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 기능 개발
     * 1. 회원 가입
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증 로직
        memberRepository.save(member);
        return member.getId();
    }

    private void  validateDuplicateMember(Member member) { //[실무] 멀티쓰레드 고려해서 DB에 멤버 네임을 유니크 제약조건 걸기
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 기능 개발
     * 2. 회원 전체 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //단건 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
