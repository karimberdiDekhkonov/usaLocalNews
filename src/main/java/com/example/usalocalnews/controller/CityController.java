package com.example.usalocalnews.controller;

import com.example.usalocalnews.service.CityService;
import com.example.usalocalnews.service.NewsService;
import com.example.usalocalnews.service.OpenAIService;
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
    @Autowired
    private OpenAIService openAIService;

    //SEARCHING FOR CITIES
    @GetMapping("/cities/{startsWith}")
    public HttpEntity<Set<String>> getCitiesList(@PathVariable String startsWith) {
        logger.info("Received request to search for cities starting with: {}", startsWith);
        Set<String> citiesOutput = cityService.getCitiesList(startsWith);
        logger.info("Returning cities list with {} results.", citiesOutput.size());
        return ResponseEntity.ok(citiesOutput);
    }

    //GETTING NEWS WITH CITY NAME
    @GetMapping("/news")
    public HttpEntity<List<JsonNode>> getNews(@RequestParam String keyword) throws IOException {
        logger.info("Received request to get news for city: {}", keyword);
        List<JsonNode> news = newsService.getNews(keyword);
        logger.info("Returning {} news articles for city: {}", news.size(), keyword);
        return ResponseEntity.ok(news);
    }

    //IMPLEMENTING AI TO CHECK NEWS
    @GetMapping("/ask")
    public String askQuestion(@RequestParam String prompt) {
        logger.info("Received AI question prompt: {}", prompt);
        try {
            String response = openAIService.getGPT4Response(prompt);
            logger.info("Returning AI response.");
            return response;
        } catch (Exception e) {
            logger.error("Error occurred while processing AI request: {}", e.getMessage(), e);
            return "Error occurred while processing your request.";
        }
    }
}
