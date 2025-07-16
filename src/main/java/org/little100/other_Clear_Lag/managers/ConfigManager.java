package org.little100.other_Clear_Lag.managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.little100.other_Clear_Lag.Other_Clear_Lag;

import java.util.List;
import java.util.stream.Collectors;

public class ConfigManager {

    private final Other_Clear_Lag plugin;
    private FileConfiguration config;

    public ConfigManager(Other_Clear_Lag plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    public void loadConfig() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        config = plugin.getConfig();
    }

    public void reloadConfig() {
        plugin.reloadConfig();
        config = plugin.getConfig();
    }

    public int getClearInterval() {
        return config.getInt("clear-interval", 3600);
    }

    public boolean isRemoveVehicles() {
        return config.getBoolean("remove-vehicles", false);
    }

    public List<Integer> getWarningTimes() {
        return config.getIntegerList("warning-times");
    }

    public String getWarningMessage() {
        return config.getString("warning-message", "&c[扫地大妈] &a将在 &e[time] &a秒后清理掉落物!");
    }

    public String getClearMessage() {
        return config.getString("clear-message", "&c[扫地大妈] &a成功清理了 &e[count] &a个掉落物!");
    }

    public int getInterceptTime() {
        return config.getInt("intercept-time", 10);
    }

    public String getInterceptMessage() {
        return config.getString("intercept-message", "&c[扫地大妈] &a服务器将在 &e[time] &a秒后清理掉落物, 确定要丢弃吗? 再次丢弃将不再提示.");
    }

    public String getColoredMessage(String path) {
        String message = config.getString(path, "");
        return message.replace("&", "§");
    }
    public boolean isDebug() {
        return config.getBoolean("debug", false);
    }
}