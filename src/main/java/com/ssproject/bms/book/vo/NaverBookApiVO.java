package com.ssproject.bms.book.vo;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class NaverBookApiVO {
    private String title;
    private String link;
    private String image;
    private String author;
    private String discount;
    private String publisher;
    private String pubdate;
    private String isbn;
    private String description;
}
