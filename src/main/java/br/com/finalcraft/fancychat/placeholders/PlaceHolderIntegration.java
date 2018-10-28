package br.com.finalcraft.fancychat.placeholders;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class PlaceHolderIntegration extends EZPlaceholderHook {

    public static boolean hasPlaceholderApi = false;
    public static String parsePlaceholder(Player player, String text){
        text = text.replace("{player}",player.getName());
        if (!hasPlaceholderApi){
            return ChatColor.translateAlternateColorCodes('&',text);
        }
        return PlaceholderAPI.setPlaceholders(player,text);
    }

    public static void initialize(JavaPlugin plugin){
        hasPlaceholderApi = true;
        new PlaceHolderIntegration(plugin,"fancytext").hook();
    }

    public PlaceHolderIntegration(Plugin plugin, String identifier) {
        super(plugin, identifier);
    }

    @Override
    public String onPlaceholderRequest(Player player, String placeholder) {
        return null;
    }
}
