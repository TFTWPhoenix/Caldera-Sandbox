package com.starbotapi.caldera.ability;

import com.starbotapi.caldera.Caldera;
import com.starbotapi.caldera.stats.PlayerStatsObject;
import com.starbotapi.caldera.stats.StatsManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.block.Action;

import java.util.HashMap;

public class BowInstashootAbility extends CalderaAbility {

    private HashMap<Player,Boolean> cooldown = new HashMap<>();

    public BowInstashootAbility() {
        name = "Bow Instashoot";
        id = "bow_immediate_shot";
        activate = e -> {
            if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) return;
            cooldown.putIfAbsent(e.getPlayer(),false);
            if(cooldown.get(e.getPlayer())) return;
            cooldown.put(e.getPlayer(),true);
            Bukkit.getScheduler().scheduleSyncDelayedTask(Caldera.p,()->{
                cooldown.put(e.getPlayer(),false);
            },10L);
            e.getPlayer().launchProjectile(Arrow.class);
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.SHOOT_ARROW,1F,1F);
        };
    }

}
