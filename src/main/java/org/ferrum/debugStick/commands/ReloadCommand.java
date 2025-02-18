package org.ferrum.debugStick.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.ferrum.debugStick.utils.ConfigManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ReloadCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            if (args[0].equals("reload")) {
                if (ConfigManager.loadConfig()) {
                    sender.sendMessage(ChatColor.GREEN + "DebugStick successfully reloaded");
                    return true;
                }
                sender.sendMessage(ChatColor.RED + "DebugStick config error, see console");
                return true;
            } else {
                sender.sendMessage(command.getUsage());
            }
        } else {
            sender.sendMessage(command.getUsage());
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1) {
            return List.of("reload");
        }
        return List.of();
    }
}
