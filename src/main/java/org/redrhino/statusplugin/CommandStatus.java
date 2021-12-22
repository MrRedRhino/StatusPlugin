package org.redrhino.statusplugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandStatus implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 0) {
            commandSender.sendMessage(ChatColor.BOLD + "Available statuses are:");
            for (int i = 0; i < StatusPlugin.statuses.size(); i++) {
                commandSender.sendMessage(" - " + StatusPlugin.statuses.keySet().toArray()[i].toString());
            }
            return true;
        }

        if (StatusPlugin.statuses.containsKey(strings[0])) {
            if (!StatusPlugin.statuses.get(strings[0]).isEmpty()) {
                StatusPlugin.updateStatus((Player) commandSender, StatusPlugin.statuses.get(strings[0]));
                commandSender.sendMessage("Your status is now " + strings[0]);
            } else {
                StatusPlugin.updateStatus((Player) commandSender, "");
                commandSender.sendMessage("Removed your status.");
            }
        } else {
            commandSender.sendMessage("That status doesn't exist.");
        }

        return true;
    }

}
