package com.starbotapi.caldera.stats;

import org.bukkit.entity.Entity;

public interface StatsObject {

    Entity getBukkit();
    String getName();
    EntityType getEntityType();
    int getMaximumHealth();
    int getHealth();
    double getDamageReduction();

    void damage(int x);
    void heal(int x);

    void tick();

    int calcDamage();

}
