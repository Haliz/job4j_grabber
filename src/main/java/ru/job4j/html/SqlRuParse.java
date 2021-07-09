package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.Parse;
import ru.job4j.grabber.Post;
import ru.job4j.grabber.utils.DateTimeParser;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SqlRuParse implements Parse {
    public static void main(String[] args) {
        SqlRuParse parse = new SqlRuParse();
        List<Post> list = parse.list("https://www.sql.ru/forum/job-offers");
        list.forEach(System.out::println);
        System.out.println(list.size());
    }

    @Override
    public List<Post> list(String link) {
        List<Post> posts = new ArrayList<>();
        try {
            for (int i = 1; i <= 5; i++) {
                Document doc = Jsoup.connect(link + '/' + i).get();
                Elements row = doc.select(".postslisttopic");
                for (Element td : row) {
                    Element href = td.child(0);
                    String descriptionLink = href.attr("href");
                    String title = href.text();
                    Post post = detail(descriptionLink);
                    post.setLink(descriptionLink);
                    post.setTitle(title);
                    posts.add(post);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Post detail(String link) {
        Post post = new Post();
        try {
            Document doc = Jsoup.connect(link).get();
            Elements row = doc.select(".msgBody");
            String text = row.get(1).text();
            String date = doc.select(".msgFooter").first().ownText();
            int index = date.indexOf(" [] |");
            String editedDate = date.substring(0, index);
            post.setDescription(text);
            DateTimeParser dateParser = new SqlRuDateTimeParser();
            LocalDateTime setDate = dateParser.parse(editedDate);
            post.setCreated(setDate);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return post;
    }
}
