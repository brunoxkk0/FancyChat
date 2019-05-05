package br.com.finalcraft.fancychat.commands;


import br.com.finalcraft.fancychat.FCBukkitUtil;
import br.com.finalcraft.fancychat.PermissionNodes;
import br.com.finalcraft.fancychat.util.MuteUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CMDMute implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        mute(label,sender,args);
        return true;
    }

    // -----------------------------------------------------------------------------------------------------------------------------//
    // Command Mute
    // -----------------------------------------------------------------------------------------------------------------------------//
    public static void mute(String label, CommandSender sender, String[] args){

        if (FCBukkitUtil.hasThePermission(sender, PermissionNodes.commandMute)){
            return;
        }

        List<String> argumentos = FCBukkitUtil.parseBukkitArgsToList(args,4);

        if (argumentos.get(0).isEmpty() || argumentos.get(1).isEmpty()){
            sender.sendMessage("§6§l ▶ §a/" + label + " <Player> <Tempo>");
            return;
        }

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(argumentos.get(0));

        if (offlinePlayer == null || !offlinePlayer.hasPlayedBefore()){
            sender.sendMessage("§6§l ▶ §cJogador §e" + argumentos.get(0) + " §cnão encontrado.");
            return;
        }
        long muteTimeInMillis;
        try {
            muteTimeInMillis = toMilliSec(argumentos.get(1).toLowerCase());
        }catch (NumberFormatException e){
            sender.sendMessage("§6§l ▶ §cErro de parâmetros, o tempo §e" + argumentos.get(1) + "§c é inválido!");
            sender.sendMessage("§6§l ▶ §6  Veja os exemplos: ");
            sender.sendMessage("§6§l ▶ §e/" + label + " Herobrina 5m");
            sender.sendMessage("§6§l ▶ §e/" + label + " Herobrine 3h");
            sender.sendMessage("§6§l ▶ §e/" + label + " Enirboreh 1d");
            sender.sendMessage("");
            return;
        }

        MuteUtil.mutePlayer(offlinePlayer.getName(), muteTimeInMillis);

        if (offlinePlayer.isOnline()){
            Player player = (Player) offlinePlayer;
            player.sendMessage("§6§l ▶ §eVocê foi mutado por " + MuteUtil.getMuteMessage(player));
        }
        sender.sendMessage("§6§l ▶ §aO jogador " + offlinePlayer.getName() + " foi mutado com sucesso!");
        return;
    }

    // I took this from here "https://github.com/DevLeoko/AdvancedBan/blob/f3c1c3b3fee957032ecd14399be83d03cfa1a906/src/main/java/me/leoko/advancedban/manager/TimeManager.java"
    public static long toMilliSec(String s) {
        // This is not my regex :P | From: http://stackoverflow.com/a/8270824
        String[] sl = s.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
        long i = Long.parseLong(sl[0]);
        if (sl.length == 1){
            return i;
        }
        switch (sl[1]) {
            case "s":
                return i * 1000;
            case "m":
                return i * 1000 * 60;
            case "h":
                return i * 1000 * 60 * 60;
            case "d":
                return i * 1000 * 60 * 60 * 24;
            case "w":
                return i * 1000 * 60 * 60 * 24 * 7;
            case "mo":
                return i * 1000 * 60 * 60 * 24 * 30;
            default:
                return -1;
        }
    }
}
