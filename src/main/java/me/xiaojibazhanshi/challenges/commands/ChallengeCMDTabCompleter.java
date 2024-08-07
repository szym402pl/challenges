package me.xiaojibazhanshi.challenges.commands;

import me.xiaojibazhanshi.challenges.config.ConfigManager;
import me.xiaojibazhanshi.challenges.records.Challenge;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class ChallengeCMDTabCompleter implements TabCompleter {


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {

        if (args.length == 1 && sender.hasPermission(ConfigManager.getChallengeCmdPermission())) {
            List<String> originals = new ArrayList<>();
            originals.add("reload");

            for (Challenge challenge : ConfigManager.getChallengeList()) {
                originals.add(challenge.id());
            }

            return StringUtil.copyPartialMatches(args[0], originals, new ArrayList<>());
        }

        return new ArrayList<>();
    }
}
