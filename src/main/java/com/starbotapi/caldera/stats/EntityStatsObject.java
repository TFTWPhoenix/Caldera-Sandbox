package com.starbotapi.caldera.stats;

import com.starbotapi.caldera.Caldera;
import com.starbotapi.caldera.event.CalderaStatsObjectTickEvent;
import com.starbotapi.caldera.mob.CalderaMob;
import com.starbotapi.caldera.mob.MobDrop;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class EntityStatsObject implements StatsObject {

    private LivingEntity spigot;
    private ArmorStand nametag;

    public String displayName;
    public String id = "";
    public EntityType entityType = EntityType.NONE;

    public int maxhealth = 100;
    public int health = 100;

    public int damage = 30;

    public double damagereduction = 0.0;

    public int sincelastdamage = 0;

    public EntityStatsObject(LivingEntity entity) {
        spigot = entity;
        displayName = spigot.getName();
    }

    @Override
    public Entity getBukkit() {
        return spigot;
    }

    @Override
    public String getName() {
        return displayName;
    }

    @Override
    public EntityType getEntityType() {
        return entityType;
    }

    @Override
    public int getMaximumHealth() {
        return maxhealth;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public double getDamageReduction() {
        return damagereduction;
    }

    @Override
    public void damage(int x) {
        sincelastdamage = 0;
        int dmg = (int) (x - (x * damagereduction));
        health -= dmg;
        if(health <= 0 && !spigot.isDead()) {
            spigot.setHealth(0);
            for(MobDrop d : Caldera.mobFromID(id).drops) {
                d.run(spigot.getEyeLocation());
            }
        }
    }

    @Override
    public void heal(int x) {
        int nh = health + x;
        if(nh > maxhealth) nh = maxhealth;
        health = nh;
    }

    @Override
    public void tick() {
        sincelastdamage++;
        if(nametag == null || nametag.isDead()) {
             nametag = (ArmorStand) spigot.getWorld().spawnEntity(spigot.getEyeLocation(), org.bukkit.entity.EntityType.ARMOR_STAND);
             nametag.setGravity(false);
             nametag.setMarker(true);
             nametag.setVisible(false);
             nametag.setCustomNameVisible(true);
             nametag.setSmall(true);
        }
        if(sincelastdamage >= 200 && health < maxhealth) {
            heal(maxhealth / 20);
        }

        nametag.setCustomName("\247c" + displayName + " \2478| \247c" + prettify(health) + "❤ \2474" + prettify(damage) + "⚔");
        nametag.teleport(spigot.getEyeLocation().add(0,0.5,0));

        Bukkit.getPluginManager().callEvent(new CalderaStatsObjectTickEvent(this));
    }

    @Override
    public int calcDamage() {
        return damage;
    }

    private String prettify(int num) {
        if(num >= 1000000000) {
            return num / 1000000000 + "B";
        } else if (num >= 1000000) {
            return num / 1000000 + "M";
        } else if (num >= 1000) {
            return num / 1000 + "k";
        } else {
            return num + "";
        }
    }
}
