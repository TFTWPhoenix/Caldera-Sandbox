package com.starbotapi.caldera.command;

import com.starbotapi.caldera.item.CalderaItem;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.awt.*;
import java.util.Arrays;

public class ItemEditorCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!commandSender.isOp() || !(commandSender instanceof Player)) return false;
        Player p = (Player) commandSender;
        ItemStack held = p.getItemInHand();
        net.minecraft.server.v1_8_R3.ItemStack nms = CraftItemStack.asNMSCopy(held);
        NBTTagCompound tag = nms.getTag();
        if(tag == null) tag = new NBTTagCompound();
        NBTTagCompound cd = tag.getCompound("CalderaData");
        if(cd == null) cd = new NBTTagCompound();

        if(strings[0].startsWith("stat_")) {
            cd.setInt(strings[0],Integer.parseInt(strings[1]));
        } else {
            switch (strings[0]) {
                case "name":
                    cd.setString("displayname", assemble(Arrays.copyOfRange(strings,1,strings.length)).replace("&","\247"));
                    break;
                case "rarity":
                    cd.setString("rarity",strings[1]);
                    break;
                case "type":
                    cd.setString("item_type",assemble(Arrays.copyOfRange(strings,1,strings.length)).replace("&","\247"));
                    break;
                case "texture":
                    cd.setString("texture",strings[1]);
                    break;
                case "autotexture":
                    cd.setString("texture",held.getType().toString());
                    break;
                case "effectiveslot":
                    cd.setInt("effectiveslot",Integer.parseInt(strings[1]));
                    break;
                case "setid":
                    cd.setString("setid",strings[1]);
                    break;
                case "has_leather_color":
                    cd.setBoolean("has_leather_color",Boolean.parseBoolean(strings[1]));
                    break;
                case "leather_color":
                    cd.setInt("leather_color",Integer.parseInt(strings[1]));
                    break;
                case "has_head_texture":
                    cd.setBoolean("has_head_texture",Boolean.parseBoolean(strings[1]));
                    break;
                case "head_texture":
                    cd.setString("head_texture",strings[1]);
                    break;
                case "head_texture_b64":
                    cd.setBoolean("is_head_texture_base64",Boolean.parseBoolean(strings[1]));
                    break;
                case "abilitytext":
                    cd.setString("ability_text",assemble(Arrays.copyOfRange(strings,1,strings.length)).replace("&","\247"));
                    break;
                case "comment":
                    cd.setString("comment",assemble(Arrays.copyOfRange(strings,1,strings.length)));
                    break;
                case "abilityid":
                    cd.setString("ability_id",strings[1]);
                    break;
                default:
                    p.sendMessage("\247cUnknown key.");
                    break;
            }
        }
        tag.set("CalderaData",cd);
        nms.setTag(tag);
        held = CraftItemStack.asBukkitCopy(nms);
        p.setItemInHand(CalderaItem.fromCraft(held).asCraft());

        return false;
    }

    private String assemble(String[] strings) {
        String s = "";
        for(String x : strings) {
            if(!s.equals("")) s += " ";
            s += x;
        }
        return s;
    }
}
