package br.com.finalcraft.fancychat.integration;

import br.com.finalcraft.fancychat.EverNifeFancyChat;
import br.com.finalcraft.fancychat.config.ConfigManager;

public class FactionsIntegration {

    private static boolean alreadyHooked = false;
    public static void initialize(){
        if (!alreadyHooked && ConfigManager.getMainConfig().getBoolean("Settings.Integrations.Factions",false)){
            alreadyHooked = true;
            EverNifeFancyChat.info("&aIntegração com Factions Ativada!");
        }
    }

}
