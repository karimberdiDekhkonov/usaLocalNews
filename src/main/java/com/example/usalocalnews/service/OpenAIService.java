package com.example.usalocalnews.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class OpenAIService {
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/engines/gpt-4o-mini/completions";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public String getGPT4Response(String prompt) throws IOException {
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        try {
            String requestBody = OBJECT_MAPPER.writeValueAsString(new GPTRequest(prompt));

            CompletableFuture<Response> futureResponse = client.preparePost(OPENAI_API_URL)
                    .setHeader("Authorization", "Bearer " + "sk-mvOdnz6TerEKcGPrpzUHBLOnU6paHX_Dkz7jQ7Bl0kT3BlbkFJze3VbyjDEFD9Uw6udQY-hXDuxlShMFf2PPvNYz3lQA")
                    .setHeader("Content-Type", "application/json")
                    .setBody(requestBody)
                    .execute()
                    .toCompletableFuture();

            Response response = futureResponse.get();
            String responseBody = response.getResponseBody();
            JsonNode jsonNode = OBJECT_MAPPER.readTree(responseBody);

            return jsonNode.path("choices").get(0).path("text").asText();

        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error fetching response: " + e.getMessage());
            return "Error occurred";
        } finally {
            client.close();
        }
    }

    private static class GPTRequest {
        private String prompt;
        private int max_tokens = 50; // Adjust as needed

        public GPTRequest(String prompt) {
            this.prompt = prompt;
        }

        // Getters and setters
        public String getPrompt() {
            return prompt;
        }

        public void setPrompt(String prompt) {
            this.prompt = prompt;
        }

        public int getMax_tokens() {
            return max_tokens;
        }

        public void setMax_tokens(int max_tokens) {
            this.max_tokens = max_tokens;
        }
    }
}