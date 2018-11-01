package br.com.finalcraft.fancychat.util.messages;

import br.com.finalcraft.fancychat.PermissionNodes;
import br.com.finalcraft.fancychat.api.FancyChatSendChannelMessageEvent;
import br.com.finalcraft.fancychat.config.fancychat.FancyChannel;
import br.com.finalcraft.fancychat.config.fancychat.FancyTag;
import br.com.finalcraft.fancychat.fancytextchat.FancyText;
import br.com.finalcraft.fancychat.util.IgnoreUtil;
import br.com.finalcraft.fancychat.util.MuteUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PublicMessage {

    public static void sendPublicMessage(Player player, FancyChannel channel, String msg){

        if (MuteUtil.isMuted(player)){
            player.sendMessage("§6§l ▶ Você está mutado");
            player.sendMessage(MuteUtil.getMuteMessage(player));
            return;
        }

        FancyChatSendChannelMessageEvent sendMessageEvent = new FancyChatSendChannelMessageEvent(player, channel, msg);
        Bukkit.getServer().getPluginManager().callEvent(sendMessageEvent);
        if (sendMessageEvent.isCancelled()){
            return;
        }

        if (player.hasPermission(PermissionNodes.chatColor)){
            msg = ChatColor.translateAlternateColorCodes('&', msg);
        }

        List<FancyText> textChatList = new ArrayList<FancyText>();
        for (FancyTag fancyTag : channel.getTagsFromThisBuilder()){
            FancyText fancyText = fancyTag.getFancyText().parsePlaceholdersAndClone(player);
            fancyText.text = fancyText.text.replace("{msg}",msg);
            textChatList.add(fancyText);
        }

        if (channel.distance <= -1){
            for (Player onlinePlayerToSendMessage : channel.getPlayersOnThisChannel()) {
                if (!IgnoreUtil.isIgnoring(onlinePlayerToSendMessage.getName(), player.getName())){
                    FancyText.sendTo(onlinePlayerToSendMessage,textChatList);
                }
            }
        }else {
            List<Player> playerThatHeardedThis = new ArrayList<Player>();
            int amoutOfPlayerTharReceivedThis = 0;

            for (Player onlinePlayerToSendMessage : channel.getPlayersOnThisChannel()) {
                if (calcDistance(player,onlinePlayerToSendMessage) <=  channel.getDistance()){
                    amoutOfPlayerTharReceivedThis++;
                    if (!IgnoreUtil.isIgnoring(onlinePlayerToSendMessage.getName(), player.getName())){
                        FancyText.sendTo(onlinePlayerToSendMessage,textChatList);
                    }
                }
            }

            if (amoutOfPlayerTharReceivedThis <= 1 && channel.getDistance() > -1){
                player.sendMessage("§6§l ▶ §cNão tem ninguem perto de você para receber essa mensagem...");
            }

            if (channel.getDistance() > -1){
                SpyMessage.spyOnThis(textChatList, playerThatHeardedThis);
            }
        }
    }

    public static double calcDistance(Player player, Player otherPlayer){
        Location playerLocation = player.getLocation();
        Location otherPlayerLocation = otherPlayer.getLocation();
        if (playerLocation.getWorld() == otherPlayerLocation.getWorld()){
            return playerLocation.distance(otherPlayerLocation);
        }
        return Double.MAX_VALUE;
    }
}
