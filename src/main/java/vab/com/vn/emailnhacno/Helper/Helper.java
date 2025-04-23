package vab.com.vn.emailnhacno.Helper;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Helper {

    public static String formatTimestampToString(Timestamp timestamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return timestamp.toLocalDateTime().format(formatter);
    }


    public static String updateDate(String date, int daysToAdd) {
        // Các định dạng có thể có của đầu vào
        DateTimeFormatter dateFormatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dateFormatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dateTimeFormatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        // Định dạng đầu ra
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try {
            LocalDateTime scheduleDueDate;

            if (date.endsWith(".0")) {
                date = date.substring(0, date.length() - 2);
            }

            // Xác định định dạng của date và parse
            if (date.length() > 10) {
                // Nếu có giờ phút giây
                if (date.charAt(4) == '-') {  // yyyy-MM-dd HH:mm:ss
                    scheduleDueDate = LocalDateTime.parse(date, dateTimeFormatter1);
                } else {  // dd-MM-yyyy HH:mm:ss
                    scheduleDueDate = LocalDateTime.parse(date, dateTimeFormatter2);
                }
            } else {
                // Nếu chỉ có ngày tháng năm
                if (date.charAt(4) == '-') {  // yyyy-MM-dd
                    scheduleDueDate = LocalDate.parse(date, dateFormatter1).atStartOfDay();
                } else {  // dd-MM-yyyy
                    scheduleDueDate = LocalDate.parse(date, dateFormatter2).atStartOfDay();
                }
            }

            // Cộng thêm số ngày nếu cần
            scheduleDueDate = scheduleDueDate.plusDays(daysToAdd);

            return scheduleDueDate.format(outputFormatter);

        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return "Invalid date format";
        }
    }



    public static String formatDate(String date) {
        return date.substring(0, 10);
    }

    public static boolean checkNull(String text) {
        boolean isNull = false;
        try {
            text.equalsIgnoreCase(null);
        } catch (NullPointerException npe) {
            isNull = true;
        }
        return isNull;
    }
}
