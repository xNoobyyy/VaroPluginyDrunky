package de.cuuky.varo.command.essentials;

import de.cuuky.varo.Main;
import de.cuuky.varo.combatlog.CombatlogCheck;
import de.cuuky.varo.entity.player.VaroPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        VaroPlayer vp = (sender instanceof Player) ? VaroPlayer.getPlayer((Player)sender) : null;
        if (vp == null) {
            sender.sendMessage("Du musst nen Spieler sein du keko!");
            return false;
        }
        if ((new CombatlogCheck(vp.getPlayer())).isCombatLog()) {
            vp.sendMessage(Main.getPrefix() + ChatColor.YELLOW + "Dein CombatLog-Status: " + ChatColor.RED + "IN COMBAT");
        } else {
            vp.sendMessage(Main.getPrefix() + ChatColor.YELLOW + "Dein CombatLog-Status: " + ChatColor.GREEN + "OK");
        }
        return false;
    }

}