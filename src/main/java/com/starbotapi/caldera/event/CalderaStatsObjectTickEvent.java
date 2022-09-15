package com.starbotapi.caldera.event;

import com.starbotapi.caldera.stats.StatsObject;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CalderaStatsObjectTickEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    private StatsObject statsObject;

    public CalderaStatsObjectTickEvent(StatsObject statsObject) {
        this.statsObject = statsObject;
    }

    public StatsObject getStatsObject() {
        return statsObject;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }
}
