package com.ssproject.bms.book;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssproject.bms.book.dto.BookOrderDTO;
import com.ssproject.bms.book.entity.BookOrderEntity;
import com.ssproject.bms.book.repository.BookRepositoryTests;
import com.ssproject.bms.book.vo.NaverBookApiVO;
import com.ssproject.bms.book.vo.NaverBookSearchResultApiVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class BookTests {

    @Autowired
    private BookRepositoryTests bookRepositoryTests;

    public static final String SEARCH_TEXT = "스프링 부트";
    public static final String NAVER_BOOK_API_URL = "https://openapi.naver.com/v1/search/book.json";
    public static final String CLIENT_ID = "";
    public static final String CLIENT_SECRET = "";

    @Test
    public void getNaverBookSearchList() {
        ResponseEntity<List<NaverBookApiVO>> list = getNaverBookSearchApi(SEARCH_TEXT);
        System.out.println(list.getBody());
    }

    @Test
    public void saveOrderBookList() {
        ResponseEntity<List<NaverBookApiVO>> list = getNaverBookSearchApi(SEARCH_TEXT);
        List<BookOrderEntity> bookOrderEntityList = list.getBody().stream()
                .map(info -> BookOrderEntity.createBuilder()
                        .orderBookTitle(info.getTitle())
                        .mberId(21)
                        .build())
                .collect(Collectors.toList());
        bookRepositoryTests.saveAll(bookOrderEntityList);
    }

    @Test
    public void getOrderBookList() {
        List<BookOrderEntity> bookOrderEntityList = bookRepositoryTests.findAll();
        List<BookOrderDTO> bookOrderDTOList = new ArrayList<>();

        for (BookOrderEntity info : bookOrderEntityList) {
            bookOrderDTOList.add(BookOrderDTO.toBookOrderDTO(info));
        }

        System.out.println(bookOrderDTOList);
    }

    public ResponseEntity<List<NaverBookApiVO>> getNaverBookSearchApi(@PathVariable String searchText) {

        String clientId = "";
        String clientSecret = "";

        URI uri = UriComponentsBuilder
                .fromUriString(NAVER_BOOK_API_URL)
                .queryParam("query", searchText)
                .queryParam("display", 10)
                .queryParam("start", 1)
                .queryParam("sort", "sim")
                .encode()
                .build()
                .toUri();

        RequestEntity<Void> requestEntity = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", CLIENT_ID)
                .header("X-Naver-Client-Secret", CLIENT_SECRET)
                .build();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        NaverBookSearchResultApiVO naverBookSearchResultApiVO = null;
        try {
            naverBookSearchResultApiVO = objectMapper.readValue(responseEntity.getBody(), NaverBookSearchResultApiVO.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        List<NaverBookApiVO> naverBookApiList = naverBookSearchResultApiVO.getItems();

        return ResponseEntity.ok().body(naverBookApiList);
    }
}
