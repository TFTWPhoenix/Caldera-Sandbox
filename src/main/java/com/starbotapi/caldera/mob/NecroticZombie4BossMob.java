package com.starbotapi.caldera.mob;

import com.starbotapi.caldera.Caldera;
import com.starbotapi.caldera.stats.EntityStatsObject;
import com.starbotapi.caldera.stats.EntityType;
import com.starbotapi.caldera.stats.StatsManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class NecroticZombie4BossMob extends CalderaMob {

    public NecroticZombie4BossMob() {
        displayName = "Necrotic Zombie (IV)";
        type = EntityType.UNDEAD;
        texture = org.bukkit.entity.EntityType.ZOMBIE;
        health = 50000;
        damage = 500;
        id = "necrotic_zombie_4";
        menuMaterial = Material.ROTTEN_FLESH;
    }

    @Override
    protected void spawns2(LivingEntity e, EntityStatsObject s) {
        e.getEquipment().setItemInHand(new ItemStack(Material.DIAMOND_SWORD));
        e.getEquipment().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
        e.getEquipment().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
        e.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
        e.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_HELMET));

        e.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,100000,2,true));

        Bukkit.getScheduler().scheduleSyncDelayedTask(Caldera.p,()-> heal(e),100L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Caldera.p,()-> rip(e),50L);
    }

    private void heal(LivingEntity boss) {
        if(boss.isDead()) return;

        StatsManager.statsObjects.get(boss).heal(500);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Caldera.p,()-> heal(boss),100L);
    }

    private void rip(LivingEntity boss) {
        if(boss.isDead()) return;

        for(Entity e : boss.getNearbyEntities(6,6,6)) {
            if(e instanceof Player) {
                StatsManager.statsObjects.get(e).damage(450);
                ((Player) e).playSound(e.getLocation(), Sound.ZOMBIE_WOODBREAK,1L,1L);
            }
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(Caldera.p,()-> rip(boss),50L);
    }
}
