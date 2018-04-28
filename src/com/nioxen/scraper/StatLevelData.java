package com.nioxen.scraper;

import java.util.ArrayList;
import java.util.List;

public class StatLevelData {
    List<String> stars;
    List<String> levels;

    List<List<String>> hpList = new ArrayList<>();
    List<List<String>> atkList = new ArrayList<>();
    List<List<String>> defList = new ArrayList<>();

    public List<String> getStars() {
        return stars;
    }

    public void setStars(List<String> stars) {
        this.stars = stars;
    }

    public List<String> getLevels() {
        return levels;
    }

    public void setLevels(List<String> levels) {
        this.levels = levels;
    }

    public List<List<String>> getHpList() {
        return hpList;
    }

    public void setHpList(List<List<String>> hpList) {
        this.hpList = hpList;
    }

    public List<List<String>> getAtkList() {
        return atkList;
    }

    public void setAtkList(List<List<String>> atkList) {
        this.atkList = atkList;
    }

    public List<List<String>> getDefList() {
        return defList;
    }

    public void setDefList(List<List<String>> defList) {
        this.defList = defList;
    }
}
