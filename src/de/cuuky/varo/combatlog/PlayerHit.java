package de.cuuky.varo.combatlog;

import de.cuuky.cfw.utils.listener.EntityDamageByEntityUtil;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlayerHit {

	/*
	 * OLD CODE
	 */

	public static class HitListener implements Listener {

		@EventHandler(priority = EventPriority.HIGHEST)
		public void onHit(EntityDamageByEntityEvent event) {
			Player damager = new EntityDamageByEntityUtil(event).getDamager();
			if (!(event.getEntity() instanceof Player) || damager == null)
				return;

			if (!Main.getVaroGame().isRunning() || event.isCancelled())
				return;

			VaroPlayer vp = VaroPlayer.getPlayer(event.getEntity().getName());
			VaroPlayer vp1 = VaroPlayer.getPlayer(damager);

			if (!vp1.getStats().isAlive() || vp1.isAdminIgnore())
				return;

			if (vp.getTeam() != null && vp1.getTeam() != null && vp.getTeam().equals(vp1.getTeam()))
				return;

			Date current = new Date();
			vp.getStats().setLastEnemyContact(current);
			vp1.getStats().setLastEnemyContact(current);

			if (!ConfigSetting.COMBATLOG_TIME.isIntActivated()) return;

			new PlayerHit(damager, vp.getPlayer());
			new PlayerHit(vp.getPlayer(), damager);
		}
	}

	private static final List<PlayerHit> hits = new ArrayList<>();

	private BukkitTask task;
	private final Player player, opponent;

	public PlayerHit(Player player, Player opponent) {
		VaroPlayer vp = VaroPlayer.getPlayer(player);

		this.player = player;
		this.opponent = opponent;

		scheduleOvertime();

		hits.add(this);
	}

	private void scheduleOvertime() {
		task = new BukkitRunnable() {
			@Override
			public void run() {
				over();
			}
		}.runTaskLater(Main.getInstance(), ConfigSetting.COMBATLOG_TIME.getValueAsInt() * 20L);
	}

	public boolean hasOld(Player p) {
		for (PlayerHit hit : hits) {
			if (!hit.getPlayer().equals(p))
				continue;

			hit.remove();
			return true;
		}

		return false;
	}

	public void over() {
		remove();
	}

	public void remove() {
		task.cancel();
		hits.remove(this);
	}

	public Player getOpponent() {
		return this.opponent;
	}

	public Player getPlayer() {
		return this.player;
	}

	public static PlayerHit getHit(Player p) {
		return hits.stream().filter(hit -> hit.getPlayer().equals(p)).findFirst().orElse(null);
	}
}