package br.com.finalcraft.fancychat.util.messages;

import br.com.finalcraft.fancychat.config.fancychat.TellTag;
import br.com.finalcraft.fancychat.config.lang.FancyChatLang;
import br.com.finalcraft.fancychat.fancytextchat.FancyText;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

public class PrivateMessage {

    public static Map<String,String> tellHistory = new HashMap<String, String>();

    public static void sendTell(Player sender, Player receiver, String msg){
        if (receiver == null
                || (receiver instanceof Player && (!((Player)receiver).isOnline()
                || (sender instanceof Player && !((Player)sender).canSee((Player)receiver))))
        ){
            sender.sendMessage(FancyChatLang.getLang("listener.invalidplayer"));
            return;
        }
        tellHistory.put(sender.getName(),receiver.getName());
        tellHistory.put(receiver.getName(),sender.getName());

        FancyText fancyTextSender   = TellTag.TELL_TAG.getFancyTextSender().parsePlaceholdersAndClone(sender);
        FancyText fancyTextReceiver = TellTag.TELL_TAG.getFancyTextReceiver().parsePlaceholdersAndClone(sender);

        fancyTextSender.text = fancyTextSender.text.replace("{sender}",sender.getName()).replace("{receiver}",receiver.getName());
        fancyTextReceiver.text = fancyTextReceiver.text.replace("{sender}",sender.getName()).replace("{receiver}",receiver.getName());

        if (sender.hasPermission("fancychat.color")){
            msg = ChatColor.translateAlternateColorCodes('&', msg);
        }

        FancyText fancyTextMessage = new FancyText(msg);

        List<FancyText> senderFancyText = new ArrayList(Arrays.asList(fancyTextSender,fancyTextMessage));

        FancyText.sendTo(sender, senderFancyText);
        FancyText.sendTo(receiver,Arrays.asList(fancyTextReceiver,fancyTextMessage));


        SpyMessage.spyOnThis(senderFancyText,new ArrayList(Arrays.asList(sender,receiver)));
    }


    public static String getLastTarget(String source){
        return tellHistory.getOrDefault(source,null);
    }
}
