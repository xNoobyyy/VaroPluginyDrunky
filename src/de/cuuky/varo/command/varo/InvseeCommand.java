package de.cuuky.varo.command.varo;

import de.cuuky.varo.Main;
import de.cuuky.varo.combatlog.CombatlogCheck;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class InvseeCommand extends VaroCommand {

    public InvseeCommand() {
        super("invsee", "See other peoples inventory", "varo.invsee", "inventorysee", "inventoryomgomgomg");
    }

    @Override
    public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
        if (vp == null) {
            sender.sendMessage(Main.getPrefix() + "Du musst ein Spieler sein!");
            return;
        }

        if (args.length == 0) {
            sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_INVSEE_HELP.getValue(vp, vp));
            return;
        }

        if (VaroPlayer.getPlayer(args[0]) == null) {
            sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_INVSEE_INVALID.getValue(vp, vp));
            return;
        }

        if ((VaroPlayer.getPlayer(args[0]).getPlayer() == null) || (!VaroPlayer.getPlayer(args[0]).getPlayer().isOnline())) {
            sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_INVSEE_INVALID.getValue(vp, vp));
            return;
        }

        vp.getPlayer().openInventory(VaroPlayer.getPlayer(args[0]).getPlayer().getInventory());

    }

}
