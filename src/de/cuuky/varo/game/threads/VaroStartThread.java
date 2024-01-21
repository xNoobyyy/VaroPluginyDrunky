package de.cuuky.varo.game.threads;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.cfw.version.types.Sounds;
import de.cuuky.varo.Main;
import de.cuuky.varo.api.VaroAPI;
import de.cuuky.varo.api.event.events.game.VaroStartEvent;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.game.VaroGame;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.logger.logger.EventLogger.LogType;

import java.util.Arrays;
import java.util.List;

public class VaroStartThread extends BukkitRunnable {

	private static List<Integer> broadcastCountdownNumbers;

	static {
		broadcastCountdownNumbers = Arrays.asList(ConfigSetting.STARTCOUNTDOWN.getValueAsInt(), 60, 50, 40, 30, 20, 15, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1);
	}

	private VaroGame game;
	private int startcountdown;

	public VaroStartThread() {
		this.game = Main.getVaroGame();

		loadVaraibles();
	}

	public void loadVaraibles() {
		this.startcountdown = ConfigSetting.STARTCOUNTDOWN.getValueAsInt();
	}

	@Override
	public void run() {
		VersionUtils.getVersionAdapter().getOnlinePlayers().stream().findFirst().ifPresent(player -> player.getWorld().setTime(1000));

		if (broadcastCountdownNumbers.contains(startcountdown)) {
			Main.getLanguageManager().broadcastMessage(ConfigMessages.GAME_START_COUNTDOWN).replace("%countdown%", startcountdown == 1 ? "einer" : String.valueOf(startcountdown));
		}

		if (startcountdown == ConfigSetting.STARTCOUNTDOWN.getValueAsInt() || startcountdown == 1) {
			for (VaroPlayer pl1 : VaroPlayer.getOnlinePlayer()) {
				if (pl1.getStats().isSpectator())
					continue;

				Player pl = pl1.getPlayer();
				pl.setGameMode(GameMode.ADVENTURE);
				pl1.cleanUpPlayer();
			}
		}

		if (this.startcountdown <= 10 && this.startcountdown >= 0) {
			for (VaroPlayer vp : VaroPlayer.getOnlinePlayer()) {
				if (vp.getStats().isSpectator())
					continue;

				Player pl = vp.getPlayer();
				if (this.startcountdown != 0) {
					if (this.startcountdown <= 10 && this.startcountdown >= 4) {
						pl.playSound(pl.getLocation(), Sounds.NOTE_PLING.bukkitSound(), 1.0F, 2.0F);
					} else {
						pl.playSound(pl.getLocation(), Sounds.NOTE_BASS_DRUM.bukkitSound(), 1.0F, 2.0F);
					}
				}

				String countdownString = String.valueOf(startcountdown);
				String[] title;
				if (this.startcountdown == 10) {
					title = (ChatColor.YELLOW + "%countdown%").replace("%countdown%", String.valueOf(this.startcountdown)).split("\n");
				} else if (this.startcountdown > 3) {
					title = (ChatColor.RED + "%countdown%").replace("%countdown%", String.valueOf(this.startcountdown)).split("\n");
				} else if (this.startcountdown != 0) {
					title = (ChatColor.DARK_RED + "%countdown%").replace("%countdown%", String.valueOf(this.startcountdown)).split("\n");
				} else {
					title = (ChatColor.DARK_GREEN + "%countdown%\n" + ChatColor.GREEN + "Mögen die Spiele beginnen!").replace("%countdown%", String.valueOf(this.startcountdown)).split("\n");
				}

				if (title.length != 0)
					vp.getVersionAdapter().sendTitle(title[0], (title.length == 2) ? title[1] : "");
			}
		}

		if (this.startcountdown == 0) {
			for (VaroPlayer vp : VaroPlayer.getOnlinePlayer()) {
				vp.getStats().setShowScoreboard(true);
				vp.getScoreboard().setEnabled(true);
				vp.getScoreboard().sendScoreBoard();
				if (vp.getNametag() != null)
					vp.getNametag().remove();
			}
			Main.getVaroGame().setFirstblood(true);
			if (VaroAPI.getEventManager().executeEvent(new VaroStartEvent(game))) {
				startcountdown = ConfigSetting.STARTCOUNTDOWN.getValueAsInt();
				cancel();
				return;
			}

			Main.getVaroGame().setGamestate(GameState.STARTED);
			this.startcountdown = ConfigSetting.STARTCOUNTDOWN.getValueAsInt();

			Bukkit.broadcastMessage(Main.getPrefix() + ChatColor.GREEN + "Mögen die Spiele beginnen!");
			after2Secs();

			Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, ConfigMessages.ALERT_GAME_STARTED.getValue());
			cancel();

			this.game.start();

			game.setStartThread(null);
			return;
		}

		startcountdown--;
	}

	private void after2Secs() {
		new BukkitRunnable() {
			public void run() {
				Bukkit.broadcastMessage(Main.getPrefix() + ChatColor.GOLD + "INFO: " + ChatColor.GRAY + "Falls du dich manuell ausloggen möchtest solltest du davor dein CombatLogStatus überprüfen, damit du keinen Strike bekommst. " + ChatColor.GOLD + "/cls");
			}
		}.runTaskLaterAsynchronously(Main.getInstance(), 100L);
	}

}