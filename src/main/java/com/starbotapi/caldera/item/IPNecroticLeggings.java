package com.starbotapi.caldera.item;

import org.bukkit.Color;
import org.bukkit.Material;

public class IPNecroticLeggings extends CalderaItem {

    public IPNecroticLeggings() {
        displayName = "Necrotic Leggings";
        texture = Material.LEATHER_LEGGINGS;
        id = "[calderaprefab]necrotic_leggings";
        itemtype = "Leggings";
        effectiveslot = 2;
        health = 15;
        defense = 60;
        strength = 10;
        rarity = CalderaRarity.RARE;
        comment = "idk man";
        colorLeather = true;
        leatherColor = Color.fromRGB(0x005500);
    }

}
