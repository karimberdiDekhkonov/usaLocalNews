package com.example.usalocalnews.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.asynchttpclient.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class NewsService {

    private static final String RAPIDAPI_KEY = "1cbe164781mshb75282ae092075fp112ae1jsn85d0ea7ea2a5";
    private static final String RAPIDAPI_HOST = "google-news13.p.rapidapi.com";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public List<JsonNode> getNews(String keyword) throws IOException {
        AsyncHttpClient client = new DefaultAsyncHttpClient();

        try {
            CompletableFuture<Response> futureResponse = client.prepare("GET", "https://google-news13.p.rapidapi.com/search?keyword=" + keyword + "%20city&lr=en-US")
                    .setHeader("x-rapidapi-key", RAPIDAPI_KEY)
                    .setHeader("x-rapidapi-host", RAPIDAPI_HOST)
                    .execute()
                    .toCompletableFuture();

            // Block and wait for the response
            Response response = futureResponse.get();

            List<JsonNode> articles = new ArrayList<>();
            String responseBody = response.getResponseBody();
            JsonNode jsonNode = OBJECT_MAPPER.readTree(responseBody);
            JsonNode items = jsonNode.path("items");

            // Add a maximum of 10 articles to the list
            int count = 0;
            for (JsonNode item : items) {
                if (count >= 10) break;
                articles.add(item);
                count++;
            }

            return articles;

        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error fetching news: " + e.getMessage());
            return new ArrayList<>(); // Return an empty list in case of an error
        } finally {
            client.close();
        }
    }
}
