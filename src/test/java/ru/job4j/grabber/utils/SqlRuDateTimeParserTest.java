package ru.job4j.grabber.utils;

import org.junit.Assert;
import org.junit.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class SqlRuDateTimeParserTest {

    @Test
    public void whenDate() {
        SqlRuDateTimeParser tp = new SqlRuDateTimeParser();
        LocalDateTime rsl = tp.parse("8 июн 21, 14:48");
        LocalDateTime exp = LocalDateTime.of(2021, 6, 8, 14, 48);
        Assert.assertEquals(exp, rsl);
    }

    @Test
    public void whenOtherDate() {
        SqlRuDateTimeParser tp = new SqlRuDateTimeParser();
        LocalDateTime rsl = tp.parse("2 июл 21, 13:23");
        LocalDateTime exp = LocalDateTime.of(2021, 7, 2, 13, 23);
        Assert.assertEquals(exp, rsl);
    }

    @Test
    public void whenToday() {
        SqlRuDateTimeParser tp = new SqlRuDateTimeParser();
        LocalDateTime rsl = tp.parse("сегодня, 20:33");
        LocalDate expDate = LocalDate.now();
        LocalTime expTime = LocalTime.of(20, 33);
        LocalDateTime exp = LocalDateTime.of(expDate, expTime);
        Assert.assertEquals(exp, rsl);
    }

    @Test
    public void whenYesterday() {
        SqlRuDateTimeParser tp = new SqlRuDateTimeParser();
        LocalDateTime rsl = tp.parse("вчера, 20:33");
        LocalDate expDate = LocalDate.now().minusDays(1);
        LocalTime expTime = LocalTime.of(20, 33);
        LocalDateTime exp = LocalDateTime.of(expDate, expTime);
        Assert.assertEquals(exp, rsl);
    }
}
