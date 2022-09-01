package com.starbotapi.caldera.item;

import org.bukkit.Material;

public class IPFireburstStaff extends CalderaItem {

    public IPFireburstStaff() {
        displayName = "Fireburst Wand";
        texture = Material.GOLD_SPADE;
        id = "[calderaprefab]fireburst_wand";
        itemtype = "Wand";
        effectiveslot = 4;
        basemagicdamage = 20;
        manapool = 50;
        abilityText = new String[]{
                "\2476Right Click Ability: Fireball",
                "Shoot a fireball in the direction",
                "you are looking."
        };
        abilityID = "fireball";
        rarity = CalderaRarity.COMMON;
        comment = "boom";
    }

}
