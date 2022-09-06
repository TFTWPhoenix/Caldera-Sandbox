package com.starbotapi.caldera.mob;

import com.starbotapi.caldera.item.CalderaItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.Random;

public class MobDrop {

    private static Random rng = new Random();

    private CalderaItem item;
    private double chance;

    public MobDrop(CalderaItem item, double chance) {
        this.item = item;
        this.chance = chance;
    }

    public void run(Location loc) {
        if(rng.nextDouble() <= chance) {
            loc.getWorld().dropItem(loc,item.asCraft());
        }
    }

}
