package utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public final class DateUtils {

    private DateUtils() {
    }

    public static String nextDayOfWeekDate(int dayOfWeek) {
        LocalDate today = LocalDate.now();
        DayOfWeek target = DayOfWeek.of(dayOfWeek);
        LocalDate next = today.with(TemporalAdjusters.next(target));
        return next.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static String formatDate(String date, String browser) {
        LocalDate d = LocalDate.parse(date.trim(),
                DateTimeFormatter.ISO_LOCAL_DATE);
        if (browser == null || browser.isBlank()) {
            return d.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        }
        return switch (browser.trim().toLowerCase()) {
            case "chromium", "chrome" -> d.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            case "firefox"            -> d.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            default                   -> d.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        };
    }

    public static String datePlusMonths(int months) {
        LocalDate today = LocalDate.now();
        LocalDate nextMonth = today.plusMonths(months);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return nextMonth.format(formatter);
    }

    public static String datePlusWeeks(String date, int weeks) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate baseDate = LocalDate.parse(date, formatter);
        LocalDate result = baseDate.plusWeeks(weeks);
        return result.format(formatter);
    }
}
