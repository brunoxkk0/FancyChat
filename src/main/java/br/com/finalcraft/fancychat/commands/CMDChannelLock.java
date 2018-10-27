package br.com.finalcraft.fancychat.commands;


import br.com.finalcraft.fancychat.FCBukkitUtil;
import br.com.finalcraft.fancychat.config.fancychat.FancyChannel;
import br.com.finalcraft.fancychat.util.ChannelManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CMDChannelLock implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        channel(label,sender,args);
        return true;
    }

    // -----------------------------------------------------------------------------------------------------------------------------//
    // Command Channel
    // -----------------------------------------------------------------------------------------------------------------------------//
    public static boolean channel(String label, CommandSender sender, String[] args){

        if (FCBukkitUtil.isNotPlayer(sender)){
            return true;
        }

        Player player = (Player) sender;

        List<FancyChannel> allFancyChannels = new ArrayList<FancyChannel>(FancyChannel.mapOfFancyChannels.values());

        List<String> allNames = new ArrayList<String>();
        List<String> allAliases = new ArrayList<String>();

        allFancyChannels.forEach(fancyChannel -> {
            allNames.add(fancyChannel.getName().toLowerCase());
            allAliases.add(fancyChannel.getAlias().toLowerCase());
        });

        if (args.length == 0){
            sender.sendMessage("§6§l ▶ §a/" + label + " <" + String.join("|",allAliases) + ">");
            return true;
        }

        String channelThePlayersWants = args[0].toLowerCase();

        FancyChannel fancyChannel = null;
        int amoutOfChannels = allFancyChannels.size();
        for (int i = 0; i < amoutOfChannels; i++){
            if (allNames.get(i).equals(channelThePlayersWants) ||
                    allAliases.get(i).equals(channelThePlayersWants)){
                fancyChannel = allFancyChannels.get(i);
                break;
            }
        }

        if (fancyChannel == null){
            sender.sendMessage("§6§l ▶ §cO canal §e" + args[0].toLowerCase() + "§c não existe!");
            return true;
        }

        ChannelManager.setPlayerLockChannel(player,fancyChannel);

        sender.sendMessage("§6§l ▶ §aCanal §e[" + fancyChannel.getName() + "]§a definido como padrão!");
        return true;
    }
}
