package de.cuuky.varo.command.varo;

import de.cuuky.varo.Main;
import de.cuuky.varo.combatlog.CombatlogCheck;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class PortalCommand extends VaroCommand {

    public PortalCommand() {
        super("portal", "(De)aktiviere Portale", "varo.portal", "deactivportal", "deactivateport", "deactivport", "port");
    }

    @Override
    public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
        if (args.length != 0) {
            sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_CHECKCOMBAT_HELP.getValue(vp, vp));
            return;
        }

        if (Main.getVaroGame().isDeactivatePortal()) {
            Main.getVaroGame().setDeactivatePortal(false);
            sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_PORTAL_ACTIVATED.getValue(vp, vp));
        } else {
            Main.getVaroGame().setDeactivatePortal(true);
            sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_PORTAL_DEACTIVATED.getValue(vp, vp));
        }
    }

}