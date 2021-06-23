package plugins.zrhpvp.zlangselector.managers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLManager {

    private static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS `PlayerLanguageData` (`Name` VARCHAR(16) PRIMARY KEY NOT NULL, `Language` VARCHAR(255) NOT NULL);";

    private static ConnectionPoolManager pool;

    public SQLManager() {
        pool = new ConnectionPoolManager();
    }

    public static ConnectionPoolManager getConnectionPoolManager() {
        return pool;
    }

    public void createTable() {

        try (Connection conn = pool.getConnection();
                PreparedStatement ps = conn.prepareStatement(CREATE_TABLE_QUERY)) {
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void onDisable() {
        pool.closePool();
    }

}
