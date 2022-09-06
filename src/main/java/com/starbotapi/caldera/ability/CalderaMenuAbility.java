package com.starbotapi.caldera.ability;

import com.starbotapi.caldera.gui.CalderaMenuGUI;
import com.starbotapi.caldera.stats.PlayerStatsObject;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class CalderaMenuAbility extends CalderaAbility {

    private HashMap<Player,CalderaMenuGUI> menus = new HashMap<>();

    public CalderaMenuAbility() {
        name = "Open Caldera Menu";
        id = "calderamenu";
        activate = e -> {
            menus.putIfAbsent(e.getPlayer(),new CalderaMenuGUI());
            menus.get(e.getPlayer()).open(e.getPlayer());
        };
    }

}
