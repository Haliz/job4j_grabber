package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SqlRuParse {
    public static void main(String[] args) throws Exception {
        for (int i = 1; i <= 5; i++) {
            Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers/" + i).get();
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                Element href = td.child(0);
                String link = href.attr("href");
                System.out.println(link);
                System.out.println(href.text());
                Element date = td.parent().child(5);
                System.out.println(date.text());
                parsingPostDescription(link);
            }
        }
    }

    public static void parsingPostDescription(String url) throws Exception {
        Document doc = Jsoup.connect(url).get();
        Elements row = doc.select(".msgBody");
        String text = row.get(1).text();
        String date = doc.select(".msgFooter").first().ownText();
        int index = date.indexOf(" [] |");
        String editedDate = date.substring(0, index);
        System.out.println(text);
        System.out.println(editedDate);
        System.out.println("------------------------------------------------------------");
    }
}
