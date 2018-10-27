package br.com.finalcraft.fancychat.commands;


import br.com.finalcraft.fancychat.FCBukkitUtil;
import br.com.finalcraft.fancychat.PermissionNodes;
import br.com.finalcraft.fancychat.api.FancyChatSendChannelMessageEvent;
import br.com.finalcraft.fancychat.config.fancychat.FancyChannel;
import br.com.finalcraft.fancychat.config.fancychat.FancyTag;
import br.com.finalcraft.fancychat.fancytextchat.FancyText;
import br.com.finalcraft.fancychat.util.IgnoreUtil;
import br.com.finalcraft.fancychat.util.MuteUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CMDInChannel implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        inChannel(label,sender,args);
        return true;
    }

    // -----------------------------------------------------------------------------------------------------------------------------//
    // Command InChannel
    // -----------------------------------------------------------------------------------------------------------------------------//
    public static boolean inChannel(String label, CommandSender sender, String[] args){

        if (FCBukkitUtil.isNotPlayer(sender)){
            return true;
        }

        Player player = (Player) sender;

        if (MuteUtil.isMuted(sender)){
            sender.sendMessage("§6§l ▶ Você está mutado");
            sender.sendMessage(MuteUtil.getMuteMessage(sender));
            return true;
        }

        List<FancyChannel> allFancyChannels = new ArrayList<FancyChannel>(FancyChannel.mapOfFancyChannels.values());
        List<String> allAliases = new ArrayList<String>();
        allFancyChannels.forEach(fancyChannel -> {
            allAliases.add(fancyChannel.getAlias().toLowerCase());
        });

        FancyChannel fancyChannel = null;
        int amoutOfChannels = allFancyChannels.size();
        for (int i = 0; i < amoutOfChannels; i++){
            if (allAliases.get(i).equals(label)){
                fancyChannel = allFancyChannels.get(i);
                break;
            }
        }

        if (!fancyChannel.getPermission().isEmpty()){
            if (!FCBukkitUtil.hasThePermission(player,fancyChannel.getPermission())){
                return true;
            }
        }

        String msg = String.join(" ", args);
        FancyChatSendChannelMessageEvent sendMessageEvent = new FancyChatSendChannelMessageEvent(player, fancyChannel, msg);
        Bukkit.getServer().getPluginManager().callEvent(sendMessageEvent);
        if (sendMessageEvent.isCancelled()){
            return true;
        }

        String rawMsg = sendMessageEvent.getMessage();
        if (player.hasPermission(PermissionNodes.chatColor)){
            rawMsg = ChatColor.translateAlternateColorCodes('&', rawMsg);
        }

        List<FancyText> textChatList = new ArrayList<FancyText>();
        for (FancyTag fancyTag : sendMessageEvent.getChannel().getTagsFromThisBuilder()){
            FancyText fancyText = fancyTag.getFancyText().parsePlaceholdersAndClone(sendMessageEvent.getSender());
            fancyText.text = fancyText.text.replace("{msg}",rawMsg);
            textChatList.add(fancyText);
        }


        int amoutOfPlayerTharReceivedThis = 0;
        for (Player onlinePlayerToSendMessage : sendMessageEvent.getChannel().getPlayersOnThisChannel()){
            if (sendMessageEvent.getChannel().getDistance() <= -1
                    || player.getLocation().distance(onlinePlayerToSendMessage.getLocation()) <=  sendMessageEvent.getChannel().getDistance()){
                amoutOfPlayerTharReceivedThis++;
                if (!IgnoreUtil.isIgnoring(onlinePlayerToSendMessage.getName(), sendMessageEvent.getSender().getName())){
                    FancyText.sendTo(onlinePlayerToSendMessage,textChatList);
                }
            }
        }

        if (amoutOfPlayerTharReceivedThis <= 1 && sendMessageEvent.getChannel().getDistance() > -1){
            player.sendMessage("§6§l ▶ §cNão tem ninguem perto de você para receber essa mensagem...");
        }

        return true;
    }
}
