package com.nioxen.scraper;

import com.nioxen.MonsterHeader;
import com.nioxen.monster.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class NScraper {
    private String baseuri;
    private Document doc;

    public NScraper(String baseuri) {
        this.baseuri = baseuri;
    }

    public List<Monster> parseRoster(String url) {
        List<MonsterHeader> roster = getRoster(url);
        List<Monster> monList = new ArrayList<>();
        for (MonsterHeader hdr :
                roster) {
            Monster mon = parseMonster(hdr.getAbsoluteMonsterURL());
            monList.add(mon);
            System.out.println(mon.getInfo().getName());
        }
        return monList;
    }

    private void openFile(String path){
        Document doc = null;
        File input = new File(path);
        try{
            doc = Jsoup.parse(input, "UTF-8", baseuri);
        }catch (IOException e){
            System.err.println("IOException: " + e.getMessage());
        }finally {
            this.doc = doc;
        }
    }

    private void openURL(String url){
        try {
            this.doc = Jsoup.connect(url)
                    .userAgent("Mozilla")
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Monster parseMonster(String url){
        openURL(url);
        Monster mon = new Monster();
        mon.parseInfo(findInfoData());
        mon.parseSkills(findSkillData());
        mon.parseStatLevels(findStatLevelsData());
        mon.parseAttributes(findAttributeData());
        return mon;
    }

    private InfoData findInfoData() {
        InfoData data = new InfoData();
        Element nameBar = doc.selectFirst("th#header");
        data.setStarColor(nameBar.select("noscript > img").get(1).attr("alt"));
        data.setName(nameBar.text());
        data.setElement(nameBar.selectFirst("a>img").attr("alt"));
        data.setThumbnails(doc.select("#images > table > tbody > tr > td > a > img").eachAttr("data-src"));
        data.setCommonInfo(doc.select("#common_info > table > tbody > tr > td").eachText());
        return data;
    }

    private SkillData findSkillData() {
        SkillData data = new SkillData();
        Elements tableRows = doc.select("#skills > div.boxborder > table > tbody > tr");
        List<String> basicInfos = new ArrayList<>();
        List<String> thumbnails = new ArrayList<>();
        List<List<String>> levelsList = new ArrayList<>();
        for (int i = 0; i < tableRows.size(); i++) {
            /* If this row is a skill level row, skip it.
             * We got this on the previous iteration as part of the skill*/
            Element ul = tableRows.get(i).getElementsByTag("ul").first();
            if ( ul != null || tableRows.get(i).text().equals("") ) {
                continue;
            }

            Elements tds = tableRows.get(i).getElementsByTag("td");
            for (Element td : tds) {
                Element img = td.getElementsByTag("img").first();
                if (img != null) {
                    thumbnails.add(img.attr("data-src"));
                    continue;
                }
                basicInfos.add(td.text());
            }
            List<String> levels = new ArrayList<>();
            if (i + 1 < tableRows.size()) {
                Element nextul = tableRows.get(i + 1).getElementsByTag("ul").first();
                if (nextul != null) {
                    levels = nextul.getElementsByTag("li").eachText();
                }
            }
            levelsList.add(levels);
        }
        data.setBasicInfos(basicInfos);
        data.setThumbnails(thumbnails);
        data.setLevels(levelsList);
        return data;
    }

    private StatLevelData findStatLevelsData() {
        StatLevelData sld = new StatLevelData();
        Element statTable = doc.selectFirst("#mw-content-text > div:nth-child(12) > table > tbody");
        Elements tableRows = statTable.getElementsByTag("tr");
        for (Element tr : tableRows) {
            Elements tcells = tr.getElementsByTag("th");
            if (tcells.size() == 0) {
                tcells = tr.getElementsByTag("td");
            }
            List<String> rowTexts = tcells.eachText();
            String firstText = rowTexts.get(0);
            switch (firstText) {
                case "Grade":
                    rowTexts.remove(0);
                    sld.setStars(rowTexts);
                    break;
                case "Level":
                    rowTexts.remove(0);
                    sld.setLevels(rowTexts);
                    break;
                case "HP":
                    rowTexts.remove(0);
                    sld.getHpList().add(rowTexts);
                    break;
                case "ATK":
                    rowTexts.remove(0);
                    sld.getAtkList().add(rowTexts);
                    break;
                case "DEF":
                    rowTexts.remove(0);
                    sld.getDefList().add(rowTexts);
                    break;
            }
        }
        return sld;
    }

    private List<List<String>> findAttributeData() {
        Elements attrTableRows = doc.select("#mw-content-text > div:nth-child(16) > table > tbody > tr");
        List<List<String>> attrList = new ArrayList<>();
        for (Element tr :
                attrTableRows) {
            List<String> rowTexts;
            rowTexts = tr.getElementsByTag("td").eachText();
            String firstText = rowTexts.get(0);
            if ("".equals(firstText) || "SPD".equals(firstText)) {
                continue;
            }
            if ("Unawakened".equals(firstText) || "Awakened".equals(firstText)) {
                rowTexts.remove(0);
            }
            attrList.add(rowTexts);
        }
        return attrList;
    }

    /*
    ** Roster stuff (name and links)
     */
    private List<MonsterHeader> getRoster(String url) {
        openURL(url);
        if (doc == null) {
            System.out.println("Cannot open roster from url");
            return null;
        }
        Element tabber = doc.selectFirst("div.tabber");
        List<MonsterHeader> roster = new ArrayList<>();
        Elements tabs = tabber.select("div.tabbertab");
        for (Element tab:tabs) {
            if (tab.attr("title").contains("(A)")) {
                continue;
            }
            Elements mons = tab.select("td>a.image-thumbnail");
            for (Element mon:mons) {
                MonsterHeader hdr = parseMonsterHeader(mon);
                if ("Homunculus".equals(hdr.getName())) {
                    continue;
                }
                roster.add(hdr);
            }
        }
        return roster;
    }

    private MonsterHeader parseMonsterHeader(Element mon) {
        MonsterHeader ret = new MonsterHeader();
        ret.setAbsoluteMonsterURL(baseuri + mon.attr("href"));
        ret.setAbsoluteThumbnailURL(mon.selectFirst("img").attr("data-src"));
        String title = mon.attr("title");
        if (title.contains("(")) {
            String[] info = title.split("\\(");
            ret.setName(info[0].trim());
            ret.setElement(info[1].substring(0, info[1].length() - 1));
        } else {
            ret.setName(title);
        }
        return ret;
    }

    public void makeRosterCSV(String path){
        List<MonsterHeader> roster = getRoster("http://summonerswar.wikia.com/wiki/Monster_Collection");
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        File ff = new File(path + "MonsterHeaders.csv");
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(ff);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        for (MonsterHeader hdr : roster) {
            pw.write(hdr.toString());
            pw.flush();
        }
    }


}
