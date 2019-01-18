package br.com.finalcraft.fancychat.placeholders;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class PlaceHolderIntegration{

    public static boolean hasPlaceholderApi = false;
    public static String parsePlaceholder(String text, Player player){
        text = text.replace("{player}",player.getName()).replace("{playername}",player.getName());
        if (!hasPlaceholderApi){
            return ChatColor.translateAlternateColorCodes('&',text);
        }
        return PlaceholderAPI.setPlaceholders(player,text);
    }

    public static void initialize(JavaPlugin plugin){
        hasPlaceholderApi = true;
    }

}
