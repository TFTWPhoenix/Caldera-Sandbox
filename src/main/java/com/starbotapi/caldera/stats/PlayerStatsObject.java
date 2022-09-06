package com.starbotapi.caldera.stats;

import com.starbotapi.caldera.Caldera;
import com.starbotapi.caldera.item.CalderaItem;
import com.starbotapi.caldera.region.RegionManager;
import com.starbotapi.caldera.util.SymbolUtil;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PlayerStatsObject implements StatsObject {

    private Player player;

    public Map<String,Integer> stats = new HashMap<>();
    public Map<String,Integer> tickers = new HashMap<>();
    public Map<String,Integer> working = new HashMap<>();

    public Location respawnPoint = null;

    public PlayerStatsObject(Player player) {
        this.player = player;
        stats.put("health",100);
        stats.put("mana",100);
    }

    @Override
    public Entity getBukkit() {
        return player;
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.HUMAN;
    }

    @Override
    public int getMaximumHealth() {
        return stats.get("max_health");
    }

    @Override
    public int getHealth() {
        return stats.get("health");
    }

    @Override
    public double getDamageReduction() {
        return (double) stats.get("defense") / ((double) stats.get("defense") + 100.0);
    }

    @Override
    public void damage(int x) {
        int f = (int) (x - (x * getDamageReduction()));
        stats.put("health",stats.get("health") - f);
        if(stats.get("health") <= 0) {
            stats.put("health",stats.get("max_health") / 2);
            player.teleport(RegionManager.getRegionOf(player.getLocation()).respawnPoint);
            player.sendMessage("\2477You died!");
        }
    }

    @Override
    public void heal(int x) {
        int newhealth = stats.get("health") + x;
        if(newhealth > stats.get("max_health")) newhealth = stats.get("max_health");
        stats.put("health",newhealth);
    }

    @Override
    public void tick() {
        tickers.putIfAbsent("health_regen",0);
        tickers.put("health_regen",tickers.get("health_regen") + 1);
        working.clear();

        working.put("max_health",100);
        working.put("health",stats.get("health"));
        working.put("speed",100);
        working.put("max_mana",100);
        working.put("mana",stats.get("mana"));
        working.put("damage",5);
        working.put("defense",0);

        ItemStack[] equipment = {
                player.getEquipment().getHelmet(),
                player.getEquipment().getChestplate(),
                player.getEquipment().getLeggings(),
                player.getEquipment().getBoots(),
                player.getEquipment().getItemInHand()
        };
        HashMap<String,Integer> sets = new HashMap<>();
        for(int i = 0; i < equipment.length; i++) {
            CalderaItem ci = CalderaItem.fromCraft(equipment[i]);
            if(ci.effectiveslot != i) continue;
            for(String s : ci.stats.keySet()) {
                working.putIfAbsent(s,0);
                working.put(s,working.get(s) + ci.stats.get(s));
            }
            if(!ci.setID.equals("")) {
                sets.putIfAbsent(ci.setID,0);
                sets.put(ci.setID,sets.get(ci.setID) + 1);
            }
        }
        for(int i = 0; i < equipment.length; i++) {
            CalderaItem ci = CalderaItem.fromCraft(equipment[i]);
            if(ci.effectiveslot != i) continue;
            ci.tick.accept(this);
        }
        for(String s : sets.keySet()) {
            Caldera.setFromID(s).tick.accept(this,sets.get(s));
        }

        if(tickers.get("health_regen") >= 5) {
            tickers.put("health_regen",0);
            int hprg = (int) (working.get("health") + working.get("max_health") * 0.01);
            int mprg = 5 + (int) (working.get("mana") + working.get("max_mana") * 0.01);
            if(hprg > working.get("max_health")) hprg = working.get("max_health");
            if(mprg > working.get("max_mana")) mprg = working.get("max_mana");

            working.put("health",hprg);
            working.put("mana",mprg);
        }

        player.setWalkSpeed(0.002F * working.get("speed"));

        double hpperc = (double) working.get("health") / (double) working.get("max_health");
        double hprep = 20.0 * hpperc;
        if(hprep <= 0) hprep = 1;
        if(hprep > 20) hprep = 20;
        player.setHealth(hprep);

        String bar = "\247c" + working.get("health") + "%health-icon%" + working.get("max_health") + " \247a" + working.get("defense") + "%defense-icon% \247b" + working.get("mana") + "%mana-icon%" + working.get("max_mana");
        bar = SymbolUtil.applySymbols(bar,"JAVA");
        PacketPlayOutChat packetPlayOutChat = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + bar + "\"}"), (byte) 2);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packetPlayOutChat);

        stats.clear();
        for(String s : working.keySet()) {
            stats.put(s,working.get(s));
        }

        player.getInventory().setItem(8,Caldera.itemFromID("calderamenu").asCraft());
    }

    public int getValueOfStat(String s) {
        if(stats.containsKey(s)) {
            return stats.get(s);
        } else {
            return 0;
        }
    }

    @Override
    public int calcDamage() {
        int dmg = (int) (getValueOfStat("damage")  + (getValueOfStat("damage")  * (0.01 * getValueOfStat("strength"))));
        int rng = (new Random()).nextInt(100);
        if(rng <= getValueOfStat("critchance") ) {
            dmg += getValueOfStat("damage")  * (0.01 * getValueOfStat("critdamage") );
        }
        return dmg;
    }

    public int calcMagicDamage() {
        int dmg = (int) (getValueOfStat("magicdamage")  + (getValueOfStat("magicdamage")  * (0.01 * getValueOfStat("intelligence"))));
        return dmg;
    }

    public boolean useMana(int x) {
        if(getValueOfStat("mana") >= x) {
            stats.put("mana",stats.get("mana") - x);
            return true;
        }
        return false;
    }
}
