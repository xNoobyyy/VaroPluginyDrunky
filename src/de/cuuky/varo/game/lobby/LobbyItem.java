package de.cuuky.varo.game.lobby;

import java.util.ArrayList;
import java.util.List;

import de.cuuky.cfw.hooking.hooks.chat.ChatHook;
import de.cuuky.cfw.hooking.hooks.chat.ChatHookHandler;
import de.cuuky.varo.entity.team.VaroTeam;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.cuuky.cfw.hooking.hooks.item.ItemHook;
import de.cuuky.cfw.hooking.hooks.item.ItemHookHandler;
import de.cuuky.cfw.utils.item.BuildItem;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.team.request.VaroTeamRequest;
import de.cuuky.varo.game.state.GameState;

public class LobbyItem {

	private static List<ItemHook> lobbyItems = new ArrayList<>();

	private static void hookItem(ItemHook hook) {
		lobbyItems.add(hook);
		Main.getCuukyFrameWork().getHookManager().registerHook(hook);

		hook.setDragable(false);
		hook.setDropable(false);
	}

	public static void giveItems(Player player) {
		if (!ConfigSetting.TEAMREQUEST_ENABLED.getValueAsBoolean() || !ConfigSetting.TEAMREQUEST_LOBBYITEMS.getValueAsBoolean() || Main.getVaroGame() == null || Main.getVaroGame().getGameState() != GameState.LOBBY)
			return;
		
		VaroPlayer varoPlayer = VaroPlayer.getPlayer(player);

		hookItem(new ItemHook(varoPlayer.getPlayer(), new BuildItem().displayName(ChatColor.RED + "Team verlassen").itemstack(Materials.RED_DYE.parseItem())
				.lore(ChatColor.GRAY + "Rechtsklicke dieses Item, um\n" +
						ChatColor.GRAY + "dein aktuelles Team wieder \n" +
						ChatColor.GRAY + "zu verlassen!\n" +
						" ").build(), 6, new ItemHookHandler() {

			@Override
			public void onInteractEntity(PlayerInteractEntityEvent event) {}

			@Override
			public void onInteract(PlayerInteractEvent event) {
				if (Main.getVaroGame().getGameState() != GameState.LOBBY)
					return;

				VaroPlayer player = VaroPlayer.getPlayer(event.getPlayer());
				if (player.getTeam() != null) {
					player.getTeam().removeMember(player);
					player.sendMessage(Main.getPrefix() + "Team erfolgreich verlassen!");
				} else
					player.sendMessage(Main.getPrefix() + "Du bist in keinem Team!");

				event.setCancelled(true);
			}

			@Override
			public void onEntityHit(EntityDamageByEntityEvent event) {}
		}));

		hookItem(new ItemHook(varoPlayer.getPlayer(), new BuildItem()
				.lore(ChatColor.GRAY + "Schlage einen einen anderen\n" +
						ChatColor.GRAY + "Spieler mit diesem Item, um\n" +
						ChatColor.GRAY + "ihn in dein Team einzuladen!\n" +
						" ")
				.itemstack(Materials.BLAZE_ROD.parseItem()).displayName(ChatColor.GREEN + "Team erstellen").build(), 2, new ItemHookHandler() {

			@Override
			public void onInteractEntity(PlayerInteractEntityEvent event) {}

			@Override
			public void onInteract(PlayerInteractEvent event) {}

			@Override
			public void onEntityHit(EntityDamageByEntityEvent event) {
				if (Main.getVaroGame().getGameState() != GameState.LOBBY)
					return;

				Player hitted = (Player) event.getEntity();
				Player damager = (Player) event.getDamager();

				if (VaroTeamRequest.getByAll(VaroPlayer.getPlayer(hitted), VaroPlayer.getPlayer(damager)) != null)
					damager.performCommand("varo tr accept " + hitted.getName());
				else
					damager.performCommand("varo tr invite " + hitted.getName());

				event.setCancelled(true);
				damager.getItemInHand().setDurability((short) 0);
			}
		}));

		hookItem(new ItemHook(varoPlayer.getPlayer(), new BuildItem()
				.lore(ChatColor.GRAY + "Rechtsklicke dieses Item, um\n" +
						ChatColor.GRAY + "deinen aktuellen Teamnamen\n" +
						ChatColor.GRAY + "zu ändern!\n" +
						" ")
				.itemstack(Materials.NAME_TAG.parseItem()).displayName(ChatColor.AQUA + "Teamname ändern").build(), 4, new ItemHookHandler() {

			@Override
			public void onInteractEntity(PlayerInteractEntityEvent event) {}

			@Override
			public void onInteract(PlayerInteractEvent event) {
				if (Main.getVaroGame().getGameState() != GameState.LOBBY)
					return;

				VaroPlayer vp = VaroPlayer.getPlayer(event.getPlayer());
				if (vp.getTeam() != null) {
					Main.getCuukyFrameWork().getHookManager().registerHook(new ChatHook(event.getPlayer(), ConfigMessages.TEAMREQUEST_ENTER_TEAMNAME.getValue(null, vp), new ChatHookHandler() {

						@Override
						public boolean onChat(AsyncPlayerChatEvent event) {
							String message = event.getMessage();
							if (message.equalsIgnoreCase("cancel")) {
								vp.sendMessage(Main.getPrefix() + "§7Aktion erfolgreich abgebrochen!");
							} else {
								if (!message.matches(VaroTeam.NAME_REGEX)) {
									vp.sendMessage(Main.getPrefix() + ConfigMessages.TEAM_NAME_INVALID.getValue());
									return false;
								}
								vp.sendMessage(Main.getPrefix() + Main.getColorCode() + "Teamname " + ChatColor.GRAY + "erfolgreich geändert");
								vp.getTeam().setName(message);
							}
							return true;
						}
					}));
					vp.sendMessage(Main.getPrefix() + "§7Gib zum Abbruch §ccancel§7 ein.");
				} else {
					varoPlayer.sendMessage(Main.getPrefix() + "Du bist in keinem Team!");
				}

				event.setCancelled(true);
			}

			@Override
			public void onEntityHit(EntityDamageByEntityEvent event) { }
		}));
	}

	public static void removeHooks() {
		lobbyItems.forEach(ItemHook::unregister);
	}
}