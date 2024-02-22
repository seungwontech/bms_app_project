package com.ssproject.bms.book.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssproject.bms.board.dto.CommentDTO;
import com.ssproject.bms.book.vo.NaverBookSearchResultApiVO;
import com.ssproject.bms.book.vo.NaverBookApiVO;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
public class BookController {

    @GetMapping("/book/list")
    public String naverApiBookList(String searchText, Model model) {
        String clientId = "";
        String clientSecret = "";

        searchText = "스프링부트";

        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/book.json")
                .queryParam("query", searchText)
                .queryParam("display", 10)
                .queryParam("start", 1)
                .queryParam("sort", "sim")
                .encode()
                .build()
                .toUri();

        RequestEntity<Void> requestEntity = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
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
        model.addAttribute("naverBookApiList", naverBookApiList);
        return "book/book_naver_search_list.html";
    }

    @GetMapping("/book/getNaverBookSearchList/{searchText}")
    public ResponseEntity<List<NaverBookApiVO>> getNaverBookSearchList(@PathVariable String searchText) {
        System.out.println("searchText : "+searchText);

        String clientId = "";
        String clientSecret = "";

        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/book.json")
                .queryParam("query", searchText)
                .queryParam("display", 10)
                .queryParam("start", 1)
                .queryParam("sort", "sim")
                .encode()
                .build()
                .toUri();

        RequestEntity<Void> requestEntity = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
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
