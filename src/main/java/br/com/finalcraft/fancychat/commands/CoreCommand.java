package br.com.finalcraft.fancychat.commands;

import br.com.finalcraft.fancychat.EverNifeFancyChat;
import br.com.finalcraft.fancychat.FCBukkitUtil;
import br.com.finalcraft.fancychat.PermissionNodes;
import br.com.finalcraft.fancychat.config.ConfigManager;
import br.com.finalcraft.fancychat.config.fancychat.FancyChannel;
import br.com.finalcraft.fancychat.fancytextchat.FancyText;
import br.com.finalcraft.fancychat.util.ChannelManager;
import br.com.finalcraft.fancychat.util.MuteUtil;
import br.com.finalcraft.fancychat.util.PrivateMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CoreCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //  Passando os argumentos para um ArrayList
        List<String> argumentos = FCBukkitUtil.parseBukkitArgsToList(args,4);

        switch (argumentos.get(0).toLowerCase()){
            case "":
            case "?":
            case "help":
                return help(label,sender,argumentos);
            case "tell":
            case "t":
            case "w":
            case "msg":
            case "private":
            case "r":
                return tell(label,sender,args);
            case "channel":
            case "ch":
            case "lock":
                return channel(label,sender,argumentos);
            case "reload":
                return reload(label,sender,argumentos);

        }

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cErro de parametros, por favor use /sell help"));
        return true;
    }

    // -----------------------------------------------------------------------------------------------------------------------------//
    // Command Help
    // -----------------------------------------------------------------------------------------------------------------------------//
    public static boolean help(String label, CommandSender sender, List<String> argumentos){
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6&m--------------------&6(  &a&lSellHand&e  &6)&m--------------------"));
        if (sender instanceof Player){
            Player player = (Player) sender;
            FancyText.sendTo(player,       new FancyText("§3§l ▶ §a/" + label + " hand","§bVende todos os itens do seu inventário que são do mesmo tipo do que está na sua mão!","/rankup addItem"));
            FancyText.sendTo(player,       new FancyText("§3§l ▶ §a/" + label + " all|inv","§bVende todos os itens do seu inventário!","/rankup up"));
            sender.sendMessage("");
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&3&oPasse o mouse em cima dos comandos para ver a descrição!"));
        }else {
            sender.sendMessage("Esse comando só pode ser utilizado dentro do jogo!");
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6&m-----------------------------------------------------"));
        return true;
    }


    // -----------------------------------------------------------------------------------------------------------------------------//
    // Command Tell
    // -----------------------------------------------------------------------------------------------------------------------------//
    public static boolean tell(String label, CommandSender sender, String[] args){

        if (MuteUtil.isMuted(sender)){
            sender.sendMessage("§cVocê está mutado!");
            sender.sendMessage(MuteUtil.getMuteMessage(sender));
            return true;
        }

        if (args.length == 1){
            sender.sendMessage("§6§l ▶ §e/" + args[0] + " <Player> <msg>");
            return true;
        }

        Player target = null;
        boolean firstArgWasAPerson = false;
        if (args.length >= 2){
            Player possibleTarge = Bukkit.getPlayer(args[1]);
            if (possibleTarge != null){
                target = possibleTarge;
                firstArgWasAPerson = true;
            }else {
                String targetName = PrivateMessage.getLastTarget(sender.getName());
                if (targetName != null){
                    target = Bukkit.getPlayer(targetName);
                }
            }
        }

        if (target == null || !target.isOnline()){
            sender.sendMessage("§6§l ▶ §cJogador " + args[1] + " não encontrado!");
            return true;
        }



        StringBuilder msgBuilder = new StringBuilder();
        if (!firstArgWasAPerson){
            msgBuilder.append(args[1]);
        }
        int lastChar = args.length - 1;
        for (int i = 2; i < args.length; i++){
            if (i == lastChar){
                msgBuilder.append(args[i]);
            }else {
                msgBuilder.append(args[i] + " ");
            }
        }

        String theMsg = msgBuilder.toString();

        if (theMsg.isEmpty()){
            sender.sendMessage("§6§l ▶ §e/" + args[0] + " " + target.getName() + " <msg>");
            return true;
        }

        PrivateMessage.sendTell(sender,target,theMsg);
        return true;
    }

    // -----------------------------------------------------------------------------------------------------------------------------//
    // Command Channel
    // -----------------------------------------------------------------------------------------------------------------------------//
    public static boolean channel(String label, CommandSender sender, List<String> argumentos){

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

        if (argumentos.get(1).isEmpty()){
            sender.sendMessage("§6§l ▶ §a/" + argumentos.get(0) + " <" + String.join("|",allAliases) + ">");
            return true;
        }

        String channelThePlayersWants = argumentos.get(1).toLowerCase();

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
            sender.sendMessage("§6§l ▶ §cO canal §e" + argumentos.get(1) + "§c não existe!");
            return true;
        }

        ChannelManager.setPlayerLockChannel(player,fancyChannel);

        sender.sendMessage("§6§l ▶ §aCanal §e[" + fancyChannel.getName() + "]§a definido como padrão!");
        return true;
    }

    // -----------------------------------------------------------------------------------------------------------------------------//
    // Command Reload
    // -----------------------------------------------------------------------------------------------------------------------------//
    public static boolean reload(String label, CommandSender sender, List<String> argumentos){

        if ( !FCBukkitUtil.hasThePermission(sender,PermissionNodes.commandSell_RELOAD)){
            return true;
        }

        ConfigManager.initialize(EverNifeFancyChat.instance);

        sender.sendMessage("§aFancyChat foi recarregado!!!");
        return true;
    }
}
