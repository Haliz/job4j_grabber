package ru.job4j.grabber.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class SqlRuDateTimeParser implements DateTimeParser {
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
        Map<String, String> months = new HashMap<>();
        months.put("янв", "01");
        months.put("фев", "02");
        months.put("мар", "03");
        months.put("апр", "04");
        months.put("май", "05");
        months.put("июн", "06");
        months.put("июл", "07");
        months.put("авг", "08");
        months.put("сен", "09");
        months.put("окт", "10");
        months.put("ноя", "11");
        months.put("дек", "12");
        return months.get(month);
    }
}
