package com.starbotapi.caldera.item;

import org.bukkit.Color;
import org.bukkit.Material;

public class IPWornPants extends CalderaItem {

    public IPWornPants() {
        displayName = "Worn Pants";
        texture = Material.LEATHER_LEGGINGS;
        id = "[calderaprefab]worn_pants";
        itemtype = "Leggings";
        effectiveslot = 2;
        defense = 10;
        rarity = CalderaRarity.COMMON;
        comment = "the true classic";
        colorLeather = true;
        leatherColor = Color.fromRGB(0x964B00);
    }

}
