package br.com.finalcraft.fancychat.util;

import br.com.finalcraft.fancychat.config.fancychat.FancyChannel;
import org.bukkit.entity.Player;

public class PermUtil {

    public static boolean hasChannelPermission(Player player, FancyChannel fancyChannel){
        if (fancyChannel.permission.isEmpty()){
            return true;
        }
        return player.hasPermission(fancyChannel.permission);
    }

}
