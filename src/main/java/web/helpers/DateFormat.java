package web.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
    public static String getDateFormat(String formatString) {
        SimpleDateFormat format = new SimpleDateFormat(formatString);
        String fecha = format.format(new Date());
        return fecha;
    }
}
