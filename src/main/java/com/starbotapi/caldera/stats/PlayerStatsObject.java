package com.starbotapi.caldera.stats;

import com.starbotapi.caldera.Caldera;
import com.starbotapi.caldera.item.CalderaItem;
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
import java.util.Random;

public class PlayerStatsObject implements StatsObject {

    private Player player;

    // RESOURCES
    public int maxhealth = 100;
    public int health = 100;
    public int maxmana = 100;
    public int mana = 100;

    // VALUES
    public int defense = 0;
    public int damage = 5;
    public int strength = 0;
    public int critchance = 0;
    public int critdamage = 0;
    public int speed = 100;
    public int intelligence = 0;
    public int basemagicdamage = 5;

    // TICKERS
    public int regentick = 0;

    // WORKING
    public int mhp;
    public int hp;
    public int mmp;
    public int mp;
    public int def;
    public int dmg;
    public int str;
    public int cc;
    public int cd;
    public int spd;
    public int inl;
    public int bmd;

    public PlayerStatsObject(Player player) {
        this.player = player;
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
        return maxhealth;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public double getDamageReduction() {
        return (double) defense / ((double) defense + 100.0);
    }

    @Override
    public void damage(int x) {
        int f = (int) (x - (x * getDamageReduction()));
        health -= f;
        if(health <= 0) {
            health = maxhealth / 2;
            player.teleport(new Location(Bukkit.getWorld("world"),-75,73,262));
            player.sendMessage("\2477You died!");
        }
    }

    @Override
    public void heal(int x) {
        int newhealth = health + x;
        if(newhealth > maxhealth) newhealth = maxhealth;
        health = newhealth;
    }

    @Override
    public void tick() {
        regentick++;

        mhp = 100;
        hp = health;
        mmp = 100;
        mp = mana;
        def = 0;
        dmg = 5;
        str = 0;
        cc = 0;
        cd = 0;
        spd = 100;
        inl = 0;
        bmd = 0;

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
            mhp += ci.health;
            def += ci.defense;
            mmp += ci.manapool;
            dmg += ci.damage;
            str += ci.strength;
            cc += ci.critchance;
            cd += ci.critdamage;
            spd += ci.speed;
            inl += ci.intelligence;
            bmd += ci.basemagicdamage;
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

        if(regentick >= 5) {
            regentick = 0;
            int hprg = (int) (hp + mhp * 0.01);
            int mprg = 5 + (int) (mp + mmp * 0.01);
            if(hprg > mhp) hprg = mhp;
            if(mprg > mmp) mprg = mmp;

            hp = hprg;
            mp = mprg;
        }

        player.setWalkSpeed(0.002F * spd);

        double hpperc = (double) hp / (double) mhp;
        double hprep = 20.0 * hpperc;
        if(hprep <= 0) hprep = 1;
        if(hprep > 20) hprep = 20;
        player.setHealth(hprep);

        String bar = "\247c" + hp + "%health-icon%" + mhp + " \247a" + def + "%defense-icon% \247b" + mp + "%mana-icon%" + mmp;
        bar = SymbolUtil.applySymbols(bar,"JAVA");
        PacketPlayOutChat packetPlayOutChat = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + bar + "\"}"), (byte) 2);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packetPlayOutChat);

        maxhealth = mhp;
        health = hp;
        maxmana = mmp;
        mana = mp;
        defense = def;
        damage = dmg;
        strength = str;
        critchance = cc;
        critdamage = cd;
        speed = spd;
        intelligence = inl;
        basemagicdamage = bmd;

    }

    @Override
    public int calcDamage() {
        int dmg = (int) (damage + (damage * (0.01 * strength)));
        int rng = (new Random()).nextInt(100);
        if(rng <= critchance) {
            dmg += damage * (0.01 * critdamage);
        }
        return dmg;
    }

    public int calcMagicDamage() {
        int dmg = (int) (basemagicdamage + (basemagicdamage * (0.01 * intelligence)));
        return dmg;
    }

    public boolean useMana(int x) {
        if(mana >= x) {
            mana -= x;
            return true;
        }
        return false;
    }
}
