package ru.job4j.grabber.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class SqlRuDateTimeParser implements DateTimeParser {

    private static final Map<String, String> MONTHS = Map.ofEntries(
            Map.entry("янв", "01"),
            Map.entry("фев", "02"),
            Map.entry("мар", "03"),
            Map.entry("апр", "04"),
            Map.entry("май", "05"),
            Map.entry("июн", "06"),
            Map.entry("июл", "07"),
            Map.entry("авг", "08"),
            Map.entry("сен", "09"),
            Map.entry("окт", "10"),
            Map.entry("ноя", "11"),
            Map.entry("дек", "12"));

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
            String month = MONTHS.get(splitDate[1]);
            outDate = LocalDate.parse(day + "." + month + "." + splitDate[2],
                    DateTimeFormatter.ofPattern("dd.MM.uu"));
        }
        return LocalDateTime.of(outDate, outTime);
    }
}
