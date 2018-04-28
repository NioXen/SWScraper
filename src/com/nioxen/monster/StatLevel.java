package com.nioxen.monster;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StatLevel {
    private int level;
    private int stars;
    private int hp;
    private int atk;
    private int def;

    public void parseLevel(String level) {
        if (level.equals("Min")) {
            this.level = 1;
            return;
        }
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(level);
        this.level = m.find() ? Integer.parseInt(m.group()) : 1;
    }
    public void parseStars(String stars) {
        if (stars.contains("★")) {
            this.stars = stars.trim().toCharArray().length;
            return;
        }
        this.stars = 1;
    }
    public void parseHp(String s) {
        this.hp = Integer.parseInt(s);
    }
    public void parseAtk(String s) {
        this.atk = Integer.parseInt(s);
    }
    public void parseDef(String s) {
        this.def = Integer.parseInt(s);
    }

}
