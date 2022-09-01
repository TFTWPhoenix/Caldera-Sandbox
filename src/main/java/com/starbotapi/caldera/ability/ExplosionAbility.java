package com.starbotapi.caldera.ability;

import com.starbotapi.caldera.Caldera;
import com.starbotapi.caldera.stats.PlayerStatsObject;
import com.starbotapi.caldera.stats.StatsManager;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.ParticleData;

import java.util.HashMap;

public class ExplosionAbility extends CalderaAbility {

    private HashMap<Player,Boolean> cooldown = new HashMap<>();

    public ExplosionAbility() {
        name = "Explosion";
        id = "explosion";
        activate = e -> {
            if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) return;
            cooldown.putIfAbsent(e.getPlayer(),false);
            if(cooldown.get(e.getPlayer())) return;
            if(!((PlayerStatsObject) StatsManager.statsObjects.get(e.getPlayer())).useMana(25)) return;
            cooldown.put(e.getPlayer(),true);
            Bukkit.getScheduler().scheduleSyncDelayedTask(Caldera.p,()->{
                cooldown.put(e.getPlayer(),false);
            },10L);
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
