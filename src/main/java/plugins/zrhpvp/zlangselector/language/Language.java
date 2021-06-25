package plugins.zrhpvp.zlangselector.language;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.configuration.file.FileConfiguration;
import plugins.zrhpvp.zlangselector.ZLangSelector;
import plugins.zrhpvp.zlangselector.utils.Message;

public class Language {

    private final String language;
    private final String id;
    private final String displayName;

    public Language(String language, String id, String displayName) {
        this.language = language.toLowerCase();
        this.id = id;
        this.displayName = Message.color(displayName);
    }

    public static Language valueOf(String string) {

        FileConfiguration languages = ZLangSelector.getInstance().getFileManager().getLanguages();

        if (languages.contains(string.toLowerCase())) {
            return new Language(languages.getString(string + ".Name"), languages.getString(string + ".ID"), languages.getString(string + ".DisplayName"));
        }

        return null;
    }

    /**
     * Gets the available languages list
     *
     * @return the Language list
     */
    public static List<Language> getList() {
        FileConfiguration languages = ZLangSelector.getInstance().getFileManager().getLanguages();

        ArrayList<Language> langs = new ArrayList<>();

        languages.getKeys(false).forEach(lang -> {
            langs.add(valueOf(lang));
        });

        return langs;
    }

    /**
     * Convert this language to string
     *
     * @return the Language converted to string
     */
    @Override
    public String toString() {
        return this.language;
    }

    /**
     * Gets the ID from this language
     *
     * @return the ID
     */
    public String getID() {
        return this.id;
    }

    /**
     * Gets the Display Name from this language
     *
     * @return the Display Name
     */
    public String getDisplayName() {
        return this.displayName;
    }

}
