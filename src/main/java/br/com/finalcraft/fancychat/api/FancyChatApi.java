package br.com.finalcraft.fancychat.api;

import br.com.finalcraft.fancychat.config.fancychat.FancyChannel;
import br.com.finalcraft.fancychat.util.ChannelManager;
import org.bukkit.entity.Player;

import java.util.Collection;

public class FancyChatApi {

    public static FancyChannel getChannel(String name){
        return FancyChannel.getFancyChannel(name);
    }

    public static Collection<FancyChannel> getAllChannels(){
        return FancyChannel.getAllChannels();
    }

    public static FancyChannel getPlayerChannel(Player player){
        return ChannelManager.getPlayerLockChannel(player);
    }

    public static void mutePlayer(String playerName, long millis){
    }

    public static void sendMessage(String message, FancyChannel fancyChannel){
        for (Player player : fancyChannel.getPlayersOnThisChannel()){
            player.sendMessage(message);
        }
    }


}
