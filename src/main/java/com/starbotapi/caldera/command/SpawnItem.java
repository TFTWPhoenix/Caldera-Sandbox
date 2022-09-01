package com.starbotapi.caldera.command;

import com.starbotapi.caldera.Caldera;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnItem implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!commandSender.isOp()) {
            commandSender.sendMessage("\247cYou do not have permission to run this command.");
            return false;
        }
        if(!(commandSender instanceof Player)) return false;
        ((Player) commandSender).getInventory().addItem(Caldera.itemFromID(strings[0]).asCraft());
        return false;
    }
}
