package org.redrhino.statusplugin;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import java.util.*;

public final class StatusPlugin extends JavaPlugin implements Listener {
    public static final HashMap<String, String> statuses = new HashMap<>();

    @Override
    public void onEnable() {
        loadConfig(getConfig());

        Objects.requireNonNull(this.getCommand("status")).setExecutor(new CommandStatus());
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    public static void updateStatus(Player player, String newTeamPrefix) {
        Scoreboard sc = player.getScoreboard();
        if (sc.getTeam("STATUS_TEAM_" + player.getUniqueId()) == null) sc.registerNewTeam("STATUS_TEAM_" + player.getUniqueId());

        sc.getTeam("STATUS_TEAM_" + player.getUniqueId()).addPlayer(player);

        sc.getTeam("STATUS_TEAM_" + player.getUniqueId()).prefix(Component.text(
                ChatColor.translateAlternateColorCodes('&', newTeamPrefix))
        );
    }

    public void loadConfig(FileConfiguration config) {
        config.options().copyDefaults(true);
        saveConfig();
        for (Map<?, ?> i : config.getMapList("statuses")) {
            String key = i.keySet().toArray()[0].toString();
            String value = i.values().toArray()[0].toString();
            statuses.put(key, value);
        }
    }

    @EventHandler
    public void onTab(TabCompleteEvent event) {
        if (event.getBuffer().startsWith("/status")) {
            event.setCompletions(new ArrayList<>(StatusPlugin.statuses.keySet()));
        }
    }
}
