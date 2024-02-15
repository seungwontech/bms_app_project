package com.ssproject.bms.book.vo;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class NaverBookSearchResultApiVO {
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<NaverBookApiVO> items;
}
