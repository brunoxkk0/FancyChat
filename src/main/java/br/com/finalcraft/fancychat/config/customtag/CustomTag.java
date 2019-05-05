package br.com.finalcraft.fancychat.config.customtag;

import br.com.finalcraft.fancychat.EverNifeFancyChat;
import br.com.finalcraft.fancychat.config.ConfigManager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomTag {

    private String name;
    private String theTag;
    private String permission;

    public static Map<String,CustomTag> mapOfCustomTag = new HashMap<>();
    public static void initialize(){
        mapOfCustomTag.clear();

        for (String customTagName : ConfigManager.getCustomTags().getKeys("CustomTags")){
            CustomTag customTag = new CustomTag(customTagName);
            mapOfCustomTag.put(customTagName,customTag);
        }

        EverNifeFancyChat.info("§aFinished Loading " + mapOfCustomTag.size() + " CustomTags!");
    }

    public static Collection<CustomTag> getAllCustomtags(){
        return mapOfCustomTag.values();
    }

    public static CustomTag getCustomTag(String tagName){
        return mapOfCustomTag.get(tagName);
    }

    public CustomTag(String name) {
        this.name           = name;
        this.theTag         = ConfigManager.getCustomTags().getString("CustomTags." + name + ".theTag","");
        this.permission     = ConfigManager.getCustomTags().getString("CustomTags." + name + ".permission","");
    }

    public String getName() {
        return name;
    }

    public String getTheTag() {
        return theTag;
    }

    public String getPermission() {
        return permission;
    }
}
