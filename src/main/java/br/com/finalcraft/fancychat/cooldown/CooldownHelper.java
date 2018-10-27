package br.com.finalcraft.fancychat.cooldown;

import br.com.finalcraft.fancychat.config.ConfigManager;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class CooldownHelper {




    public static Map<String,Long> mapOfCooldowns = new HashMap<String, Long>();

    public static boolean isNotInCooldown(String identifier, int secondsToWait) {
        return !isInCooldown(identifier,secondsToWait);
    }

    public static boolean isInCooldown(String identifier, int secondsToWait) {

        if ( getTimeLeft(identifier,secondsToWait) > 0 ) {
            return true;
        }
        return false;

    }

    public static void setLastUse(String identifier) {
        long lastUse = System.currentTimeMillis();
        ConfigManager.getDataStore().setValue("Cooldown." + identifier,lastUse);
        ConfigManager.getDataStore().save();
        mapOfCooldowns.put(identifier,lastUse);
    }

    public static Long getTimeLeft(String identifier, int secondsToWait){

        if (!mapOfCooldowns.containsKey(identifier)){
            return 0L;
        }
        return (mapOfCooldowns.get(identifier) + (secondsToWait * 1000)) - System.currentTimeMillis();
    }

    public static FCTimeFrame getTimeLeftFormated(String identifier, int secondsToWait){
        FCTimeFrame fcTimeFrame = new FCTimeFrame(getTimeLeft(identifier,secondsToWait));
        return fcTimeFrame;
    }

    public static void warnPlayer(CommandSender sender, String identifier, int secondsToWait){
        warnPlayer(sender,getTimeLeftFormated(identifier,secondsToWait));
    }

    public static void warnPlayer(CommandSender sender, FCTimeFrame fcTimeFrame){

        String dia = "dia";
        String hora = "hora";
        String minuto = "minuto";
        String segundo = "segundo";
        if ( fcTimeFrame.getDays() >= 2){
            dia = "dias";
        }
        if ( fcTimeFrame.getHours() >= 2){
            hora = "horas";
        }
        if ( fcTimeFrame.getMinutes() >= 2){
            minuto = "minutos";
        }
        if ( fcTimeFrame.getSeconds() >= 2){
            segundo = "segundos";
        }

        if (fcTimeFrame.getDays() > 0){
            sender.sendMessage("§cVocê precisa esperar mais §6" + fcTimeFrame.getDays() + " §c" + dia + ", §6" + fcTimeFrame.getHours() + " §c" + hora + ", §6" + fcTimeFrame.getMinutes() + " §c" + minuto + " e §6" + fcTimeFrame.getSeconds() + " §c" + segundo);
        }else if (fcTimeFrame.getHours() > 0){
            sender.sendMessage("§cVocê precisa esperar mais §6" + fcTimeFrame.getHours() + " §c" + hora + ", §6" + fcTimeFrame.getMinutes() + " §c" + minuto + " e §6" + fcTimeFrame.getSeconds() + " §c" + segundo);
        }else if (fcTimeFrame.getMinutes() > 0){
            sender.sendMessage("§cVocê precisa esperar mais §6" + fcTimeFrame.getMinutes() + " §c" + minuto + " e §6" + fcTimeFrame.getSeconds() + " §c" + segundo);
        }else {
            sender.sendMessage("§cVocê precisa esperar mais §6" + fcTimeFrame.getSeconds() + " §c" + segundo);
        }

    }

}
