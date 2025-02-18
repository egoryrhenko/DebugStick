package org.ferrum.debugStick.listeners;


import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.ferrum.debugStick.DebugStick;
import org.ferrum.debugStick.utils.ConfigManager;

import java.util.ArrayList;

public class DebugStickListener implements Listener {

    DebugStick plugin;
    public DebugStickListener(DebugStick plugin){
        this.plugin = plugin;
    }

    public static ArrayList<String> BlackList = new ArrayList<>();
    public static ArrayList<String> PreventWaterInWorld = new ArrayList<>();

    public static Boolean BypassInCreative;
    public static Boolean sendMessage;
    public static Boolean isActionbar;

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {

        Player player = event.getPlayer();
        Block block = event.getBlock();
        String world_name = block.getWorld().getName();

        if (player.getInventory().getItemInMainHand().getType().equals(Material.DEBUG_STICK) || player.getInventory().getItemInOffHand().getType().equals(Material.DEBUG_STICK)) {
            if ((player.getGameMode().equals(GameMode.CREATIVE) && BypassInCreative) || player.hasPermission("debugstick.bypass")) return;
            if (BlackList.contains(event.getBlock().getType().name())) {
                event.setCancelled(true);
                if (sendMessage) {
                    BaseComponent message = new TextComponent(ConfigManager.getStringByKey("Interaction_cancellation_messages.messageBlock",player));
                    if (isActionbar) {
                        player.sendActionBar(message);
                    } else {
                        player.sendMessage(message);
                    }
                }
            } else if (PreventWaterInWorld.contains(world_name) && isWaterlogged(block)) {
                event.setCancelled(true);
                if (sendMessage) {
                    BaseComponent message = new TextComponent(ConfigManager.getStringByKey("Interaction_cancellation_messages.messageState",player));
                    if (isActionbar) {
                        player.sendActionBar(message);
                    } else {
                        player.sendMessage(message);
                    }
                }
            }
        }
    }

    public boolean isWaterlogged(Block block) {

        // Проверка, является ли блок "waterlogged"
        if (block.getBlockData() instanceof Waterlogged) {
            return ((Waterlogged) block.getBlockData()).isWaterlogged();
        }
        return false;
    }
}
