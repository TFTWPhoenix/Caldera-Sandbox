package com.starbotapi.caldera.mob;

import com.starbotapi.caldera.stats.EntityStatsObject;
import com.starbotapi.caldera.stats.EntityType;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class SkeletalGuardMob extends CalderaMob {

    public SkeletalGuardMob() {
        displayName = "Skeletal Guard";
        type = EntityType.UNDEAD;
        texture = org.bukkit.entity.EntityType.SKELETON;
        health = 500;
        damage = 75;
        id = "skeletal_guard";
        menuMaterial = Material.BONE;
    }

    @Override
    protected void spawns2(LivingEntity e, EntityStatsObject s) {
        e.getEquipment().setItemInHand(new ItemStack(Material.IRON_SWORD));
    }
}
