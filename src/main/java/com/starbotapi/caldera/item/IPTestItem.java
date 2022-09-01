package com.starbotapi.caldera.item;

import org.bukkit.Material;

public class IPTestItem extends CalderaItem {

    public IPTestItem() {
        displayName = "Test Item";
        texture = Material.STONE_SWORD;
        id = "[calderaprefab]test_item";
        itemtype = "Debug Item";
        effectiveslot = 4;
        damage = 20;
        defense = 10;
        strength = 4;
        abilityText = new String[]{
                "it finally works..! yay..",
                "that took a damn long time.."
        };
        comment = "day 1392393: the item system finally works..";
    }

}
