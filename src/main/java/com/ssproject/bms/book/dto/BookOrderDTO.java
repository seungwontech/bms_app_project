package com.ssproject.bms.book.dto;

import com.ssproject.bms.book.entity.BookOrderEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class BookOrderDTO {
    private int orderSn;
    private String orderBookTitle;
    private int mberId;
    
    public static BookOrderDTO toBookOrderDTO(BookOrderEntity bookOrderEntity) {
        BookOrderDTO bookOrderDTO = new BookOrderDTO();
        bookOrderDTO.setOrderSn(bookOrderEntity.getOrderSn());
        bookOrderDTO.setOrderBookTitle(bookOrderEntity.getOrderBookTitle());
        bookOrderDTO.setOrderSn(bookOrderEntity.getMberId());
        return bookOrderDTO;
    }

}
