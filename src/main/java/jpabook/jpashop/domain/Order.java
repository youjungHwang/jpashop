package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") // member_id가 FK, Member와 Order는 양방향연관관계 - 주인 정하기
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // OrderItem테이블에 있는 order필드
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // 일대일관계는 FK는 어디다 둬도 된다, 그럼 access잦은 쪽에 둔다, 연관관계주인 Order가 됨
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 [ORDER, CANCEL]

    //== (양방향)연관관계 (편의)메서드 - 핵심적으로 컨트롤 하는 쪽에 위치한다 ==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    /**
     *  핵심 비즈니스 로직
     *  복잡한 생성 메서드는 별로의 생성 메서드로 빼서 구현
     */
//    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) { //OrderItem 여러개 넣을 수 있음(가변인자)
//
//    }


}
