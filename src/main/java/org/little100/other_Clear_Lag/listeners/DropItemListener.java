package org.little100.other_Clear_Lag.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.Bukkit;
import org.little100.other_Clear_Lag.Other_Clear_Lag;
import org.little100.other_Clear_Lag.managers.ClearManager;
import org.little100.other_Clear_Lag.managers.ConfigManager;

public class DropItemListener implements Listener {

    private final Other_Clear_Lag plugin;
    private final ClearManager clearManager;
    private final ConfigManager configManager;

    public DropItemListener(Other_Clear_Lag plugin, ClearManager clearManager, ConfigManager configManager) {
        this.plugin = plugin;
        this.clearManager = clearManager;
        this.configManager = configManager;
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        int countdown = clearManager.getCountdown();
        int interceptTime = configManager.getInterceptTime();

        if (configManager.isDebug()) {
            System.out.println("[Other_Clear_Lag DEBUG] PlayerDropItemEvent triggered for " + player.getName());
        }

        if (countdown <= interceptTime) {
            if (!clearManager.getConfirmedDrops().contains(player.getUniqueId())) {
                event.setCancelled(true);
                String message = configManager.getInterceptMessage().replace("[time]", String.valueOf(countdown));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                clearManager.getConfirmedDrops().add(player.getUniqueId());
                if (configManager.isDebug()) {
                    System.out.println("[Other_Clear_Lag DEBUG] Cancelled drop for " + player.getName() + ".");
                }
            } else {
                clearManager.getConfirmedDrops().remove(player.getUniqueId());
                if (configManager.isDebug()) {
                    System.out.println("[Other_Clear_Lag DEBUG] Allowed drop for " + player.getName() + " (confirmed).");
                }
            }
        }
    }
}