/*
 * This file is part of Relics.
 *
 * Copyright (c) 2017, Austin Payne <amperialdev@gmail.com - http://github.com/Amperial>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.crafting;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.crafting.CraftingMenu.CraftingHolder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

public class CraftingListener implements Listener {

    public CraftingListener(ItemPlugin plugin) {
        // Register listener
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory() != null && event.getInventory().getHolder() instanceof CraftingHolder &&
                event.getWhoClicked() instanceof Player) {
            ((CraftingHolder) event.getInventory().getHolder()).getMenu().onClick(event);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getInventory() != null && event.getInventory().getHolder() instanceof CraftingHolder &&
                event.getWhoClicked() instanceof Player) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory() != null && event.getInventory().getHolder() instanceof CraftingHolder) {
            ((CraftingHolder) event.getInventory().getHolder()).getMenu().onClose(event);
        }
    }

    public void closeOpenMenus() {
        Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.getOpenInventory() != null)
                .forEach(player -> {
                    Inventory inventory = player.getOpenInventory().getTopInventory();
                    if (inventory.getHolder() instanceof CraftingHolder) {
                        player.closeInventory();
                    }
                });
    }

}
