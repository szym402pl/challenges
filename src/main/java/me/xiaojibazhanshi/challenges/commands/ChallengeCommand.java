package me.xiaojibazhanshi.challenges.commands;

import me.xiaojibazhanshi.challenges.Challenges;
import me.xiaojibazhanshi.challenges.config.ConfigManager;
import me.xiaojibazhanshi.challenges.records.Challenge;
import me.xiaojibazhanshi.challenges.runnables.ChallengeRunnable;
import me.xiaojibazhanshi.challenges.utils.ChallengeFactory;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChallengeCommand implements CommandExecutor {

    private final Challenges main;

    public ChallengeCommand(Challenges main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player player)) {
            Bukkit.getLogger().info("This command can only be executed by a player!");
            return true;
        }

        if (!(player.hasPermission(ConfigManager.getChallengeCmdPermission()))) {
            player.sendMessage(ChatColor.RED + "No permission!");
            return true;
        }

        if (ConfigManager.activeChallenge != null) {
            player.sendMessage(ChatColor.RED + "A challenge is already active! Use /complete to cancel it.");
            return true;
        }

        if (args.length == 0) {
            ConfigManager.activeChallenge = new ChallengeRunnable();
            ConfigManager.activeChallenge.start(main, ChallengeFactory.getRandomChallenge());
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "Usage: " + command.getUsage());
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            ConfigManager.reloadConfig(main);
            player.sendMessage(ChatColor.GREEN + "Config has been reloaded successfully! \n" + ChatColor.GRAY
                     + ChatColor.ITALIC + "If any config values are null, they'll be listed in the console.");
            return true;
        }

        Challenge challenge = ChallengeFactory.getChallengeById(args[0]);

        if (challenge == null) {
            player.sendMessage(ChatColor.RED + "You've specified an invalid challenge!");
            return true;
        }

        ConfigManager.activeChallenge = new ChallengeRunnable();
        ConfigManager.activeChallenge.start(main, challenge);

        return true;
    }
}
