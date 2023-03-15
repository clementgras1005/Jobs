package fr.sofa.db;

import java.sql.*;
import java.util.Collection;
import java.util.UUID;

import fr.sofa.jobs.PlayerData;

public class DatabaseManager {
    private final String url;
    private final String user;
    private final String password;

    public DatabaseManager(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public PlayerData getPlayerData(UUID uuid) {

        if (uuid == null) {
            return null;
        }

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            // Requ�te de r�cup�ration des donn�es du joueur
            PreparedStatement stmt = conn.prepareStatement("SELECT mining_level FROM player_data WHERE uuid = ?");
            stmt.setString(1, uuid.toString());
            ResultSet rs = stmt.executeQuery();

            // Si le joueur existe, r�cup�ration de ses donn�es
            if (rs.next()) {
                int miningLevel = rs.getInt("mining_level");
                return new PlayerData(uuid, miningLevel);
            }

            // Si le joueur n'existe pas, cr�ation d'un nouvel enregistrement avec des donn�es par d�faut
            PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO player_data (uuid, mining_level) VALUES (?, ?)");
            insertStmt.setString(1, uuid.toString());
            insertStmt.setInt(2, 0);
            insertStmt.executeUpdate();
            return new PlayerData(uuid, 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void savePlayerData(Collection<PlayerData> playerDataCollection) {
        // Enregistrement en bloc des donn�es des joueurs dans la base de donn�es
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            conn.setAutoCommit(false);

            // Requ�te de mise � jour des donn�es pour chaque joueur
            PreparedStatement stmt = conn.prepareStatement("UPDATE player_data SET mining_level = ? WHERE uuid = ?");
            for (PlayerData playerData : playerDataCollection) {
                stmt.setInt(1, playerData.getMiningLevel());
                stmt.setString(2, playerData.getUuid().toString());
                stmt.addBatch();
            }
            stmt.executeBatch();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
