package com.starbotapi.caldera.mob;

import com.starbotapi.caldera.Caldera;
import com.starbotapi.caldera.stats.EntityStatsObject;
import com.starbotapi.caldera.stats.EntityType;
import com.starbotapi.caldera.stats.StatsManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class NecroticZombie3BossMob extends CalderaMob {

    public NecroticZombie3BossMob() {
        displayName = "Necrotic Zombie (III)";
        type = EntityType.UNDEAD;
        texture = org.bukkit.entity.EntityType.ZOMBIE;
        health = 25000;
        damage = 350;
        id = "necrotic_zombie_3";
        menuMaterial = Material.ROTTEN_FLESH;
    }

    @Override
    protected void spawns2(LivingEntity e, EntityStatsObject s) {
        e.getEquipment().setItemInHand(new ItemStack(Material.IRON_SWORD));
        e.getEquipment().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
        e.getEquipment().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
        e.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));

        e.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,100000,1,true));

        Bukkit.getScheduler().scheduleSyncDelayedTask(Caldera.p,()-> heal(e),100L);
    }

    private void heal(LivingEntity boss) {
        if(boss.isDead()) return;

        StatsManager.statsObjects.get(boss).heal(150);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Caldera.p,()-> heal(boss),100L);
    }
}
