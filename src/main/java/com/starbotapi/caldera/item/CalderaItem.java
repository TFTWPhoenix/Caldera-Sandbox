package com.starbotapi.caldera.item;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.starbotapi.caldera.Caldera;
import com.starbotapi.caldera.stats.PlayerStatsObject;
import com.starbotapi.caldera.stats.StatsManager;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;

public class CalderaItem {


    public String displayName = "Air";
    public CalderaRarity rarity = CalderaRarity.COMMON;
    public String id = "generic/air";
    public String itemtype = "Item";
    public Material texture = Material.AIR;
    public int effectiveslot = -1;
    public String setID = "";

    public Map<String,Integer> stats = new HashMap<>();

    public Consumer<PlayerInteractEvent> interact = e -> {};
    public Consumer<PlayerStatsObject> tick = o -> {};
    public Consumer<EntityDamageByEntityEvent> onAttack = e -> {};

    public String[] abilityText = {};

    public boolean colorLeather = false;
    public Color leatherColor = Color.WHITE;
    public boolean textureHead = false;
    public boolean base64 = false;
    public String headTexture = "";

    public String comment = "";
    public String abilityID = "";

    public CalderaItem() {

    }

    public CalderaItem copy() {
        CalderaItem copy = new CalderaItem();
        copy.displayName = displayName;
        copy.rarity = rarity;
        copy.id = id;
        copy.itemtype = itemtype;
        copy.texture = texture;
        copy.effectiveslot = effectiveslot;
        copy.stats = stats;
        copy.interact = interact;
        copy.tick = tick;
        copy.abilityText = abilityText;
        copy.setID = setID;
        copy.colorLeather = colorLeather;
        copy.leatherColor = leatherColor;
        copy.textureHead = textureHead;
        copy.base64 = base64;
        copy.headTexture = headTexture;
        copy.onAttack = onAttack;
        copy.comment = comment;
        copy.abilityID = abilityID;
        return copy;
    }

    public ItemStack asCraft() {
        ItemStack i = new ItemStack(texture);
        if(textureHead) i = new ItemStack(Material.SKULL_ITEM,1,(byte)3);
        ItemMeta m = i.getItemMeta();

        m.setDisplayName(rarity.color() + displayName);

        List<String> lore = new ArrayList<>();
        for(String s : stats.keySet()) {
            String name = s;
            if(StatsManager.statDisplayNames.containsKey(s)) name = StatsManager.statDisplayNames.get(s);
            lore.add("\2477" + name + "\2478:\247c " + stats.get(s));
        }

        if(!lore.isEmpty()) lore.add("\2477");
        boolean abil = false;
        for(String s : abilityText) {
            lore.add("\2477" + s);
            abil = true;
        }
        if(abil) lore.add("\2477");
        lore.add(rarity.color() + rarity.display() + " " + itemtype);
        m.setLore(lore);

        m.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        m.addItemFlags(ItemFlag.HIDE_DESTROYS);
        m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        m.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        m.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        m.spigot().setUnbreakable(true);
        i.setItemMeta(m);
        net.minecraft.server.v1_8_R3.ItemStack nms = CraftItemStack.asNMSCopy(i);
        NBTTagCompound tag = nms.getTag();
        if(tag == null) tag = new NBTTagCompound();
        NBTTagCompound cd = new NBTTagCompound();

        cd.setString("id",id);
        cd.setString("displayname",displayName);
        cd.setString("rarity",rarity.toString());
        cd.setString("item_type",itemtype);
        cd.setString("texture",texture.toString());
        cd.setInt("effectiveslot",effectiveslot);
        String statslist = "";
        for(String s : stats.keySet()) {
            cd.setInt("stat_" + s,stats.get(s));
            if(!statslist.equals("")) statslist += ",";
            statslist += s;
        }
        cd.setString("stats",statslist);
        cd.setString("setid",setID);
        cd.setBoolean("has_leather_color",colorLeather);
        cd.setInt("leather_color", leatherColor.asRGB());
        cd.setBoolean("has_head_texture",textureHead);
        cd.setString("head_texture",headTexture);
        cd.setBoolean("is_head_texture_base64",base64);
        cd.setString("comment",comment);
        String atex = "";
        for(String atl : abilityText) {
            if(!atex.equals("")) atex += "\\n";
            atex += atl;
        }
        cd.setString("ability_text",atex);
        cd.setString("ability_id",abilityID);

        tag.set("CalderaData",cd);
        nms.setTag(tag);
        i = CraftItemStack.asBukkitCopy(nms);
        if(colorLeather) {
            LeatherArmorMeta lm = (LeatherArmorMeta) i.getItemMeta();
            lm.setColor(leatherColor);
            i.setItemMeta(lm);
        }
        if(textureHead) {
            SkullMeta meta = (SkullMeta) Objects.requireNonNull(i).getItemMeta();
            GameProfile profile = new GameProfile(UUID.randomUUID(), "");
            profile.getProperties().put("textures", new Property("textures", base64 ? headTexture : new String(Base64.getEncoder().encode(String.format("{\"textures\": {\"SKIN\": {\"url\": \"https://textures.minecraft.net/texture/%s\"}}}", texture).getBytes()))));

            Field profileField;
            try {
                profileField = Objects.requireNonNull(meta).getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(meta, profile);
            } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
                e.printStackTrace();
            }
            i.setItemMeta(meta);
        }
        return i;
    }

    public static CalderaItem fromCraft(ItemStack craft) {
        if(craft == null) return new CalderaItem();
        if(!craft.hasItemMeta()) return new CalderaItem();
        String id;
        NBTTagCompound cd = CraftItemStack.asNMSCopy(craft).getTag().getCompound("CalderaData");
        id = cd.getString("id");
        CalderaItem ci = new CalderaItem();
        ci.id = id;
        ci.displayName = cd.getString("displayname");
        ci.rarity = ((cd.getString("rarity").equals("") ? CalderaRarity.COMMON : CalderaRarity.valueOf(cd.getString("rarity"))));
        ci.itemtype = cd.getString("item_type");
        ci.texture = ((cd.getString("texture").equals("") ? Material.AIR : Material.valueOf(cd.getString("texture"))));
        ci.effectiveslot = cd.getInt("effectiveslot");
        String[] stats = cd.getString("stats").split(",");
        for(String s : stats) {
            ci.stats.put(s,cd.getInt("stat_" + s));
        }
        ci.setID = cd.getString("setid");
        ci.colorLeather = cd.getBoolean("has_leather_color");
        ci.leatherColor = Color.fromRGB(cd.getInt("leather_color"));
        ci.textureHead = cd.getBoolean("has_head_texture");
        ci.headTexture = cd.getString("head_texture");
        ci.base64 = cd.getBoolean("is_head_texture_base64");
        ci.abilityText = cd.getString("ability_text").split("\\n");
        ci.comment = cd.getString("comment");
        ci.abilityID = cd.getString("ability_id");
        return ci;
    }

}
