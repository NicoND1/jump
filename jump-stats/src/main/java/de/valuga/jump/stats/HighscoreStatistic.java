package de.valuga.jump.stats;
import de.valuga.database.DatabaseConnection;
import de.valuga.database.factory.DatabaseFactory;
import de.valuga.database.table.DataItem;
import de.valuga.database.table.DataItem.Type;
import de.valuga.database.table.Table;
import de.valuga.database.table.executor.Upsert;
import de.valuga.database.table.executor.Value;

import java.sql.SQLException;
import java.util.UUID;

/**
 * @author Nico_ND1
 */
public class HighscoreStatistic {

    private final Table table;

    public HighscoreStatistic(String tableName) {
        final DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        final DatabaseFactory databaseFactory = databaseConnection.getDatabaseFactory();
        this.table = databaseFactory.createTable(tableName, new DataItem[]{
            databaseFactory.createDataItem("uuid", Type.UUID, true, false, true, 0),
            databaseFactory.createDataItem("name", Type.TEXT, true, false, true, 16),
            databaseFactory.createDataItem("highscore", Type.BIGINT, false, false, true, 0),
            databaseFactory.createDataItem("time", Type.BIGINT, false, false, true, 0)
        });
        try {
            databaseConnection.getConnection().createStatement().executeUpdate(table.createTable());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void trackPlayer(UUID uuid, String name, long highscore, long time, Runnable runnable) {
        final DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        final DatabaseFactory databaseFactory = databaseConnection.getDatabaseFactory();
        final Upsert upsert = databaseFactory.createUpsert(this.table, new Value[]{
            databaseFactory.createValue("uuid", uuid),
            databaseFactory.createValue("name", name),
            databaseFactory.createValue("highscore", highscore),
            databaseFactory.createValue("time", time)
        });
        databaseConnection.execute(runnable, upsert);
    }

}
