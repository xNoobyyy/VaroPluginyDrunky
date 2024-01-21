package de.cuuky.varo.listener;

import de.cuuky.varo.entity.player.stats.stat.PlayerState;
import de.cuuky.varo.game.state.GameState;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.VaroPlayer;

public class PlayerRespawnListener implements Listener {

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		VaroPlayer vp = VaroPlayer.getPlayer(event.getPlayer());
		new BukkitRunnable() {
			@Override
			public void run() {
				vp.setNormalAttackSpeed();
			}
		}.runTaskLater(Main.getInstance(), 20L);
		if (Main.getVaroGame().getGameState() == GameState.STARTED) {
			vp.getStats().setState(PlayerState.SPECTATOR);
			Location tpLoc = Main.getVaroGame().getVaroWorldHandler().getMainWorld().getWorld().getSpawnLocation();
			tpLoc.setY(tpLoc.getWorld().getHighestBlockYAt(tpLoc) + 50);
			vp.update();
			new BukkitRunnable() {
				@Override
				public void run() {
					vp.getPlayer().setGameMode(GameMode.SPECTATOR);
				}
			}.runTaskLater(Main.getInstance(), 1);
		}
	}
}
