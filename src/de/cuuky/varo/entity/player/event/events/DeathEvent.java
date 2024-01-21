package de.cuuky.varo.entity.player.event.events;

import de.cuuky.cfw.utils.listener.EntityDamageByEntityUtil;
import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.event.BukkitEventType;
import de.cuuky.varo.entity.player.stats.stat.inventory.InventoryBackup;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class DeathEvent extends AbstractDeathEvent {

	public DeathEvent() {
		super(BukkitEventType.DEATH);
	}

	@Override
	public void onExec(VaroPlayer player) {
        Location location;
        if (player.isOnline()) {
            player.getStats().addInventoryBackup(new InventoryBackup(player));
            location = player.getPlayer().getLocation();
        } else location = player.getStats().getLastLocation();

		World world = location.getWorld();

		if (ConfigSetting.DEATH_LIGHTNING_EFFECT.getValueAsBoolean())
			world.strikeLightningEffect(location);

        this.dropInventory(Main.getDataManager().getListManager().getDeathItems().getItems().toArray(new ItemStack[0]), location);
	}
}