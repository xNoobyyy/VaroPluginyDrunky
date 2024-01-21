package de.cuuky.varo.listener.saveable;

import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.stats.stat.inventory.VaroSaveable;

public class EntityExplodeListener implements Listener {

	private boolean chestNearby(Location location) {
		for (int x = location.getBlockX() - 1; x <= location.getBlockX() + 1; x++) {
			for (int y = location.getBlockY() - 1; y <= location.getBlockY() + 1; y++) {
				for (int z = location.getBlockZ() - 1; z <= location.getBlockZ() + 1; z++) {
					Location loc = new Location(location.getWorld(), x, y, z);
					if (VaroSaveable.getByLocation(loc) != null)
						return true;
				}
			}
		}
		return false;
	}

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {

		if (!Main.getVaroGame().hasStarted()) {
			event.setCancelled(true);
			return;
		}

		if (ConfigSetting.NIKS_DUMMER_CREEPER_EXPLOSIONS_EFFECT_ENABLED.getValueAsBoolean()) {
			if (event.getEntityType() == EntityType.CREEPER) {
				event.setCancelled(true);
				event.getEntity().getWorld().playEffect(event.getLocation(), Effect.MOBSPAWNER_FLAMES, 3);
				for (Block block : event.blockList()) {
					block.getWorld().playEffect(block.getLocation(), Effect.MOBSPAWNER_FLAMES, 3);
					if (ThreadLocalRandom.current().nextInt(1, 4) == 1) {
						block.getDrops().forEach(d -> {
							block.getWorld().dropItem(block.getLocation(), d);
						});
					}
					block.setType(Material.AIR);
				}
			}
		}

		Iterator<Block> iter = event.blockList().iterator();
		while (iter.hasNext()) {
			Block block = iter.next();
			if (block.getType() == Material.CHEST || block.getType() == Material.FURNACE) {
				if (VaroSaveable.getByLocation(block.getLocation()) != null)
					iter.remove();
			} else if (block.getType().name().contains("SIGN"))
				if (chestNearby(block.getLocation()))
					iter.remove();
		}
	}
}