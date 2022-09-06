package com.starbotapi.caldera.event;

import com.starbotapi.caldera.item.CalderaItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class InventoryEvents implements Listener {

    @EventHandler
    public static void onClick(InventoryClickEvent e) {
        if(e.getClickedInventory() == e.getWhoClicked().getInventory() && e.getSlot() == 8) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public static void onDrop(PlayerDropItemEvent e) {
        if(CalderaItem.fromCraft(e.getItemDrop().getItemStack()).id.equals("calderamenu")) {
            e.setCancelled(true);
        }
    }

}
