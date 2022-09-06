package com.starbotapi.caldera.mining;

import com.starbotapi.caldera.item.CalderaItem;
import org.bukkit.Material;

public class MineableBlock {

    private Material material;
    private Material replaceWith;
    private long ticksTillRegenerate;
    private CalderaItem drop;
    private int minDrop;
    private int maxDrop;
    private String toolType;
    private int hardness;
    private int strength;

    public MineableBlock(Material material,
                         Material replaceWith,
                         long ticksTillRegenerate,
                         CalderaItem drop,
                         int minDrop,
                         int maxDrop,
                         String toolType,
                         int hardness,
                         int strength) {
        this.material = material;
        this.replaceWith = replaceWith;
        this.ticksTillRegenerate = ticksTillRegenerate;
        this.drop = drop;
        this.minDrop = minDrop;
        this.maxDrop = maxDrop;
        this.toolType = toolType;
        this.hardness = hardness;
        this.strength = strength;
    }

    public Material getMaterial() {
        return material;
    }

    public Material getReplaceWith() {
        return replaceWith;
    }

    public long getTicksTillRegenerate() {
        return ticksTillRegenerate;
    }

    public CalderaItem getDrop() {
        return drop;
    }

    public int getMinDrop() {
        return minDrop;
    }

    public int getMaxDrop() {
        return maxDrop;
    }

    public String getToolType() {
        return toolType;
    }

    public int getHardness() {
        return hardness;
    }

    public int getStrength() {
        return strength;
    }
}
