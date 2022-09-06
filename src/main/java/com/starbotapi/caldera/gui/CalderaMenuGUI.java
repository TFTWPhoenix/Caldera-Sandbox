package com.starbotapi.caldera.gui;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.function.Consumer;

public class CalderaMenuGUI extends CalderaGUI {

    private static HashMap<Integer, Consumer<InventoryClickEvent>> buttons = new HashMap<>();
    private static HashMap<Integer, ItemStack> items = new HashMap<>();

    public static void setItem(int slot, ItemStack stack) {
        items.put(slot,stack);
    }
    public static void setButton(int slot, ItemStack stack, Consumer<InventoryClickEvent> click) {
        items.put(slot,stack);
        buttons.put(slot,click);
    }

    public CalderaMenuGUI() {
        super();
        title = "Caldera Menu";
        size = 54;
        for(int i = 0; i < 54; i++) {
            putItem(i,createGUIItem("\2477", Material.STAINED_GLASS_PANE,1,(byte)15));
        }
        for(Integer i : items.keySet()) {
            putItem(i,items.get(i));
        }
        for(Integer i : buttons.keySet()) {
            putButton(i,items.get(i),buttons.get(i));
        }
    }

}
