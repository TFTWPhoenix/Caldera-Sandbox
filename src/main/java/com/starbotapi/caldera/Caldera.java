package com.starbotapi.caldera;

import com.starbotapi.caldera.ability.*;
import com.starbotapi.caldera.command.ItemEditorCommand;
import com.starbotapi.caldera.command.PrefabsCommand;
import com.starbotapi.caldera.command.SpawnItem;
import com.starbotapi.caldera.command.UpdateItemCommand;
import com.starbotapi.caldera.crafting.CraftingRecipe;
import com.starbotapi.caldera.event.ConnectionEvents;
import com.starbotapi.caldera.event.DamageEvents;
import com.starbotapi.caldera.event.InteractEvents;
import com.starbotapi.caldera.event.InventoryEvents;
import com.starbotapi.caldera.gui.CalderaGUI;
import com.starbotapi.caldera.gui.CalderaMenuGUI;
import com.starbotapi.caldera.gui.CraftingGUI;
import com.starbotapi.caldera.item.*;
import com.starbotapi.caldera.mob.*;
import com.starbotapi.caldera.region.Region;
import com.starbotapi.caldera.region.RegionManager;
import com.starbotapi.caldera.stats.StatsManager;
import com.starbotapi.caldera.util.SymbolUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Caldera extends JavaPlugin {

    public static Plugin p;

    public static List<CalderaItem> item_prefabs = new ArrayList<>();
    public static CalderaItem itemFromID(String id) {
        for(CalderaItem i : item_prefabs) {
            if(i.id.equals(id)) {
                return i;
            }
        }
        return new CalderaItem();
    }
    public static List<SetBonus> sets = new ArrayList<>();
    public static SetBonus setFromID(String id) {
        for(SetBonus i : sets) {
            if(i.id.equals(id)) {
                return i;
            }
        }
        return new SetBonus();
    }
    public static List<CalderaAbility> abilities = new ArrayList<>();
    public static CalderaAbility abilityFromID(String id) {
        for(CalderaAbility i : abilities) {
            if(i.id.equals(id)) {
                return i;
            }
        }
        return new CalderaAbility();
    }
    public static List<CalderaMob> mobs = new ArrayList<>();
    public static CalderaMob mobFromID(String id) {
        for(CalderaMob i : mobs) {
            if(i.id.equals(id)) {
                return i;
            }
        }
        return new CalderaMob();
    }
    public static List<CraftingRecipe> craftingRecipes = new ArrayList<>();
    public static CraftingRecipe recipeFromID(String id) {
        for(CraftingRecipe i : craftingRecipes) {
            if(i.id.equals(id)) {
                return i;
            }
        }
        return new CraftingRecipe(new CalderaItem(),new HashMap<>());
    }
    @Override
    public void onEnable() {
        // Plugin startup logic
        p = this;

        SymbolUtil.init();

        StatsManager.run();

        getServer().getPluginManager().registerEvents(new ConnectionEvents(),this);
        getServer().getPluginManager().registerEvents(new DamageEvents(),this);
        getServer().getPluginManager().registerEvents(new InteractEvents(),this);
        getServer().getPluginManager().registerEvents(new InventoryEvents(),this);

        getCommand("spawnitem").setExecutor(new SpawnItem());
        getCommand("prefabs").setExecutor(new PrefabsCommand());
        getCommand("updateitem").setExecutor(new UpdateItemCommand());
        getCommand("updateitem").setExecutor(new UpdateItemCommand());
        getCommand("itemeditor").setExecutor(new ItemEditorCommand());

        abilities.add(new FireballAbility());
        abilities.add(new WitherSkullAbility());
        abilities.add(new BowInstashootAbility());
        abilities.add(new TeleportAbility());
        abilities.add(new ExplosionAbility());
        abilities.add(new TeleportExplosionAbility());
        abilities.add(new ImpactAbility());
        abilities.add(new CalderaMenuAbility());

        mobs.add(new MutatedZombieMob());
        mobs.add(new NecroticZombie1BossMob());
        mobs.add(new NecroticZombie2BossMob());
        mobs.add(new NecroticZombie3BossMob());
        mobs.add(new NecroticZombie4BossMob());
        mobs.add(new NecroticZombie5BossMob());
        mobs.add(new SkeletalGuardMob());

        item_prefabs.add(new IPCalderaMenu());

        CalderaMenuGUI.setButton(31, CalderaGUI.createGUIItem("\2473Crafting", Material.WORKBENCH,1,(byte)0,""), click ->{
            click.getWhoClicked().closeInventory();
            CraftingGUI craftingGUI = new CraftingGUI();
            craftingGUI.open((Player) click.getWhoClicked());
        });

        Bukkit.clearRecipes();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
