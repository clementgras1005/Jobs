package fr.sofa.jobs;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CacheManager {
    
    private static Map<UUID, PlayerData> playerDataCache = new HashMap<>();
    
    public static PlayerData getPlayerData(UUID playerId) {
        return playerDataCache.get(playerId);
    }
    
    public static Map<UUID, PlayerData> getAllPlayer(){
    	return playerDataCache;
    }
    
    public static void setPlayerData(UUID playerId, PlayerData playerData) {
        playerDataCache.put(playerId, playerData);
    }
    
    public static void clearCache() {
        playerDataCache.clear();
    }
    
}
