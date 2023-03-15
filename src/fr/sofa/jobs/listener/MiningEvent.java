package fr.sofa.jobs.listener;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;

import fr.sofa.db.DatabaseManager;
import fr.sofa.jobs.CacheManager;
import fr.sofa.jobs.PlayerData;

public class MiningEvent implements Listener {
    private final DatabaseManager databaseManager;

    public MiningEvent(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;

        // Enregistrement de l'écouteur d'événements
        Bukkit.getPluginManager().registerEvents(this, (Plugin) this);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        PlayerData playerData = null;

        // Récupération des données du joueur depuis le cache ou la base de données
        if(CacheManager.getPlayerData(playerUUID) != null) {
        	playerData = CacheManager.getPlayerData(playerUUID);
        }else {
        	playerData = databaseManager.getPlayerData(playerUUID);
        	CacheManager.setPlayerData(playerUUID, playerData);
        }
    }
}
