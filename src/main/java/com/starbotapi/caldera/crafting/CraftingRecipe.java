package com.starbotapi.caldera.crafting;

import com.starbotapi.caldera.item.CalderaItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;

public class CraftingRecipe {

    private CalderaItem result;
    private Map<CalderaItem,Integer> cost;
    public String id;

    public CraftingRecipe(CalderaItem result, Map<CalderaItem,Integer> cost) {
        this.result = result;
        this.cost = cost;
        id = result.id;
    }

    public void attempt(Player p) {
        boolean cancraft = true;
        for(CalderaItem c : cost.keySet()) {
            if(!p.getInventory().containsAtLeast(c.asCraft(),cost.get(c))) {
                cancraft = false;
                p.sendMessage("\247cYou don't have enough \247f" + c.rarity.color() + c.displayName + "\247c!");
            }
        }
        if(cancraft) {
            for(CalderaItem c : cost.keySet()) {
                ItemStack rm = c.asCraft();
                rm.setAmount(cost.get(c));
                p.getInventory().removeItem(rm);
            }
            p.getInventory().addItem(result.asCraft());
        }
    }

    public ItemStack generateRecipeItem() {
        ItemStack i = result.asCraft();
        ItemMeta m = i.getItemMeta();
        List<String> lore = m.getLore();
        lore.add("\2478----=\2477Cost\2478=----");
        for(CalderaItem c : cost.keySet()) {
            lore.add("\2477" + cost.get(c) + "\2478x " + c.rarity.color() + c.displayName);
        }
        m.setLore(lore);
        i.setItemMeta(m);
        return i;
    }

}
