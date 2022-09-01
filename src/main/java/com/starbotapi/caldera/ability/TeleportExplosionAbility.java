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

import java.util.HashMap;
import java.util.Set;

public class TeleportExplosionAbility extends CalderaAbility {

    private HashMap<Player,Boolean> cooldown = new HashMap<>();

    public TeleportExplosionAbility() {
        name = "Teleport Explosion";
        id = "teleport_explosion";
        activate = e -> {
            if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) return;
            if(!((PlayerStatsObject) StatsManager.statsObjects.get(e.getPlayer())).useMana(50)) return;
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
            ParticleEffect.EXPLOSION_LARGE.display(e.getPlayer().getEyeLocation(),new Vector(),6,2, null);
            e.getPlayer().playSound(e.getPlayer().getEyeLocation(), Sound.EXPLODE,1F,1F);
            for(Entity n : e.getPlayer().getNearbyEntities(6,6,6)) {
                if(n == e.getPlayer()) continue;
                if(StatsManager.statsObjects.containsKey(n)) {
                    StatsManager.statsObjects.get(n).damage(((PlayerStatsObject) StatsManager.statsObjects.get(e.getPlayer())).calcMagicDamage());
                    if(n instanceof LivingEntity) ((LivingEntity) n).damage(0.00001);
                }
            }
        };
    }

}
