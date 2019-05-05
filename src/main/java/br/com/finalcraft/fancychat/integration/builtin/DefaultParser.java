package br.com.finalcraft.fancychat.integration.builtin;

import br.com.finalcraft.fancychat.config.customtag.CustomTag;
import br.com.finalcraft.fancychat.integration.ThirdPartTagsParser;
import br.com.finalcraft.fancychat.placeholders.PlaceHolderIntegration;
import br.com.finalcraft.fancychat.util.CustomTagUtil;
import org.bukkit.entity.Player;

public class DefaultParser extends ThirdPartTagsParser {

    private static boolean isHooked = false;
    public static void initialize(){
        if (!isHooked){
            isHooked = true;
            addThirdPartTagsParser(new DefaultParser());
        }
    }

    @Override
    public String parseTags(String theMessage, Player sender, Player receiver) {
        if (sender != null){
            CustomTag customTag = CustomTagUtil.getActiveCustomTag(sender);
            String playerPrefix = customTag != null ? customTag.getTheTag() : PlaceHolderIntegration.parsePlaceholder("%vault_prefix%",sender);
            theMessage = theMessage
                    .replace("{player-prefix}"       , playerPrefix);

        }
        return theMessage;
    }
}
