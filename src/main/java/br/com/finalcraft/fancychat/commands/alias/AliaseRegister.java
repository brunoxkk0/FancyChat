package br.com.finalcraft.fancychat.commands.alias;

import br.com.finalcraft.fancychat.EverNifeFancyChat;
import br.com.finalcraft.fancychat.commands.CommandRegisterer;
import br.com.finalcraft.fancychat.config.fancychat.FancyChannel;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AliaseRegister {

    public static void registerChannelAliases(){

        List<FancyChannel> allFancyChannels = new ArrayList<FancyChannel>(FancyChannel.mapOfFancyChannels.values());
        List<String> allAliases = new ArrayList<String>();
        allFancyChannels.forEach(fancyChannel -> {
            allAliases.add(fancyChannel.getAlias().toLowerCase());
        });


        registerChannelAlise("inchannel", allAliases, true, CommandRegisterer.cmdInChannel);
    }

    public static void registerChannelAlise(String name, List<String> aliases, boolean shouldReg, CommandExecutor executor) {
        List<String> aliases1 = new ArrayList<>(aliases);

        for (Command cmd: PluginCommandYamlParser.parse(EverNifeFancyChat.instance)){
            if (cmd.getName().equals(name)){
                if (shouldReg){
                    Bukkit.getServer().getPluginCommand(name).setExecutor(executor);
                    cmd.setAliases(aliases1);
                    cmd.setLabel(name);
                }
                try {
                    Field field = SimplePluginManager.class.getDeclaredField("commandMap");
                    field.setAccessible(true);
                    CommandMap commandMap = (CommandMap)(field.get(Bukkit.getServer().getPluginManager()));
                    if (shouldReg){
                        Method register = commandMap.getClass().getMethod("register", String.class, Command.class);
                        register.invoke(commandMap, cmd.getName(),cmd);
                        ((PluginCommand) cmd).setExecutor(executor);
                    } else if (Bukkit.getServer().getPluginCommand(name).isRegistered()){
                        Bukkit.getServer().getPluginCommand(name).unregister(commandMap);
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
