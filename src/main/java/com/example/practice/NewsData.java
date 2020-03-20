package com.example.practice;

import java.io.Serializable;
//Serializable : data가 많을때 (직렬하라) 정보가 복잡하고 있고, 희한한 데이터일때 알아들을 수 있는 코드로 변환
//data 분류용
public class NewsData implements Serializable {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getdescription() {
        return description;
    }

    public void setdescription(String description) {
        this.description = description;
    }

    private String title;
    private String urlToImage;
    private String description;
}
