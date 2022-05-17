package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 상속관계전략 - SINGLE_TABLE 한 테이블에 다 넣음
@DiscriminatorColumn(name = "dtype") // 싱글테이블전략이므로 구분자 지정
@Getter
@Setter
public abstract class Item { // 추상 클래스

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    /**
     * 사용할 데이터(stockQuantity)를 가지고 있는 [엔티티 안에 비즈니스 로직 추가]하는 것이 응집도가 있다.
     *   Service에서 비즈니스 로직을 짜는 것 보다 객체 지향적이다. 관리용이.
     *   Setter를 이용해서 stockQuantity를 변경하는 것이 아닌 비즈니스 로직을 만들어서 수행
     */
    public void addStock(int quantity) { //재고 수량 증가
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) { //재고 수량 감소
        int restStock = this.stockQuantity - quantity;
        if(restStock < 0) {
            throw new NotEnoughStockException("need more stock"); //예외 만들어야함
        }
        this.stockQuantity = restStock;
    }




}
