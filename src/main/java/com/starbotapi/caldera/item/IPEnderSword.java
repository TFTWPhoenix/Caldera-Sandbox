package com.starbotapi.caldera.item;

import org.bukkit.Material;

public class IPEnderSword extends CalderaItem {

    public IPEnderSword() {
        displayName = "Ender Sword";
        texture = Material.GOLD_SWORD;
        id = "[calderaprefab]ender_sword";
        itemtype = "Sword";
        effectiveslot = 4;
        stats.put("damage",20);
        stats.put("max_mana",100);
        abilityText = new String[]{
                "\2476Right Click Ability: Ender Warp",
                "Teleport 8 blocks ahead of you.",
                "Costs 25 Mana."
        };
        abilityID = "teleport8";
        rarity = CalderaRarity.UNCOMMON;
        comment = "zoop";
    }

}
