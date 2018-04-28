package com.nioxen.scraper;

import java.util.List;

public class SkillData {
    List<String> basicInfos;
    List<String> thumbnails;
    List<List<String>> levels;

    public List<String> getBasicInfos() {
        return basicInfos;
    }

    public void setBasicInfos(List<String> basicInfos) {
        this.basicInfos = basicInfos;
    }

    public List<String> getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(List<String> thumbnails) {
        this.thumbnails = thumbnails;
    }

    public List<List<String>> getLevels() {
        return levels;
    }

    public void setLevels(List<List<String>> levels) {
        this.levels = levels;
    }
}
