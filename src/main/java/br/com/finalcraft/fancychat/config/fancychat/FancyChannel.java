package br.com.finalcraft.fancychat.config.fancychat;

import br.com.finalcraft.fancychat.EverNifeFancyChat;
import br.com.finalcraft.fancychat.config.ConfigManager;
import org.bukkit.entity.Player;

import java.util.*;

public class FancyChannel {

    public static FancyChannel GLOBAL_CHANNEL;
    public static FancyChannel DEFAULT_CHANNEL = null;

    public static String globalChannelName;
    public static String defaultChannelName;

    public String name;
    public String alias;
    public String tag_builder;
    public String permission;
    public int distance;

    public List<Player> playersOnThisChannel = new ArrayList<Player>();

    public List<FancyTag> tagsFromThisBuilder = new ArrayList<FancyTag>();

    public static Map<String, FancyChannel> mapOfFancyChannels = new HashMap<String, FancyChannel>();

    public static void initialize(){
        mapOfFancyChannels.clear();

        globalChannelName   = ConfigManager.getMainConfig().getString("Settings.globalChannelName","Global");
        defaultChannelName  = ConfigManager.getMainConfig().getString("Settings.defaultChannelName","Global");

        for (String fancyChannelName : ConfigManager.getMainConfig().getKeys("ChannelFormats")){
            FancyChannel fancyChannel = new FancyChannel(fancyChannelName);
            mapOfFancyChannels.put(fancyChannelName,fancyChannel);
            if (fancyChannelName.equalsIgnoreCase(globalChannelName)){
                GLOBAL_CHANNEL = fancyChannel;
            }
            if (fancyChannelName.equalsIgnoreCase(defaultChannelName)){
                DEFAULT_CHANNEL = fancyChannel;
            }
        }

        if (GLOBAL_CHANNEL == null || DEFAULT_CHANNEL == null){
            EverNifeFancyChat.info("[WARNING] Meu consagrado, você setou um canal default/global que não existe!");
        }

        EverNifeFancyChat.info("§aFinished Loading " + mapOfFancyChannels.size() + " FancyChannels!");
    }

    public FancyChannel(String name){
        this.name           = name;
        this.alias          = ConfigManager.getMainConfig().getString("ChannelFormats." + name + ".alias",("" + name.charAt(0)).toLowerCase());
        this.tag_builder    = ConfigManager.getMainConfig().getString("ChannelFormats." + name + ".tag-builder","");
        this.distance       = ConfigManager.getMainConfig().getInt("ChannelFormats." + name + ".distance",-1);
        this.permission     = ConfigManager.getMainConfig().getString("ChannelFormats." + name + ".permission","");

        for (String tagName : tag_builder.split(",")){
            FancyTag fancyTag = FancyTag.mapOfFancyTags.getOrDefault(tagName,null);
            if (fancyTag == null){
                EverNifeFancyChat.info("I was building the channels and found out that there is no \"" + tagName + "\" FancyTag.");
            }else {
                tagsFromThisBuilder.add(fancyTag);
            }
        }

    }

    public void addMember(Player player){
        this.playersOnThisChannel.add(player);
    }

    public void removeMember(Player player){
        this.playersOnThisChannel.remove(player);
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    public String getTag_builder() {
        return tag_builder;
    }

    public String getPermission() {
        return permission;
    }

    public int getDistance() {
        return distance;
    }

    public List<FancyTag> getTagsFromThisBuilder() {
        return tagsFromThisBuilder;
    }

    public List<Player> getPlayersOnThisChannel() {
        return playersOnThisChannel;
    }

    public static Collection<FancyChannel> getAllChannels(){
        return mapOfFancyChannels.values();
    }

    public static FancyChannel getFancyChannel(String name){
        for (FancyChannel fancyChannel : getAllChannels()){
            if (fancyChannel.getName().equalsIgnoreCase(name)){
                return fancyChannel;
            }
        }
        return null;
    }
}
