package plugins.zrhpvp.zlangselector;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import plugins.zrhpvp.zlangselector.commands.LanguageCMD;
import plugins.zrhpvp.zlangselector.commands.ZLangSelectorCMD;
import plugins.zrhpvp.zlangselector.managers.ConnectionPoolManager;
import plugins.zrhpvp.zlangselector.listeners.InventoryClick;
import plugins.zrhpvp.zlangselector.managers.LangManager;
import plugins.zrhpvp.zlangselector.utils.Message;
import plugins.zrhpvp.zlangselector.managers.SQLManager;
import plugins.zrhpvp.zlangselector.managers.FileManager;
import plugins.zrhpvp.zlangselector.api.LanguageAPI;

public class ZLangSelector extends JavaPlugin {

    private static ZLangSelector instance = null;

    private FileManager fileManager = null;
    private SQLManager sqlManager = null;
    private LangManager langManager = null;
    private LanguageAPI languageAPI = null;

    @Override
    public void onEnable() {

        Message.send(Bukkit.getConsoleSender(), "&bzLangSelector &f>> Enabling plugin...");

        instance = this;
        fileManager = new FileManager();
        fileManager.createFiles();
        langManager = new LangManager();
        languageAPI = new LanguageAPI();

        registerCommands();
        registerEvents();

        if (getConfig().getBoolean("MySQL.Enabled") == true) {
            Message.send(Bukkit.getConsoleSender(), "");
            Message.send(Bukkit.getConsoleSender(), "&6MySQL &f>> &fConnecting to the database...");
            sqlManager = new SQLManager();

            try {
                ConnectionPoolManager.setupPool();
                sqlManager.createTable();
                Message.send(Bukkit.getConsoleSender(), "&6MySQL &f>> &aConnection to the database successful.");
            } catch (Exception e) {
                Message.send(Bukkit.getConsoleSender(), "&6MySQL &f>> &cThe connection to the database could not be completed.");
            }

            Message.send(Bukkit.getConsoleSender(), "");
        }
        Message.send(Bukkit.getConsoleSender(), "&bzLangSelector &f>> Plugin enabled.");
        Message.send(Bukkit.getConsoleSender(), "&bAuthor &f>> &bzRH_PvP_.");
    }

    @Override
    public void onDisable() {
        if (getConfig().getBoolean("MySQL.Enabled") == true) {
            sqlManager.onDisable();
        }

        Message.send(Bukkit.getConsoleSender(), "&4zLangSelector &f>> &cPlugin disabled.");
        Message.send(Bukkit.getConsoleSender(), "&4Author &f>> &bzRH_PvP_.");
    }

    public void registerCommands() {
        getCommand("language").setExecutor(new LanguageCMD());
        getCommand("zlangselector").setExecutor(new ZLangSelectorCMD());
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new InventoryClick(), this);
    }

    // Getter
    public static ZLangSelector getInstance() {
        return instance;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public SQLManager getSQLManager() {
        return sqlManager;
    }

    public LangManager getLangManager() {
        return langManager;
    }

    public LanguageAPI getLanguageAPI() {
        return languageAPI;
    }

}
