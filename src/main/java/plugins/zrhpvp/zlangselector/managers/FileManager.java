package plugins.zrhpvp.zlangselector.managers;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import plugins.zrhpvp.zlangselector.ZLangSelector;

public class FileManager {

    private File usersFile, messagesFile, languagesFile;
    private FileConfiguration usersConfig, messagesConfig, languagesConfig;

    public FileConfiguration getUsers() {
        return this.usersConfig;
    }

    public FileConfiguration getMessages() {
        return this.messagesConfig;
    }

    public FileConfiguration getLanguages() {
        return this.languagesConfig;
    }

    public void createFiles() {

        ZLangSelector.getInstance().saveDefaultConfig();

        usersFile = new File(ZLangSelector.getInstance().getDataFolder(), "users.yml");
        messagesFile = new File(ZLangSelector.getInstance().getDataFolder(), "messages.yml");
        languagesFile = new File(ZLangSelector.getInstance().getDataFolder(), "languages.yml");

        if (!usersFile.exists()) {
            usersFile.getParentFile().mkdirs();
            ZLangSelector.getInstance().saveResource("users.yml", false);
        }

        if (!messagesFile.exists()) {
            messagesFile.getParentFile().mkdirs();
            ZLangSelector.getInstance().saveResource("messages.yml", false);
        }

        if (!languagesFile.exists()) {
            languagesFile.getParentFile().mkdirs();
            ZLangSelector.getInstance().saveResource("languages.yml", false);
        }

        usersConfig = new YamlConfiguration();
        messagesConfig = new YamlConfiguration();
        languagesConfig = new YamlConfiguration();

        try {
            usersConfig.load(usersFile);
            messagesConfig.load(messagesFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        try {
            messagesConfig.load(messagesFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        try {
            languagesConfig.load(languagesFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void saveFiles() {
        try {
            getUsers().save(usersFile);
            getMessages().save(messagesFile);
            getLanguages().save(languagesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadFiles() {
        usersConfig = YamlConfiguration.loadConfiguration(usersFile);
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
        languagesConfig = YamlConfiguration.loadConfiguration(languagesFile);
    }

}
