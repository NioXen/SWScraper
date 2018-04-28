package com.nioxen.monster;

import com.nioxen.scraper.InfoData;
import com.nioxen.scraper.SkillData;
import com.nioxen.scraper.StatLevelData;

import java.util.ArrayList;
import java.util.List;

public class Monster {

    private int id;

    private Info info;
    private List<Skill> skills;
    private List<StatLevel> statLevels;
    private Attributes attributes;

    private Monster awakened;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Info getInfo() {
        return this.info;
    }

    public void parseInfo(InfoData data) {
        Info i = new Info();
        i.parseStarColor(data.getStarColor());
        i.parseName(data.getName());
        //i.parseStars(data.getName());
        i.parseElement(data.getElement());
        i.parseThumbnails(data.getThumbnails());
        i.parseCommonInfo(data.getCommonInfo());
        this.info = i;
        if (info.getStarColor() == 'G') {
            InfoData d2 = data;
            d2.setStarColor("P");
            awakened = new Monster();
            awakened.parseInfo(d2);
        }
    }

    public void parseSkills(SkillData data) {
        List<Skill> sList = new ArrayList<>();
        for (int i = 0; i < data.getBasicInfos().size(); i++) {
            if (data.getBasicInfos().get(i).contains("Awakened") ||
            data.getBasicInfos().get(i).contains("Strengthened") && info.getStarColor() != 'P') {
                continue;
            }
            Skill skill = new Skill();
            skill.parseBasic(data.getBasicInfos().get(i));
            skill.parseThumbnail(data.getThumbnails().get(i));
            skill.parseLevels(data.getLevels().get(i));
            sList.add(skill);
        }
        this.skills = sList;
        if (awakened != null) {
            awakened.parseSkills(data);
        }
    }

    public void parseStatLevels(StatLevelData data) {
        int j;
        List<StatLevel> slList = new ArrayList<>();
        for (int i = 0; i < data.getLevels().size(); i++) {
            j = (int)Math.floor(i/2);
            StatLevel sl = new StatLevel();
            sl.parseLevel(data.getLevels().get(i));
            sl.parseStars(data.getStars().get(j));
            sl.parseHp(data.getHpList().get(0).get(i));
            sl.parseAtk(data.getAtkList().get(0).get(i));
            sl.parseDef(data.getDefList().get(0).get(i));
            slList.add(sl);
        }
        this.statLevels = slList;
        if (awakened != null) {
            data.getHpList().remove(0);
            data.getAtkList().remove(0);
            data.getDefList().remove(0);
            awakened.parseStatLevels(data);
        }
    }

    public void parseAttributes(List<List<String>> attributes) {
        Attributes a = new Attributes();
        List<String> l = attributes.get(0);
        a.parseSpeed(l.get(0));
        a.parseCriRate(l.get(1));
        a.parseCriDmg(l.get(2));
        a.parseRes(l.get(3));
        a.parseAcc(l.get(4));
        this.attributes = a;

        if (awakened != null) {
            List<List<String>> awAttributes = new ArrayList<>();
            awAttributes.add(attributes.get(1));
            awakened.parseAttributes(awAttributes);
        }
    }

    class Attributes {
        private int speed;
        private double criRate;
        private double criDmg;
        private double res;
        private double acc;

        public void parseSpeed(String speed) {
            this.speed = Integer.parseInt(speed);
        }
        public void parseCriRate(String criRate) {
            this.criRate = Double.parseDouble(criRate.replace("%", "")) / 100;
        }
        public void parseCriDmg(String criDmg) {
            this.criDmg = Double.parseDouble(criDmg.replace("%", "")) / 100;
        }
        public void parseRes(String res) {
            this.res = Double.parseDouble(res.replace("%", "")) / 100;
        }
        public void parseAcc(String acc) {
            this.acc = Double.parseDouble(acc.replace("%", "")) / 100;
        }
    }
}
