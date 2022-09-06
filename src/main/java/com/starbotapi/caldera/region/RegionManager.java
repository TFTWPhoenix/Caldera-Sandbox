package com.starbotapi.caldera.region;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegionManager {

    private static List<Region> regions = new ArrayList<>();

    public static void registerRegion(Region region) {
        regions.add(region);
    }

    public static Region getRegionOf(Location location) {
        for(Region r : regions) {
            if(r.containsPosition(location)) {
                return r;
            }
        }
        return new Region();
    }

}
