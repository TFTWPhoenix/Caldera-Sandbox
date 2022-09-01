package com.starbotapi.caldera.command;

import com.starbotapi.caldera.item.CalderaItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UpdateItemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) return false;
        Player p = (Player) commandSender;
        p.setItemInHand(CalderaItem.fromCraft(p.getItemInHand()).asCraft());
        return false;
    }
}
