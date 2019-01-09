package de.valuga.jump.stats;
import de.valuga.database.DatabaseConnection;
import de.valuga.database.factory.DatabaseFactory;
import de.valuga.database.table.DataItem;
import de.valuga.database.table.DataItem.Type;
import de.valuga.database.table.Table;
import de.valuga.database.table.WhereStatement;
import de.valuga.database.table.executor.Upsert;
import de.valuga.database.table.executor.Value;
import de.valuga.database.table.query.AdditionalQueryData;
import de.valuga.database.table.query.Query;
import de.valuga.database.table.query.Result;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * @author Nico_ND1
 */
public class HighscoreStatistic {

    // TODO: Make jump-stats module more abstract so it can be used for more porpuses

    private final Table table;

    public HighscoreStatistic(String tableName) {
        final DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        final DatabaseFactory databaseFactory = databaseConnection.getDatabaseFactory();
        this.table = databaseFactory.createTable(tableName, new DataItem[]{
            databaseFactory.createDataItem("uuid", Type.UUID, true, false, true, 0),
            databaseFactory.createDataItem("name", Type.TEXT, false, false, true, 0),
            databaseFactory.createDataItem("highscore", Type.BIGINT, false, false, true, 0),
            databaseFactory.createDataItem("time", Type.BIGINT, false, false, true, 0)
        });
        try {
            databaseConnection.getConnection().createStatement().executeUpdate(this.table.createTable());
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

    public CompletableFuture<PlayerStats> getPlayerStats(UUID uuid, String name) {
        final DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        final DatabaseFactory databaseFactory = databaseConnection.getDatabaseFactory();
        final Query query = databaseFactory.createQuery(this.table, this.table.getDataItems(), new WhereStatement[]{
            databaseFactory.createWhereStatement("uuid", uuid),
            databaseFactory.createWhereStatement("name", name)
        }, new AdditionalQueryData[0]);
        final CompletableFuture<PlayerStats> completableFuture = new CompletableFuture<>();
        databaseConnection.execute(query).thenAccept(results -> completableFuture.complete(new PlayerStats(results)));

        return completableFuture;
    }

    @AllArgsConstructor
    @Data
    public static class PlayerStats {

        private UUID uuid;
        private String name;
        private long highscore = Long.MAX_VALUE;
        private long time;

        public PlayerStats(Result[] results) {
            for (Result result : results) {
                if (result == null) continue;
                if (result.getDataItem().getName().equalsIgnoreCase("uuid"))
                    this.uuid = UUID.fromString(result.getResult().toString());
                else if (result.getDataItem().getName().equalsIgnoreCase("name"))
                    this.name = result.getResult().toString();
                else if (result.getDataItem().getName().equalsIgnoreCase("highscore"))
                    this.highscore = Long.valueOf(result.getResult().toString());
                else if (result.getDataItem().getName().equalsIgnoreCase("time"))
                    this.time = Long.valueOf(result.getResult().toString());
            }
        }

    }

}
