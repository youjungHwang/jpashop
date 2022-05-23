package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sound.sampled.DataLine;
import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     *   memberId를 가져오기 위해선 MemberRepository있어야 함
     *   어떠한 도메인이든 Controller, Service, Repository에서 접근 가능
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성(회원정보 Address)
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성 (생성메서드 이용) - static으로 생성했으므로 객체 생성없이 클래스.메서드명으로 가져옴(위 코드와 차이점)
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성 (생성메서드 이용)
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장 - order 식별자값 반환
        orderRepository.save(order);
        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) { //주문취소할 때 아이디값만 넘어감
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소(이미 Order에 주문취소 비즈니스 로직 만들었음)
        order.cancel();
    }

    /**
     * 검색
     * [실무] 아키텍처마다 다르겠지만, (지금처럼) 단순 위임의 경우
     * Controller에서 바로 Repository 접근해서 처리 가능
     */
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByString(orderSearch);
    }






}
