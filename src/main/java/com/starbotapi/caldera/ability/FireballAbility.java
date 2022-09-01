package com.starbotapi.caldera.ability;

import com.starbotapi.caldera.Caldera;
import com.starbotapi.caldera.stats.PlayerStatsObject;
import com.starbotapi.caldera.stats.StatsManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

import java.util.HashMap;

public class FireballAbility extends CalderaAbility {

    private HashMap<Player,Boolean> cooldown = new HashMap<>();

    public FireballAbility() {
        name = "Fireball";
        id = "fireball";
        activate = e -> {
            if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) return;
            cooldown.putIfAbsent(e.getPlayer(),false);
            if(cooldown.get(e.getPlayer())) return;
            if(!((PlayerStatsObject) StatsManager.statsObjects.get(e.getPlayer())).useMana(25)) return;
            cooldown.put(e.getPlayer(),true);
            Bukkit.getScheduler().scheduleSyncDelayedTask(Caldera.p,()->{
                cooldown.put(e.getPlayer(),false);
            },10L);
            e.getPlayer().launchProjectile(Fireball.class);
        };
    }

}
