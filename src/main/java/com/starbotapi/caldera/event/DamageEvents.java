package com.starbotapi.caldera.event;

import com.starbotapi.caldera.item.CalderaItem;
import com.starbotapi.caldera.stats.PlayerStatsObject;
import com.starbotapi.caldera.stats.StatsManager;
import com.starbotapi.caldera.stats.StatsObject;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class DamageEvents implements Listener {

    @EventHandler
    public static void natural(EntityDamageEvent e) {
        if(e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {e.setDamage(0.00001);return;}
        if(e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {e.setDamage(0.00001);return;}
        if(e.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {e.setDamage(0.00001);return;}
        if(StatsManager.statsObjects.containsKey(e.getEntity())) StatsManager.statsObjects.get(e.getEntity()).damage((int) (e.getDamage() * 5));
        e.setDamage(0.0001);
    }

    @EventHandler
    public static void entity(EntityDamageByEntityEvent e) {
        StatsObject attacker = null;
        StatsObject defender = null;
        if(e.getDamager() instanceof Projectile && ((Projectile)e.getDamager()).getShooter() instanceof LivingEntity) {
            LivingEntity s = (LivingEntity) ((Projectile)e.getDamager()).getShooter();
            if(StatsManager.statsObjects.containsKey(s)) attacker = StatsManager.statsObjects.get(s);
        } else {
            if(StatsManager.statsObjects.containsKey(e.getDamager())) attacker = StatsManager.statsObjects.get(e.getDamager());
        }
        if(StatsManager.statsObjects.containsKey(e.getEntity())) defender = StatsManager.statsObjects.get(e.getEntity());
        if(attacker == null || defender == null) {
            e.setCancelled(true);
            return;
        }
        boolean magicprojectile = false;
        if(e.getDamager() instanceof Fireball || e.getDamager() instanceof WitherSkull) magicprojectile = true;
        if(magicprojectile && ((Projectile)e.getDamager()).getShooter() instanceof Player) {
            defender.damage(((PlayerStatsObject)attacker).calcMagicDamage());
        } else {
            defender.damage(attacker.calcDamage());

        }
        e.setDamage(0.000001);
        if(attacker instanceof PlayerStatsObject) {
            Player player = ((Player) attacker.getBukkit());
            ItemStack[] equipment = {
                    player.getEquipment().getHelmet(),
                    player.getEquipment().getChestplate(),
                    player.getEquipment().getLeggings(),
                    player.getEquipment().getBoots(),
                    player.getEquipment().getItemInHand()
            };
            for(int i = 0; i < equipment.length; i++) {
                CalderaItem ci = CalderaItem.fromCraft(equipment[i]);
                if(ci.effectiveslot != i) continue;
                ci.onAttack.accept(e);
            }
        }
    }

}
