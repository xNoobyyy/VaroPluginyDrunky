package de.cuuky.varo.command.essentials;

import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.VaroPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VtCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        VaroPlayer vp = (sender instanceof Player) ? VaroPlayer.getPlayer((Player)sender) : null;
        if (vp == null) {
            sender.sendMessage("Du musst nen Spieler sein du keko!");
            return false;
        }
        String msg = Main.getPrefix() + ChatColor.YELLOW + "Du hast noch ";
        int secs = vp.getStats().getCountdown();
        int hours = secs / 3600;
        String minsSecs = String.format("%02d", secs / 60 % 60) + ":" + String.format("%02d", secs % 60);
        msg = msg + ChatColor.RED + ((hours >= 1) ? (Main.getColorCode() + String.format("%02d", hours) + ":" + minsSecs) : minsSecs) + " Minuten" + ChatColor.YELLOW + ".";
        vp.sendMessage(msg);
        return false;
    }

}