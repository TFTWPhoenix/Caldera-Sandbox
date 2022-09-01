package com.starbotapi.caldera.item;

import org.bukkit.Material;

public class IPAssassinsTouch extends CalderaItem {

    public IPAssassinsTouch() {
        displayName = "Assassin's Touch";
        texture = Material.STONE_SWORD;
        id = "[calderaprefab/volcaniclegend]assassins_touch";
        itemtype = "Dagger \2478(Legacy Item)";
        effectiveslot = 4;
        damage = 650;
        strength = 200;
        critchance = 20;
        critdamage = 600;
        rarity = CalderaRarity.MYTHIC;
        comment = "feels good to hold one of these again";
    }

}
