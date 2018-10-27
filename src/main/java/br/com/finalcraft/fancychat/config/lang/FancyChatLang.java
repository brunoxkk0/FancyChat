package br.com.finalcraft.fancychat.config.lang;

import java.util.HashMap;
import java.util.Map;

public class FancyChatLang {

    public static Map<String,String> mapOfLocale = new HashMap<String, String>();


    static {
        mapOfLocale.put("player.muted.perma","§6§l ▶ §cVocê está mutado!");
        mapOfLocale.put("listener.invalidplayer","§6§l ▶ §cEsse jogador não está online!");
    }

    public static String getLang(String key){
        return mapOfLocale.getOrDefault(key,"No translation for \"" + key + "\"");
    }
}
