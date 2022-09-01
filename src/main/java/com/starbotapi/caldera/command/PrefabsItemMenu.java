package com.starbotapi.caldera.command;

import com.starbotapi.caldera.Caldera;
import com.starbotapi.caldera.item.CalderaItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class PrefabsItemMenu implements Listener {

    private Inventory inventory;

    private HashMap<Integer, ArrayList<CalderaItem>> items = new HashMap<>();

    private int page;

    public PrefabsItemMenu() {
        Caldera.p.getServer().getPluginManager().registerEvents(this,Caldera.p);
        inventory = Bukkit.createInventory(null,54,"Prefab Items");
        loadPages();
        initItems();
        page = 0;
    }

    private ItemStack nodesc(Material mat,String display) {
        ItemStack i = new ItemStack(mat);
        ItemMeta m = i.getItemMeta();

        m.setDisplayName(display);

        i.setItemMeta(m);
        return i;
    }
    private ItemStack nodescdata(Material mat,String display,byte d) {
        ItemStack i = new ItemStack(mat,1,d);
        ItemMeta m = i.getItemMeta();

        m.setDisplayName(display);

        i.setItemMeta(m);
        return i;
    }

    private void loadPages() {
        int page = 0;
        int slot = 0;
        for(CalderaItem item : Caldera.item_prefabs) {
            items.putIfAbsent(page,new ArrayList<>());
            items.get(page).add(item);
            slot++;
            if(slot > 45) {
                page++;
                slot = 0;
            }
        }
    }

    private void initItems() {
        page = 0;
        drawPage();
    }

    private void drawPage() {
        for(int i = 0; i < 45; i++) {
            inventory.setItem(i,null);
        }
        for(int i = 45; i < 54; i++) {
            inventory.setItem(i,nodescdata(Material.STAINED_GLASS_PANE,"\2477",(byte)3));
        }
        inventory.setItem(45,nodesc(Material.ARROW,"\2477Previous Page"));
        inventory.setItem(53,nodesc(Material.ARROW,"\2477Next Page"));

        int n = 0;
        for(CalderaItem i : items.get(page)) {
            inventory.setItem(n,i.asCraft());
            n++;
        }

    }

    @EventHandler
    public void click(InventoryClickEvent e) {
        if(e.getClickedInventory() == null) return;
        if(e.getClickedInventory().equals(inventory)) {
            if(e.getSlot() < 45 && inventory.getItem(e.getSlot()) != null) {
                ((Player)e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.NOTE_PLING,1F,2F);
                e.getWhoClicked().getInventory().addItem(inventory.getItem(e.getSlot()));
            } else if (e.getSlot() == 45) {
                ((Player)e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.CLICK,1F,1F);
                int pg = page--;
                if(items.containsKey(pg)) {
                    page = pg;
                    drawPage();
                }
            } else if (e.getSlot() == 53) {
                ((Player)e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.CLICK,1F,2F);
                int pg = page++;
                if(items.containsKey(pg)) {
                    page = pg;
                    drawPage();
                }
            }
            e.setCancelled(true);
        }
    }

    public void open(Player p) {
        p.openInventory(inventory);
    }

}
