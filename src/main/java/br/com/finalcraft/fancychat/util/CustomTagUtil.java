package br.com.finalcraft.fancychat.util;

import br.com.finalcraft.fancychat.config.ConfigManager;
import br.com.finalcraft.fancychat.config.customtag.CustomTag;
import org.bukkit.entity.Player;

import java.util.*;

public class CustomTagUtil {

    public static Map<String,CustomTag> mapOfPlayerCustomTags = new HashMap<String, CustomTag>();
    public static void initialize(){
        mapOfPlayerCustomTags.clear();
        boolean needsToSave = false;
        for (String playerName : ConfigManager.getDataStore().getKeys("CustomTag.")){
            String tagName = ConfigManager.getDataStore().getString("CustomTag." + playerName + ".tagName");
            CustomTag customTag = CustomTag.getCustomTag(tagName);
            if (customTag == null){
                ConfigManager.getDataStore().setValue("CustomTag." + playerName, null);
                needsToSave = true;
            }else {
                mapOfPlayerCustomTags.put(playerName, customTag);
            }
        }
        if (needsToSave) ConfigManager.getDataStore().save();
    }

    public static CustomTag getActiveCustomTag(Player player){
        return mapOfPlayerCustomTags.get(player.getName());
    }

    public static void setActiveCustomTag(Player player, CustomTag customTag){
        mapOfPlayerCustomTags.put(player.getName(),customTag);
    }

    public static CustomTag getCustomTag(String tagName){
        return CustomTag.getCustomTag(tagName);
    }

    public static Iterator<CustomTag> getPossibleCustomTags(Player player){
        return CustomTag.getAllCustomtags().stream().filter(customTag -> player.hasPermission(customTag.getPermission())).iterator();
    }

}
