package com.starbotapi.caldera.stats;

import org.bukkit.entity.Entity;

public class DummyStatsObject implements StatsObject {

    private Entity bukkit;

    public DummyStatsObject(Entity e) {
        bukkit = e;
    }

    @Override
    public Entity getBukkit() {
        return bukkit;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.NONE;
    }

    @Override
    public int getMaximumHealth() {
        return 1;
    }

    @Override
    public int getHealth() {
        return 1;
    }

    @Override
    public double getDamageReduction() {
        return 1;
    }

    @Override
    public void damage(int x) {

    }

    @Override
    public void heal(int x) {

    }

    @Override
    public void tick() {

    }

    @Override
    public int calcDamage() {
        return 0;
    }
}
