package com.starbotapi.caldera.command;

import com.starbotapi.caldera.stats.PlayerStatsObject;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PrefabsCommand implements CommandExecutor {

    private static HashMap<Player,PrefabsItemMenu> prefabsItemMenus = new HashMap<>();
    private static HashMap<Player,PrefabsMobMenu> prefabsMobMenus = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings[0].equals("item")) {
            if(commandSender.isOp() && commandSender instanceof Player) {
                prefabsItemMenus.putIfAbsent((Player) commandSender,new PrefabsItemMenu());
                prefabsItemMenus.get((Player) commandSender).open((Player) commandSender);
            }
        } else if (strings[0].equals("entity")) {
            if(commandSender.isOp() && commandSender instanceof Player) {
                prefabsMobMenus.putIfAbsent((Player) commandSender,new PrefabsMobMenu());
                prefabsMobMenus.get((Player) commandSender).open((Player) commandSender);
            }        } else {
            commandSender.sendMessage("\247cUnknown prefab type \"" + strings[0] + "\"");
        }

        return false;
    }
}
