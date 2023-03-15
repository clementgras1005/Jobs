package fr.sofa.jobs;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.sofa.db.DatabaseManager;

public class MiningLevelCommand implements CommandExecutor {
    private final DatabaseManager databaseManager;

    public MiningLevelCommand(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Cette commande doit être exécutée par un joueur.");
            return true;
        }

        Player player = (Player) sender;
        PlayerData playerData = null;

        // Récupération des données du joueur depuis le cache ou la base de données
        if(databaseManager.getPlayerData(player.getUniqueId()) != null) {
        	playerData = CacheManager.getPlayerData(player.getUniqueId());
        }else {
        	playerData = databaseManager.getPlayerData(player.getUniqueId());
        	CacheManager.setPlayerData(player.getUniqueId(), playerData);
        }

        if (playerData != null) {
            int miningLevel = playerData.getMiningLevel();
            player.sendMessage("Votre niveau de minage est : " + miningLevel);
        } else {
            player.sendMessage("Votre niveau de minage est : 0");
        }

        return true;
    }
}
