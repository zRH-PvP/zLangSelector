package plugins.zrhpvp.zlangselector.managers;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import plugins.zrhpvp.zlangselector.ZLangSelector;
import plugins.zrhpvp.zlangselector.language.Language;

public class LangManager {

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private final LoadingCache<String, String> cache = Caffeine.newBuilder().expireAfterWrite(10L, TimeUnit.SECONDS).build(this::fetchLang);

    private final FileConfiguration config, users;

    public LangManager() {
        this.config = ZLangSelector.getInstance().getConfig();
        this.users = ZLangSelector.getInstance().getFileManager().getUsers();
    }

    private void insertOrUpdate(String name, String language) {
        executorService.execute(() -> {
            try (Connection connection = SQLManager.getConnectionPoolManager().getConnection();
                    PreparedStatement insert = connection.prepareStatement("INSERT INTO `PlayerLanguageData` (`Name`, `Language`) VALUES (?,?) ON DUPLICATE KEY UPDATE " + "`Language`=VALUES(Language);")) {
                insert.setString(1, name);
                insert.setString(2, language);
                insert.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private String fetchLang(final String name) {

        try (Connection connection = SQLManager.getConnectionPoolManager().getConnection();
                PreparedStatement select = connection.prepareStatement("SELECT Language FROM PlayerLanguageData WHERE Name=?")) {

            select.setString(1, name);

            try (ResultSet resultSet = select.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("Language");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ZLangSelector.getInstance().getConfig().getString("DefaultLanguage");
    }

    public void setPlayerLang(OfflinePlayer p, Language language) {
        if (config.getBoolean("MySQL.Enabled")) {
            insertOrUpdate(p.getName(), language.toString());
            cache.invalidate(p);
        } else {
            users.set(p.getUniqueId().toString(), language.toString());
            ZLangSelector.getInstance().getFileManager().saveFiles();
        }
    }

    public Language getPlayerLang(OfflinePlayer p) {
        if (config.getBoolean("MySQL.Enabled")) {
            return Language.valueOf(cache.get(p.getName()));
        }

        return Language.valueOf(users.getString(p.getUniqueId().toString()));
    }

}
