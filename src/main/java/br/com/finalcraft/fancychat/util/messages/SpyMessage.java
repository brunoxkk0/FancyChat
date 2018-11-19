package br.com.finalcraft.fancychat.util.messages;

import br.com.finalcraft.fancychat.fancytextchat.FancyText;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SpyMessage {

    private static List<Player> spyingPlayers = new ArrayList<Player>();

    /**
     *
     * @return <tt>true</tt> if this player has benn added to the SpyList
     * @return <tt>false</tt> if this player has benn removed from the SpyList
     */
    public static boolean changeSpyState(Player player){
        if (spyingPlayers.contains(player)){
            spyingPlayers.remove(player);
            return false;
        }
        spyingPlayers.add(player);
        return true;
    }

    public static void spyOnThis(List<FancyText> msg, List<Player> playerThatHeardedThis){
        if (spyingPlayers.size() == 0){
            return;
        }

        StringBuilder allPlayersThatHearded = new StringBuilder();
        for (Player player : playerThatHeardedThis){
            allPlayersThatHearded.append("\n  - " + player.getName());
        }

        msg.forEach(fancyText -> {
            fancyText.text = "§7" + ChatColor.stripColor(fancyText.text);
            fancyText.setHoverText("Jogadores que escutaram essa mensagem: \n " + allPlayersThatHearded);
        });

        for (Player player : spyingPlayers){
            if (player.isOnline() && !playerThatHeardedThis.contains(player)){
                FancyText.sendTo(player,msg);
            }
        }
    }
}
