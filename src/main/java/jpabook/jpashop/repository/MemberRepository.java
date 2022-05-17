package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository //@Component 스캔대상이므로 자동으로 스프링 빈에 등록되어짐
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member); //트랜잭션 커밋시점에 DB에 반영
    }

    public Member findOne(Long id) { //단건 조회(타입, PK)
        return em.find(Member.class, id);
    }

    public List<Member> findAll() { //List 조회는 JPQL사용(객체를 대상으로한 쿼리)
       return em.createQuery("select m from Member m", Member.class) // (JPQL, 반환타입)
               .getResultList();
    }

    public List<Member> findByName(String name) { //이름으로 조회
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }





}
