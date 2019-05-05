package br.com.finalcraft.fancychat.commands;


import br.com.finalcraft.fancychat.util.MuteUtil;
import br.com.finalcraft.fancychat.util.messages.PrivateMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDTell implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        tell(label,sender,args);
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

        if (args.length == 0){
            sender.sendMessage("§6§l ▶ §e/" + label + " <Player> <msg>");
            return true;
        }

        Player target = null;
        boolean firstArgWasAPerson = false;
        if (args.length >= 1){
            Player possibleTarget = Bukkit.getPlayer(args[0]);
            if (possibleTarget != null){
                target = possibleTarget;
                firstArgWasAPerson = true;
            }else {
                String targetName = PrivateMessage.getLastTarget(sender.getName());
                if (targetName != null){
                    target = Bukkit.getPlayer(targetName);
                }
            }
        }

        if (target == null || !target.isOnline()){
            sender.sendMessage("§6§l ▶ §cJogador " + args[0] + " não encontrado!");
            return true;
        }



        StringBuilder msgBuilder = new StringBuilder();
        if (!firstArgWasAPerson){
            msgBuilder.append(args[0] + " ");
        }
        int lastChar = args.length - 1;
        for (int i = 1; i < args.length; i++){
            if (i == lastChar){
                msgBuilder.append(args[i]);
            }else {
                msgBuilder.append(args[i] + " ");
            }
        }

        String theMsg = msgBuilder.toString();

        if (theMsg.isEmpty()){
            sender.sendMessage("§6§l ▶ §e/" + label + " " + target.getName() + " <msg>");
            return true;
        }

        PrivateMessage.sendTell((Player)sender,target,theMsg);
        return true;
    }
}
