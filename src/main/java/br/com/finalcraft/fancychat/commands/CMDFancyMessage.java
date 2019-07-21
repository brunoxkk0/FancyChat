package br.com.finalcraft.fancychat.commands;


import br.com.finalcraft.fancychat.FCBukkitUtil;
import br.com.finalcraft.fancychat.PermissionNodes;
import br.com.finalcraft.fancychat.fancytextchat.FancyText;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDFancyMessage implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (FCBukkitUtil.isNotPlayer(sender)){
            //Console here
        }else {
            if (!FCBukkitUtil.hasThePermission(sender,PermissionNodes.commandFancyMessage)){
                return true;
            }
        }


        if (args.length <= 1){
            sender.sendMessage("§6§l ▶ §e/" + label + " <Player> <msg>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null){
            sender.sendMessage("§6§l ▶ §cJogador " + args[0] + " não encontrado!");
            return true;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < args.length; i++){
            stringBuilder.append(args[i] + " ");
        }

        String msg = stringBuilder.toString().trim();
        FancyText fancyText = new FancyText(ChatColor.translateAlternateColorCodes('&',msg));
        FancyText.sendTo(target,fancyText);
        return true;
    }
}
