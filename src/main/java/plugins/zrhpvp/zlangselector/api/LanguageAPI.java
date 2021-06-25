package plugins.zrhpvp.zlangselector.api;

import org.bukkit.entity.Player;
import plugins.zrhpvp.zlangselector.ZLangSelector;
import plugins.zrhpvp.zlangselector.language.Language;

public class LanguageAPI {

    /**
     * Set the language to the target player
     * @param player
     * @param language 
     */
    public static void setPlayerLang(Player player, Language language) {
        ZLangSelector.getInstance().getLangManager().setPlayerLang(player, language);
    }

    /**
     * Get the language from the target player
     * @param player
     * @return the Language from the target player
     */
    public static Language getPlayerLang(Player player) {
        return ZLangSelector.getInstance().getLangManager().getPlayerLang(player);
    }

}
