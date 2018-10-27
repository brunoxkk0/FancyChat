package br.com.finalcraft.fancychat.config;

import br.com.finalcraft.fancychat.commands.alias.AliaseRegister;
import br.com.finalcraft.fancychat.config.fancychat.FancyChannel;
import br.com.finalcraft.fancychat.config.fancychat.FancyTag;
import br.com.finalcraft.fancychat.config.fancychat.TellTag;
import br.com.finalcraft.fancychat.cooldown.CooldownHelper;
import br.com.finalcraft.fancychat.util.ChannelManager;
import br.com.finalcraft.fancychat.util.MuteUtil;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager {

    public static JavaPlugin instance;

    public static Config mainConfig;
    public static Config dataStore;

    public static Config getMainConfig(){
        return mainConfig;
    }
    public static Config getDataStore(){
        return dataStore;
    }

    public static void initialize(JavaPlugin aInstace){
        instance = aInstace;

        mainConfig  = new Config(instance,"config.yml"      ,false);
        dataStore   = new Config(instance,"DataStore.yml"      ,false);

        DefaultConfigV1.setDefault();

        FancyTag.initialize();                      //Ler Tags
        TellTag.initialize();                       //Ler TellTag
        FancyChannel.initialize();                  //Ler Canais
        ChannelManager.refresh();                   //Carregar ChannelManager
        MuteUtil.initialize();                //Carregar mutes e tempmutes
        AliaseRegister.registerChannelAliases();    //Registrar os atalhos para os canais, /g /l etc
    }

}
