package helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class DateHelper {
    
    private static final TimeZone timeZone = TimeZone.getTimeZone("UTC");
    
    public static String getDateTime(java.util.Date date){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fecha = dateFormatter.format(date);
        return fecha;
    }
    
    public static String getDateTime() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fecha = dateFormatter.format(new java.util.Date());
        return fecha;
    }
    
    public static String getDateFormat(String format) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
        return dateFormatter.format(new java.util.Date());
    }
    
    // recibe fechar y la regresa al formato que se recibe como segundo parametro
    public static String getDateFormat(java.util.Date date, String format) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
        return dateFormatter.format(date);
    }
    
    public static String fromSQLDate(java.sql.Date date)  {
        java.util.Date javaDate = new java.util.Date(date.getTime());
        SimpleDateFormat dateFormatter = new SimpleDateFormat();
        dateFormatter.setTimeZone(timeZone);
        String fecha = dateFormatter.format(javaDate);
        return fecha;
    }
    
    public static java.util.Date fromSQLDatetime(java.sql.Timestamp date) {
        java.util.Date parsedDate = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateFormat.setTimeZone(timeZone);
            java.util.Date fecha = new java.util.Date(date.getTime());
            String stringFecha = dateFormat.format(fecha);
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            parsedDate = dateFormat2.parse(stringFecha);
        } catch (ParseException ex) {
            ex.printStackTrace(System.out);
        }
        return parsedDate;
    }
    
}
