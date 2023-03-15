package fr.sofa.jobs;

import java.util.UUID;

public class PlayerData {

    private final UUID uuid;
    private int miningLevel;

    public PlayerData(UUID uuid, int miningLevel) {
        this.uuid = uuid;
        this.miningLevel = miningLevel;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getMiningLevel() {
        return miningLevel;
    }

    public void setMiningLevel(int miningLevel) {
        this.miningLevel = miningLevel;
    }
}
