package com.starbotapi.caldera.gui;

import com.starbotapi.caldera.Caldera;
import net.minecraft.server.v1_8_R3.ItemMapEmpty;
import net.minecraft.server.v1_8_R3.LayerIsland;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class CalderaGUI implements Listener {

    public String title = "";
    public int size = 54;
    private Inventory inventory = null;

    private HashMap<Integer,Consumer<InventoryClickEvent>> buttons = new HashMap<>();

    public CalderaGUI() {
        Bukkit.getServer().getPluginManager().registerEvents(this, Caldera.p);
    }

    @EventHandler
    public void click(InventoryClickEvent e) {
        if(e.getView().getTopInventory().equals(inventory) && e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
            e.setCancelled(true);
            return;
        }
        if(!e.getClickedInventory().equals(inventory)) return;
        e.setCancelled(true);
        if(buttons.containsKey(e.getSlot())) buttons.get(e.getSlot()).accept(e);
    }

    public void putItem(int slot, ItemStack item) {
        if(inventory == null) {
            inventory = Bukkit.createInventory(null,size,title);
        }
        inventory.setItem(slot,item);
    }

    public void putButton(int slot, ItemStack item, Consumer<InventoryClickEvent> click) {
        putItem(slot,item);
        buttons.put(slot,click);
    }

    public static ItemStack createGUIItem(String name, Material material, int amount, byte data, String... description) {
        ItemStack i = new ItemStack(material,amount,data);
        ItemMeta m = i.getItemMeta();
        m.setDisplayName("\2477" + name);
        List<String> lore = new ArrayList<>();
        for(String s : description) {
            lore.add(s);
        }
        m.setLore(lore);
        i.setItemMeta(m);
        return i;
    }

    public void open(Player p) {
        if(inventory == null) {
            inventory = Bukkit.createInventory(null,size,title);
        }
        p.openInventory(inventory);
    }

}
