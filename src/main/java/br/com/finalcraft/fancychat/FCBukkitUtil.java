package br.com.finalcraft.fancychat;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FCBukkitUtil {

    public static Random random = new Random();
    public static CommandSender consoleSender = Bukkit.getConsoleSender();

    public static Random getRandom(){
        return random;
    }

    //===========================================================================================
    //  Documented Functions
    //===========================================================================================

    /**
     * Verifica se um CommandSender é de fato um Player online!
     *
     * @param sender O jogador a ser verificado
     *
     * @return retorna <>true</> se não é um jogador e <>false</>
     * caso contrário
     */
    public static boolean isNotPlayer(CommandSender sender) {
        if ( !(sender instanceof Player)){
            sender.sendMessage("§cApenas jogadores podem usar esse comando!");
            return true;
        }
        return false;
    }

    /**
     * Verifica se um dado jogador possui uma determinada permissão
     * e retorna true ou false, alem de notificar o jogador que ele precisa
     * dessa determina permissão.
     *
     * @param player O jogador a ser verificado
     * @param permission A permissão a ser checada
     *
     * @return retorna <>true</> se o jogador possui a permissão e <>false</>
     * caso contrário
     */
    public static boolean hasThePermission(CommandSender player, String permission) {

        if (!player.hasPermission(permission)){
            player.sendMessage("§cVocê não tem permissão " + permission + " para fazer isto.");
            return false;
        }
        return true;
    }

    /**
     *   Força o console a executar um comando!
     */
    public static void makeConsoleExecuteCommand(String theCommand){
        Bukkit.dispatchCommand(consoleSender, theCommand);
    }

    /**
     *   Força o jogador a executar um comando!
     */
    public static void makePlayerExecuteCommand(CommandSender player, String theCommand){
        Bukkit.dispatchCommand(player, theCommand);
    }

    /**
     *   Transforma os argumentos inseridos em um Arraylist!
     */
    public static List<String> parseBukkitArgsToList(String[] args, int numOfArgs){
        List<String> argumentos = new ArrayList<String>();
        for (int i = 0; i < numOfArgs; i++) {
            if (i < args.length)
                argumentos.add(args[i]);
            else
                argumentos.add("");
        }
        return argumentos;
    }

    public static void playSound(String playerName, String music) {
        makeConsoleExecuteCommand("playsound " + music + " " + playerName + " ~0 ~0 ~0 100");
    }


}
