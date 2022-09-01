package com.starbotapi.caldera.item;

import org.bukkit.Color;
import org.bukkit.Material;

public class IPWornTunic extends CalderaItem {

    public IPWornTunic() {
        displayName = "Worn Tunic";
        texture = Material.LEATHER_CHESTPLATE;
        id = "[calderaprefab]worn_tunic";
        itemtype = "Chestplate";
        effectiveslot = 1;
        defense = 10;
        rarity = CalderaRarity.COMMON;
        comment = "the true classic";
        colorLeather = true;
        leatherColor = Color.fromRGB(0x964B00);
    }

}
