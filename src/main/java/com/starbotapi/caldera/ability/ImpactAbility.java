package com.starbotapi.caldera.ability;

import com.starbotapi.caldera.stats.PlayerStatsObject;
import com.starbotapi.caldera.stats.StatsManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.color.DustData;

import java.awt.*;
import java.util.HashMap;
import java.util.Set;

public class ImpactAbility extends CalderaAbility {

    private HashMap<Player,Boolean> cooldown = new HashMap<>();

    public ImpactAbility() {
        name = "Impact";
        id = "impact";
        activate = e -> {
            if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) return;
            if(!((PlayerStatsObject) StatsManager.statsObjects.get(e.getPlayer())).useMana(100)) return;
            e.getPlayer().playSound(e.getPlayer().getEyeLocation(), Sound.ZOMBIE_REMEDY,1F,0F);
            e.getPlayer().getWorld().createExplosion(e.getPlayer().getLocation(),10F);
            for(Entity n : e.getPlayer().getNearbyEntities(10,10,10)) {
                if(n == e.getPlayer()) continue;
                if(StatsManager.statsObjects.containsKey(n)) {
                    StatsManager.statsObjects.get(n).damage(((PlayerStatsObject) StatsManager.statsObjects.get(e.getPlayer())).calcMagicDamage());
                    if(n instanceof LivingEntity) ((LivingEntity) n).damage(0.00001);
                }
            }
        };
    }

}
