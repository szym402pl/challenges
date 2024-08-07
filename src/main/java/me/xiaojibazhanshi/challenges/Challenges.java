package me.xiaojibazhanshi.challenges;

import me.xiaojibazhanshi.challenges.commands.ChallengeCMDTabCompleter;
import me.xiaojibazhanshi.challenges.commands.ChallengeCommand;
import me.xiaojibazhanshi.challenges.commands.CompleteCommand;
import me.xiaojibazhanshi.challenges.config.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Challenges extends JavaPlugin {

    @Override
    public void onEnable() {
        ConfigManager.setupConfig(this);

        getCommand("challenge").setExecutor(new ChallengeCommand(this));
        getCommand("challenge").setTabCompleter(new ChallengeCMDTabCompleter());

        getCommand("complete").setExecutor(new CompleteCommand(this));
    }

    @Override
    public void onDisable() {

    }
}
