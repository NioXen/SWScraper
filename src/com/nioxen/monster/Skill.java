package com.nioxen.monster;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Skill {

    private String name;
    private String description;
    private String absoluteThumbnailURL;
    private List<SkillLevel> levels;

    public void parseBasic(String basic) {
        String[] s = basic.split(":");
        this.name = s[0].trim();
        this.description = s[1].trim();
    }

    public void parseThumbnail(String thumbnail) {
        this.absoluteThumbnailURL = thumbnail;
    }

    public void parseLevels(List<String> levels) {
        if (levels.size() > 0) {
            this.levels = new ArrayList<>();
            for (int i = 0; i < levels.size(); i++) {
                SkillLevel sl = new SkillLevel();
                sl.parseLevel(levels.get(i));
                this.levels.add(sl);
            }
        }
    }

    class SkillLevel {

        private static final String LEVEL_PATTERN = "\\.\\d*";
        private static final String EFFECT_PATTERN = " \\w*";
        private static final String AMOUNT_PATTERN = "(\\+|-)(\\d*)";

        private int level;
        private String effect;
        private int amount;
        private String unit = "";

        public void parseLevel(String levelinfo) {
            Pattern p = Pattern.compile(LEVEL_PATTERN);
            Matcher m = p.matcher(levelinfo);
            this.level = Integer.parseInt(m.find() ? m.group().substring(1) : "0");

            p = Pattern.compile(EFFECT_PATTERN);
            m = p.matcher(levelinfo);
            this.effect = m.find() ? m.group().trim() : "";

            p = Pattern.compile(AMOUNT_PATTERN);
            m = p.matcher(levelinfo);
            this.amount = Integer.parseInt(m.find() ? m.group().trim() : "0");

            if (levelinfo.contains("%")) {
                this.unit = "%";
            }
        }
    }
}
