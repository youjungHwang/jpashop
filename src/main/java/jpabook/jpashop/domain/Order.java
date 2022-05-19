package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //직접 생성을 막고 이미 있는 메서드 사용해서 하라는 의미 (생성메서드)
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
     *  생성 메서드
     *  장점 : 생성 관련된 것(여기서 set)은 이 부분만 수정하면 됨 - 유지보수 쉬움
     */
    //static메서드는 클래스 메서드 - 클래스이용해서 호출, 유틸리티성 메서드 작성시 유리
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) { //OrderItem 여러개 넣을 수 있음(가변인자)
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order; // 리턴타입 Order -> return order;
    }

    /**
     * 비즈니스 로직 - 주문 취소
     */
    public void cancel() {
        if(delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL); //this는 필드 의미함
        for(OrderItem orderItem : orderItems) {
            orderItem.cancel(); //OrderItem에도 비즈니스 로직 추가
        }
    }

    /**
     * 조회 로직 - 전체 주문 가격 조회
     */
    public int getTotalPrice() { //OrderItem 다 더하기
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
