package de.cuuky.varo.game.start;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;

public class ProtectionTime {

	private BukkitTask scheduler;
	private int timer;

	public ProtectionTime() {
		startGeneralTimer(ConfigSetting.STARTPERIOD_PROTECTIONTIME.getValueAsInt());
	}

	public ProtectionTime(int t) {
		startGeneralTimer(t);
	}

	private void startGeneralTimer(int t) {
		Main.getVaroGame().setCanHunger(false);
		timer = t;
		this.scheduler = new BukkitRunnable() {

			@Override
			public void run() {
				if (!Main.getVaroGame().isRunning()) {
					scheduler.cancel();
					return;
				}

				if (timer == 0) {
					Main.getLanguageManager().broadcastMessage(ConfigMessages.PROTECTION_TIME_OVER);
					Main.getVaroGame().setProtection(null);
					scheduler.cancel();
				} else if (timer % ConfigSetting.STARTPERIOD_PROTECTIONTIME_BROADCAST_INTERVAL.getValueAsInt() == 0 && timer != t)
					Main.getLanguageManager().broadcastMessage(ConfigMessages.PROTECTION_TIME_UPDATE).replace("%minutes%", getCountdownMin(timer)).replace("%seconds%", getCountdownSec(timer));

				timer--;
			}
		}.runTaskTimer(Main.getInstance(), 1L, 20L);
	}

	private String getCountdownMin(int sec) {
		int min = sec / 60;

		if (min < 10)
			return "0" + min;
		else
			return min + "";
	}

	private String getCountdownSec(int sec) {
		sec = sec % 60;

		if (sec < 10)
			return "0" + sec;
		else
			return sec + "";
	}
}