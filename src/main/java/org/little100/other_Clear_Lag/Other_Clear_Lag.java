package org.little100.other_Clear_Lag;

import org.bukkit.plugin.java.JavaPlugin;
import org.little100.other_Clear_Lag.commands.ClearLagCommand;
import org.little100.other_Clear_Lag.listeners.DropItemListener;
import org.little100.other_Clear_Lag.managers.ClearManager;
import org.little100.other_Clear_Lag.managers.ConfigManager;

public final class Other_Clear_Lag extends JavaPlugin {

    private ConfigManager configManager;
    private ClearManager clearManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Other_Clear_Lag enabling...");

        configManager = new ConfigManager(this);
        clearManager = new ClearManager(this, configManager);

        getServer().getPluginManager().registerEvents(new DropItemListener(this, clearManager, configManager), this);
        getCommand("ocl").setExecutor(new ClearLagCommand(configManager, clearManager));

        getLogger().info("Other_Clear_Lag has been enabled.");
        getLogger().info("Made by Little100");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Other_Clear_Lag has been disabled.");
    }
}
