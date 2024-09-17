package com.example.usalocalnews.service;

import com.example.usalocalnews.model.NewsDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.asynchttpclient.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class NewsService {

    @Value("${api.rapid.key}")
    private String rapidApiKey;

    @Value("${api.rapid.host}")
    private String rapidApiHost;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final OpenAIService openAIService;

    public NewsService(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    public List<NewsDto> getNews(String keyword, int page) throws IOException {
        // CHECK AND FORMAT THE KEYWORD
        keyword = checked(keyword);
        AsyncHttpClient client = new DefaultAsyncHttpClient();

        // CONSTRUCT THE REQUEST URL
        String path = "https://google-news13.p.rapidapi.com/search?keyword=" + keyword + "%20city&lr=en-US";

        try {
            // SEND THE GET REQUEST AND FETCH THE RESPONSE ASYNCHRONOUSLY
            CompletableFuture<Response> futureResponse = client.prepare("GET", path)
                    .setHeader("x-rapidapi-key", rapidApiKey)
                    .setHeader("x-rapidapi-host", rapidApiHost)
                    .execute()
                    .toCompletableFuture();

            // BLOCK AND WAIT FOR THE RESPONSE
            Response response = futureResponse.get();
            String responseBody = response.getResponseBody();

            // PARSE THE RESPONSE BODY TO JSON
            JsonNode jsonNode = OBJECT_MAPPER.readTree(responseBody);
            JsonNode items = jsonNode.path("items");

            // CALCULATE START AND END INDICES BASED ON THE PAGE NUMBER
            int start = (page - 1) * 10;  // START INDEX FOR THE PAGE (0-based)
            int end = start + 10;         // END INDEX FOR THE PAGE

            List<JsonNode> articles = new ArrayList<>();

            // LOOP THROUGH THE ITEMS AND ADD ONLY THE NEWS ARTICLES IN THE PAGE RANGE
            for (int i = start; i < end && i < items.size(); i++) {
                articles.add(items.get(i));
            }

            // CONVERT THE SELECTED ARTICLES TO A LIST OF NewsDto OBJECTS
            List<NewsDto> newsDtoList = fromJsonNodeListToNewsDtoList(articles);

            // FILTER THE NEWS USING OPENAI AND RETURN THE FINAL LIST
            return openAIService.checkingNews(newsDtoList, keyword);

        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error fetching news: " + e.getMessage());
            return new ArrayList<>(); // RETURN AN EMPTY LIST IN CASE OF ERROR
        } finally {
            client.close();
        }
    }


    private String checked(String keyword) {
        // USING STRINGBUILDER TO REMOVE SPACES EFFICIENTLY
        StringBuilder modifiedKeyword = new StringBuilder();

        for (int i = 0; i < keyword.length(); i++) {
            // ONLY APPEND CHARACTERS THAT ARE NOT SPACES
            if (keyword.charAt(i) != ' ') {
                modifiedKeyword.append(keyword.charAt(i));
            }
        }

        // RETURN THE STRING WITHOUT SPACES
        return modifiedKeyword.toString();
    }


    private List<NewsDto> fromJsonNodeListToNewsDtoList(List<JsonNode> request) {
        List<NewsDto> response = new ArrayList<>();
        for (JsonNode jsonNode : request) {
            String title = jsonNode.get("title").asText();
            String snippet = jsonNode.get("snippet").asText();
            String publisher = jsonNode.get("publisher").asText();

            long timestamp = jsonNode.get("timestamp").asLong();
            LocalDateTime dateTime = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();

            String newsUrl = jsonNode.get("newsUrl").asText();

            response.add(new NewsDto(title, snippet, publisher, String.valueOf(dateTime), newsUrl));
        }
        return response;
    }

}
