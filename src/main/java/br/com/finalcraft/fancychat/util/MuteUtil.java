package br.com.finalcraft.fancychat.util;

import br.com.finalcraft.fancychat.PermissionNodes;
import br.com.finalcraft.fancychat.config.ConfigManager;
import br.com.finalcraft.fancychat.cooldown.FCTimeFrame;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class MuteUtil {

    public static boolean globalMute;

    String playerName;
    long start;
    long time;

    public static Map<String,MuteUtil> mapOfCooldowns = new HashMap<>();
    public static void initialize(){
        mapOfCooldowns.clear();
        for (String name : ConfigManager.getDataStore().getKeys("Cooldown.")){
            mapOfCooldowns.put(name, new MuteUtil(name));
        }
    }

    public static boolean isMuted(CommandSender sender){
        if ( !(sender instanceof Player)){
            return false;
        }

        if (globalMute && sender.hasPermission(PermissionNodes.chatNoGlobalMute)){
            return true;
        }

        MuteUtil muteUtil = mapOfCooldowns.getOrDefault(sender.getName(),null);
        if (muteUtil == null){
            return false;
        }

        if (muteUtil.time == -1){
            return true;
        }

        if (System.currentTimeMillis() - muteUtil.start >= muteUtil.time){
            muteUtil.delete();
            return false;
        }

        return true;
    }

    public static String getMuteMessage(CommandSender sender){
        if ( !(sender instanceof Player)){
            return "";
        }

        if (globalMute && !sender.hasPermission(PermissionNodes.chatNoGlobalMute)){
            return "§c§l   (GlobalMute está ativado)";
        }

        MuteUtil muteUtil = mapOfCooldowns.getOrDefault(sender.getName(),null);
        if (muteUtil == null){
            return "";
        }

        if (muteUtil.time == -1){
            return "§c  §l[§cMute Permanente§l]";
        }

        if (System.currentTimeMillis() - muteUtil.start >= muteUtil.time){
            muteUtil.delete();
            return "";
        }

        return getMessage(new FCTimeFrame(System.currentTimeMillis() - muteUtil.start));
    }

    public MuteUtil(String playerName) {
        this.playerName = playerName;
        this.start  = ConfigManager.getDataStore().getLong("Cooldown." + this.playerName + ".start",-1);
        this.time   = ConfigManager.getDataStore().getLong("Cooldown." + this.playerName + ".time",0);
    }

    public MuteUtil(String playerName, long start, long time) {
        this.playerName = playerName;
        this.start = start;
        this.time = time;
        saveOnYML();
    }

    public void delete(){
        mapOfCooldowns.remove(this.playerName);
        ConfigManager.getDataStore().setValue("Cooldown." + this.playerName, null);
        ConfigManager.getDataStore().save();
    }

    public void saveOnYML(){
        ConfigManager.getDataStore().setValue("Cooldown." + this.playerName + ".start", start);
        ConfigManager.getDataStore().setValue("Cooldown." + this.playerName + ".time", time);
        ConfigManager.getDataStore().save();
    }

    public static String getMessage(FCTimeFrame fcTimeFrame){
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
            return "§cVocê precisa esperar mais §6" + fcTimeFrame.getDays() + " §c" + dia + ", §6" + fcTimeFrame.getHours() + " §c" + hora + ", §6" + fcTimeFrame.getMinutes() + " §c" + minuto + " e §6" + fcTimeFrame.getSeconds() + " §c" + segundo;
        }else if (fcTimeFrame.getHours() > 0){
            return "§cVocê precisa esperar mais §6" + fcTimeFrame.getHours() + " §c" + hora + ", §6" + fcTimeFrame.getMinutes() + " §c" + minuto + " e §6" + fcTimeFrame.getSeconds() + " §c" + segundo;
        }else if (fcTimeFrame.getMinutes() > 0){
            return "§cVocê precisa esperar mais §6" + fcTimeFrame.getMinutes() + " §c" + minuto + " e §6" + fcTimeFrame.getSeconds() + " §c" + segundo;
        }else {
            return "§cVocê precisa esperar mais §6" + fcTimeFrame.getSeconds() + " §c" + segundo;
        }

    }





}

