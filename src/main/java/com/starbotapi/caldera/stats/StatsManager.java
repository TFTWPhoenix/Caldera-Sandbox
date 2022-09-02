package com.starbotapi.caldera.stats;

import com.starbotapi.caldera.Caldera;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.*;

import java.util.HashMap;
import java.util.Map;

public class StatsManager {

    public static Map<Entity,StatsObject> statsObjects = new HashMap<>();
    public static Map<String,String> statDisplayNames = new HashMap<>();

    public static void run() {
        statDisplayNames.put("max_health","Maximum Health");
        statDisplayNames.put("defense","Defense");
        statDisplayNames.put("max_mana","Maximum Mana");
        statDisplayNames.put("health","Health");
        statDisplayNames.put("mana","Mana");
        statDisplayNames.put("intelligence","Intelligence");
        statDisplayNames.put("damage","Damage");
        statDisplayNames.put("strength","Strength");
        statDisplayNames.put("critchance","Critical Chance");
        statDisplayNames.put("critdamage","Critical Damage");
        statDisplayNames.put("speed","Speed");
        statDisplayNames.put("magicdamage","Magic Damage");

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Caldera.p,()->{
            for(World w : Bukkit.getWorlds()) {
                for(Entity e : w.getEntities()) {
                    if(e instanceof Player) {
                        statsObjects.putIfAbsent(e,new PlayerStatsObject((Player) e));
                        statsObjects.get(e).tick();
                    } else if (e instanceof ArmorStand) {
                        boolean found = false;
                        for(Entity n : e.getNearbyEntities(1,1,1)) {
                            if(n instanceof Player) continue;
                            if(n instanceof ArmorStand) continue;
                            found = true;
                        }
                        if(!found) e.remove();
                    } else if (e instanceof LivingEntity) {
                        statsObjects.putIfAbsent(e,new EntityStatsObject((LivingEntity) e));
                        statsObjects.get(e).tick();
                    } else if (e instanceof Arrow) {
                        if(((Arrow)e).isOnGround()) e.remove();
                    }
                }
            }
        },1L,1L);
    }

}
