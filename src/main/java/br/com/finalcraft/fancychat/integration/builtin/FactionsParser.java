package br.com.finalcraft.fancychat.integration.builtin;

import br.com.finalcraft.fancychat.integration.ThirdPartTagsParser;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class FactionsParser extends ThirdPartTagsParser {

    private static boolean isHooked = false;
    public static void initialize(){
        if (!isHooked){
            isHooked = true;
            addThirdPartTagsParser(new FactionsParser());
        }
    }

    @Override
    public String parseTags(String theMessage, Player sender, Player receiver) {
        if (sender != null){
            MPlayer mp = MPlayer.get(sender.getUniqueId());
            if (!mp.getFaction().isNone()) {
                Faction fac = mp.getFaction();
                theMessage = theMessage
                        .replace("{faction-name}"       , fac.getName())
                        .replace("{faction-kdr}"        , fac.getKdrRounded())
                        .replace("{faction-kills}"      , String.valueOf(fac.getKills()))
                        .replace("{faction-deaths}"     , String.valueOf(fac.getDeaths()))
                        .replace("{faction-lifetime}"   , String.valueOf(TimeUnit.MILLISECONDS.toDays(fac.getAge())))
                        .replace("{faction-motd}"       , (fac.hasMotd() ? fac.getMotd() : "" ))
                        .replace("{faction-description}", (fac.hasDescription() ? fac.getDescription() : "" ))
                ;
                //.replace("{faction-owner}", fac.getLeader().getNameAndFactionName());  //Should be only "getName()" but seems to be private!
                return theMessage;
            }
        }
        theMessage = theMessage
                .replace("{faction-name}", "")
                .replace("{faction-kdr}", "")
                .replace("{faction-kills}", "")
                .replace("{faction-deaths}", "")
                .replace("{faction-lifetime}", "")
                .replace("{faction-motd}", "")
                .replace("{faction-lifetime}", "");
        return theMessage;
    }
}
