package com.starbotapi.caldera.item;

import org.bukkit.Color;
import org.bukkit.Material;

public class IPNecroticBoots extends CalderaItem {

    public IPNecroticBoots() {
        displayName = "Necrotic Boots";
        texture = Material.LEATHER_BOOTS;
        id = "[calderaprefab]necrotic_boots";
        itemtype = "Boots";
        effectiveslot = 3;
        stats.put("max_health",5);
        stats.put("defense",20);
        stats.put("strength",10);
        rarity = CalderaRarity.RARE;
        comment = "idk man";
        colorLeather = true;
        leatherColor = Color.fromRGB(0x005500);
    }

}
