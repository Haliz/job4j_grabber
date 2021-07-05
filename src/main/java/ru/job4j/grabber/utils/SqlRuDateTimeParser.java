package ru.job4j.grabber.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class SqlRuDateTimeParser implements DateTimeParser {

    private static final Map<String, String> MONTHS = new HashMap<>();

    public SqlRuDateTimeParser() {
        SqlRuDateTimeParser.setMonths();
    }

    @Override
    public LocalDateTime parse(String parse) {
        String[] split = parse.split(",");
        String inDate = split[0];
        LocalTime outTime = LocalTime.parse(split[1].substring(1));
        LocalDate outDate;
        if (inDate.equals("сегодня")) {
            outDate = LocalDate.now();
        } else if (inDate.equals("вчера")) {
            outDate = LocalDate.now().minusDays(1);
        } else {
            String[] splitDate = inDate.split(" ");
            String day = String.format("%02d", Integer.parseInt(splitDate[0]));
            String month = getMonth(splitDate[1]);
            outDate = LocalDate.parse(day + "." + month + "." + splitDate[2],
                    DateTimeFormatter.ofPattern("dd.MM.uu"));
        }
        return LocalDateTime.of(outDate, outTime);
    }

    public String getMonth(String month) {
        return MONTHS.get(month);
    }

    private static void setMonths() {
        MONTHS.put("янв", "01");
        MONTHS.put("фев", "02");
        MONTHS.put("мар", "03");
        MONTHS.put("апр", "04");
        MONTHS.put("май", "05");
        MONTHS.put("июн", "06");
        MONTHS.put("июл", "07");
        MONTHS.put("авг", "08");
        MONTHS.put("сен", "09");
        MONTHS.put("окт", "10");
        MONTHS.put("ноя", "11");
        MONTHS.put("дек", "12");
    }
}
