package com.nioxen.monster;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Info {

    //private static final String NAME_PATTERN = "(\\s*\\w*)+(?=\\()";
    private static final String NAME_PATTERN = "([A-Z])\\w+";
    private static final String STAR_PATTERN = "(\\d)";
    private static final String READ_ERROR = "Unable to read value";

    private String name;
    private String element;
    private int stars;
    private char starColor;
    private String thumbnail;
    private String type;
    private String awakenedBonus;
    private String obtainableFrom;


    public void parseStarColor(String starColor) {
        this.starColor = starColor.toUpperCase().charAt(0);
    }
    public char getStarColor() {
        return this.starColor;
    }

    public void parseName(String name) {
//        String s = name;
//        if (this.starColor == 'P'){
//            s = name.split("-")[1];
//        }
//        Pattern p = Pattern.compile(NAME_PATTERN);
//        Matcher m = p.matcher(s);
//        this.name = m.find() ? m.group().trim() : READ_ERROR;
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    public void parseStars(String stars){
        if (stars.contains("(")) {
            String s = stars;
            if (this.starColor == 'P'){
                s = stars.split("-")[1];
            }
            Pattern p = Pattern.compile(STAR_PATTERN);
            Matcher m = p.matcher(s);
            if (m.find()) {
                this.stars = Integer.parseInt(m.group());
                return;
            }
        }
        this.stars = 1;
    }
    public int getStars() {
        return this.stars;
    }

    public void parseElement(String element) {
        this.element = element;
    }
    public String getElement() {
        return this.element;
    }

    public void parseCommonInfo(List<String> common) {
        this.type = common.get(0).split(":")[1].trim();
        this.obtainableFrom = common.get(1).split(":")[1].trim();
        this.awakenedBonus = common.get(2).split(":")[1].trim();
    }
    public String getObtainableFrom() {
        return this.obtainableFrom;
    }
    public String getType(){
        return this.type;
    }
    public String getAwakenedBonus(){
        return this.awakenedBonus;
    }

    public void parseThumbnails(List<String> thumbnails) {
        if (this.starColor == 'P'){
            this.thumbnail = thumbnails.get(1);
        }
        this.thumbnail = thumbnails.get(0);
    }
    public String getThumbnail() {
        return this.thumbnail;
    }

}
