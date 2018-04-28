package com.nioxen.scraper;

import java.util.List;

public class InfoData {
    private String name;
    private String element;
    private String starColor;
    private List<String> thumbnails;
    private List<String> commonInfo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getStarColor() {
        return starColor;
    }

    public void setStarColor(String starColor) {
        this.starColor = starColor;
    }

    public List<String> getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(List<String> thumbnails) {
        this.thumbnails = thumbnails;
    }

    public List<String> getCommonInfo() {
        return commonInfo;
    }

    public void setCommonInfo(List<String> commonInfo) {
        this.commonInfo = commonInfo;
    }
}
