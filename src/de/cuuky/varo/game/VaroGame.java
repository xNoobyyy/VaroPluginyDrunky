package de.cuuky.varo.game;

import de.cuuky.cfw.version.types.Sounds;
import de.cuuky.varo.Main;
import de.cuuky.varo.api.VaroAPI;
import de.cuuky.varo.api.event.events.game.VaroEndEvent;
import de.cuuky.varo.bot.discord.VaroDiscordBot;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.stat.YouTubeVideo;
import de.cuuky.varo.entity.team.VaroTeam;
import de.cuuky.varo.game.end.WinnerCheck;
import de.cuuky.varo.game.leaderboard.TopScoreList;
import de.cuuky.varo.game.lobby.LobbyItem;
import de.cuuky.varo.game.start.AutoStart;
import de.cuuky.varo.game.start.ProtectionTime;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.game.threads.VaroMainHeartbeatThread;
import de.cuuky.varo.game.threads.VaroStartThread;
import de.cuuky.varo.game.world.VaroWorld;
import de.cuuky.varo.game.world.VaroWorldHandler;
import de.cuuky.varo.game.world.border.decrease.BorderDecreaseDayTimer;
import de.cuuky.varo.game.world.border.decrease.BorderDecreaseMinuteTimer;
import de.cuuky.varo.game.world.generators.SpawnGenerator;
import de.cuuky.varo.recovery.recoveries.VaroBackup;
import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;
import de.cuuky.varo.spawns.sort.PlayerSort;
import de.cuuky.varo.threads.daily.dailycheck.checker.YouTubeCheck;
import de.cuuky.varo.utils.VaroUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VaroGame implements VaroSerializeable {

    @VaroSerializeField(path = "autostart")
    private AutoStart autostart;

    @VaroSerializeField(path = "borderDecrease")
    private BorderDecreaseDayTimer borderDecrease;

    @VaroSerializeField(path = "gamestate")
    private GameState gamestate;

    @VaroSerializeField(path = "lastCoordsPost")
    private Date lastCoordsPost;

    @VaroSerializeField(path = "lastDayTimer")
    private Date lastDayTimer;

    @VaroSerializeField(path = "lobby")
    private Location lobby;

    @VaroSerializeField(path = "firstblood")
    private boolean firstblood;

    @VaroSerializeField(path = "deactivatePortal")
    private boolean deactivatePortal;

    @VaroSerializeField(path = "isFirstStart")
    public boolean isFirstStart = false;

    private boolean finaleJoinStart, firstTime;
    private VaroMainHeartbeatThread mainThread;
    private VaroStartThread startThread;
    private BorderDecreaseMinuteTimer minuteTimer;
    private ProtectionTime protection;
    private VaroWorldHandler varoWorldHandler;
    private TopScoreList topScores;
    private boolean canHunger;

    public VaroGame() {
        Main.setVaroGame(this);
    }

    private void loadVariables() {
        if (startThread != null)
            startThread.loadVaraibles();

        if (mainThread != null)
            mainThread.loadVariables();

        this.topScores = new TopScoreList();
    }

    public void init() {
        startRefreshTimer();
        loadVariables();

        this.varoWorldHandler = new VaroWorldHandler();

        this.firstblood = true;
        this.gamestate = GameState.LOBBY;
        this.borderDecrease = new BorderDecreaseDayTimer(true);
        this.canHunger = true;
    }

    public void prepareStart() {
        if (hasStarted() || isStarting())
            return;

        new VaroBackup();
        LobbyItem.removeHooks();

        if (ConfigSetting.REMOVE_PLAYERS_ABSENT_AT_START.getValueAsBoolean())
            removeAbsentAtStart();

        if (ConfigSetting.DO_RANDOMTEAM_AT_START.getValueAsInt() > 0) {
            VaroUtils.doRandomTeam(ConfigSetting.DO_RANDOMTEAM_AT_START.getValueAsInt());
            Bukkit.broadcastMessage(Main.getPrefix() + "Alle Spieler haben einen zufaelligen Teampartner erhalten!");
        }

        if (ConfigSetting.DO_SPAWN_GENERATE_AT_START.getValueAsBoolean()) {
            new SpawnGenerator(Main.getVaroGame().getVaroWorldHandler().getMainWorld().getWorld().getSpawnLocation(), (int) (VaroPlayer.getAlivePlayer().size() * 0.85), true, null, null);
            Bukkit.broadcastMessage(Main.getPrefix() + "Die Loecher fuer den Spawn wurden generiert!");
        }

        if (ConfigSetting.DO_SORT_AT_START.getValueAsBoolean()) {
            new PlayerSort().sortPlayers();
            Bukkit.broadcastMessage(Main.getPrefix() + "Alle Spieler wurden sortiert!");
        }

        if (minuteTimer != null)
            minuteTimer.remove();

        this.lastDayTimer = new Date();
        (startThread = new VaroStartThread()).runTaskTimer(Main.getInstance(), 0, 20);
    }
    
    public void start() {
        for (VaroPlayer pl1 : VaroPlayer.getOnlinePlayer()) {
            if (pl1.getStats().isSpectator())
                continue;

            Player pl = pl1.getPlayer();
            pl.playSound(pl.getLocation(), Sounds.LEVEL_UP.bukkitSound(), 1, 1);
            pl.setGameMode(GameMode.SURVIVAL);
            pl1.cleanUpPlayer();
        }

        for (VaroPlayer pl1 : VaroPlayer.getVaroPlayers())
            pl1.getStats().loadStartDefaults();

        Main.getVaroGame().setFirstTime(true);
        Main.getDataManager().getListManager().getStartItems().giveToAll();
        Main.getVaroGame().setMinuteTimer(new BorderDecreaseMinuteTimer());

        for (VaroWorld world : Main.getVaroGame().getVaroWorldHandler().getWorlds())
            world.fillChests();

        if (ConfigSetting.STARTPERIOD_PROTECTIONTIME.getValueAsInt() > 0) {
            Main.getLanguageManager().broadcastMessage(ConfigMessages.PROTECTION_START).replace("%seconds%", String.valueOf(ConfigSetting.STARTPERIOD_PROTECTIONTIME.getValueAsInt()));
            Main.getVaroGame().setProtection(new ProtectionTime());
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                setFirstTime(false);
            }
        }.runTaskLater(Main.getInstance(), ConfigSetting.PLAY_TIME.getValueAsInt() * 60 * 20);
        
        if (ConfigSetting.YOUTUBE_ENABLED.getValueAsBoolean())
        	new BukkitRunnable() {
				
				@Override
				public void run() {
					// Copy the list to avoid ConcurrentModificationException
					// This is only executed once anyway so performance doen't really matter
					for (VaroPlayer player : VaroPlayer.getVaroPlayers().toArray(new VaroPlayer[0]))
						if (player.getStats().getYoutubeLink() != null) {
							List<YouTubeVideo> videos = YouTubeCheck.loadNewVideos(player);
							if (videos != null)
								player.getStats().getVideos().addAll(videos);
						}
				}
			}.runTaskAsynchronously(Main.getInstance());
    }

    public void abort() {
        startThread.cancel();
        Bukkit.broadcastMessage("§7Der Start wurde §cabgebrochen§7!");

        startThread = null;
    }

    private void removeAbsentAtStart() {
        for (VaroPlayer varoplayer : (ArrayList<VaroPlayer>) VaroPlayer.getVaroPlayers().clone())
            if (!varoplayer.isOnline())
                varoplayer.delete();
    }

    public void end(WinnerCheck check) {
        if (VaroAPI.getEventManager().executeEvent(new VaroEndEvent(this)))
            return;

        sendWinnerTitle(check.getPlaces().get(1).get(0), check.getPlaces().get(1).get(0).getTeam());

        this.gamestate = GameState.END;

        String first = "";
        String second = "";
        String third = "";
        for (int i = 1; i <= 3; i++) {
            ArrayList<VaroPlayer> won;
            won = check.getPlaces().get(i);

            if (won == null)
                break;

            StringBuilder names = new StringBuilder();
            for (VaroPlayer vp : won)
                names.append(!won.toArray()[won.size() - 1].equals(vp) ? vp.getName() + (won.size() > 2 ? (won.toArray()[won.size() - 2].equals(vp) ? "" : ", ") : "") : ((won.size() == 1 ? "" : " & ") + vp.getName()));
            names.append(won.get(0).getTeam() != null ? " (#" + won.get(0).getTeam().getName() + ")" : "");

            switch (i) {
                case 1:
                    first = names.toString();
                    break;
                case 2:
                    second = names.toString();
                    break;
                case 3:
                    third = names.toString();
                    break;
            }
        }

        if (check.getPlaces().get(1).get(0) != null)
            if (check.getPlaces().get(1).get(0).getTeam() != null) {
                Bukkit.broadcastMessage(Main.getLanguageManager().replaceMessage("&2&lDas Team &e&l" + check.getPlaces().get(1).get(0).getTeam().getName() + " &8&l(&6&l" + (
                        check.getPlaces().get(1)).get(0).getTeam().getId() + "&8&l) &2&lhat das VARO-Projekt gewonnen"));
            } else {
                Bukkit.broadcastMessage(Main.getLanguageManager().replaceMessage("&2&lDer Spieler &e&l" + check.getPlaces().get(1).get(0).getName() + " &8&l(&6&l" + (
                        check.getPlaces().get(1)).get(0).getId() + "&8&l) &2&lhat das VARO-Projekt gewonnen"));
            }

        VaroDiscordBot db = Main.getBotLauncher().getDiscordbot();
        if (db != null && db.isEnabled()) {
            if (db.getResultChannel() != null && db.isEnabled())
                db.sendMessage(":first_place: " + first + "\n" + ":second_place: " + second + "\n" + ":third_place: " + third + "\n\nHerzlichen Glueckwunsch!", "Das Projekt ist nun vorbei!", Color.MAGENTA, Main.getBotLauncher().getDiscordbot().getResultChannel());

            if (Main.getBotLauncher().getDiscordbot().getResultChannel() != null) {
                File file = new File("plugins/Varo/logs", "logs.yml");
                if (file.exists())
                    db.sendFile("Die Logs des Projektes", file, Main.getBotLauncher().getDiscordbot().getResultChannel());
            }
        }

        if (ConfigSetting.STOP_SERVER_ON_WIN.isIntActivated()) {
            Bukkit.getServer().broadcastMessage("§7Der Server wird in " + Main.getColorCode() + ConfigSetting.STOP_SERVER_ON_WIN.getValueAsInt() + " Sekunden §7heruntergefahren...");

            new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.getServer().shutdown();
                }
            }.runTaskLater(Main.getInstance(), ConfigSetting.STOP_SERVER_ON_WIN.getValueAsInt() * 20);
        }
    }

    private void startRefreshTimer() {
        (mainThread = new VaroMainHeartbeatThread()).runTaskTimer(Main.getInstance(), 0L, 20L);
    }

    private void sendWinnerTitle(VaroPlayer vp, VaroTeam team) {
        String message = (team != null) ? String.format(ChatColor.GREEN + "" + ChatColor.BOLD + "Das Team " + ChatColor.DARK_GREEN + "#%s " + ChatColor.GREEN +
                "hat gewonnen.", team.getName()) : String.format(ChatColor.GREEN + "" + ChatColor.BOLD + "Der Spieler " + ChatColor.DARK_GREEN + "%s " + ChatColor.GREEN +
                "hat gewonnen.", vp.getName());
        for (VaroPlayer current : VaroPlayer.getOnlinePlayer()) {
            Player p = current.getPlayer();
            p.playSound(p.getLocation(), Sounds.LEVEL_UP.bukkitSound(), 1.0F, 1.0F);
            current.getVersionAdapter().sendTitle(ChatColor.DARK_GREEN + "GEWINNER", message);
        }
    }

    public TopScoreList getTopScores() {
        return this.topScores;
    }

    public VaroMainHeartbeatThread getMainThread() {
        return this.mainThread;
    }

    public AutoStart getAutoStart() {
        return autostart;
    }

    public void setStartThread(VaroStartThread startThread) {
        this.startThread = startThread;
    }

    public boolean getFinaleJoinStart() {
        return finaleJoinStart;
    }

    public GameState getGameState() {
        return gamestate;
    }

    public Date getLastCoordsPost() {
        return lastCoordsPost;
    }

    public Date getLastDayTimer() {
        return lastDayTimer;
    }

    public Location getLobby() {
        return lobby;
    }

    public ProtectionTime getProtection() {
        return protection;
    }

    public boolean hasStarted() {
        return gamestate != GameState.LOBBY;
    }

    public boolean isFirstTime() {
        return firstTime;
    }

    public boolean isRunning() {
        return gamestate == GameState.STARTED;
    }

    public boolean isStarting() {
        return startThread != null;
    }

    public VaroWorldHandler getVaroWorldHandler() {
        return this.varoWorldHandler;
    }

    public void setAutoStart(AutoStart autoStart) {
        this.autostart = autoStart;
    }

    public void setBorderDecrease(BorderDecreaseDayTimer borderDecrease) {
        this.borderDecrease = borderDecrease;
    }

    public void setMinuteTimer(BorderDecreaseMinuteTimer minuteTimer) {
        this.minuteTimer = minuteTimer;
    }

    public void setFinaleJoinStart(boolean finaleJoinStart) {
        this.finaleJoinStart = finaleJoinStart;
    }

    public void setGamestate(GameState gamestate) {
        this.gamestate = gamestate;
    }

    public void setLastCoordsPost(Date lastCoordsPost) {
        this.lastCoordsPost = lastCoordsPost;
    }

    public void setLastDayTimer(Date lastDayTimer) {
        this.lastDayTimer = lastDayTimer;
    }

    public void setLobby(Location lobby) {
        this.lobby = lobby;
    }

    public void setProtection(ProtectionTime protection) {
        this.protection = protection;
    }

    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }

    public void setFirstblood(boolean firstblood) {
        this.firstblood = firstblood;
    }

    public boolean isFirstblood() {
        return firstblood;
    }

    public void setDeactivatePortal(boolean deactivatePortal) {
        this.deactivatePortal = deactivatePortal;
    }

    public boolean isDeactivatePortal() {
        return deactivatePortal;
    }

    public void setCanHunger(boolean canHunger) {
        this.canHunger = canHunger;
    }

    public boolean isCanHunger() {
        return canHunger;
    }

    @Override
    public void onDeserializeEnd() {
        if (gamestate == GameState.STARTED)
            minuteTimer = new BorderDecreaseMinuteTimer();

        startRefreshTimer();
        loadVariables();

        this.varoWorldHandler = new VaroWorldHandler();
    }

    @Override
    public void onSerializeStart() {
    }
}