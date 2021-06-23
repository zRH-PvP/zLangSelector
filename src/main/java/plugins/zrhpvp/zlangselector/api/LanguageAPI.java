package plugins.zrhpvp.zlangselector.api;

import org.bukkit.entity.Player;
import plugins.zrhpvp.zlangselector.ZLangSelector;
import plugins.zrhpvp.zlangselector.language.Language;

public class LanguageAPI {

    public void setPlayerLang(Player p, Language language) {
        ZLangSelector.getInstance().getLangManager().setPlayerLang(p, language);
    }

    public Language getPlayerLang(Player p) {
        return ZLangSelector.getInstance().getLangManager().getPlayerLang(p);
    }

}
