package org.ferrum.debugStick.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.ferrum.debugStick.DebugStick;

import java.util.HashMap;

public class CreateCraft {
    public static void addRecipeDebugStick(DebugStick plugin, String[] craftShape, HashMap<Character, Material> ingredientList, Integer craft_size) {

        ItemStack result = new ItemStack(Material.DEBUG_STICK);
        NamespacedKey key = new NamespacedKey(plugin, "debug_stick");
        ShapedRecipe recipe = new ShapedRecipe(key, result);

        switch (craft_size){
            case 3:
                recipe.shape(craftShape[0],craftShape[1],craftShape[2]);
                break;
            case 2:
                recipe.shape(craftShape[0],craftShape[1]);
                break;
            case 1:
                recipe.shape(craftShape[0]);
                break;
        }


        for (char keyIngredient : ingredientList.keySet()) {
            recipe.setIngredient(keyIngredient, ingredientList.get(keyIngredient));
        }

        Bukkit.addRecipe(recipe);

    }
}
