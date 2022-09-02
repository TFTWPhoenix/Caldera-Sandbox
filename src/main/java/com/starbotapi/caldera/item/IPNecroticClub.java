package com.starbotapi.caldera.item;

import org.bukkit.Material;

public class IPNecroticClub extends CalderaItem {

    public IPNecroticClub() {
        displayName = "Necrotic Club";
        texture = Material.WOOD_SWORD;
        id = "[calderaprefab]necrotic_club";
        itemtype = "Club";
        effectiveslot = 4;
        stats.put("damage",50);
        stats.put("strength",25);
        rarity = CalderaRarity.RARE;
    }

}
