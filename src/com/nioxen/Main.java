package com.nioxen;

import com.nioxen.monster.Monster;
import com.nioxen.scraper.NScraper;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            URL url = new URL("http://summonerswar.wikia.com/wiki/Low_Elemental_(Fire)");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            BufferedWriter writer = new BufferedWriter(new FileWriter("data.html"));
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                writer.write(line);
                writer.newLine();
            }
            reader.close();
            writer.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

//        NScraper ns = new NScraper("http://summonerswar.wikia.com");
//        ns.makeRosterCSV("E:\\Downloads\\curl-7.59.0-win64-mingw\\curl-7.59.0-win64-mingw\\bin\\output");
//        List<Monster> monList = ns.parseRoster("http://summonerswar.wikia.com/wiki/Monster_Collection");
//        System.out.println("Done");
    }
}
