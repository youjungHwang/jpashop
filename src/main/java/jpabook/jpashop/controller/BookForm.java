package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookForm { //엔티티는 핵심 비즈니스 로직만 가지고 있고, 화면을 위한 로직은 없어야 한다. 화면에 맞는 Form생성
    // Item 공통 속성
    private Long id; //수정을 위해 id필요

    private String name;
    private int price;
    private int stockQuantity;

    // Book 속성
    private String author;
    private String isbn;
}
