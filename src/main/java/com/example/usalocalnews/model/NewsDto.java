package com.example.usalocalnews.model;

import java.time.LocalDateTime;

public class NewsDto {
    private String title;
    private String snippet;
    private String publisher;
    private String dateTime;
    private String newsUrl;

    public NewsDto() {
    }

    public NewsDto(String title, String snippet, String publisher, String dateTime, String newsUrl) {
        this.title = title;
        this.snippet = snippet;
        this.publisher = publisher;
        this.dateTime = dateTime;
        this.newsUrl = newsUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    @Override
    public String toString() {
        return "NewsDto{" +
                "title='" + title + '\'' +
                ", snippet='" + snippet + '\'' +
                ", publisher='" + publisher + '\'' +
                ", dateTime=" + dateTime +
                ", newsUrl='" + newsUrl + '\'' +
                '}';
    }
}
