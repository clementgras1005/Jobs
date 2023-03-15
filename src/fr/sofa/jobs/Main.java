package fr.sofa.jobs;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import fr.sofa.jobs.PlayerData;
import fr.sofa.jobs.listener.MiningEvent;
import fr.sofa.db.DatabaseManager;

public class Main extends JavaPlugin implements Listener {

    private DatabaseManager databaseManager;
	private CacheManager cacheManager;

    @Override
    public void onEnable() {
        this.cacheManager = new CacheManager();
        databaseManager = new DatabaseManager();
        
        // Enregistrement des événements
        Bukkit.getPluginManager().registerEvents(new MiningEvent(this, databaseManager), this);
    }
}