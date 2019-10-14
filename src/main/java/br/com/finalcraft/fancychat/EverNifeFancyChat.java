package br.com.finalcraft.fancychat;

import br.com.finalcraft.fancychat.api.CustomTagParser;
import br.com.finalcraft.fancychat.commands.CommandRegisterer;
import br.com.finalcraft.fancychat.config.ConfigManager;
import br.com.finalcraft.fancychat.config.customtag.CustomTag;
import br.com.finalcraft.fancychat.integration.builtin.DefaultParser;
import br.com.finalcraft.fancychat.integration.builtin.FactionsParser;
import br.com.finalcraft.fancychat.listener.FancyChatListener;
import br.com.finalcraft.fancychat.placeholders.PlaceHolderIntegration;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class EverNifeFancyChat extends JavaPlugin{

    public static EverNifeFancyChat instance;

    public static void info(String msg){
        instance.getLogger().info("[Info] " + msg.replace("&","§"));
    }
    public static void chatLog(String msg){
        instance.getLogger().info("[ChatLog] " + msg);
    }
    public static void debug(String msg){
        instance.getLogger().info("[Debug] " + msg.replace("&","§"));
    }

    private FancyChatListener fancyChatListener = new FancyChatListener();
    private static CustomTagParser customTagParser;

    @Override
    public void onEnable() {
        instance = this;

        info("&aIniciando o Plugin...");

        info("&aCarregando configuracoes...");
        ConfigManager.initialize(this);

        info("&aRegistrando comandos...");
        CommandRegisterer.registerCommands(this);

        info("&aRegistrando Listeners");
        this.getServer().getPluginManager().registerEvents(fancyChatListener, this);

        //Iniciando PlaceHolderAPI Integration
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
            info("&aIntegration to PlaceHolderAPI");
            PlaceHolderIntegration.initialize(this);
        }

        //Iniciando Factions Integration
        if (Bukkit.getPluginManager().isPluginEnabled("Factions")){
            info("&aIntegration to Factions");
            FactionsParser.initialize();
        }

        DefaultParser.initialize();

        try {
            customTagParser  = new CustomTagParser();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(fancyChatListener);
    }

    public static CustomTagParser getCustomTagParser(){
        return customTagParser;
    }


}
