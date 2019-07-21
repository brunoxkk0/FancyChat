package br.com.finalcraft.fancychat.commands;

import br.com.finalcraft.fancychat.EverNifeFancyChat;
import br.com.finalcraft.fancychat.FCBukkitUtil;
import br.com.finalcraft.fancychat.PermissionNodes;
import br.com.finalcraft.fancychat.config.ConfigManager;
import br.com.finalcraft.fancychat.fancytextchat.FancyText;
import br.com.finalcraft.fancychat.util.messages.SpyMessage;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
            case "spy":
                return spy(label,sender,argumentos);
            case "ignore":
                return ignore(label,sender,argumentos);
            case "ignorelist":
                return ignorelist(label,sender,argumentos);
            case "reload":
                return reload(label,sender,argumentos);

        }

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cErro de parametros, por favor use /" + label + " help"));
        return true;
    }

    // -----------------------------------------------------------------------------------------------------------------------------//
    // Command Help
    // -----------------------------------------------------------------------------------------------------------------------------//
    public static boolean help(String label, CommandSender sender, List<String> argumentos){
        sender.sendMessage("§6§m--------------------§6(  §a§lFancyChat§e  §6)§m--------------------");
        if (sender instanceof Player){
            Player player = (Player) sender;
            FancyText.sendTo(player, new FancyText("§3§l ▶ §a/" + label + " ignore <Player>","§bIgnora as mensagens de um jogador específico!").setSuggestCommandAction("/" + label + " ignore "));
            FancyText.sendTo(player, new FancyText("§3§l ▶ §a/" + label + " ignoredList","§bMostra todos os jogadores que você está ignorando!").setRunCommandActionText("/" + label + " ignoreList"));

            if (player.hasPermission(PermissionNodes.commandSpy)) FancyText.sendTo(player, new FancyText("§3§l ▶ §e/" + label + " spy [colorCode]","§bMostra todas as conversas do servidor. \n   Você pode usar a cor que quiser.","/" + label + " spy"));
            if (player.hasPermission(PermissionNodes.commandReload)) FancyText.sendTo(player, new FancyText("§3§l ▶ §a/" + label + " reload","§bRecarrega todas as configurações do Plugin!","/" + label + " reload"));
            sender.sendMessage("");
            sender.sendMessage("§3§oPasse o mouse em cima dos comandos para ver a descrição!");
        }else {
            sender.sendMessage("Esse comando só pode ser utilizado dentro do jogo!");
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6&m-----------------------------------------------------"));
        return true;
    }

    // -----------------------------------------------------------------------------------------------------------------------------//
    // Command Ignoire
    // -----------------------------------------------------------------------------------------------------------------------------//
    public static boolean ignore(String label, CommandSender sender, List<String> argumentos){

        sender.sendMessage("§cEsse comando ainda não foi ativado T.T");
        return true;
    }

    // -----------------------------------------------------------------------------------------------------------------------------//
    // Command IgnoreList
    // -----------------------------------------------------------------------------------------------------------------------------//
    public static boolean ignorelist(String label, CommandSender sender, List<String> argumentos){

        if ( !FCBukkitUtil.hasThePermission(sender,PermissionNodes.commandSpy)){
            return true;
        }

        sender.sendMessage("§cEsse comando ainda não foi ativado T.T");
        return true;
    }

    // -----------------------------------------------------------------------------------------------------------------------------//
    // Command Spy
    // -----------------------------------------------------------------------------------------------------------------------------//
    public static boolean version(String label, CommandSender sender, List<String> argumentos){

        if ( !FCBukkitUtil.hasThePermission(sender,PermissionNodes.commandSpy)){
            return true;
        }

        sender.sendMessage("§aFancyChat foi recarregado!!!");
        return true;
    }


    // -----------------------------------------------------------------------------------------------------------------------------//
    // Command Spy
    // -----------------------------------------------------------------------------------------------------------------------------//
    public static boolean spy(String label, CommandSender sender, List<String> argumentos){

        if (FCBukkitUtil.isNotPlayer(sender)){
            return true;
        }

        if ( !FCBukkitUtil.hasThePermission(sender,PermissionNodes.commandSpy)){
            return true;
        }

        Player player = (Player) sender;

        switch (argumentos.get(1).toLowerCase()){
            case "":
                sender.sendMessage("§6§l ▶ §e/" + label + " spy [ON|OFF|setColor]");
                return true;
            case "off":
                sender.sendMessage("§6§l ▶ §eChatSpy Desativado!");
                SpyMessage.changeSpyState(player,"§7",false);
                return true;
            case "on":
                sender.sendMessage("§6§l ▶ §aChatSpy Ativado!");
                SpyMessage.changeSpyState(player,"§7",true);
                return true;
            case "setcolor":
                if (!SpyMessage.isSpying(player)){
                    sender.sendMessage("§6§l ▶ §aSeu ChatSpy está desativado...");
                    return true;
                }
                String color = argumentos.get(2).isEmpty() ? "§7" : ChatColor.translateAlternateColorCodes('&',argumentos.get(2));
                SpyMessage.changeSpyState(player, color,true);
                sender.sendMessage("§6§l ▶ §aCor do ChatSpy alterado para: " + color + argumentos.get(2) + ")");
                return true;
        }
        sender.sendMessage("§6§l ▶ §aParâmetro inválido...");
        return true;
    }


    // -----------------------------------------------------------------------------------------------------------------------------//
    // Command Reload
    // -----------------------------------------------------------------------------------------------------------------------------//
    public static boolean reload(String label, CommandSender sender, List<String> argumentos){

        if ( !FCBukkitUtil.hasThePermission(sender,PermissionNodes.commandReload)){
            return true;
        }

        ConfigManager.initialize(EverNifeFancyChat.instance);

        sender.sendMessage("§aFancyChat foi recarregado!!!");
        return true;
    }
}
