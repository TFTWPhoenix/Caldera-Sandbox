package com.starbotapi.caldera.mob;

import com.starbotapi.caldera.Caldera;
import com.starbotapi.caldera.stats.EntityStatsObject;
import com.starbotapi.caldera.stats.StatsManager;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CalderaMob {

    public String displayName = "";
    public String id = "";
    public Material menuMaterial = Material.MONSTER_EGG;
    public int damage = 30;
    public int health = 100;
    public EntityType texture = EntityType.ZOMBIE;
    public com.starbotapi.caldera.stats.EntityType type = com.starbotapi.caldera.stats.EntityType.NONE;
    public List<MobDrop> drops = new ArrayList<>();

    public CalderaMob() {

    }

    public ItemStack asCraft() {
        ItemStack i = new ItemStack(menuMaterial);
        ItemMeta m = i.getItemMeta();
        m.setDisplayName("\247c" + displayName);
        List<String> lore = new ArrayList<>();
        lore.add("\2477Health: \247d" + health);
        lore.add("\2477Damage: \2474" + damage);
        lore.add("\2477Entity Type: \247a" + type.toString());
        lore.add("\2477Texture: \247a" + texture.toString());
        m.setLore(lore);

        m.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        m.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        i.setItemMeta(m);
        net.minecraft.server.v1_8_R3.ItemStack nms = CraftItemStack.asNMSCopy(i);
        NBTTagCompound tag = nms.getTag();
        if(tag == null) tag = new NBTTagCompound();
        tag.setString("caldera_id",id);
        nms.setTag(tag);
        i = CraftItemStack.asBukkitCopy(nms);
        return i;
    }

    public void spawn(Location loc) {
        LivingEntity entity = (LivingEntity) loc.getWorld().spawnEntity(loc,texture);
        EntityStatsObject so = new EntityStatsObject(entity);
        so.displayName = displayName;
        so.damage = damage;
        so.maxhealth = health;
        so.health = health;
        so.entityType = type;
        so.id = id;
        spawns2(entity,so);
        StatsManager.statsObjects.put(entity,so);
    }

    protected void spawns2(LivingEntity e, EntityStatsObject s) {

    }

}
