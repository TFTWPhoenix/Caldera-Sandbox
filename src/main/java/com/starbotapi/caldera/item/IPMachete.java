package com.starbotapi.caldera.item;

import org.bukkit.Material;

public class IPMachete extends CalderaItem {

    public IPMachete() {
        displayName = "Machete";
        texture = Material.STONE_SWORD;
        id = "[calderaprefab]machete";
        itemtype = "Sword";
        effectiveslot = 4;
        stats.put("damage",20);
        stats.put("strength",10);
        comment = "the true classic";
    }

}
