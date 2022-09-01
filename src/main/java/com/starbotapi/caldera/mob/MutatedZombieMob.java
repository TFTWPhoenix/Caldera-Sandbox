package com.starbotapi.caldera.mob;

import com.starbotapi.caldera.stats.EntityType;
import org.bukkit.Material;

public class MutatedZombieMob extends CalderaMob {

    public MutatedZombieMob() {
        displayName = "Mutated Zombie";
        type = EntityType.UNDEAD;
        texture = org.bukkit.entity.EntityType.ZOMBIE;
        health = 250;
        damage = 60;
        id = "mutated_zombie";
        menuMaterial = Material.ROTTEN_FLESH;
    }

}
