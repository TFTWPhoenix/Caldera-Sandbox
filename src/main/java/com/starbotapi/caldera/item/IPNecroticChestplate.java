package com.starbotapi.caldera.item;

import org.bukkit.Color;
import org.bukkit.Material;

public class IPNecroticChestplate extends CalderaItem {

    public IPNecroticChestplate() {
        displayName = "Necrotic Chestplate";
        texture = Material.LEATHER_CHESTPLATE;
        id = "[calderaprefab]necrotic_chestplate";
        itemtype = "Chestplate";
        effectiveslot = 1;
        health = 20;
        defense = 80;
        strength = 10;
        rarity = CalderaRarity.RARE;
        comment = "idk man";
        colorLeather = true;
        leatherColor = Color.fromRGB(0x005500);
    }

}
