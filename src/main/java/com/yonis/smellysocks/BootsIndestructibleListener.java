package com.yonis.smellysocks;

import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class BootsIndestructibleListener implements Listener {

    private final JavaPlugin plugin;

    public BootsIndestructibleListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void registerListeners() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        plugin.getLogger().info("BootsIndestructibleListener registered!");
    }

    @EventHandler
    public void onPlayerItemDamage(PlayerItemDamageEvent event) {
        ItemStack item = event.getItem();
        if (item != null && BootsRecipe.isSmellyBoots(item)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Item)) {
            return;
        }

        Item item = (Item) event.getEntity();
        ItemStack itemStack = item.getItemStack();

        if (itemStack != null && BootsRecipe.isSmellyBoots(itemStack)) {
            event.setCancelled(true);
            item.setFireTicks(0);

            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                item.setVelocity(item.getVelocity().add(new Vector(0, 0.1, 0)));
            }
        }
    }

    @EventHandler
    public void onItemDespawn(ItemDespawnEvent event) {
        Item item = event.getEntity();
        ItemStack itemStack = item.getItemStack();

        if (itemStack != null && BootsRecipe.isSmellyBoots(itemStack)) {
            event.setCancelled(true);
        }
    }
}
