package br.com.finalcraft.fancychat.util;

import br.com.finalcraft.fancychat.EverNifeFancyChat;
import br.com.finalcraft.fancychat.config.ConfigManager;
import br.com.finalcraft.fancychat.config.fancychat.FancyChannel;
import com.earth2me.essentials.api.ESAPIUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IgnoreUtil {

    public static Map<String, List<String>> playerIgnoreListMap = new HashMap<String, List<String>>();

    private static boolean essentialsEnabled = false;
    public static void initialize(){

        /*
        if (essentialsEnabled = Bukkit.getPluginManager().isPluginEnabled("Essentials")){
            EverNifeFancyChat.info("[Essentials Found] Utilizando ESS-Ignore System");
            return;
        }
         */

        playerIgnoreListMap.clear();

        for (String playerName : ConfigManager.getDataStore().getKeys("IgnoreList")){
            List<String> ignoreList = ConfigManager.getDataStore().getStringList("IgnoreList." + playerName);
            playerIgnoreListMap.put(playerName,ignoreList);
        }
    }

    public static List<String> getIgnoreList(String playerName){
        return playerIgnoreListMap.getOrDefault(playerName, Collections.emptyList());
    }

    public static boolean isIgnoring(Player player, Player otherPlayer){
        if (essentialsEnabled){
            return ESAPIUtil.isIgnoring(player,otherPlayer);
        }else {
            return isIgnoring(player.getName(),otherPlayer.getName());
        }
    }

    public static boolean isIgnoring(String playerName, String otherPlayerName){
        for (String name : getIgnoreList(playerName)){
            if (name.equalsIgnoreCase(otherPlayerName)){
                return true;
            }
        }
        return false;
    }

    public static boolean hasChannelPermission(Player player, FancyChannel fancyChannel){
        if (fancyChannel.permission.isEmpty()){
            return true;
        }
        return player.hasPermission(fancyChannel.permission);
    }

    public static boolean ignorePlayer(String playerName, String otherPlayerName){
        List<String> playerCurrentIgnorelist = getIgnoreList(playerName);

        boolean addedToMutelsit;
        if (playerCurrentIgnorelist.contains(otherPlayerName)){
            playerCurrentIgnorelist.remove(otherPlayerName);
            addedToMutelsit = true;
        }else {
            playerCurrentIgnorelist.add(otherPlayerName);
            addedToMutelsit = false;
        }

        playerIgnoreListMap.put(playerName,playerCurrentIgnorelist);
        ConfigManager.getDataStore().setValue("IgnoreList." + playerName,playerCurrentIgnorelist);
        return addedToMutelsit;
    }
}
