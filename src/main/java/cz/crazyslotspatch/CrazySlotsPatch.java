package cz.crazyslotspatch;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class CrazySlotsPatch extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("CrazySlotsPatch enabled - overriding CrazySlots recipe");
        
        // Zaregistrujeme se po AltarSMP
        Bukkit.getScheduler().runTaskLater(this, this::patchCrazySlots, 40L);
    }

    private void patchCrazySlots() {
        try {
            // Získáme AltarSMP plugin
            org.bukkit.plugin.Plugin altarPlugin = Bukkit.getPluginManager().getPlugin("AltarSMP");
            if (altarPlugin == null) {
                getLogger().warning("AltarSMP not found!");
                return;
            }

            // Získáme config AltarSMP
            org.bukkit.configuration.file.FileConfiguration config = altarPlugin.getConfig();
            
            // Přepíšeme recept pro crazyslots v AltarSMP configu
            config.set("recipes.crazyslots.PLAYER_HEAD", 3);
            config.set("recipes.crazyslots.NETHERITE_INGOT", 2);
            
            // Smažeme hardcoded ingredience
            config.set("recipes.crazyslots.NETHER_STAR", null);
            config.set("recipes.crazyslots.AMETHYST_SHARD", null);
            config.set("recipes.crazyslots.GOLD_BLOCK", null);
            config.set("recipes.crazyslots.TOTEM_OF_UNDYING", null);
            config.set("recipes.crazyslots.custom_illusion_core", null);
            config.set("recipes.crazyslots.custom_vulkan_head", null);
            config.set("recipes.crazyslots.custom_warden_heart", null);
            config.set("recipes.crazyslots.custom_weapon_handle", null);
            config.set("recipes.crazyslots.WITHER_SKULL", null);

            // Reloadneme AltarSMP
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "altarsmp reload");
            getLogger().info("CrazySlots recipe patched successfully!");
            
        } catch (Exception e) {
            getLogger().severe("Failed to patch CrazySlots: " + e.getMessage());
        }
    }
}
