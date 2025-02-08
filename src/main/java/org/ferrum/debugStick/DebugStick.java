package org.ferrum.debugStick;


import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import org.ferrum.debugStick.commands.ReloadCommand;
import org.ferrum.debugStick.listeners.DebugStickListener;
import org.ferrum.debugStick.utils.ConfigManager;


public final class DebugStick extends JavaPlugin {

    @Override
    public void onEnable() {
        ConfigManager.plugin = this;
        if(!ConfigManager.loadConfig()) {
            getLogger().severe("An error occurred while starting the plugin");
            getPluginLoader().disablePlugin(this);
            return;
        }

        DebugStickListener debugStickListener = new DebugStickListener(this);
        ReloadCommand reloadCommand = new ReloadCommand();

        getServer().getPluginManager().registerEvents(debugStickListener,this);
        RegisterCommand("debugstick",reloadCommand, reloadCommand,"debugstick.reload");

    }

    private void RegisterCommand(String name, CommandExecutor commandExecutor, TabCompleter tabCompleter, String permission ){
        PluginCommand command = getCommand(name);
        if (command == null) {
            return;
        }
        command.setExecutor(commandExecutor);
        command.setTabCompleter(tabCompleter);
        if (permission != null){
            command.setPermission(permission);
        }
    }
}
