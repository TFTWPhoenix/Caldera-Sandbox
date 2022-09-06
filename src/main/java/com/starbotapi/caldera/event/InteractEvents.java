package com.starbotapi.caldera.event;

import com.starbotapi.caldera.Caldera;
import com.starbotapi.caldera.item.CalderaItem;
import com.starbotapi.caldera.mining.MineableBlock;
import com.starbotapi.caldera.region.RegionManager;
import com.starbotapi.caldera.stats.PlayerStatsObject;
import com.starbotapi.caldera.stats.StatsManager;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.texture.BlockTexture;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class InteractEvents implements Listener {

    public static Map<Player,Boolean> cooldown = new HashMap<>();
    public static Map<Player,Boolean> isMining = new HashMap<>();


    @EventHandler
    public static void interact(PlayerInteractEvent e) {
        cooldown.putIfAbsent(e.getPlayer(),false);
        if(cooldown.get(e.getPlayer())) return;
        cooldown.put(e.getPlayer(),true);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Caldera.p,()->cooldown.put(e.getPlayer(),false),2L);
        Player player = e.getPlayer();
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
            Caldera.abilityFromID(ci.abilityID).activate.accept(e);
        }

        if(e.getAction() == Action.LEFT_CLICK_BLOCK && e.getPlayer().getGameMode() == GameMode.SURVIVAL) {
            e.setCancelled(true);
            MineableBlock b = RegionManager.getRegionOf(e.getClickedBlock().getLocation()).getMineable(e.getClickedBlock().getType());
            PlayerStatsObject so = ((PlayerStatsObject)StatsManager.statsObjects.get(e.getPlayer()));
            if(b != null && (CalderaItem.fromCraft(player.getItemInHand()).itemtype.equals(b.getToolType()) || b.getStrength() == 0) &&
                    so.getValueOfStat("toolpower") >= b.getStrength()) {
                isMining.putIfAbsent(e.getPlayer(),false);
                if(isMining.get(e.getPlayer())) return;
                isMining.put(e.getPlayer(),true);
                int ticksToMine = b.getHardness() - so.getValueOfStat("toolspeed");
                if(ticksToMine <= 0) ticksToMine = 0;
                int stepTicks = ticksToMine / 10;
                for(int i = 0; i < stepTicks; i++) {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Caldera.p,()->{
                        ParticleEffect.BLOCK_CRACK.display(e.getClickedBlock().getLocation().add(0.5,1,0.5),
                                0F,0F,0F,
                                2F,100,new BlockTexture(e.getClickedBlock().getType()));
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.DIG_STONE,1F,1F);
                    },10L * i);
                }
                Bukkit.getScheduler().scheduleSyncDelayedTask(Caldera.p,()->{
                    e.getClickedBlock().setType(b.getReplaceWith());
                    int amnt = b.getMinDrop() + (new Random()).nextInt(b.getMaxDrop() - b.getMinDrop());
                    ItemStack i = b.getDrop().asCraft();
                    i.setAmount(amnt);
                    e.getPlayer().getInventory().addItem(i);
                    isMining.put(e.getPlayer(),false);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Caldera.p,()->{
                        e.getClickedBlock().setType(b.getMaterial());
                    },b.getTicksTillRegenerate());
                },ticksToMine);
            }
        }
    }

}
