package com.starbotapi.caldera.mob;

import com.starbotapi.caldera.stats.EntityStatsObject;
import com.starbotapi.caldera.stats.EntityType;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class NecroticZombie1BossMob extends CalderaMob {

    public NecroticZombie1BossMob() {
        displayName = "Necrotic Zombie (I)";
        type = EntityType.UNDEAD;
        texture = org.bukkit.entity.EntityType.ZOMBIE;
        health = 1000;
        damage = 50;
        id = "necrotic_zombie_1";
        menuMaterial = Material.ROTTEN_FLESH;
    }

    @Override
    protected void spawns2(LivingEntity e, EntityStatsObject s) {
        e.getEquipment().setItemInHand(new ItemStack(Material.WOOD_SWORD));
        e.getEquipment().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
    }
}
