package de.cuuky.varo.game.world.border.decrease;

import java.util.ArrayList;

import org.bukkit.scheduler.BukkitRunnable;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.game.world.VaroWorldHandler;

public class BorderDecrease {

	private static ArrayList<BorderDecrease> decreases;
	private static boolean running;

	static {
		decreases = new ArrayList<>();
		running = false;

		startShrinking();
	}

	private double amount, bps;
	private Runnable startHook, finishHook;

	public BorderDecrease(double amount, double bps) {
		this.amount = amount;
		this.bps = bps;

		decreases.add(this);
	}

	private void waitForBorder(double d) {
		try {
			Thread.sleep((long) (d * 1000) + 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void setBorderSizeSync(double size, long time) {
		VaroWorldHandler worldHandler = Main.getVaroGame().getVaroWorldHandler();
		worldHandler.setBorderSize(size, time, null);
	}

	public void shrink() {
		VaroWorldHandler worldHandler = Main.getVaroGame().getVaroWorldHandler();

		int minsize = ConfigSetting.MIN_BORDER_SIZE.getValueAsInt();
		double size = worldHandler.getBorderSize(null);
		if (size <= minsize) {
			Main.getLanguageManager().broadcastMessage(ConfigMessages.BORDER_MINIMUM_REACHED);
			remove();
			return;
		}

		if (startHook != null)
			startHook.run();
		if (minsize > 0)
			if ((int) (size - amount) < minsize) {
				this.setBorderSizeSync(minsize, (long) ((size - minsize) / bps));
				waitForBorder((size - minsize) / bps);
			} else {
				this.setBorderSizeSync(size - amount, (long) (amount / bps));
				waitForBorder(amount / bps);
			}

		if (finishHook != null)
			finishHook.run();
		remove();
	}

	public void remove() {
		decreases.remove(this);
	}

	public double getBps() {
		return bps;
	}

	public void setFinishHook(Runnable finishHook) {
		this.finishHook = finishHook;
	}

	public void setStartHook(Runnable startHook) {
		this.startHook = startHook;
	}

	private static void startShrinking() {
		new BukkitRunnable() {
			@Override
			public void run() {
				if (running)
					return;

				for (int i = 0; i < decreases.size(); i++) {
					running = true;
					decreases.get(i).shrink();
				}

				running = false;
			}
		}.runTaskTimerAsynchronously(Main.getInstance(), 1L, 20L);
	}
}