package me.xiaojibazhanshi.challenges.utils;

import me.xiaojibazhanshi.challenges.config.ConfigManager;
import me.xiaojibazhanshi.challenges.records.Challenge;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChallengeFactory {

    public static List<Challenge> retrieveChallenges(ConfigurationSection section) {
        if (section == null) {
            return null;
        }

        List<Challenge> challenges = new ArrayList<>();

        for (String key : section.getKeys(false)) {
            String path = section.getCurrentPath();

            int timeToComplete = (int) Utils.nullCheck(path + key,
                    section.getInt(key + "." + "time-to-complete-in-seconds"));

            String id = (String) Utils.nullCheck(path + key, key);
            String description = (String) Utils.nullCheck(path, section.getString(key + "." + "description"));

            challenges.add(new Challenge(id, timeToComplete, description));
        }

        if (challenges.isEmpty())
            Bukkit.getLogger().warning("There are no challenges set in the config!");

        return challenges;
    }

    public static Challenge getRandomChallenge() {
        List<Challenge> copy = new ArrayList<>(List.copyOf(ConfigManager.getChallengeList()));
        Collections.shuffle(copy);

        return copy.getLast();
    }

    public static Challenge getChallengeById(String id) {
        for (Challenge challenge : ConfigManager.getChallengeList()) {
            if (challenge.id().equalsIgnoreCase(id)) return challenge;
        }

        return null; // couldn't find one
    }

}
