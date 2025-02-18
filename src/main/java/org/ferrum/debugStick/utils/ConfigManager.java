package org.ferrum.debugStick.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.ferrum.debugStick.DebugStick;
import org.ferrum.debugStick.listeners.DebugStickListener;
import java.io.File;
import java.util.*;

public class ConfigManager {
    public static DebugStick plugin;
    private static FileConfiguration config;
    private static boolean PlaceholderAPI_isLoad;

    public static boolean loadConfig() {
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false);
        }

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            PlaceholderAPI_isLoad = true;
        }

        config = YamlConfiguration.loadConfiguration(configFile);

        DebugStickListener.BypassInCreative = config.getBoolean("Bypass_in_creative",false);
        DebugStickListener.sendMessage = config.getBoolean("Interaction_cancellation_messages.enable",false);
        DebugStickListener.isActionbar = config.getBoolean("Interaction_cancellation_messages.actionbar",false);

        if (!updateCraftDebugStick()) return false;

        updatePreventWaterWorldList();
        updateBlackList();
        return true;
    }

    public static String getStringByKey(String key, Player player) {
        String string = config.getString(key, "not found line by key: "+key);
        if (PlaceholderAPI_isLoad) {
            string = PlaceholderAPI.setPlaceholders(player, string);
        }
        string = ChatColor.translateAlternateColorCodes('&', string);

        return string;
    }

    private static boolean updateCraftDebugStick() {
        Bukkit.removeRecipe(new NamespacedKey(plugin, "debug_stick"));

        if (config.getBoolean("Debug_stick_craft.enabled")) {
            int craft_size = config.getInt("Debug_stick_craft.grid_size");
            if (craft_size < 1 || craft_size > 3) {
                plugin.getLogger().severe("Error in config, value \"Debug_stick_craft -> grid_size\" not in range 1-3");
                return false;
            }

            String[] craftShape = new String[craft_size];

            for (int i = 0; i < craft_size; i++) {
                String line = config.getString("Debug_stick_craft.shape.line"+(i+1));
                if (line == null){
                    plugin.getLogger().severe("Error in config, line \"Debug_stick_craft -> shape -> line" + (i+1) + "\" not found");
                    return false;
                }
                if (line.length()!=craft_size) {
                    plugin.getLogger().severe("Error in config, line length \"Debug_stick_craft -> shape -> line" + (i+1) + "\" not equal to " + craft_size +" characters");
                    return false;
                }
                craftShape[i] = line;
            }

            HashMap<Character, Material> ingredientList = new HashMap<>();

            ConfigurationSection ingredientListConfig = config.getConfigurationSection("Debug_stick_craft.ingredient");
            if (ingredientListConfig == null) {
                plugin.getLogger().severe("Error in config, line \"Debug_stick_craft -> ingredient\" not found");
                return false;
            }

            for (String key : ingredientListConfig.getKeys(false)) {
                String value = (String) ingredientListConfig.get(key);
                if (key.length() != 1) {
                    plugin.getLogger().severe("Error in config, ingredient key \"" + key + "\" must be 1 character long");
                    return false;
                }
                if (!craftShape[0].contains(key) && !craftShape[1].contains(key) && !craftShape[2].contains(key)) {
                    plugin.getLogger().severe("Error in config, key \"" + key + "\" not found in shape");
                    return false;
                }
                if (value == null) {
                    plugin.getLogger().severe("Error in config, ingredient for key \"" + key + "\" is null");
                    return false;
                }
                Material material = Material.getMaterial(value.toUpperCase());
                if (material == null) {
                    plugin.getLogger().severe("Error in config, invalid material \"" + value + "\"");
                    return false;
                }
                ingredientList.put(key.charAt(0), Material.getMaterial(value.toUpperCase()));
            }

            CreateCraft.addRecipeDebugStick(plugin, craftShape, ingredientList, craft_size);
        }
        return true;
    }

    public static void updatePreventWaterWorldList() {
        DebugStickListener.PreventWaterInWorld = (ArrayList<String>) config.getStringList("Prevent_water_in_world");
    }

    public static void updateBlackList() {
        ArrayList<String> blockInBlackList  = new ArrayList<>();

        List<String> BlackListBlock = config.getStringList("Blacklist_block");
        List<String> BlackListTag = config.getStringList("Blacklist_tag_block");

        for (String blockName : BlackListBlock) {
            blockInBlackList.add(blockName.toUpperCase());
        }

        for (String tagName : BlackListTag) {
            Tag<Material> itemsForTag = Bukkit.getTag(Tag.REGISTRY_BLOCKS, NamespacedKey.minecraft(tagName.toLowerCase()), Material.class);
            if (itemsForTag != null) {
                for (Material material : itemsForTag.getValues()) {
                    blockInBlackList.add(material.name());
                }
            }
        }

        DebugStickListener.BlackList = blockInBlackList;
    }
}
