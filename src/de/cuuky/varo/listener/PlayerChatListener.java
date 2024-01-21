package de.cuuky.varo.listener;

import de.cuuky.varo.configuration.configurations.config.ConfigSettingSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.listener.helper.ChatMessage;
import de.cuuky.varo.listener.helper.TeamChat;
import de.cuuky.varo.listener.helper.cancelable.CancelAbleType;
import de.cuuky.varo.listener.helper.cancelable.VaroCancelAble;
import de.cuuky.varo.logger.logger.ChatLogger.ChatLogType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

public class PlayerChatListener implements Listener {
	
	private static final String AD_REGEX = "(?ui).*(w\\s*w\\s*w|h\\s*t\\s*t\\s*p\\s*s?|[.](d\\s*e|n\\s*e\\s*t|c\\s*o\\s*m|t\\s*v|t\\s*o)).*";
	private static final String AD_REGEX_AGRESSIVE = "(?ui).*(w\\s*w\\s*w|h\\s*t\\s*t\\s*p\\s*s?|[.,;]\\s*(d\\s*e|n\\s*e\\s*t|c\\s*o\\s*m|t\\s*v|t\\s*o)).*";

	private HashMap<Player, String> antispam = new HashMap<>();
	private HashSet<Player> spamlvl1 = new HashSet<>();
	private HashSet<Player> spamlvl2 = new HashSet<>();
	private HashSet<Player> spamlvl3 = new HashSet<>();
	private HashSet<Player> spamlvl4 = new HashSet<>();
	private HashSet<Player> spamblock = new HashSet<>();
	private int blocktime = 0;

	private void sendMessageToAll(String msg, VaroPlayer vp, AsyncPlayerChatEvent event) {
		if (vp.getStats().getYoutubeLink() == null) {
			event.setCancelled(false);
			event.setFormat(msg);
			return;
		}

		for (VaroPlayer vpo : VaroPlayer.getOnlinePlayer())
			vpo.getVersionAdapter().sendLinkedMessage(msg, vp.getStats().getYoutubeLink());
		event.setCancelled(true);
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		if (event.isCancelled())
			return;

		String message = event.getMessage();
		Player player = event.getPlayer();

		{
			final File fileas = new File("plugins/varo/config/" + ConfigSettingSection.ANTISPAM.getName().toLowerCase() + ".yml");
			final YamlConfiguration as = YamlConfiguration.loadConfiguration(fileas);
			if (!player.isOp()) {
				if (ConfigSetting.ANTISPAM_ENABLED.getValueAsBoolean()) {
					if (this.spamblock.contains(player)) {
						player.sendMessage(Main.getPrefix() + "§4AntiSpam §8| §cDu bist zur Zeit für den Chat gesperrt.");
						event.setCancelled(true);
						return;
					}
					if (this.spamlvl1.contains(player)) {
						if (this.spamlvl2.contains(player)) {
							if (this.spamlvl3.contains(player)) {
								if (this.spamlvl4.contains(player)) {
									player.sendMessage(Main.getPrefix() + "§4AntiSpam §8| §cDu bist auf Grund von Spamming für 20 Sekunden im Chat gesperrt.");
									this.spamblock(player);
									event.setCancelled(true);
									return;
								}
								this.spamLVL4(player);
							} else {
								this.spamLVL3(player);
							}
						} else {
							this.spamLVL2(player);
						}
					} else {
						this.spamLVL1(player);
					}
					if (this.antispam.containsKey(player)) {
						final String oldmsg = this.antispam.get(player);
						if (message.equalsIgnoreCase(oldmsg)) {
							player.sendMessage(Main.getPrefix() + "§4AntiSpam §8| §cDeine Nachricht wurde gelöscht.");
							event.setCancelled(true);
							return;
						}
						this.antispam.remove(player);
						this.antispam.put(player, message);
					} else {
						this.antispam.put(player, message);
					}
				}
				if (ConfigSetting.ANTICAPS_ENABLED.getValueAsBoolean()) {
					final String caps = message.toUpperCase();
					if (message.equals(caps) && message.length() > 2 && !message.toLowerCase().equals(message)) {
						player.sendMessage(Main.getPrefix() + "§4AntiSpam §8| §cBitte nicht in Caps schreiben.");
						event.setMessage(message.toLowerCase());
					}
				}
				if (ConfigSetting.BANWORDS_ENABLED.getValueAsBoolean()) {
					for (final String bannedwords : as.getStringList("bannedWords")) {
						if (message.toLowerCase().contains(bannedwords.toLowerCase())) {
							player.sendMessage(Main.getPrefix() + "§4AntiSpam §8| §cBitte keine Beleidigungen verwenden.");
							event.setCancelled(true);
							return;
						}
					}
				}
			}
		}

		if (message.contains("%"))
			message = message.replace("%", "");

		boolean mentionsHack = false;
		String[] hackMentions = { "hack", "cheat", "x-ray", "xray", "unlegit" };
		for (String mention : hackMentions) {
			if (message.toLowerCase().contains(mention)) {
				mentionsHack = true;
			}
		}
		if (mentionsHack == true && ConfigSetting.REPORTSYSTEM_ENABLED.getValueAsBoolean()) {
			player.sendMessage(Main.getPrefix() + "§7Erinnerung: Reporte Hacks, Cheats und aehnliches mit §l/report");
		}

		VaroPlayer vp = VaroPlayer.getPlayer(player);
		String tc = ConfigSetting.CHAT_TRIGGER.getValueAsString();
		boolean globalTrigger = ConfigSetting.TRIGGER_FOR_GLOBAL.getValueAsBoolean();
		if ((message.startsWith(tc) && !globalTrigger) || (!message.startsWith(tc) && globalTrigger)) {
			new TeamChat(vp, message.replaceFirst("\\\\" + tc, ""));
			event.setCancelled(true);
			return;
		} else if (message.startsWith(tc)) {
			message = message.replaceFirst("\\\\" + tc, "");
		}

		if (ConfigSetting.BLOCK_CHAT_ADS.getValueAsBoolean() && !player.isOp()) {
			if (message.matches(ConfigSetting.BLOCK_CHAT_ADS_AGRESSIVE.getValueAsBoolean() ? AD_REGEX_AGRESSIVE : AD_REGEX)) {
				player.sendMessage(Main.getPrefix() + "Du darfst keine Werbung senden - bitte sende keine Links.");
				player.sendMessage(Main.getPrefix() + "Falls dies ein Fehler sein sollte, frage einen Admin.");
				event.setCancelled(true);
				return;
			}
			
			if (message.matches("(?iu).*(meins?e?m?n?)\\s*(Projekt|Plugin|Server|Netzwerk|Varo).*")) {
				player.sendMessage(Main.getPrefix() + "Du darfst keine Werbung senden - bitte sende keine Eigenwerbung.");
				player.sendMessage(Main.getPrefix() + "Falls dies ein Fehler sein sollte, frage einen Admin.");
				event.setCancelled(true);
				return;
			}
		}

		if (VaroCancelAble.getCancelAble(vp, CancelAbleType.MUTE) != null) {
			vp.sendMessage(ConfigMessages.CHAT_MUTED);
			event.setCancelled(true);
			return;
		}

		if (!player.isOp()) {
			if (ConfigSetting.CHAT_COOLDOWN_IF_STARTED.getValueAsBoolean() || !Main.getVaroGame().hasStarted()) {
				ChatMessage msg = vp.getLastMessage();
				if (msg != null) {
					long delta = System.currentTimeMillis() - msg.getTimestamp();
					if (delta < ConfigSetting.CHAT_COOLDOWN_IN_SECONDS.getValueAsInt() * 1000) {
						player.sendMessage(Main.getPrefix() + "§7Du kannst nur alle §7" + ConfigSetting.CHAT_COOLDOWN_IN_SECONDS.getValueAsInt() + " §7Sekunden schreiben!");
						event.setCancelled(true);
						return;
					}
				}
				vp.setLastMessage(new ChatMessage(message));
			}

			if (!Main.getVaroGame().hasStarted() && !ConfigSetting.CAN_CHAT_BEFORE_START.getValueAsBoolean()) {
				vp.sendMessage(ConfigMessages.CHAT_WHEN_START);
				event.setCancelled(true);
				return;
			}
		} else
			message = message.replace("&", "§");

		Main.getDataManager().getVaroLoggerManager().getChatLogger().println(ChatLogType.CHAT, player, null, message);

		String messageFormat = "";
		if (vp.getTeam() != null) {
			if (vp.getRank() == null) {
				messageFormat = ConfigMessages.CHAT_PLAYER_WITH_TEAM.getValue(null, vp);
			} else {
				messageFormat = ConfigMessages.CHAT_PLAYER_WITH_TEAM_RANK.getValue(null, vp);
			}
		} else {
			if (vp.getRank() == null) {
				messageFormat = ConfigMessages.CHAT_PLAYER_WITHOUT_TEAM.getValue(null, vp);
			} else {
				messageFormat = ConfigMessages.CHAT_PLAYER_WITHOUT_TEAM_RANK.getValue(null, vp);
			}
		}

		sendMessageToAll(messageFormat.replace("%message%", message), vp, event);
	}

	public void spamblock(final Player p) {
		if (this.spamblock.contains(p)) {
			return;
		}
		this.spamblock.add(p);
		new BukkitRunnable() {
			@Override
			public void run() {
				PlayerChatListener.this.spamblock.remove(p);
			}
		}.runTaskLater(Main.getInstance(), 400L);
	}

	public void spamLVL1(final Player p) {
		if (this.spamlvl1.contains(p)) {
			return;
		}
		this.spamlvl1.add(p);
		new BukkitRunnable() {
			@Override
			public void run() {
				PlayerChatListener.this.spamlvl1.remove(p);
			}
		}.runTaskLater(Main.getInstance(), 200L);
	}

	public void spamLVL2(final Player p) {
		if (this.spamlvl2.contains(p)) {
			return;
		}
		this.spamlvl2.add(p);
		new BukkitRunnable() {
			@Override
			public void run() {
				PlayerChatListener.this.spamlvl2.remove(p);
			}
		}.runTaskLater(Main.getInstance(), 140L);
	}

	public void spamLVL3(final Player p) {
		if (this.spamlvl3.contains(p)) {
			return;
		}
		this.spamlvl3.add(p);
		new BukkitRunnable() {
			@Override
			public void run() {
				PlayerChatListener.this.spamlvl3.remove(p);
			}
		}.runTaskLater(Main.getInstance(), 100L);
	}

	public void spamLVL4(final Player p) {
		if (this.spamlvl4.contains(p)) {
			return;
		}
		this.spamlvl4.add(p);
		new BukkitRunnable() {
			@Override
			public void run() {
				PlayerChatListener.this.spamlvl4.remove(p);
			}
		}.runTaskLater(Main.getInstance(), 60L);
	}

}