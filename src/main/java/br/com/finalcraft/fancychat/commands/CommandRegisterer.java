package br.com.finalcraft.fancychat.commands;

import org.bukkit.plugin.java.JavaPlugin;

public class CommandRegisterer {


    public static CMDInChannel cmdInChannel = new CMDInChannel();

    public static void registerCommands(JavaPlugin pluginInstance) {
        pluginInstance.getCommand("chat").setExecutor(new CoreCommand());
        pluginInstance.getCommand("tell").setExecutor(new CMDTell());
        pluginInstance.getCommand("channellock").setExecutor(new CMDChannelLock());
        pluginInstance.getCommand("mute").setExecutor(new CMDMute());
    }

}
