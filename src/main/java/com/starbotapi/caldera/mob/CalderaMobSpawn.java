package com.starbotapi.caldera.mob;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class CalderaMobSpawn {

    private CalderaMob mob;
    private Location loc;
    private int radius;

    public CalderaMobSpawn(CalderaMob mob, Location location, int checkRadius) {
        this.mob = mob;
        this.loc = location;
        this.radius = checkRadius;
    }

    public void run() {
        if(!loc.getChunk().isLoaded()) return;
        boolean spawn = true;
        for(Entity e : loc.getWorld().getNearbyEntities(loc,radius,radius,radius)) {
            if(e instanceof Player) continue;
            if(e instanceof ArmorStand) continue;
            spawn = false;
        }
        boolean foundplayer = false;
        for(Entity e : loc.getWorld().getNearbyEntities(loc,50,50,50)) {
            if(e instanceof Player) {
                foundplayer = true;
            }
        }
        if(!foundplayer) spawn = false;
        if(spawn) mob.spawn(loc);
    }

}
