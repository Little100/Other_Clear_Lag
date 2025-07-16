package org.little100.other_Clear_Lag.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Boat;
import org.bukkit.scheduler.BukkitRunnable;
import org.little100.other_Clear_Lag.Other_Clear_Lag;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ClearManager {

    private final Other_Clear_Lag plugin;
    private final ConfigManager configManager;
    private int countdown;
    private final Set<UUID> confirmedDrops = new HashSet<>();

    public ClearManager(Other_Clear_Lag plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
        this.countdown = configManager.getClearInterval();
        startCountdown();
    }

    public int getCountdown() {
        return countdown;
    }

    public Set<UUID> getConfirmedDrops() {
        return confirmedDrops;
    }

    private void startCountdown() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (configManager.getWarningTimes().contains(countdown)) {
                    String message = configManager.getWarningMessage().replace("[time]", String.valueOf(countdown));
                    Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(ChatColor.translateAlternateColorCodes('&', message)));
                }

                if (countdown <= 0) {
                    clearItems();
                    countdown = configManager.getClearInterval();
                    confirmedDrops.clear();
                }

                countdown--;
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    private void clearItems() {
        int itemCount = 0;
        int vehicleCount = 0;
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity instanceof Item) {
                    entity.remove();
                    itemCount++;
                }
                if (configManager.isRemoveVehicles()) {
                    if ((entity instanceof Boat || entity instanceof Minecart) && entity.getPassengers().isEmpty()) {
                        entity.remove();
                        vehicleCount++;
                    }
                }
            }
        }
        String message = configManager.getClearMessage()
                .replace("[count]", String.valueOf(itemCount))
                .replace("[vehicles]", String.valueOf(vehicleCount));
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
        plugin.getLogger().info(ChatColor.stripColor(message));
    }

    public void manualClear() {
        clearItems();
        countdown = configManager.getClearInterval();
        confirmedDrops.clear();
    }
}