package de.cuuky.varo.configuration.configurations.language.languages;

import de.cuuky.cfw.configuration.language.Language;
import de.cuuky.cfw.configuration.language.languages.DefaultLanguage;
import de.cuuky.cfw.player.CustomPlayer;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;

public enum ConfigMessages implements DefaultLanguage {

	FIRST_BLOOD("firstblood", "%prefix%&4&lFIRST BLOOD!"),
	DEACTIVATED_PORTAL("deactivatedPortal", "&cPortale sind momentan deaktiviert!"),
	TEAMCHEST_TEAM_DIED("teamchestTeamDied", "&3Die Teamchests von &e%team% &3wurden entsichert."),
	TEAMCHEST_OPENED("teamchestOpened", "&3Du hast die Teamchest deines Teams geöffnet!"),
	ALERT_AUTOSTART_AT("alerts.BOTS_ALERT.autostartAt", "%projectname% wird am %date% starten!"),
	ALERT_BORDER_CHANGED("alerts.BOTS_ALERT.borderChanged", "%null%"),
	ALERT_BORDER_DECREASED_DEATH("alerts.BOTS_ALERT.borderDecrease.death", "%null%"),
	ALERT_BORDER_DECREASED_TIME_DAYS("alerts.BOTS_ALERT.borderDecrease.days", "%null%"),
	ALERT_BORDER_DECREASED_TIME_MINUTE("alerts.BOTS_ALERT.borderDecrease.minutes", "%null%"),
	ALERT_COMBAT_LOG("alerts.BOTS_ALERT.combatlog", "%null%"),
	ALERT_COMBAT_LOG_STRIKE("alerts.BOTS_ALERT.combatlogStrike", "%null%"),
	ALERT_DISCONNECT_TOO_OFTEN("alerts.BOTS_ALERT.disconnectTooOften", "%player% hat das Spiel zu oft verlassen, weswegen seine Session entfernt wurde!"),
	ALERT_DISCORD_DEATH("alerts.BOTS_ALERT.death", "%player% ist gestorben! Grund: %reason%"),
	ALERT_DISCORD_KILL("alerts.BOTS_ALERT.kill", "%player% wurde von %killer% getötet!"),
	ALERT_FIRST_STRIKE("alerts.BOTS_ALERT.firstStrike", "%player% hat nun einen Strike. Der Strike wurde von %striker% gegeben. Begründung: %reason%\nAufgrund dessen sind hier die derzeiten Koordinaten: %pos%!"),
	ALERT_FIRST_STRIKE_NEVER_ONLINE("alerts.BOTS_ALERT.firstStrikeNeverOnline", "%player% hat nun einen Strike. Der Strike wurde von %striker% gegeben. Begründung: %reason%\nDer Spieler war noch nicht online und wird an den Spawn-Koordinaten spawnen: %pos%!"),
	ALERT_GAME_STARTED("alerts.BOTS_ALERT.gameStarted", "%projectname% wurde gestartet!"),
	ALERT_GENERAL_STRIKE("alerts.BOTS_ALERT.generalStrike", "%player% hat nun den %strikeNumber%ten Strike! Der Strike wurde von %striker% gegeben. Begründung: %reason%"),
	ALERT_JOIN_FINALE("alerts.BOTS_ALERT.finale", "%player% &3hat den Server zum Finale betreten."),
	ALERT_KICKED_PLAYER("alerts.BOTS_ALERT.kickedPlayer", "%player% wurde gekickt!"),
	ALERT_SESSIONS_ENDED("alerts.BOTS_ALERT.sessionsEnded", "%player%'s Session wurde beendet, da der Spieltag vorrüber ist!"),
	ALERT_NEW_SESSIONS("alerts.BOTS_ALERT.newSessions", "Es wurden %newSessions% neue Folgen an die Spieler gegeben!"),
	ALERT_NEW_SESSIONS_FOR_ALL("alerts.BOTS_ALERT.newSessionsForAll", "Alle haben %newSessions% neue Folgen bekommen!"),
	ALERT_NO_BLOODLUST("alerts.BOTS_ALERT.noBloodlust", "%player% hat nun %days% Tage nicht gekämpft, was das Limit überschritten hat!"),
	ALERT_NO_BLOODLUST_STRIKE("alerts.BOTS_ALERT.noBloodlustStrike", "%player% hat nun %days% Tage nicht gekämpft, weswegen %player% jetzt gestriket wurde!"),
	ALERT_NOT_JOIN("alerts.BOTS_ALERT.notJoin", "%player% war nun %days% Tage nicht online, was das Limit überschritten hat!"),
	ALERT_NOT_JOIN_STRIKE("alerts.BOTS_ALERT.notJoinStrike", "%player% war nun %days% Tage nicht online, weswegen %player% jetzt gestriket wurde!"),
	ALERT_PLAYER_DC_TO_EARLY("alerts.BOTS_ALERT.playerQuitToEarly", "%player% hat das Spiel vorzeitig verlassen! %player% hat noch %seconds% Sekunden Spielzeit über!"),
	ALERT_PLAYER_JOIN_MASSREC("alerts.BOTS_ALERT.playerJoinMassrec", "%player% hat den Server in der Massenaufnahme betreten und spielt nun die %episodesPlayedPlus1%te Folge"),
	ALERT_PLAYER_JOIN_NORMAL("alerts.BOTS_ALERT.playerJoinNormal", "%player% hat das Spiel betreten!"),
	ALERT_PLAYER_JOINED("alerts.BOTS_ALERT.playerJoined", "%player% hat den Server betreten und spielt nun die %episodesPlayedPlus1%te Folge!"),
	ALERT_PLAYER_QUIT("alerts.BOTS_ALERT.playerQuit", "%player% hat das Spiel verlassen!"),
	ALERT_PLAYER_RECONNECT("alerts.BOTS_ALERT.playerReconnect", "%player% hatte das Spiel vorzeitig verlassen und ist rejoint! %player% hat noch %seconds% Sekunden verbleibend!"),
	ALERT_SECOND_STRIKE("alerts.BOTS_ALERT.secondStrike", "%player% hat nun zwei Strikes. Der Strike wurde von %striker% gegeben. Begründung: %reason%\nAufgrund dessen wurde das Inventar geleert!"),
	ALERT_SWITCHED_NAME("alerts.BOTS_ALERT.switchedName", "%player% hat den Namen gewechselt und ist nun unter %newName% bekannt!"),
	ALERT_TELEPORTED_TO_MIDDLE("alerts.BOTS_ALERT.teleportedToMiddle", "%player% wurde zur Mitte teleportiert!"),
	ALERT_THRID_STRIKE("alerts.BOTS_ALERT.thirdStrike", "%player% hat nun drei Strikes. Der Strike wurde von %striker% gegeben. Begründung: %reason%\nDamit ist %player% aus %projectname% ausgeschieden!"),
	ALERT_WINNER("alerts.BOTS_ALERT.win.player", "%player% hat %projectname% gewonnen! Gratulation!"),
	ALERT_WINNER_TEAM("alerts.BOTS_ALERT.win.team", "%winnerPlayers% haben %projectname% gewonnen! Gratulation!"),

	BOTS_DISCORD_NOT_REGISTERED_DISCORD("bots.notRegisteredDiscord", "%colorcode%Du bist noch nicht mit dem Discord authentifiziert!\n&3Um dich zu authentifizieren, schreibe in den #verify -Channel %colorcode%'varo verify <Deine ID>' &3auf dem Discord!\nLink zum Discordserver: %colorcode%%discordLink%\n&3Deine ID lautet: %colorcode%%code%"),
	BOTS_DISCORD_NO_SERVER_USER("bots.noServerUser", "%colorcode%Dein Account ist nicht auf dem Discord!%nextLine%&3Joine dem Discord und versuche es erneut."),

	BORDER_MINIMUM_REACHED("border.minimumReached", "%colorcode%Die Border hat ihr Minimum erreicht!"),
	BORDER_DECREASE_DAYS("border.decreaseDays", "&3Die Border wird jetzt um %colorcode%%size% &3Bloecke mit %colorcode%%speed% &3Bloecken/s verkleinert. &3Naechste Verkleinerung in %colorcode%%days% &3Tagen!"),
	BORDER_DECREASE_DEATH("border.decreaseDeath", "&3Die Border wird jetzt um %colorcode%%size% &3Bloecke mit %colorcode%%speed% &3Bloecken/s aufgrund eines Todes verkleinert."),
	BORDER_MINUTE_TIME_UPDATE("border.minuteTimeUpdate", "&3Die Border wird in %colorcode%%minutes%&3:%colorcode%%seconds% &3verkleinert!"),
	BORDER_DECREASE_MINUTES("border.decreaseMinutes", "&3Die Border wird jetzt um %colorcode%%size% &3Bloecke mit %colorcode%%speed% &3Bloecken/s verkleinert. &3Naechste Verkleinerung in %colorcode%%days% &3Minuten!"),
	BORDER_DISTANCE("border.distanceToBorder", "&3Distanz zur Border: %colorcode%%size% &3Bloecke"),
	BORDER_COMMAND_SET_BORDER("border.borderSet", "&3Die Border wurde auf %colorcode%%size% &3gesetzt!"),

	CHAT_PLAYER_WITH_TEAM("chat.format.withTeam", "%colorcode%%team% &8| &7%player%&8: &f%message%"),
	CHAT_PLAYER_WITH_TEAM_RANK("chat.format.withTeamAndRank", "%colorcode%%team% &8| &7%player%&8: &f%message%"),
	CHAT_PLAYER_WITHOUT_TEAM("chat.format.withoutTeam", "%colorcode%-Teamlos- &8| &7%player%&8: &f%message%"),
	CHAT_PLAYER_WITHOUT_TEAM_RANK("chat.format.withoutTeamWithRank", "&4Admin &4| &4%player%&4: &f%message%"),

	CHAT_TEAMCHAT_FORMAT("chat.teamchatFormat", "&3[%team%&3] %from% &8» &f%message%"),
	CHAT_MUTED("chat.muted", "&3Du wurdest gemutet!"),
	CHAT_WHEN_START("chat.chatOnStart", "&3Du kannst erst ab dem Start wieder schreiben!"),

	COMBAT_FRIENDLY_FIRE("combat.friendlyfire", "&3Dieser Spieler ist in deinem Team!"),
	COMBAT_IN_FIGHT("combat.inFight", "&3Du bist nun im Kampf, logge dich &4NICHT &3aus!"),
	COMBAT_LOGGED_OUT("combat.loggedOut", "%colorcode%%player% &3hat den Server waehrend eines Kampfes verlassen!"),
	COMBAT_NOT_IN_FIGHT("combat.notInFight", "&3Du bist nun nicht mehr im %colorcode%Kampf&3!"),

	SPAWN_WORLD("spawn.spawn", "%colorcode%Koordinaten&3 vom Spawn: %colorcode%%x%&3, %colorcode%%y%&3, %colorcode%%z%"),
	SPAWN_NETHER("spawn.spawnNether", "%colorcode%Koordinaten&3 vom Portal zur Oberwelt: %colorcode%%x%&3, %colorcode%%y%&3, %colorcode%%z%"),
	SPAWN_DISTANCE("spawn.spawnDistance", "&3Du bist %colorcode%%distance% &3Bloecke vom Spawn entfernt!"),
	SPAWN_DISTANCE_NETHER("spawn.spawnDistanceNether", "&3Du bist %colorcode%%distance% &3Bloecke vom Portal zur Oberwelt entfernt!"),

	DEATH_DEAD("death.killMessage", "%colorcode%%player% &7ist gestorben."),
	DEATH_KILLED_BY("death.killed", "%colorcode%%player% &7wurde von %colorcode%%killer% &7getoetet."),
	DEATH_LIFE_LOST("death.teamLifeLost", "%player% hat nun noch %colorcode%%teamLifes% &3Teamleben!"),
	DEATH_RESPAWN_PROTECTION("death.respawnProtection", "%colorcode%%player% hat nun ein Leben weniger und ist fuer %seconds% unverwundbar!"),
	DEATH_RESPAWN_PROTECTION_OVER("death.respawnProtectionOver", "%colorcode%%player% ist nun wieder verwundbar!"),
	DEATH_KILL_LIFE_ADD("death.killLifeAdd", "Dein Team hat aufgrund eines Kills ein Teamleben erhalten!"),
	DEATH_KILL_TIME_ADD("death.killTimeAdd", "Aufgrund deines Kills hast du zusätzlich %colorcode%%timeAdded% &7Sekunden Zeit erhalten!"),

	GAME_START_COUNTDOWN("game.start.startCountdown", "%prefix%&3%projectname% &3startet in &e%countdown% Sekunden&3."),
	GAME_VARO_START("game.start.varoStart", "%projectname% &3wurde gestartet! &5Viel Erfolg!"),
	GAME_WIN("game.win.single", "'%prefix%&2&lDer Spieler &e&l%player%&8&l(&6&l7&8&l) &2&lhat das VARO-Projekt gewonnen."),
	GAME_WIN_TEAM("game.win.team", "%prefix%&2&lDie Spieler &e&l%winnerPlayers%&8&l(&6&l7&8&l) &2&lhaben das VARO-Projekt gewonnen."),

	JOIN_MESSAGE("joinmessage.join", "&a+ %colorcode%%player% &3hat den Server &abetreten&3."),
	JOIN_PLAYERS_REQUIRED("joinmessage.requiredplayers", "%prefix%Es werden noch %required% Spieler zum Start benötigt!"),
	JOIN_FINALE("joinmessage.finale", "&a+ %colorcode%%player% &3hat den Server &abetreten&3."),
	JOIN_FINALE_PLAYER("joinmessage.finaleplayer", "%prefix%Das Finale beginnt bald. Bis zum Finalestart wurden alle gefreezed."),
	JOIN_MASS_RECORDING("joinmessage.massrecording", "%prefix%&a%player% &3hat den Server in der Massenaufnahme betreten und ist in %colorcode%%protectionTime% &3Sekunden angreifbar!"),
	JOIN_NO_MOVE_IN_PROTECTION("joinmessage.notMoveinProtection", "&3Du kannst dich nicht bewegen, solange du noch in der %colorcode%Schutzzeit &3bist!"),
	JOIN_PROTECTION_OVER("joinmessage.joinProtectionOver", "%prefix%&a%player% &3ist nun angreifbar!"),
	JOIN_PROTECTION_TIME("joinmessage.joinProtectionTime", "&a+ %colorcode%%player% &3hat den Server &abetreten&3. %nextLine%%prefix%&7Die Schutzzeit von &7%player% &7ist in &7%protectionTime% &7Sekunden vorbei."),
	JOIN_SPECTATOR("joinmessage.spectator", "&a+ %colorcode%%player% &3hat den Server &abetreten&3."),
	JOIN_WITH_REMAINING_TIME("joinmessage.joinWithRemainingTime", "&a+ %colorcode%%player% &3hat den Server &abetreten&3."),

	QUIT_MESSAGE("quitmessage.quit", "&4- %colorcode%%player% &3hat den Server &cverlassen&3."),
	QUIT_DISCONNECT_SESSION_END("quitmessage.disconnectKilled", "%null%"),
	QUIT_SPECTATOR("quitmessage.spectator", "&4- %colorcode%%player% &3hat den Server &cverlassen&3."),
	QUIT_TOO_OFTEN("quitmessage.quitTooOften", "%null%"),
	QUIT_WITH_REMAINING_TIME("quitmessage.quitRemainingTime", "&4- %colorcode%%player% &3hat den Server &cverlassen&3."),
	QUIT_KICK_BROADCAST("quitmessage.broadcast", "%null%"),
	QUIT_KICK_DELAY_OVER("quitmessage.protectionOver", "%null%"),
	QUIT_KICK_IN_SECONDS("quitmessage.kickInSeconds", "%null%"),
	QUIT_KICK_PLAYER_NEARBY("quitmessage.noKickPlayerNearby", "&cDu besitzt den CombatLog-Status. Du wirst automatisch gekickt, wenn dieser entfernt wird."),
	QUIT_KICK_SERVER_CLOSE_SOON("quitmessage.serverCloseSoon", "&3Der Server schliesst in %colorcode%%minutes% &3Minuten!"),

	DEATH_KICK_DEAD("kick.kickedKilled", "&cDu bist ausgeschieden!"),
	DEATH_KICK_KILLED("kick.killedKick", "&cDu bist ausgeschieden!"),
	JOIN_KICK_NOT_USER_OF_PROJECT("kick.notUserOfTheProject", "&fYou are not white-listed on this server!"),
	JOIN_KICK_SERVER_FULL("kick.serverFull", "%colorcode%Der Server ist voll!%nextLine%&3Sprich mit dem Owner, falls das das ein Irrtum sein sollte!"),
	JOIN_KICK_STRIKE_BAN("kick.strikeBan", "%colorcode%Du wurdest aufgrund deines letzten Strikes fuer %hours% gebannt!\nVersuche es spaeter erneut"),
	JOIN_KICK_BANNED("kick.banned", "&4Du bist vom Server gebannt!\n&3Melde dich bei einem Admin, falls dies ein Fehler sein sollte.\n&3Grund: %colorcode%%reason%"),
	JOIN_KICK_NO_PREPRODUCES_LEFT("kick.noPreproduceLeft", "%colorcode%Du hast bereits vorproduziert! %nextLine%&3Versuche es morgen erneut."),
	JOIN_KICK_NO_SESSIONS_LEFT("kick.noSessionLeft", "&cDu hast keine Episoden mehr zur Verfuegung!"),
	JOIN_KICK_NO_TIME_LEFT("kick.noTimeLeft", "%colorcode%Du darfst nur alle &4%timeHours% %colorcode%Stunden regulaer spielen! %nextLine%&3Du kannst erst in %colorcode%%stunden%&3:%colorcode%%minuten%&3:%colorcode%%sekunden% &3wieder joinen!"),
	JOIN_KICK_NOT_STARTED("kick.notStarted", "%colorcode%Der Server wurde noch nicht eroeffnet! %nextLine%&3Gedulde dich noch ein wenig!"),
	KICK_SESSION_OVER("kick.kickMessage", "&cDeine Aufnahme-Zeit ist vorbei!"),
	KICK_MASS_REC_SESSION_OVER("kick.kickMessageMassRec", "&cDeine Aufnahme-Zeit ist vorbei!"),
	KICK_TOO_MANY_STRIKES("kick.tooManyStrikes", "&3Du hast zu viele Strikes bekommen und wurdest daher aus dem Projekt %projectname% &3entfernt."),
	KICK_COMMAND("kick.kick", "%colorcode%%player% &3wurde gekick.kickt!"),

	LABYMOD_DISABLED("labymod.labyModDisabled", "&3Alle deine LabyMod Funktionen wurden deaktiviert!"),
	LABYMOD_KICK("labymod.labyMod", "%colorcode%LabyMod isn't allowed on this server."),

	SERVER_MODT_CANT_JOIN_HOURS("motd.cantJoinHours", "%colorcode%Du kannst nur zwischen &4%minHour% %colorcode%und &4%maxHour%%colorcode% Uhr joinen! %nextLine%&3Versuche es spaeter erneut! &3%currHour%&3:&3%currMin%&3:&3%currSec%"),
	SERVER_MODT_NOT_OPENED("motd.serverNotOpened", "%colorcode%Der Server wurde noch nicht fuer alle geoeffnet! %nextLine%&3Versuche es spaeter erneut!"),
	SERVER_MODT_OPEN("motd.serverOpen", "&aSei nun bei %projectname% &adabei! \n&3Viel Spass!"),

	NAMETAG_NORMAL("nametag.normalNametagPrefix", "&7"),
	NAMETAG_SUFFIX("nametag.normalSuffix", "&c %kills%"),
	NAMETAG_TEAM_PREFIX("nametag.nametagWithTeam", "%colorcode%%team% &7"),

	CHEST_NOT_TEAM_CHEST("chest.notTeamChest", "%prefix%&7Diese Chest gehoert &c%team%&7! %nextLine%&7Dein Team kann auf diese Chest nicht zugreifen."),
	CHEST_NOT_TEAM_FURNACE("chest.notTeamFurnace", "%prefix%&7Dieser Furnace gehoert &c%team%&7! %nextLine%&7Dein Team kann auf diesen Furnace nicht zugreifen."),
	CHEST_REMOVED_SAVEABLE("chest.removedChest", "%prefix%&7Teamchest erfolgreich entfernt."),
	CHEST_SAVED_CHEST("chest.newChestSaved", "%prefix%&7Neue Teamchest erfolgreich erstellt."),
	CHEST_SAVED_FURNACE("chest.newFurnaceSaved", "%prefix%&7Neuer Teamofen erfolgreich erstellt."),

	NOPERMISSION_NO_PERMISSION("nopermission.noPermission", "&7[%colorcode%projectname&7] &cDu hast keine Permissions fuer diesen Befehl."),
	NOPERMISSION_NOT_ALLOWED_CRAFT("nopermission.notAllowedCraft", "&cDiese Aktion ist dir untersagt!"),
	NOPERMISSION_NO_LOWER_FLIGHT("nopermission.noLowerFlight", "%null%"),

	PROTECTION_NO_MOVE_START("protection.noMoveStart", "&3Das Projekt wurde noch nicht gestartet!"),
	PROTECTION_START("protection.start", "%prefix%&aDie Schutzzeit ist in &2%seconds% Sekunden &avorbei."),
	PROTECTION_TIME_OVER("protection.protectionOver", "%prefix%&aDie Schutzzeit ist nun &czuende&a."),
	PROTECTION_TIME_UPDATE("protection.protectionUpdate", "%prefix%&aDie Schutzzeit ist in &2%minutes%:%seconds% &2Minuten &avorbei."),
	PROTECTION_TIME_RUNNING("protection.timeRunning", "&3Die %colorcode%Schutzzeit &3laeuft noch!"),

	SORT_NO_HOLE_FOUND("sort.noHoleFound", "&3%null%"),
	SORT_NO_HOLE_FOUND_TEAM("sort.noHoleFoundTeam", "%null%"),
	SORT_NUMBER_HOLE("sort.numberHoleTeleport", "%null%"),
	SORT_OWN_HOLE("sort.ownHoleTeleport", "%null%"),
	SORT_SPECTATOR_TELEPORT("sort.spectatorTeleport", "%null%"),
	SORT_SORTED("sort.sorted", "%null%"),

	TABLIST_PLAYER_WITH_TEAM("tablist.player.withTeam", "%colorcode%%team% &8| &7%player%"),
	TABLIST_PLAYER_WITH_TEAM_RANK("tablist.player.withTeamAndRank", "%colorcode%%team% &8| &4%player%"),
	TABLIST_PLAYER_WITHOUT_TEAM("tablist.player.withoutTeam", "%colorcode%-Teamlos- &8| &7%player%"),
	TABLIST_PLAYER_WITHOUT_TEAM_RANK("tablist.player.withoutTeamWithRank", "&4Admin &4| &4%player%"),

	TEAM_NAME_INVALID("team.name.invalid", "%prefix%&cUngültiger Name!"),
    TEAM_NAME_DUPLICATE("team.name.duplicate", "%prefix%&cDieser Name ist bereits vergeben!"),
    TEAM_NAME_TOO_LONG("team.name.tooLong", "Dein Teamname darf maximal %colorcode%%maxLength% &3Zeichen enthalten!"),
    TEAM_RENAME("team.rename", "%prefix%%colorcode%&lBitte gib einen neuen Teamnamen ein"),
    TEAM_RENAMED("team.renamed", "%prefix%&7Das Team %colorcode%%teamId% &7hat seinen namen zu %colorcode%%newName% &7geändert"),
	
	TEAMREQUEST_ENTER_TEAMNAME("teamrequest.enterTeamName", "%prefix%%colorcode%&lGib jetzt den Teamnamen für dich und %invited% ein:"),
	TEAMREQUEST_PLAYER_NOT_ONLINE("teamrequest.playerNotOnline", "%colorcode%%invitor% ist nicht mehr online!"),
	TEAMREQUEST_REVOKED("teamrequest.invationRevoked", "Einladung erfolgreich zurückgezogen!"),
	TEAMREQUEST_TEAM_FULL("teamrequest.teamIsFull", "%invited% konnte dem Team nicht beitreten - es ist bereits voll."),
	TEAMREQUEST_TEAM_REQUEST_RECIEVED("teamrequest.teamRequestRecieved", "%colorcode%%invitor% &3hat dich in ein Team eingeladen (/varo tr)!"),
	TEAMREQUEST_INVITED_TEAM("teamrequest.invitedInTeam", "&3Du hast %colorcode%%invited% &3in das Team %colorcode%%team% &3eingeladen!"),
	TEAMREQUEST_NO_TEAMNAME("teamrequest.noteamname", "&3Du hast noch &3keinen &3Teamnamen!"),
	TEAMREQUEST_LOBBYITEM_INVITE_NAME("teamRequest.items.invite.name", "&6Spieler Einladen"),
	TEAMREQUEST_LOBBYITEM_INVITE_LORE("teamRequest.items.invite.lore", "&7Schlage einen Spieler um ihn\n&7in dein Team einzuladen"),
	TEAMREQUEST_LOBBYITEM_LEAVE_NAME("teamRequest.items.leave.name", "&cTeam Verlassen"),
	TEAMREQUEST_LOBBYITEM_LEAVE_LORE("teamRequest.items.leave.lore", "&7Schlage einen anderen\n&7Spieler mit diesem Item, um\n&7ihn in dein Team einzuladen\n "),
	TEAMREQUEST_LOBBYITEM_RENAME_NAME("teamRequest.items.rename.name", "&cTeam Umbenennen"),
	TEAMREQUEST_LOBBYITEM_RENAME_LORE("teamRequest.items.rename.lore", ""),

	VARO_COMMANDS_HELP_HEADER("varoCommands.help.header", "&3-------- %colorcode%%category% &3-------"),
	VARO_COMMANDS_HELP_FOOTER("varoCommands.help.footer", "&3------------------------"),

	VARO_COMMANDS_ERROR_USER_NOT_FOUND("varoCommands.error.usernotfound", "&3Es konnte kein User für diesen Spieler gefunden werden!"),

	VARO_COMMANDS_ERROR_UNKNOWN_PLAYER("varoCommands.error.unknownplayer", "&3Der Spieler %colorcode%%player% &3hat den Server noch nie betreten!"),
	VARO_COMMANDS_ERROR_NO_CONSOLE("varoCommands.error.noconsole", "Du musst ein Spieler sein!"),
	VARO_COMMANDS_ERROR_NOT_STARTED("varoCommands.error.notstarted", "Das Spiel wurde noch nicht gestartet!"),
	VARO_COMMANDS_ERROR_USAGE("varoCommands.error.usage", "%colorcode%Fehler! &3Nutze %colorcode%/varo %command% &3fuer Hilfe."),
	VARO_COMMANDS_ERROR_NO_NUMBER("varoCommands.error.nonumber", "%colorcode%%text% &3ist keine Zahl!"),
	VARO_COMMANDS_ERROR_WRONGVERSION("varoCommands.error.wrongVersion", "&3Dieses Feature ist vor der Version %colorcode%%version% &3nicht verfügbar!"),
	VARO_COMMANDS_ERROR("varoCommands.error.error", "&3Es ist ein Fehler aufgetreten!"),

	VARO_COMMANDS_BUGREPORT_CREATED("varoCommands.bugreport.created", "Bugreport wurde unter %colorcode%%filename% &3gespeichert! Bitte lade die Datei auf unserem Discord hoch: " + Main.DISCORD_INVITE),
	VARO_COMMANDS_BUGREPORT_SEND_TO_DISCORD("varoCommands.bugreport.sendtodiscord", "Bitte sende den Bugreport als DATEI manuell auf den Discord in den Support, da das Hochladen nicht funktioniert hat!"),
	VARO_COMMANDS_BUGREPORT_OUTDATED_VERSION("varoCommands.bugreport.outdatedversion", "Du kannst keine Bugreports von einer alten Plugin-Version machen!"),
	VARO_COMMANDS_BUGREPORT_CURRENT_VERSION("varoCommands.bugreport.currentversion", "Derzeitige Version: %colorcode%%version%"),
	VARO_COMMANDS_BUGREPORT_NEWEST_VERSION("varoCommands.bugreport.newestversion", "Neueste Version: &a%version%"),
	VARO_COMMANDS_BUGREPORT_UPDATE("varoCommands.bugreport.update", "&3Nutze %colorcode%/varo update &3zum updaten."),
	VARO_COMMANDS_BUGREPORT_COLLECTING_DATA("varoCommands.bugreport.collectingdata", "Daten werden gesammelt..."),
	VARO_COMMANDS_BUGREPORT_UPLOADING("varoCommands.bugreport.uploading", "Lade Bugreport hoch..."),
	VARO_COMMANDS_BUGREPORT_UPLOAD_ERROR("varoCommands.bugreport.uploaderror", "Der Bugreport konnte nicht hochgeladen werden!"),
	VARO_COMMANDS_BUGREPORT_UPLOADED("varoCommands.bugreport.uploaded", "Bugreport wurde auf %colorcode%%url% &3hochgeladen!"),
	VARO_COMMANDS_BUGREPORT_CLICK_ME("varoCommands.bugreport.clickme", " &3(&aKlick mich&3)"),

	VARO_COMMANDS_ABORT_COUNTDOWN_NOT_ACTIVE("varoCommands.abort.notactive", "Der Startcountdown ist nicht aktiv!"),
	VARO_COMMANDS_ABORT_COUNTDOWN_STOPPED("varoCommands.abort.stopped", "Startcountdown erfolgreich gestoppt!"),

	VARO_COMMANDS_ACTIONBAR_ACTIVATED("varoCommands.actionbar.activated", "Du siehst nun die Zeit in der Actionbar!"),
	VARO_COMMANDS_ACTIONBAR_DEACTIVATED("varoCommands.actionbar.deactivated", "Du siehst nun nicht mehr die Zeit in der Actionbar!"),

	VARO_COMMANDS_AUTOSETUP_NOT_SETUP_YET("varoCommands.autosetup.notsetupyet", "Das AutoSetup wurde noch nicht in der Config eingerichtet!"),
	VARO_COMMANDS_AUTOSETUP_FINISHED("varoCommands.autosetup.finished", "Der AutoSetup ist fertig."),
	VARO_COMMANDS_AUTOSETUP_HELP("varoCommands.autosetup.help", "%colorcode%/varo autosetup run &3startet den Autosetup"),
	VARO_COMMANDS_AUTOSETUP_ATTENTION("varoCommands.autosetup.attention", "%colorcode%Vorsicht: &3Dieser Befehl setzt neue Spawns, Lobby, Portal, Border und &loptional&3 einen Autostart."),

	VARO_COMMANDS_AUTOSTART_ALREADY_STARTED("varoCommands.autostart.alreadystarted", "%projectname% &3wurde bereits gestartet!"),
	VARO_COMMANDS_AUTOSTART_ALREADY_SETUP("varoCommands.autostart.alreadysetup", "&3Entferne erst den AutoStart, bevor du einen neuen setzt!"),
	VARO_COMMANDS_AUTOSTART_HELP_SET("varoCommands.autostart.helpset", "%colorcode%/autostart &3set <Hour> <Minute> <Day> <Month> <Year>"),
	VARO_COMMANDS_AUTOSTART_NO_NUMBER("varoCommands.autostart.nonumber", "Eines der Argumente ist %colorcode%keine &3Zahl!"),
	VARO_COMMANDS_AUTOSTART_DATE_IN_THE_PAST("varoCommands.autostart.dateinthepast", "&3Das %colorcode%Datum &3darf nicht in der Vergangenheit sein!"),
	VARO_COMMANDS_AUTOSTART_NOT_SETUP_YET("varoCommands.autostart.notsetupyet", "&3Es wurde noch kein %colorcode%Autostart &3festegelegt!"),
	VARO_COMMANDS_AUTOSTART_REMOVED("varoCommands.autostart.removed", "%colorcode%AutoStart &3erfolgreich entfernt!"),
	VARO_COMMANDS_AUTOSTART_DELAY_HELP("varoCommands.autostart.delayhelp", "%colorcode%/autostart delay &3<Delay in Minutes>"),
	VARO_COMMANDS_AUTOSTART_DELAY_TO_SMALL("varoCommands.autostart.delaytosmall", "Der Delay darf nicht kleiner als 1 sein!"),
	VARO_COMMANDS_AUTOSTART_START_DELAYED("varoCommands.autostart.startdelayed", "&3Der Start wurde um %colorcode%%delay% &3Minuten verzoegert!"),
	VARO_COMMANDS_AUTOSTART_INFO_NOT_ACTIVE("varoCommands.autostart.notactive", "AutoStart nicht aktiv"),
	VARO_COMMANDS_AUTOSTART_INFO_ACTIVE("varoCommands.autostart.active", "AutoStart &aaktiv&3:"),
	VARO_COMMANDS_AUTOSTART_INFO_DATE("varoCommands.autostart.info.date", "%colorcode%Datum: &3%date%"),
	VARO_COMMANDS_AUTOSTART_INFO_AUTOSORT("varoCommands.autostart.info.autosort", "%colorcode%AutoSort: &3%active%"),
	VARO_COMMANDS_AUTOSTART_INFO_RANDOM_TEAM_SIZE("varoCommands.autostart.info.randomteamsize", "%colorcode%AutoRandomteamgroesse: &3%teamsize%"),
	VARO_COMMANDS_BACKPACK_PLAYER_DOESNT_EXIST("varoCommands.backpack.playerdoesntexist", "Der Spieler %colorcode%%player% &3existiert nicht."),
	VARO_COMMANDS_BACKPACK_TEAM_DOESNT_EXIST("varoCommands.backpack.teamdoesntexist", "Das Team %colorcode%%team% &3existiert nicht."),
	VARO_COMMANDS_BACKPACK_CANT_SHOW_BACKPACK("varoCommands.backpack.cantshowbackpack", "Der Rucksack kann dir daher nicht angezeigt werden."),
	VARO_COMMANDS_BACKPACK_NO_TEAM("varoCommands.backpack.noteam", "Du befindest dich in keinem Team und hast deshalb keinen Teamrucksack."),
	VARO_COMMANDS_BACKPACK_CHOOSE_TYPE("varoCommands.backpack.choosetype", "Bitte waehle aus, welchen Rucksack du oeffnen willst %colorcode%(Player/Team)&3."),
	VARO_COMMANDS_BACKPACK_NOT_ENABLED("varoCommands.backpack.notenabled", "Die Rucksacke sind nicht aktiviert."),
	VARO_COMMANDS_CONFIG_RELOADED("varoCommands.config.reloaded", "&3Alle %colorcode%Listen&3, %colorcode%Nachrichten &3und die %colorcode%Config &3wurden erfolgreich neu geladen."),
	VARO_COMMANDS_CONFIG_HELP_SET("varoCommands.config.helpset", "%colorcode%/varo config &3set <key> <value>"),
	VARO_COMMANDS_CONFIG_NO_INGAME_SET("varoCommands.config.noimgameset", "%colorcode%Du kannst diese Einstellung nur in der Config Datei ändern!"),
	VARO_COMMANDS_CONFIG_ERROR_SET("varoCommands.config.errorset", "%colorcode%Es ist ein fehler aufgetreten: &c%error%"),
	VARO_COMMANDS_CONFIG_HELP_SEARCH("varoCommands.config.helpsearch", "%colorcode%/varo config &3search <key>"),
	VARO_COMMANDS_CONFIG_ENTRY_SET("varoCommands.config.entryset", "&3Der Eintrag '%colorcode%%entry%&3' wurde erfolgreich auf '%colorcode%%value%&3' gesetzt."),
	VARO_COMMANDS_CONFIG_ENTRY_NOT_FOUND("varoCommands.config.entrynotfound", "&3Der Eintrag '%colorcode%%entry%&3' wurde nicht gefunden."),
	VARO_COMMANDS_CONFIG_RESET("varoCommands.config.reset", "&3Alle Eintraege wurden erfolgreich zurueckgesetzt."),
	VARO_COMMANDS_CONFIG_SEARCH_LIST_TITLE("varoCommands.config.searchlisttitle", "&lFolgende Einstellungen wurden gefunden:"),
	VARO_COMMANDS_CONFIG_SEARCH_LIST_FORMAT("varoCommands.config.searchlistformat", "%colorcode%%entry% &8- &3%description%"),
	VARO_COMMANDS_EXPORT_SUCCESSFULL("varoCommands.export.players", "&3Alle Spieler wurden erfolgreich in '%colorcode%%file%&3' exportiert."),
	VARO_COMMANDS_DISCORD_PLEASE_RELOAD("varoCommands.discord.pleasereload", "&3Der DiscordBot wurde beim Start nicht aufgesetzt, bitte reloade!"),
	VARO_COMMANDS_DISCORD_VERIFY_DISABLED("varoCommands.discord.verifydisabled", "&3Das Verifzierungs-System wurde in der Config deaktiviert!"),
	VARO_COMMANDS_DISCORD_BOT_DISABLED("varoCommands.discord.botdisabled", "&3Der DiscordBot wurde nicht aktiviert!"),
	VARO_COMMANDS_DISCORD_USER_NOT_FOUND("varoCommands.discord.usernotfound", "&3User fuer diesen Spieler nicht gefunden!"),
	VARO_COMMANDS_INTRO_ALREADY_STARTED("varoCommands.intro.alreadystarted", "&3Das Intro wurde bereits gestartet!"),
	VARO_COMMANDS_INTRO_GAME_ALREADY_STARTED("varoCommands.intro.gamealreadystarted", "&3Das Spiel wurde bereits gestartet!"),
	VARO_COMMANDS_INTRO_STARTED("varoCommands.intro.started", "&3Und los geht's!"),
	VARO_COMMANDS_PRESET_NOT_FOUND("varoCommands.preset.notfound", "Das Preset %colorcode%%preset% &3wurde nicht gefunden."),
	VARO_COMMANDS_PRESET_LOADED("varoCommands.preset.loaded", "Das Preset %colorcode%%preset% &3wurde &aerfolgreich &3geladen."),
	VARO_COMMANDS_PRESET_SAVED("varoCommands.preset.saved", "Die aktuellen Einstellungen wurden &aerfolgreich &3als Preset %colorcode%%preset% &3gespeichert."),
	VARO_COMMANDS_PRESET_LIST("varoCommands.preset.list", "&lListe aller Presets:"),
	VARO_COMMANDS_PRESET_HELP_LOAD("varoCommands.preset.helploaded", "%colorcode%/varo preset &3load <PresetPath>"),
	VARO_COMMANDS_PRESET_HELP_SAVE("varoCommands.preset.helpsave", "%colorcode%/varo preset &3save <PresetPath>"),
	VARO_COMMANDS_RANDOMTEAM_HELP("varoCommands.randomteam.help", "%colorcode%/varo randomTeam <Teamgrösse>"),
	VARO_COMMANDS_RANDOMTEAM_TEAMSIZE_TOO_SMALL("varoCommands.randomteam.teamsizetoosmall", "&3Die Teamgroesse muss mindestens 1 betragen."),
	VARO_COMMANDS_RANDOMTEAM_SORTED("varoCommands.randomteam.sorted", "&3Alle Spieler ohne Team sind nun in %colorcode%%teamsize%er &3Teams!"),
	VARO_COMMANDS_RANDOMTEAM_NO_PARTNER("varoCommands.randomteam.nopartner", "&3Fuer dich konnten nicht genug Teampartner gefunden werden."),

	VARO_COMMANDS_CHECKCOMBAT_HELP("varoCommands.checkcombat.help", "%colorcode%/varo checkcombat"),
	VARO_COMMANDS_CHECKCOMBAT_INCOMBAT("varoCommands.checkcombat.incombat", "&3Du bist momentan im %colorcode%Combat&3!"),
	VARO_COMMANDS_CHECKCOMBAT_NOTINCOMBAT("varoCommands.checkcombat.notincombat", "&3Du bist momentan nicht im %colorcode%Combat&3!"),
	VARO_COMMANDS_PORTAL_ACTIVATED("varoCommands.activated", "%colorcode%Portale sind nun aktiviert!"),
	VARO_COMMANDS_PORTAL_DEACTIVATED("varoCommands.deactivated", "%colorcode%Portale sind nun deaktiviert!"),
	VARO_COMMANDS_RESTART_IN_LOBBY("varoCommands.restart.inlobby", "&3Das Spiel befindet sich bereits in der Lobby-Phase!"),
	VARO_COMMANDS_RESTART_RESTARTED("varoCommands.restart.restarted", "&3Das Spiel wurde neugestartet."),
	VARO_COMMANDS_SCOREBOARD_DEACTIVATED("varoCommands.scoreboard.deactivated", "&3Scoreboard sind deaktiviert."),
	VARO_COMMANDS_SCOREBOARD_ENABLED("varoCommands.scoreboard.enabled", "&3Du siehst nun das Scoreboard."),
	VARO_COMMANDS_SCOREBOARD_DISABLED("varoCommands.scoreboard.disabled", "&3Du siehst nun das Scoreboard."),
	VARO_COMMANDS_SORT_HELP("varoCommands.sort.help", "%colorcode%/varo sort"),
	VARO_COMMANDS_SORT_SORTED_WELL("varoCommands.sort.sorted", "&3Alle Spieler wurden erfolgreich sortiert."),
	VARO_COMMANDS_SORT_NO_SPAWN_WITH_TEAM("varoCommands.sort.nospawnwithteam", "&3Es konnte nicht fuer jeden Spieler ein Loch bei den Teampartnern gefunden werden!"),
	VARO_COMMANDS_SORT_NO_SPAWN("varoCommands.sort.nospawn", "&3Es konnte nicht fuer jeden Spieler ein Loch gefunden werden!"),

	VARO_COMMANDS_DISCORD_NOT_SETUP("varoCommands.discord.notsetup", "&3Der DiscordBot wurde beim Start nicht aufgesetzt!"),
	VARO_COMMANDS_DISCORD_STATUS("varoCommands.discord.status", "&3Deine Discord Verifizierung ist %status%&3."),
	VARO_COMMANDS_DISCORD_ACTIVE("varoCommands.discord.status.active", "&aaktiv"),
	VARO_COMMANDS_DISCORD_INACTIVE("varoCommands.discord.status.inactive", "%colorcode%inaktiv"),
	VARO_COMMANDS_DISCORD_NOT_VERIFIED("varoCommands.discord.notverified", "&3Du bist noch nicht verifiziert!"),
	VARO_COMMANDS_DISCORD_VERIFICATION_REMOVED("varoCommands.discord.verificationremoved", "&3Deine Verifizierung wurde entfernt."),
	VARO_COMMANDS_DISCORD_VERIFY_SYSTEM_DISABLED("varoCommands.discord.verifysystemdisabled", "&3Das Verifysystem ist deaktiviert."),
	VARO_COMMANDS_DISCORD_DISCORDBOT_DISABLED("varoCommands.discord.discordbotdisabled", "&3Der Discordbot ist deaktiviert."),
	VARO_COMMANDS_DISCORD_GETLINK("varoCommands.discord.getlink", "&3Der Discord Account von %colorcode%%player% heisst %colorcode%%user%&3 und die ID lautet %colorcode%%id%&3!"),
	VARO_COMMANDS_DISCORD_UNVERIFY("varoCommands.discord.unverify", "&3Der Discord Account wurde erfolgreich von %colorcode%%player% &3entkoppelt!"),
	VARO_COMMANDS_DISCORD_RELOADED("varoCommands.discord.reloaded", "&3Der Discordbot wurde &aerfolgreich &3neu geladen."),
	VARO_COMMANDS_DISCORD_SHUTDOWN("varoCommands.discord.shutdown", "&3Der Discordbot wurde &aerfolgreich &3heruntergefahren."),
	VARO_COMMANDS_DISCORD_BOT_OFFLINE("varoCommands.discord.botoffline", "&3Der Discordbot ist nicht online!"),
	VARO_COMMANDS_DISCORD_NO_EVENT_CHANNEL("varoCommands.discord.noeventchannel", "&3Dem Bot wurde keine Event-Channel gegeben."),
	VARO_COMMANDS_DISCORD_VERIFY_ENABLED("varoCommands.discord.verifyenabled", "&3Das Verifysystem wurde aktiviert."),
	VARO_COMMANDS_DISCORD_DISCORD_MESSAGE_TITLE("varoCommands.discord.discordmessagetitle", "MESSAGE"),
	VARO_COMMANDS_DISCORD_BYPASS_ACTIVE("varoCommands.discord.bypassactive", "&3%player% umgeht nun das Verifysystem."),
	VARO_COMMANDS_DISCORD_BYPASS_INACTIVE("varoCommands.discord.bypassinactive", "&3%player% umgeht nicht mehr das Verifysystem."),
	VARO_COMMANDS_DISCORD_VERIFY_ACCOUNT("varoCommands.discord.account", "&3Account: %colorcode%%account%"),
	VARO_COMMANDS_DISCORD_VERIFY_REMOVE_USAGE("varoCommands.discord.remove.usage", "&3Nutze %colorcode%/varo discord verify remove &3ein, um die Verifizierung zu entfernen."),

	COMMANDS_MIDDLE("varoCommands.middle", "&3Die Mitte befindet sich an den Koordinaten %colorcode%%value%"),

	VARO_COMMANDS_PLAYTIME("varoCommands.playtime", "&3Deine verbleibende Zeit: %formattedCountdown%&3."),

	COMMANDS_XRAY_ERROR_NOT_AVAIALABLE("varoCommands.xray.errorNotAvailable", "%colorcode%Es gab einen Fehler mit dem Anti-Xray-System. Bitte überprüfe, ob deine Serverversion (%version%) auf Spigot basiert."),
	COMMANDS_XRAY_INSTALLING_PLUGIN("varoCommands.xray.installingPlugin", "Das Anti-Xray-Plugin wird installiert und der Server danach heruntergefahren."),
	COMMANDS_XRAY_INSTALLING_ERROR("varoCommands.xray.installingError", "Es gab einen kritischen Fehler beim Download des Plugins. Du kannst dir das externe Plugin hier manuell herunterladen: %colorcode%&nhttps://www.spigotmc.org/resources/22818/"),
	COMMANDS_XRAY_VERSION_NOT_AVAIALABLE("varoCommands.xray.versionNotAvailable", "%colorcode%Auf deiner Serverversion ist kein X-Ray verfügbar (%version%)."),
	COMMANDS_XRAY_STATUS("varoCommands.xray.status", "Anti-Xray ist momentan: %status%&3."),
	COMMANDS_XRAY_STATUS_ACTIVATED("varoCommands.xray.statusActivated", "&aaktiviert"),
	COMMANDS_XRAY_STATUS_DEACTIVATED("varoCommands.xray.statusDeactivated", "%colorcode%deaktiviert"),
	COMMANDS_XRAY_ACTIVATED("varoCommands.xray.activated", "Das Anti-Xray wurde aktiviert."),
	COMMANDS_XRAY_DEACTIVATED("varoCommands.xray.dectivated", "Das Anti-Xray wurde deaktiviert."),
	COMMANDS_XRAY_ALREADY_ACTIVATED("varoCommands.xray.alreadyActivated", "Das Anti-Xray ist bereits aktiviert."),
	COMMANDS_XRAY_ALREADY_DEACTIVATED("varoCommands.xray.alreadyDactivated", "Das Anti-Xray ist bereits deaktiviert."),

	COMMANDS_BORDER_SIZE("varoCommands.border.size", "Die Border ist momentan %colorcode%%size% Bloecke &3gross."),
	COMMANDS_BORDER_DISTANCE("varoCommands.border.distance", "Du bist %colorcode%%distance% Bloecke &3von der Border entfernt."),
	COMMANDS_BORDER_USAGE("varoCommands.border.usage", "Du kannst die Groesse der Border mit %colorcode%/border <Durchmesser> [Sekunden] &3setzen. Der Mittelpunkt der Border wird zu deinem derzeiten Punkt gesetzt."),

	COMMANDS_BROADCAST_FORMAT("varoCommands.broadcast.format", "&7[%colorcode%Varo&7] %colorcode%Benachrichtigung &8| &7%message%"),

	COMMANDS_CHATCLEAR_CLEAR("varoCommands.chatclear.cleared", "Der Chat wurde %colorcode%gecleart&3."),

	COMMANDS_COUNTDOWN_ABORT("varoCommands.countdown.abort", "Der Countdown wurde abgebrochen."),
	COMMANDS_COUNTDOWN_TOO_SMALL("varoCommands.countdown.tooSmall", "Der Countdown kann nicht negativ oder 0 sein!"),
	COMMANDS_COUNTDOWN_START("varoCommands.countdown.start", "Los!"),
	COMMANDS_COUNTDOWN_FORMAT("varoCommands.countdown.format", "%colorcode%%seconds%"),

	COMMANDS_TIME_DAY("varoCommands.time.day", "Es ist jetzt %colorcode%Tag&3."),
	COMMANDS_TIME_NIGHT("varoCommands.time.night", "Es ist jetzt %colorcode%Nacht&3."),

	COMMANDS_WEATHER_SUN("varoCommands.weather.sun", "Es ist jetzt %colorcode%sonnig&3."),
	COMMANDS_WEATHER_RAIN("varoCommands.weather.rain", "Es ist jetzt %colorcode%regnerisch&3."),
	COMMANDS_WEATHER_THUNDER("varoCommands.weather.thunder", "Es %colorcode%gewittert &3nun."),

	COMMANDS_SETWORLDSPAWN("varoCommands.setworldspawn.setworldspawn", "Weltspawn erfolgreich gesetzt."),

	COMMANDS_DENIED("varoCommands.blockedcommand", "Du darst diesen Befehl nicht benutzen!"),

	SPAWNS_SPAWN_NUMBER("spawns.spawnNameTag.number", "&3Spawn %colorcode%%number%"),
	SPAWNS_SPAWN_PLAYER("spawns.spawnNameTag.player", "&3Spawn von %colorcode%%player%"),

	MODS_BLOCKED_MODS_KICK("mods.blockedModsKick", "&3Bitte entferne folgende Mods: %colorcode%%mods%"),
	MODS_BLOCKED_MODLIST_SPLIT("mods.blockedModsListSplit", "&3, "),
	MODS_BLOCKED_MODS_BROADCAST("mods.blockedModsBroadcast", "&3Der Spieler %colorcode%%player% &3hat versucht mit folgenden blockierten Mods zu joinen: %colorcode%%mods%"),

	OTHER_CONFIG("other.configReload", "&3Die %colorcode%Config &3wurde neu geladen"),
	OTHER_PING("other.ping", "&3Dein %colorcode%Ping &3betraegt: %colorcode%%ping%&3ms"),

	LOGGER_FILTER_INVALID_FILTER("blockLoggerFilter.invalidFilter", "&c%filterName% \"%content%\" ist nicht gültig!"),
	LOGGER_FILTER_SET_FILTER("blockLoggerFilter.setFilter", "&7%filterName% wurde auf %colorcode%%newContent% &7gesetzt (vorher: %oldContent%)."),
	LOGGER_FILTER_RESET_FILTER("blockLoggerFilter.resetFilter", "&7%filterName% wurde zurückgesetzt (vorher: %oldContent%)."),
	LOGGER_FILTER_PLAYER_FILTER_MESSAGE("blockLoggerFilter.playerFilterMessage", "&7Bitte gib einen Spielernamen ein:"),
	LOGGER_FILTER_MATERIAL_FILTER_MESSAGE("blockLoggerFilter.materialFilterMessage", "&7Bitte gib einen Materialnamen ein:");

	private String path, defaultMessage, message;

	ConfigMessages(String path, String message) {
		this.path = path;
		this.defaultMessage = message;
		this.message = message;
	}

	private synchronized String getMessage(Language lang) {
		String message = null;
		try {
			if (lang == null || lang == Main.getLanguageManager().getDefaultLanguage() || !ConfigSetting.MAIN_LANGUAGE_ALLOW_OTHER.getValueAsBoolean())
				message = Main.getLanguageManager().getDefaultLanguage().getMessage(this.path);
			else {
				String langMsg = lang.getMessage(this.path);
				message = langMsg == null ? Main.getLanguageManager().getDefaultLanguage().getMessage(this.path) : langMsg;
			}
		} catch (NullPointerException ex) {
			if (!ex.getMessage().equalsIgnoreCase("Nodes must be provided.")) {
				ex.printStackTrace();
			}
		}
		return Main.getLanguageManager().replaceMessage(message != null ? message : this.message);
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public String getDefaultMessage() {
		return this.defaultMessage;
	}

	@Override
	public String getValue() {
		return getMessage(Main.getLanguageManager().getDefaultLanguage());
	}

	@Override
	public String getValue(CustomPlayer localeHolder) {
		return getMessage(localeHolder != null && localeHolder.getLocale() != null ? Main.getLanguageManager().getLanguages().get(localeHolder.getLocale()) : Main.getLanguageManager().getDefaultLanguage());
	}

	@Override
	public String getValue(CustomPlayer localeHolder, CustomPlayer replace) {
		return Main.getLanguageManager().replaceMessage(getValue(localeHolder), replace);
	}
}
