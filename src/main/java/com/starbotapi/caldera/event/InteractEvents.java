package com.starbotapi.caldera.event;

import com.starbotapi.caldera.Caldera;
import com.starbotapi.caldera.item.CalderaItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class InteractEvents implements Listener {

    public static Map<Player,Boolean> cooldown = new HashMap<>();


    @EventHandler
    public static void interact(PlayerInteractEvent e) {
        cooldown.putIfAbsent(e.getPlayer(),false);
        if(cooldown.get(e.getPlayer())) return;
        cooldown.put(e.getPlayer(),true);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Caldera.p,()->cooldown.put(e.getPlayer(),false),2L);
        Player player = e.getPlayer();
        ItemStack[] equipment = {
                player.getEquipment().getHelmet(),
                player.getEquipment().getChestplate(),
                player.getEquipment().getLeggings(),
                player.getEquipment().getBoots(),
                player.getEquipment().getItemInHand()
        };
        for(int i = 0; i < equipment.length; i++) {
            CalderaItem ci = CalderaItem.fromCraft(equipment[i]);
            if(ci.effectiveslot != i) continue;
            Caldera.abilityFromID(ci.abilityID).activate.accept(e);
        }
    }

}
