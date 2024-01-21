package de.cuuky.varo.command.essentials;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MiddleCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        VaroPlayer vp = (sender instanceof Player) ? VaroPlayer.getPlayer((Player)sender) : null;
        if (vp == null) {
            sender.sendMessage("Du musst nen Spieler sein du keko!");
            return false;
        }

        Location loc = vp.getPlayer().getWorld().getSpawnLocation();
        vp.sendMessage(Main.getPrefix() + ConfigMessages.COMMANDS_MIDDLE.getValue(vp, vp)
                .replace("%value%", loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ()));
        return false;
    }

}