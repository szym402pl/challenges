package me.xiaojibazhanshi.challenges.runnables;

import me.xiaojibazhanshi.challenges.Challenges;
import me.xiaojibazhanshi.challenges.config.ConfigManager;
import me.xiaojibazhanshi.challenges.records.Challenge;
import me.xiaojibazhanshi.challenges.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ChallengeRunnable extends BukkitRunnable {

    private Challenge challenge;

    private boolean startSendingDamage = false;
    private int damageCounter = 30;
    private int timerSeconds;
    private float damage;

    @Override
    public void run() {
        if (startSendingDamage) {
            if (damageCounter <= 0) {
                cancel();
            }

            for (Player player : Bukkit.getOnlinePlayers()) {
                player.damage(damage);
            }

            damageCounter--;
        } else {
            if (timerSeconds <= 0) {
                end(false);
            }

            Utils.displayTimer(timerSeconds);
            timerSeconds--;
        }
    }

    public void start(Challenges main, Challenge challenge) {
        ConfigManager.activeChallenge = this;

        this.challenge = challenge;
        this.timerSeconds = challenge.timeToComplete();
        this.damage = (float) ConfigManager.getDpsOnChallengeEnd();
        this.damageCounter = ConfigManager.getDamageDuration();

        Utils.sendMessage(Utils.translate(ConfigManager.getChallengeStartFormat(), challenge));

        if (ConfigManager.isTitleOnStatusChange())
            Utils.sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD + "Challenge started!");

        if (challenge.id().contains("lightning")) // Custom logic, could be implemented per challenge later on
            Utils.setStorm(this.challenge.id());

        runTaskTimer(main, 0, 20);
    }

    public void end(boolean completed) {
        ConfigManager.activeChallenge = null;

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent());
        }

        if (completed) {
            Utils.sendMessage(Utils.translate(ConfigManager.getChallengeCompleteFormat(), challenge));
            Utils.sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD + "Challenge completed!");

            this.cancel();
            return;
        }

        Utils.sendMessage(Utils.translate(ConfigManager.getChallengeFailedFormat(), challenge));
        Utils.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "Challenge failed!");

        startSendingDamage = true;
    }


}
