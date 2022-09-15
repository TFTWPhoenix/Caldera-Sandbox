package com.starbotapi.caldera.event;

import com.starbotapi.caldera.item.CalderaItem;
import com.starbotapi.caldera.stats.StatsObject;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CalderaStatsObjectApplyItemEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    private StatsObject statsObject;
    private CalderaItem item;
    private boolean cancelled;

    public CalderaStatsObjectApplyItemEvent(StatsObject statsObject, CalderaItem item) {
        this.statsObject = statsObject;
        this.item = item;
        this.cancelled = false;
    }

    public StatsObject getStatsObject() {
        return statsObject;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }
}
