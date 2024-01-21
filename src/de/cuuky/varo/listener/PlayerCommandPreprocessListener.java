package de.cuuky.varo.listener;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import de.cuuky.varo.game.state.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;

public class PlayerCommandPreprocessListener implements Listener {
	
	private static final List<String> WORLDEDIT_CRASH_COMMANDS = Arrays.asList("//calc", "/worldedit:/calc", "//calculate", "/worldedit:/calculate", "//eval", "/worldedit:/eval", "//evaluate", "/worldedit:/evaluate", "//solve", "/worldedit:/solve");
	private static final Pattern CRASH_DETECT_PATTERN = Pattern.compile(".+for\\(.+for\\(.+for\\(.+", Pattern.CASE_INSENSITIVE);
	private static final Pattern CRASH_DETECT_PATTERN_SEVERE = Pattern.compile(".+\\sfor\\([a-z]+=0;[a-z]+<256;[a-z]+\\+\\+\\)\\{for\\([a-z]+=0;[a-z]+<256;[a-z]+\\+\\+\\)\\{for\\([a-z]+=0;[a-z]+<256;[a-z]+\\+\\+\\)\\{for\\([a-z]+=0;[a-z]+<256;[a-z]+\\+\\+\\)\\{\\}\\}\\}\\}", Pattern.CASE_INSENSITIVE);

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		String lowerMessage = event.getMessage().split(" ", 2)[0].toLowerCase();

		VaroPlayer vp = VaroPlayer.getPlayer(event.getPlayer());
		String comma = ChatColor.WHITE + "," + ChatColor.GREEN + " ";

		if (WORLDEDIT_CRASH_COMMANDS.contains(lowerMessage)) {
			event.setCancelled(true);
			if (CRASH_DETECT_PATTERN.matcher(event.getMessage()).matches()) {
				if (CRASH_DETECT_PATTERN_SEVERE.matcher(event.getMessage()).matches())
					Bukkit.getServer().broadcastMessage(String.format("%s§e%s §chat mit hoher Sicherheit versucht den Server zu crashen!", Main.getPrefix(), event.getPlayer().getName(), event.getMessage()));
				else
					Bukkit.getServer().broadcastMessage(String.format("%s§e%s §chat möglicherweise versucht den Server zu crashen!", Main.getPrefix(), event.getPlayer().getName(), event.getMessage()));
			} else
				event.getPlayer().sendMessage(Main.getPrefix() + ConfigMessages.COMMANDS_DENIED.getValue(VaroPlayer.getPlayer(event.getPlayer())));
		} else if (lowerMessage.contains("msg")) {
			event.setCancelled(true);
			vp.sendMessage(Main.getPrefix() + ChatColor.AQUA + "Bitte benutze " + ChatColor.YELLOW + "/tell");
		} else if (lowerMessage.equalsIgnoreCase("/tell") ||
				lowerMessage.equalsIgnoreCase("/minecraft:tell") ||
				lowerMessage.equalsIgnoreCase("/bukkit:tell")) {
			if (event.getMessage().split(" ").length > 1 && event.getMessage().split(" ")[1].equalsIgnoreCase(vp.getName()))
				vp.sendMessage("can't send a private message to yourself!");
			if (Main.getVaroGame().getGameState() == GameState.LOBBY && !vp.getPlayer().isOp()) return;
			vp.getStats().setShowScoreboard(true);
			vp.getScoreboard().setEnabled(true);
			vp.getScoreboard().sendScoreBoard();
			if (vp.getNametag() != null)
				vp.getNametag().remove();
			if (vp.isOnline())
				vp.update();
		} else if (lowerMessage.startsWith("/pl") || lowerMessage.startsWith("/plugins") || lowerMessage.startsWith("/bukkit:plugins") || lowerMessage.startsWith("/bukkit:pl")) {
			if (!vp.getPlayer().isOp()) {
				event.setCancelled(true);
				vp.sendMessage(ChatColor.WHITE + "Plugins (5): " + ChatColor.GREEN + "VAROplugin" + comma + "WorldEdit" + comma + "FastAsyncWorldEdit" + comma + "ScenariosUHC" + comma + "ProtocolLib");
			}
		} else if (lowerMessage.startsWith("/me") || lowerMessage.startsWith("/minecraft:me")) {
			if (!vp.getPlayer().isOp()) {
				event.setCancelled(true);
				vp.sendMessage(Main.getPrefix() + "§cDer" + " §4/me " + "§cBefehl ist nicht für Spieler verfügbar.");
			}
		}
	}
}