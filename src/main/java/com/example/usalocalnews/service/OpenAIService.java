package com.example.usalocalnews.service;

import com.example.usalocalnews.model.NewsDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OpenAIService {
    private final OpenAiChatModel chatModel;
    private final ObjectMapper objectMapper;

    public OpenAIService(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
        this.objectMapper = new ObjectMapper();
    }

    public List<NewsDto> checkingNews(List<NewsDto> request, String cityName) {
        // CREATE A LIST OF NEWS TITLES TO SEND TO THE AI MODEL
        List<String> newsTitles = request.stream().map(NewsDto::getTitle).collect(Collectors.toList());

        // CONVERT THE LIST OF TITLES INTO A JSON FORMAT
        String jsonRequest = "";
        try {
            jsonRequest = objectMapper.writeValueAsString(newsTitles);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        // SEND A MESSAGE TO THE AI MODEL ASKING FOR VALID INDEX NUMBERS BASED ON THE CITY NAME
        String messageText = "Here are news titles from USA. Please return only the valid index numbers related to " + cityName
                + ": " + jsonRequest;

        // GET THE RESPONSE FROM THE AI MODEL
        String responseFromChatModel = chatModel.call(messageText);

        // EXTRACT THE INDEX NUMBERS FROM THE RESPONSE
        List<Integer> validIndices = extractIndicesFromResponse(responseFromChatModel);

        // FILTER THE ORIGINAL LIST OF NEWS BASED ON THE EXTRACTED INDEX NUMBERS
        List<NewsDto> filteredNews = new ArrayList<>();
        for (Integer index : validIndices) {
            if (index >= 0 && index < request.size()) {
                filteredNews.add(request.get(index));
            }
        }

        // RETURN THE FILTERED LIST OF NEWS
        return filteredNews;
    }

    private List<Integer> extractIndicesFromResponse(String response) {
        // REMOVE ALL NON-NUMERIC CHARACTERS EXCEPT COMMAS, THEN SPLIT BY COMMAS AND CONVERT TO INTEGERS
        String numericPart = response.replaceAll("[^0-9,]", "");
        return Arrays.stream(numericPart.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}
