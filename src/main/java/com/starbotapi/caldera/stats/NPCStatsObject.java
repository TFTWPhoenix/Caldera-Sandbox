package com.starbotapi.caldera.stats;

import com.starbotapi.caldera.Caldera;
import com.starbotapi.caldera.event.CalderaStatsObjectTickEvent;
import com.starbotapi.caldera.mob.MobDrop;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import java.util.function.Consumer;

public class NPCStatsObject implements StatsObject {

    private LivingEntity spigot;
    private ArmorStand nametag;

    public String displayName;
    public String id = "";
    public EntityType entityType = EntityType.NONE;
    private Consumer<PlayerInteractAtEntityEvent> clickHandler = e -> {};

    public NPCStatsObject(LivingEntity entity) {
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
        return 100;
    }

    @Override
    public int getHealth() {
        return 100;
    }

    @Override
    public double getDamageReduction() {
        return 1.0;
    }

    @Override
    public void damage(int x) {
    }

    @Override
    public void heal(int x) {
    }

    @Override
    public void tick() {
        if(nametag == null || nametag.isDead()) {
             nametag = (ArmorStand) spigot.getWorld().spawnEntity(spigot.getEyeLocation(), org.bukkit.entity.EntityType.ARMOR_STAND);
             nametag.setGravity(false);
             nametag.setMarker(true);
             nametag.setVisible(false);
             nametag.setCustomNameVisible(true);
             nametag.setSmall(true);
        }

        nametag.setCustomName("\247a" + displayName);
        nametag.teleport(spigot.getEyeLocation().add(0,0.5,0));
        Bukkit.getPluginManager().callEvent(new CalderaStatsObjectTickEvent(this));
    }

    @Override
    public int calcDamage() {
        return 0;
    }

    public void onClick(PlayerInteractAtEntityEvent e) {
        clickHandler.accept(e);
    }

    public void setClickHandler(Consumer<PlayerInteractAtEntityEvent> handler) {
        clickHandler = handler;
    }

    public Consumer<PlayerInteractAtEntityEvent> getClickHandler() {
        return clickHandler;
    }

}
