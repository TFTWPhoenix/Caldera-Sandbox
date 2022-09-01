package com.starbotapi.caldera.item;

import com.starbotapi.caldera.stats.PlayerStatsObject;

import java.util.function.BiConsumer;

public class SetBonus {

    public String name = "";
    public String id = "";
    public BiConsumer<PlayerStatsObject,Integer> tick = (o,a) -> {};

    public SetBonus() {};

}
