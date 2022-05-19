package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberForm { //회원 등록 폼 객체 (폼 객체를 사용해서 화면 계층과 서비스 계층을 명확하게 분리)

    @NotEmpty(message = "회원 이름은 필수 입력사항입니다.")
    private String name;

    private String city;
    private String street;
    private String zipcode;

}
