package br.com.finalcraft.fancychat.config.fancychat;

import br.com.finalcraft.fancychat.EverNifeFancyChat;
import br.com.finalcraft.fancychat.config.ConfigManager;
import br.com.finalcraft.fancychat.fancytextchat.FancyText;
import org.bukkit.ChatColor;

public class TellTag {

    public static TellTag TELL_TAG;

    public String name;
    public String sender_format;
    public String receiver_format;
    public String hover_message;
    public String suggest_command;

    FancyText fancyTextSender;
    FancyText fancyTextReceiver;

    public static void initialize(){
        TELL_TAG = new TellTag();
        EverNifeFancyChat.info("§aFinished Loading TellTag!");
    }

    public TellTag(){
        this.name               = "TellTag";
        this.sender_format      = ConfigManager.getMainConfig().getString("TellTag.sender-format","");
        this.receiver_format    = ConfigManager.getMainConfig().getString("TellTag.receiver-format","");
        this.suggest_command    = ConfigManager.getMainConfig().getString("TellTag.suggest-command","");

        StringBuilder hoverBuilder = new StringBuilder();
        for (String line : ConfigManager.getMainConfig().getStringList("TellTag.hover-messages")){
            hoverBuilder.append(line + "\n");
        }
        if (hoverBuilder.toString().isEmpty()){
            this.hover_message = "";
        }else {
            this.hover_message = hoverBuilder.substring(0,hoverBuilder.length() - 1);
        }

        this.sender_format           = ChatColor.translateAlternateColorCodes('&',this.sender_format);
        this.receiver_format         = ChatColor.translateAlternateColorCodes('&',this.receiver_format);
        this.hover_message           = ChatColor.translateAlternateColorCodes('&',this.hover_message);
        suggest_command = (!suggest_command.startsWith("/") ? "/" + suggest_command : suggest_command);

        fancyTextSender = new FancyText(sender_format);
        fancyTextReceiver = new FancyText(receiver_format);
        if (!this.hover_message.isEmpty()){
            fancyTextSender.setHoverText(hover_message);
            fancyTextReceiver.setHoverText(hover_message);
        }

        if (!this.suggest_command.isEmpty()) {
            fancyTextSender.setSuggestCommandAction(suggest_command);
            fancyTextReceiver.setSuggestCommandAction(suggest_command);
        }
    }

    public String getName() {
        return name;
    }

    public FancyText getFancyTextSender() {
        return fancyTextSender;
    }

    public FancyText getFancyTextReceiver() {
        return fancyTextReceiver;
    }
}
