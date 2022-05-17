package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany  //실무에서 @ManyToMany 쓰면 안된다
    @JoinTable(name = "category_item",  //중간 테이블 매핑해주어야한다. 관계형DB는 일대다 다대일로 풀어내야하므로
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items = new ArrayList<>();


    //셀프로 양방향연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    //== (양방향)연관관계 (편의)메서드 - 핵심적으로 컨트롤 하는 쪽에 위치한다 ==//
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);

    }





}
