package me.xiaojibazhanshi.challenges.commands;

import me.xiaojibazhanshi.challenges.Challenges;
import me.xiaojibazhanshi.challenges.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CompleteCommand implements CommandExecutor {

    private final Challenges main;

    public CompleteCommand(Challenges main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player player)) {
            Bukkit.getLogger().info("This command can only be executed by a player!");
            return true;
        }

        if (!(player.hasPermission(ConfigManager.getCompleteCmdPermission()))) {
            player.sendMessage(net.md_5.bungee.api.ChatColor.RED + "No permission!");
            return true;
        }

        if (ConfigManager.activeChallenge == null) {
            sender.sendMessage(ChatColor.RED + "There are no active challenges!");
            return true;
        }

        ConfigManager.activeChallenge.end(true);

        return true;
    }
}
