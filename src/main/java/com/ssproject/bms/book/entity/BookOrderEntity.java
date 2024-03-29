package com.ssproject.bms.book.entity;

import com.ssproject.bms.board.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "book_order_tbl")
public class BookOrderEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderSn;

    private String orderBookTitle;
    private int mberId;

    @Builder(builderMethodName = "createBuilder")
    public BookOrderEntity(String orderBookTitle, int mberId) {
        this.orderBookTitle = orderBookTitle;
        this.mberId = mberId;
    }
}
