package com.starbotapi.caldera.item;

import org.bukkit.Material;

public class IPCalderaMenu extends CalderaItem {

    public IPCalderaMenu() {
        displayName = "Caldera Menu";
        id = "calderamenu";
        texture = Material.BOOK;
        effectiveslot = 4;
        abilityID = "calderamenu";
        rarity = CalderaRarity.SPECIAL;
    }

}
