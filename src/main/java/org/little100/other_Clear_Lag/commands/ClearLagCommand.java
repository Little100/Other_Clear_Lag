package org.little100.other_Clear_Lag.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.little100.other_Clear_Lag.managers.ClearManager;
import org.little100.other_Clear_Lag.managers.ConfigManager;

public class ClearLagCommand implements CommandExecutor {

    private final ConfigManager configManager;
    private final ClearManager clearManager;

    public ClearLagCommand(ConfigManager configManager, ClearManager clearManager) {
        this.configManager = configManager;
        this.clearManager = clearManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.GREEN + "/ocl reload - 重载配置文件");
            sender.sendMessage(ChatColor.GREEN + "/ocl clear - 手动清理掉落物");
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("otherclearlag.admin")) {
                configManager.reloadConfig();
                sender.sendMessage(ChatColor.GREEN + "配置文件已重载!");
            } else {
                sender.sendMessage(ChatColor.RED + "你没有权限执行此命令!");
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("clear")) {
            if (sender.hasPermission("otherclearlag.admin")) {
                clearManager.manualClear();
                sender.sendMessage(ChatColor.GREEN + "手动清理已执行!");
            } else {
                sender.sendMessage(ChatColor.RED + "你没有权限执行此命令!");
            }
            return true;
        }

        return false;
    }
}