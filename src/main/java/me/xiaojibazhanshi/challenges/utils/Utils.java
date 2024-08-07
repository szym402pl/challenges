package me.xiaojibazhanshi.challenges.utils;

import me.xiaojibazhanshi.challenges.config.ConfigManager;
import me.xiaojibazhanshi.challenges.records.Challenge;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Utils {

    public static String translate(String string, Challenge challenge) {
        return ChatColor.translateAlternateColorCodes('&',
                string.replace("{prefix}", ConfigManager.getChatPrefix())
                        .replace("{challenge-description}", challenge.description()));
    }

    public static Object nullCheck(String path, Object obj) {
        if (obj == null) {
            Bukkit.getLogger().warning("A config item is not set properly! Path: " + path + "\n");
        }

        return obj;
    }

    public static void displayTimer(int timerSeconds) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                    new TextComponent(net.md_5.bungee.api.ChatColor.GREEN + "Challenge ends in: " + net.md_5.bungee.api.ChatColor.RED + timerSeconds));
        }
    }

    public static void sendMessage(String text) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(text);
        }
    }

    public static void sendTitle(String text) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendTitle("", text, 10, 20, 10);
        }
    }

    public static void setStorm(String challengeID) {
        if (challengeID.contains("lightning")) {
            Bukkit.getWorlds().stream()
                    .filter(world -> world.getEnvironment().equals(World.Environment.NORMAL))
                    .forEach(world -> world.setStorm(true));
        }
    }

}
