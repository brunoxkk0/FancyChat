package br.com.finalcraft.fancychat.commands;


import br.com.finalcraft.fancychat.FCBukkitUtil;
import br.com.finalcraft.fancychat.PermissionNodes;
import br.com.finalcraft.fancychat.fancytextchat.FancyText;;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CMDBroadcast implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!FCBukkitUtil.hasThePermission(sender,PermissionNodes.commandBroadcast)){
            return true;
        }

        String msg = String.join(" ", args).trim();
        FancyText fancyText = new FancyText(ChatColor.translateAlternateColorCodes('&',msg));
        FancyText.tellRawBroadcast(fancyText);
        return true;
    }
}
