package br.com.finalcraft.fancychat.util.messages;

import br.com.finalcraft.fancychat.fancytextchat.FancyText;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpyMessage {

    private static Map<Player,String> spyingPlayers = new HashMap<>();

    /**
     *
     * @return <tt>true</tt> if this player has benn added to the SpyList
     * @return <tt>false</tt> if this player has benn removed from the SpyList
     */
    public static boolean changeSpyState(Player player, String color){
        if (spyingPlayers.containsKey(player)){
            spyingPlayers.remove(player);
            return false;
        }
        spyingPlayers.put(player, color);
        return true;
    }

    public static void spyOnThis(List<FancyText> msg, List<Player> allPlayerWhoHeard){
        if (spyingPlayers.size() == 0){
            return;
        }

        StringBuilder allPlayerWhoHeardString = new StringBuilder();
        for (Player player : allPlayerWhoHeard){
            allPlayerWhoHeardString.append("\n  - " + player.getName());
        }

        msg.forEach(fancyText -> {
            fancyText.setText("§7" + ChatColor.stripColor(fancyText.text));
            fancyText.setHoverText("Jogadores que escutaram essa mensagem: \n " + allPlayerWhoHeardString);
        });


        String previousColor = "§7";
        for (Map.Entry<Player, String> playerStringEntry : spyingPlayers.entrySet()) {
            if (playerStringEntry.getKey().isOnline()){
                if (!allPlayerWhoHeard.contains(playerStringEntry.getKey())){
                    if (!previousColor.equalsIgnoreCase(playerStringEntry.getValue())){
                        previousColor = playerStringEntry.getValue();
                        for (FancyText fancyText : msg) {
                            fancyText.setText(previousColor + ChatColor.stripColor(fancyText.text));
                        }
                    }
                    FancyText.sendTo(playerStringEntry.getKey(),msg);
                }
            }
        }
    }
}
