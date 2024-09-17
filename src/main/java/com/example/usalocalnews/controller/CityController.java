package com.example.usalocalnews.controller;

import com.example.usalocalnews.model.NewsDto;
import com.example.usalocalnews.service.CityService;
import com.example.usalocalnews.service.NewsService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController()
@RequestMapping("/api/usa")
public class CityController {

    private static final Logger logger = LoggerFactory.getLogger(CityController.class);

    @Autowired
    private CityService cityService;
    @Autowired
    private NewsService newsService;

    //SEARCHING FOR CITIES
    @GetMapping("/cities/{startsWith}")
    public HttpEntity<Set<String>> getCitiesList(@PathVariable String startsWith) {
        logger.info("Received request to search for cities starting with: {}", startsWith);
        Set<String> citiesOutput = cityService.getCitiesList(startsWith);
        logger.info("Returning cities list with {} results.", citiesOutput.size());
        return ResponseEntity.ok(citiesOutput);
    }

    // GETTING NEWS WITH CITY NAME AND PAGE NUMBER
    @GetMapping("/news")
    public HttpEntity<List<NewsDto>> getNews(@RequestParam String keyword, @RequestParam(defaultValue = "1") int page) throws IOException {
        // LOG THE REQUEST FOR NEWS WITH THE GIVEN KEYWORD AND PAGE
        logger.info("Received request to get news for city: {} on page: {}", keyword, page);

        // CALL THE SERVICE TO GET THE NEWS BASED ON THE KEYWORD AND PAGE
        List<NewsDto> news = newsService.getNews(keyword, page);

        // LOG THE NUMBER OF NEWS ARTICLES RETURNED
        logger.info("Returning {} news articles for city: {} on page: {}", news.size(), keyword, page);

        // RETURN THE NEWS ARTICLES IN THE HTTP RESPONSE
        return ResponseEntity.ok(news);
    }
}
