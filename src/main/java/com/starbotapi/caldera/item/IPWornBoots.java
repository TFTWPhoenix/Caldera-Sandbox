package com.starbotapi.caldera.item;

import org.bukkit.Color;
import org.bukkit.Material;

public class IPWornBoots extends CalderaItem {

    public IPWornBoots() {
        displayName = "Worn Boots";
        texture = Material.LEATHER_BOOTS;
        id = "[calderaprefab]worn_boots";
        itemtype = "Boots";
        effectiveslot = 3;
        defense = 10;
        rarity = CalderaRarity.COMMON;
        comment = "the true classic";
        colorLeather = true;
        leatherColor = Color.fromRGB(0x964B00);
    }

}
