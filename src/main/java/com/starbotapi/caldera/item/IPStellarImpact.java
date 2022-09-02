package com.starbotapi.caldera.item;

import org.bukkit.Material;

public class IPStellarImpact extends CalderaItem {

    public IPStellarImpact() {
        displayName = "Stellar Impact";
        texture = Material.NETHER_STAR;
        id = "[calderaprefab]stellarimpact";
        itemtype = "Item";
        effectiveslot = 4;
        stats.put("magicdamage",200);
        stats.put("max_mana",500);
        abilityText = new String[]{
                "\2476Right Click Ability: Impact",
                "Create a large explosion at your location",
                "dealing magic damage to all enemies it hits",
                "and launching you up."
        };
        abilityID = "impact";
        rarity = CalderaRarity.EPIC;
        comment = "boom boom";
    }

}
