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
}
