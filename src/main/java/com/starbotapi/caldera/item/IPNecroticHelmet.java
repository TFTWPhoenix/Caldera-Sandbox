package com.starbotapi.caldera.item;

import org.bukkit.Color;
import org.bukkit.Material;

public class IPNecroticHelmet extends CalderaItem {

    public IPNecroticHelmet() {
        displayName = "Necrotic Helmet";
        texture = Material.LEATHER_HELMET;
        id = "[calderaprefab]necrotic_helmet";
        itemtype = "Helmet";
        effectiveslot = 0;
        health = 10;
        defense = 40;
        strength = 10;
        rarity = CalderaRarity.RARE;
        comment = "idk man";
        colorLeather = true;
        leatherColor = Color.fromRGB(0x005500);
    }

}
