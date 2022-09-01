package com.starbotapi.caldera.ability;

import com.starbotapi.caldera.Caldera;
import com.starbotapi.caldera.stats.PlayerStatsObject;
import com.starbotapi.caldera.stats.StatsManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

import java.util.HashMap;
import java.util.Set;

public class TeleportAbility extends CalderaAbility {

    private HashMap<Player,Boolean> cooldown = new HashMap<>();

    public TeleportAbility() {
        name = "Teleport";
        id = "teleport8";
        activate = e -> {
            if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) return;
            if(!((PlayerStatsObject) StatsManager.statsObjects.get(e.getPlayer())).useMana(25)) return;
            boolean collided = false;
            Location loc = e.getPlayer().getLocation();
            for(Block b : e.getPlayer().getLineOfSight((Set<Material>) null,8)) {
                if(collided) continue;
                if(b != null && !b.isEmpty() && b.getType() != Material.AIR) {
                    collided = true;
                }
                if(!collided) loc = b.getLocation().add(0.5,0,0.5);
            }
            loc.setYaw(e.getPlayer().getLocation().getYaw());
            loc.setPitch(e.getPlayer().getLocation().getPitch());
            e.getPlayer().teleport(loc);
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT,1F,1F);
        };
    }

}
