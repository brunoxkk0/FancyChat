package br.com.finalcraft.fancychat.util.messages;

import br.com.finalcraft.fancychat.EverNifeFancyChat;
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
    public static void changeSpyState(Player player, String color, boolean state){
        if (state == false && isSpying(player)){
            spyingPlayers.remove(player);
            return;
        }
        if (state == true){
            spyingPlayers.remove(player);
            spyingPlayers.put(player, color);
        }
    }

    public static boolean isSpying(Player player){
        return spyingPlayers.containsKey(player);
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
        for (FancyText fancyText : msg) {
            fancyText.setText(previousColor + ChatColor.stripColor(fancyText.text));
        }

        EverNifeFancyChat.chatLog(FancyText.textOnlyTextBuilder(msg));
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
