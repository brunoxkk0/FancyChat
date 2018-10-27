package br.com.finalcraft.fancychat.cooldown;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class FCTimeFrame {

    private Long days;
    private Long hours;
    private Long minutes;
    private Long seconds;
    private Long millis;


    public FCTimeFrame(Long millis) {
        this.millis = millis;
        days = TimeUnit.MILLISECONDS.toDays(millis);
        hours = TimeUnit.MILLISECONDS.toHours(millis - TimeUnit.DAYS.toMillis(days));
        minutes = TimeUnit.MILLISECONDS.toMinutes((millis - TimeUnit.DAYS.toMillis(days)) - TimeUnit.HOURS.toMillis(hours));
        seconds = TimeUnit.MILLISECONDS.toSeconds(((millis - TimeUnit.DAYS.toMillis(days)) - TimeUnit.HOURS.toMillis(hours)) - TimeUnit.MINUTES.toMillis(minutes));
    }

    public Long getDays() {
        return days;
    }

    public Long getHours() {
        return hours;
    }

    public Long getMinutes() {
        return minutes;
    }

    public Long getSeconds() {
        return seconds;
    }

    public Long getMillis() {
        return millis;
    }

    public Long getAllInDays(){
        return TimeUnit.MILLISECONDS.toDays(days);
    }

    public Long getAllInHours(){
        return TimeUnit.MILLISECONDS.toHours(days);
    }

    public Long getALlInMinutes(){
        return TimeUnit.MILLISECONDS.toMinutes(days);
    }

    public Long getAllInSeconds(){
        return TimeUnit.MILLISECONDS.toSeconds(millis);
    }

    public String getFormated(){
        Date date = new Date(millis);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(date);
    }


    // -----------------------------------------------------------------------------------------------------------------------------//
    // Static Commands
    // -----------------------------------------------------------------------------------------------------------------------------//

    public static Long getSeconds(Long millis) {
        return TimeUnit.MILLISECONDS.toSeconds(millis);
    }

    public static Long getDays(Long millis) {
        return TimeUnit.MILLISECONDS.toDays(millis);
    }

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    public static String getFormated(Long millis){
        Date date = new Date(millis);
        return sdf.format(date);
    }
}
