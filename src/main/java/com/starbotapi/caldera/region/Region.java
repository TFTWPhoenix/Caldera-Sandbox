package com.starbotapi.caldera.region;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Region {

    public String displayName = "";
    public String id = "";

    public Location position1 = new Location(Bukkit.getWorld("world"),0,0,0);
    public Location position2 = new Location(Bukkit.getWorld("world"),0,0,0);

    public Location respawnPoint = new Location(Bukkit.getWorld("world"),0,Bukkit.getWorld("world").getHighestBlockYAt(0,0),0);

    public Region() {

    }

    public boolean containsPosition(Location loc) {
        if(loc.getX() > position1.getX() && loc.getY() > position1.getY() && loc.getZ() > position1.getZ() &&
        loc.getX() < position2.getX() && loc.getY() < position2.getY() && loc.getZ() < position2.getZ()) return true;
        return false;
    }
}
