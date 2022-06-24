package de.cuuky.varo.listener.saveable;

import de.cuuky.cfw.version.BukkitVersion;
import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.stat.inventory.VaroSaveable;
import de.cuuky.varo.entity.player.stats.stat.inventory.VaroSaveable.SaveableType;
import org.bukkit.Bukkit;
import org.bukkit.block.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.InventoryHolder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class SignChangeListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onSignChange(SignChangeEvent event) {

		if (event.getPlayer().isOp())
			for (int i = 0; i < event.getLines().length; i++)
				event.setLine(i, event.getLines()[i].replace("&", "§"));

		if (!Main.getVaroGame().hasStarted())
			return;

		BlockFace attachedFace;

		if (VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_13) && VersionUtils.getVersion().isLowerThan(BukkitVersion.ONE_17)) {

			try {

				Object state = event.getBlock().getClass().getMethod("getState").invoke(event.getBlock());
				Object blockData = event.getBlock().getClass().getMethod("getBlockData").invoke(event.getBlock());
				if (Class.forName("org.bukkit.block.data.type.WallSign").isInstance(blockData)) {
					Method attachedFaceMethod = Class.forName("org.bukkit.block.data.type.WallSign").getMethod("getFacing");
					attachedFace = ((BlockFace) attachedFaceMethod.invoke(blockData)).getOppositeFace();
				} else {
					attachedFace = BlockFace.DOWN;
				}

			} catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException | ClassNotFoundException e) {
				throw new RuntimeException(e);
			}

		} else {
			attachedFace = VersionUtils.getVersionAdapter().getSignAttachedFace(event.getBlock());
		}
		
		if(attachedFace == null)
			throw new Error("attachedFace should not be null");
		
		Block attached = event.getBlock().getRelative(attachedFace);

		if (attached.getState() instanceof Chest) {
			Chest chest = (Chest) attached.getState();
			InventoryHolder ih = chest.getInventory().getHolder();
			Chest secChest = (ih instanceof DoubleChest ? (Chest) ((DoubleChest) ih).getLeftSide() : null);
			if (chest.equals(secChest) && secChest != null)
				secChest = (Chest) ((DoubleChest) ih).getRightSide();

			if (ConfigSetting.PLAYER_CHEST_LIMIT.getValueAsInt() == 0) {
				event.getPlayer().sendMessage(Main.getPrefix() + "§7Die Chestsicherung wurde in der Config §7deaktiviert!");
				return;
			}

			Player p = event.getPlayer();
			VaroPlayer player = VaroPlayer.getPlayer(p);
			ArrayList<VaroSaveable> teamSaves = VaroSaveable.getSaveable(player);
			ArrayList<VaroSaveable> sorted = new ArrayList<>();

			for (VaroSaveable saves : teamSaves) {
				if (saves.getType() == SaveableType.CHEST)
					sorted.add(saves);
			}

			if (sorted.size() >= ConfigSetting.PLAYER_CHEST_LIMIT.getValueAsInt() || secChest != null && sorted.size() + 1 >= ConfigSetting.PLAYER_CHEST_LIMIT.getValueAsInt()) {
				p.sendMessage(Main.getPrefix() + "§7Die maximale Anzahl an gesetzten Kisten fuer " + Main.getColorCode() + (player.getTeam() != null ? "das Team " + player.getTeam().getName() : "dich") + " §7wurde bereits §7erreicht! (Anzahl: §6" + sorted.size() + " §7Max: §6" + ConfigSetting.PLAYER_CHEST_LIMIT.getValueAsInt() + "§7)");
				event.setCancelled(true);
				return;
			}

			if (VaroSaveable.getByLocation(chest.getLocation()) != null || secChest != null && VaroSaveable.getByLocation(secChest.getLocation()) != null) {
				p.sendMessage(Main.getPrefix() + "§7Diese " + Main.getColorCode() + " Kiste §7ist bereits gesichert!");
				event.setCancelled(true);
				return;
			}

			if (secChest != null) {
				new VaroSaveable(SaveableType.CHEST, secChest.getLocation(), player);
			}

			new VaroSaveable(SaveableType.CHEST, chest.getLocation(), player);
			player.sendMessage(ConfigMessages.CHEST_SAVED_CHEST);
		} else if (attached.getState() instanceof Furnace) {
			Furnace furnace = (Furnace) attached.getState();

			if (ConfigSetting.PLAYER_FURNACE_LIMIT.getValueAsInt() == 0) {
				event.getPlayer().sendMessage(Main.getPrefix() + "§7Die Furnacesicherung wurde in der Config §7deaktiviert!");
				return;
			}

			Player p = event.getPlayer();
			VaroPlayer player = VaroPlayer.getPlayer(p);

			ArrayList<VaroSaveable> teamSaves = VaroSaveable.getSaveable(player);
			ArrayList<VaroSaveable> sorted = new ArrayList<>();

			for (VaroSaveable saves : teamSaves) {
				if (saves.getType() == SaveableType.FURNACE)
					sorted.add(saves);
				continue;
			}

			if (VaroSaveable.getByLocation(furnace.getLocation()) != null) {
				p.sendMessage(Main.getPrefix() + "§7Diese " + Main.getColorCode() + " Furnace §7ist bereits gesichert!");
				event.setCancelled(true);
				return;
			}

			if (ConfigSetting.PLAYER_FURNACE_LIMIT.isIntActivated())
				if (sorted.size() >= ConfigSetting.PLAYER_FURNACE_LIMIT.getValueAsInt()) {
					p.sendMessage(Main.getPrefix() + "§7Die maximale Anzahl an gesetzten Oefen fuer " + Main.getColorCode() + (player.getTeam() != null ? "das Team " + player.getTeam().getName() : "dich") + " §7wurde bereits §7erreicht! (Anzahl: §6" + sorted.size() + " §7Max: §6" + ConfigSetting.PLAYER_CHEST_LIMIT.getValueAsInt() + "§7)");
					event.setCancelled(true);
					return;
				}

			new VaroSaveable(SaveableType.FURNACE, furnace.getBlock().getLocation(), player);
		}
	}
}