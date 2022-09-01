package com.starbotapi.caldera.mob;

import com.starbotapi.caldera.Caldera;
import com.starbotapi.caldera.stats.EntityStatsObject;
import com.starbotapi.caldera.stats.EntityType;
import com.starbotapi.caldera.stats.StatsManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class NecroticZombie2BossMob extends CalderaMob {

    public NecroticZombie2BossMob() {
        displayName = "Necrotic Zombie (II)";
        type = EntityType.UNDEAD;
        texture = org.bukkit.entity.EntityType.ZOMBIE;
        health = 5000;
        damage = 150;
        id = "necrotic_zombie_2";
        menuMaterial = Material.ROTTEN_FLESH;
    }

    @Override
    protected void spawns2(LivingEntity e, EntityStatsObject s) {
        e.getEquipment().setItemInHand(new ItemStack(Material.STONE_SWORD));
        e.getEquipment().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
        e.getEquipment().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));

        Bukkit.getScheduler().scheduleSyncDelayedTask(Caldera.p,()-> heal(e),100L);
    }

    private void heal(LivingEntity boss) {
        if(boss.isDead()) return;

        StatsManager.statsObjects.get(boss).heal(100);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Caldera.p,()-> heal(boss),100L);
    }
}
