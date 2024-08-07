package me.xiaojibazhanshi.challenges.config;

import lombok.Getter;
import me.xiaojibazhanshi.challenges.Challenges;
import me.xiaojibazhanshi.challenges.records.Challenge;
import me.xiaojibazhanshi.challenges.runnables.ChallengeRunnable;
import me.xiaojibazhanshi.challenges.utils.ChallengeFactory;
import me.xiaojibazhanshi.challenges.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ConfigManager {

    private static FileConfiguration config;

    @Getter public static ChallengeRunnable activeChallenge;

    @Getter private static double dpsOnChallengeEnd;
    @Getter private static String challengeCmdPermission;
    @Getter private static boolean titleOnStatusChange;
    @Getter private static int damageDuration;

    @Getter private static String chatPrefix;

    @Getter private static String challengeStartFormat;
    @Getter private static String challengeCompleteFormat;
    @Getter private static String challengeFailedFormat;

    @Getter private static List<Challenge> challengeList;

    public static void setupConfig(Challenges main) {
        ConfigManager.config = main.getConfig();
        main.saveDefaultConfig();
        initiateVariables();
    }

    public static void reloadConfig(Challenges main) {
        ConfigManager.config = main.getConfig();
        main.reloadConfig();
        setupConfig(main);
    }

    private static void initiateVariables() {
        activeChallenge = null;

        damageDuration = (int) Utils.nullCheck("damage-duration-in-seconds",
                config.getInt("damage-duration-in-seconds"));

        challengeCmdPermission = (String) Utils.nullCheck("challenge-command-permission",
                config.getString("challenge-command-permission"));

        dpsOnChallengeEnd = (double) Utils.nullCheck("dps-on-challenge-end",
                config.getDouble("dps-on-challenge-end"));

        titleOnStatusChange = (boolean) Utils.nullCheck("title-on-challenge-status-change",
                config.getBoolean("title-on-challenge-status-change"));

        chatPrefix = (String) Utils.nullCheck("messages.chat-prefix",
                config.getString("messages.chat-prefix"));

        challengeStartFormat = (String) Utils.nullCheck("messages.challenge-start-format",
                config.getString("messages.challenge-start-format"));
        challengeCompleteFormat = (String) Utils.nullCheck("messages.challenge-complete-format",
                config.getString("messages.challenge-complete-format"));
        challengeFailedFormat = (String) Utils.nullCheck("messages.challenge-failed-format",
                config.getString("messages.challenge-failed-format"));

        try {
            challengeList = ChallengeFactory.retrieveChallenges(config.getConfigurationSection("challenge-list"));
        } catch (NullPointerException npe) {
            Bukkit.getLogger().warning("Challenge list section is not set in the config!");
        }
    }


}