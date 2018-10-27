package br.com.finalcraft.fancychat.config;

import java.util.Arrays;

public class DefaultConfigV1 {


    public static void setDefault(){

        if (ConfigManager.getMainConfig().getInt("ConfigVersion",0) != 1){
            ConfigManager.getMainConfig().setValue("ConfigVersion",1);

            ConfigManager.getMainConfig().setValue("TellTag.sender-format","&6&l({sender}&6&l)&6 -> &6&l(&3{receiver}&6&l) &8&l> &f");
            ConfigManager.getMainConfig().setValue("TellTag.receiver-format","&6&l({sender}&6&l)&6 -> &6&l(&3{receiver}&6&l) &8&l> &f");
            ConfigManager.getMainConfig().setValue("TellTag.hover-messages", Arrays.asList("&3Isso é uma mensagem privada!"));

            ConfigManager.getMainConfig().setValue("TagFormats.ch-global.format","&8&l(&a&l&oG&8&l) &r");
            ConfigManager.getMainConfig().setValue("TagFormats.ch-global.run-command","ch g");
            ConfigManager.getMainConfig().setValue("TagFormats.ch-global.hover-messages",Arrays.asList("&3Canal: &a&oGlobal","&bClique para travar no canal Global"));

            ConfigManager.getMainConfig().setValue("ChannelFormats.Global.alias","g");
            ConfigManager.getMainConfig().setValue("ChannelFormats.Global.distance",-1);
            ConfigManager.getMainConfig().setValue("ChannelFormats.Global.tag-builder","ch-global,group-prefix,nickname,global-premes");
            ConfigManager.getMainConfig().setValue("ChannelFormats.Global.permission","");

            ConfigManager.getMainConfig().save();
        }
    }

}
