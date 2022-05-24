package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id") // order_id(FK)
    @JsonIgnore
    private Order order;

    private int orderPrice; //주문 가격
    private int count; //주문 수량

    /**
     *  생성 메서드
     */
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        // 할인도 가능할 수 있기 때문에 원래 필드가 아닌 객체 생성해서 set해주는 것.
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        //주문시 재고를 주문수량만큼 줄여야함
        item.removeStock(count);
        return orderItem;
    }


    /**
     * 비즈니스 로직 - 주문 취소
     */
    public void cancel() { //핵심은 주문 수량만큼 재고 원상복구
        getItem().addStock(count);

    }

    /**
     * 조회 로직 - 주문상품 전체 가격 조회
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
