package br.com.finalcraft.fancychat.config;

import java.util.Arrays;

public class DefaultConfigV1 {


    public static void setDefault(){

        if (ConfigManager.getMainConfig().getInt("ConfigVersion",0) != 1){
            ConfigManager.getMainConfig().setValue("ConfigVersion",1);

            ConfigManager.getMainConfig().setValue("TellTag.sender-format"," {sender} &b-> &r{receiver}&r > &f");
            ConfigManager.getMainConfig().setValue("TellTag.receiver-format"," {sender} &c-> &r{receiver}&r > &f");
            ConfigManager.getMainConfig().setValue("TellTag.hover-messages", Arrays.asList("&3Isso é uma mensagem privada!","This is a private message"));

            ConfigManager.getMainConfig().setValue("TagFormats.ch-global.format","&7[&aG&7]");
            ConfigManager.getMainConfig().setValue("TagFormats.ch-global.run-command","/ch g");
            ConfigManager.getMainConfig().setValue("TagFormats.ch-global.hover-messages",Arrays.asList("&3Channel: &a&oGlobal","&bClick here to lock conversation in this channel"));

            ConfigManager.getMainConfig().setValue("TagFormats.nickname.format","{player}");

            ConfigManager.getMainConfig().setValue("TagFormats.nickname.,global-premes"," &r > {msg}");

            ConfigManager.getMainConfig().setValue("ChannelFormats.Global.alias","g");
            ConfigManager.getMainConfig().setValue("ChannelFormats.Global.distance",-1);
            ConfigManager.getMainConfig().setValue("ChannelFormats.Global.tag-builder","ch-global,nickname,global-premes");
            ConfigManager.getMainConfig().setValue("ChannelFormats.Global.permission","");

            ConfigManager.getMainConfig().save();
        }
    }

}
