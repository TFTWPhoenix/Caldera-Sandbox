package com.starbotapi.caldera.ability;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.function.Consumer;

public class CalderaAbility {

    public String name = "";
    public String id = "";
    public Consumer<PlayerInteractEvent> activate = e -> {};

    public CalderaAbility() {

    }

}
