package com.nioxen;

public class MonsterHeader {

    //BasicInfo
    private String name;
    private String element;

    //URLS
    private String absoluteMonsterURL;
    private String absoluteThumbnailURL;

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

    public String getAbsoluteMonsterURL() {
        return absoluteMonsterURL;
    }

    public void setAbsoluteMonsterURL(String absoluteMonsterURL) {
        this.absoluteMonsterURL = absoluteMonsterURL;
    }

    public String getAbsoluteThumbnailURL() {
        return absoluteThumbnailURL;
    }

    public void setAbsoluteThumbnailURL(String absoluteThumbnailURL) {
        this.absoluteThumbnailURL = absoluteThumbnailURL;
    }

    @Override
    public String toString() {
        String dl = ",";
        return name + dl + element + dl + absoluteMonsterURL + dl + absoluteThumbnailURL + '\n';
    }
}
