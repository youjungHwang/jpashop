package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor // private final EntityManager em;
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if(item.getId() == null) { //새로 생성한 객체
            em.persist(item); //신규 등록
        }else {
            em.merge(item); //이미 JPA통해 DB들어간것, 업데이트와 비슷
        }
    }

    public Item findOne(Long id) { //단건 조회는 find()
        return em.find(Item.class, id);
    }

    public List<Item> findAll() { //전체 조회는 JPQL
        return em.createQuery("select i from Item i", Item.class )
                .getResultList();
    }
}
