package de.cuuky.varo.listener;

import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.cfw.version.types.Sounds;
import de.cuuky.varo.entity.player.stats.stat.inventory.VaroSaveable;
import de.cuuky.varo.entity.team.VaroTeam;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import de.cuuky.varo.Main;
import de.cuuky.varo.combatlog.PlayerHit;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.event.BukkitEventType;
import de.cuuky.varo.entity.player.stats.stat.PlayerState;
import de.cuuky.varo.listener.helper.cancelable.CancelAbleType;
import de.cuuky.varo.listener.helper.cancelable.VaroCancelAble;
import de.cuuky.varo.logger.logger.EventLogger.LogType;

public class PlayerDeathListener implements Listener {

    private void kickDeadPlayer(VaroPlayer deadPlayer, VaroPlayer killerPlayer) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!deadPlayer.isOnline())
                    return;

                if (killerPlayer == null)
                    deadPlayer.getPlayer().kickPlayer(ConfigMessages.DEATH_KICK_DEAD.getValue(deadPlayer, deadPlayer));
                else
                    deadPlayer.getPlayer().kickPlayer(ConfigMessages.DEATH_KICK_KILLED.getValue(deadPlayer, deadPlayer).replace("%killer%", killerPlayer.getName()));
            }
        }.runTaskLater(Main.getInstance(), 1L);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) throws Exception {
        Player deadPlayer = event.getEntity();
        Player killerPlayer = event.getEntity().getKiller();
        VaroPlayer deadP = VaroPlayer.getPlayer(deadPlayer);
        VaroPlayer killer = killerPlayer == null ? null : VaroPlayer.getPlayer(killerPlayer);
        event.setDeathMessage(null);

        if (Main.getVaroGame().hasStarted()) {
            deadP.onEvent(BukkitEventType.DEATH);
            if (killerPlayer != null)
                killer.onEvent(BukkitEventType.KILL);

            PlayerHit hit = PlayerHit.getHit(deadPlayer);
            if (hit != null)
                hit.over();

            if (deadP.getTeam() == null || deadP.getTeam().getLifes() <= 1) {
                String cause = deadPlayer.getLastDamageCause() != null ? deadPlayer.getLastDamageCause().getCause().toString() : "?";
                if (killerPlayer == null) {
                    Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.DEATH, ConfigMessages.ALERT_DISCORD_DEATH.getValue(null, deadP).replace("%death%", deadPlayer.getName()).replace("%deathTeam%", ((deadP.getTeam() == null) ? "-Teamlos-" : deadP.getTeam().getName())).replace("%reason%", cause));
                    Main.getLanguageManager().broadcastMessage(ConfigMessages.DEATH_DEAD, deadP).replace("%death%", deadPlayer.getName()).replace("%deathTeam%", ((deadP.getTeam() == null) ? "-Teamlos-" : deadP.getTeam().getName())).replace("%reason%", cause);
                } else {
                    Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.KILL, ConfigMessages.ALERT_DISCORD_KILL.getValue(null, deadP).replace("%death%", deadPlayer.getName()).replace("%killer%", killerPlayer.getName()).replace("%killerTeam%", ((killer.getTeam() == null) ? "-Teamlos-" : killer.getTeam().getName())).replace("%deathTeam%", ((deadP.getTeam() == null) ? "-Teamlos-" : deadP.getTeam().getName())));
                    Main.getLanguageManager().broadcastMessage(ConfigMessages.DEATH_KILLED_BY, deadP).replace("%death%", deadPlayer.getName()).replace("%killer%", killerPlayer.getName()).replace("%killerTeam%", ((killer.getTeam() == null) ? "-Teamlos-" : killer.getTeam().getName())).replace("%deathTeam%", ((deadP.getTeam() == null) ? "-Teamlos-" : deadP.getTeam().getName()));
                }

                deadP.onEvent(BukkitEventType.DEATH_NO_LIFES);

                if (!ConfigSetting.PLAYER_SPECTATE_AFTER_DEATH.getValueAsBoolean()) {
                    if (ConfigSetting.KICK_DELAY_AFTER_DEATH.isIntActivated()) {
                        deadP.getStats().setState(PlayerState.SPECTATOR);
                        deadP.setSpectacting();
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (!deadP.getStats().isAlive()) {
                                    deadP.getStats().setState(PlayerState.DEAD);
                                    kickDeadPlayer(deadP, killer);
                                    Main.getLanguageManager().broadcastMessage(ConfigMessages.QUIT_KICK_DELAY_OVER, deadP);
                                }
                            }
                        }.runTaskLater(Main.getInstance(), ConfigSetting.KICK_DELAY_AFTER_DEATH.getValueAsInt() * 20L);
                    } else
                        kickDeadPlayer(deadP, killer);
                }
            } else {
                if (ConfigSetting.RESPAWN_PROTECTION.isIntActivated()) {
                    VaroCancelAble prot = new VaroCancelAble(CancelAbleType.PROTECTION, deadP, ConfigSetting.RESPAWN_PROTECTION.getValueAsInt());
                    Main.getLanguageManager().broadcastMessage(ConfigMessages.DEATH_RESPAWN_PROTECTION, deadP).replace("%seconds%", String.valueOf(ConfigSetting.RESPAWN_PROTECTION.getValueAsInt()));
                    prot.setTimerHook(() -> Main.getLanguageManager().broadcastMessage(ConfigMessages.DEATH_RESPAWN_PROTECTION_OVER, deadP));
                }

                deadP.getTeam().setLifes(deadP.getTeam().getLifes() - 1);
                Main.getLanguageManager().broadcastMessage(ConfigMessages.DEATH_LIFE_LOST, deadP);
            }
            if (deadP.getTeam() != null && deadP.getTeam().isDead()) {
                for (VaroSaveable saveable : deadP.getTeam().getSaveables()) {
                    if (saveable.getType() == VaroSaveable.SaveableType.CHEST) {
                        Main.getLanguageManager().broadcastMessage(ConfigMessages.TEAMCHEST_TEAM_DIED, deadP);
                        break;
                    }
                }
            }
            if (Main.getVaroGame().isFirstblood()) {
                Main.getLanguageManager().broadcastMessage(ConfigMessages.FIRST_BLOOD, deadP);
                /*
                if (!ConfigSetting.FIRSTBLOOD_SOUND.getValueAsString().equalsIgnoreCase("-1")) {
                    VersionUtils.getVersionAdapter().getOnlinePlayers().forEach(pl -> pl.playSound(pl.getLocation(), Sounds.valueOf(ConfigSetting.FIRSTBLOOD_SOUND.getValueAsString()).bukkitSound(), 1.0F, 1.0F));
                }
                */
                VersionUtils.getVersionAdapter().getOnlinePlayers().forEach(pl -> pl.playSound(pl.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0F, 1.0F));
                Main.getVaroGame().setFirstblood(false);
            }

            if (ConfigSetting.DEATH_SOUND_ENABLED.getValueAsBoolean()) {
                if (ConfigSetting.DEATH_SOUND_INVOLVED.getValueAsString().equalsIgnoreCase("-1")) {
                    if (!ConfigSetting.DEATH_SOUND.getValueAsString().equalsIgnoreCase("-1")) {
                        VersionUtils.getVersionAdapter().getOnlinePlayers().forEach(pl -> {
                            try {
                                pl.playSound(pl.getLocation(),
                                        ConfigSetting.DEATH_SOUND.getValueAsString().contains("THUNDER") ? findThunderSound() :
                                                Sound.valueOf(ConfigSetting.DEATH_SOUND.getValueAsString()),
                                        getVolume(ConfigSetting.DEATH_SOUND.getValueAsString()),
                                        getPitch(ConfigSetting.DEATH_SOUND.getValueAsString()));
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }
                } else {
                    VersionUtils.getVersionAdapter().getOnlinePlayers().forEach(pl -> {
                        if (isInvolved(event, pl)) {
                            try {
                                pl.playSound(pl.getLocation(),
                                        ConfigSetting.DEATH_SOUND_INVOLVED.getValueAsString().contains("THUNDER") ? findThunderSound() :
                                                Sound.valueOf(ConfigSetting.DEATH_SOUND_INVOLVED.getValueAsString()),
                                        getVolume(ConfigSetting.DEATH_SOUND_INVOLVED.getValueAsString()),
                                        getPitch(ConfigSetting.DEATH_SOUND_INVOLVED.getValueAsString()));
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            if (!ConfigSetting.DEATH_SOUND.getValueAsString().equalsIgnoreCase("-1")) {
                                try {
                                    pl.playSound(pl.getLocation(),
                                            ConfigSetting.DEATH_SOUND.getValueAsString().contains("THUNDER") ? findThunderSound() :
                                                    Sound.valueOf(ConfigSetting.DEATH_SOUND.getValueAsString()),
                                            getVolume(ConfigSetting.DEATH_SOUND.getValueAsString()),
                                            getPitch(ConfigSetting.DEATH_SOUND.getValueAsString()));
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    });
                }
            }
        }
    }

    public boolean isInvolved(PlayerDeathEvent event, Player p) {
        VaroPlayer player = VaroPlayer.getPlayer(event.getEntity());
        if (player.getPlayer().equals(p)) return true;
        if (player.getTeam() != null && player.getTeam().getMember().stream().anyMatch(vp -> vp.getPlayer().equals(p))) return true;
        VaroPlayer killer = VaroPlayer.getPlayer(event.getEntity().getKiller());
        if (killer != null) {
            if (killer.getPlayer().equals(p)) return true;
            if (killer.getTeam() != null && killer.getTeam().getMember().stream().anyMatch(vp -> vp.getPlayer().equals(p))) return true;
        }
        return false;
    }

    public Sound findThunderSound() throws Exception {
        Sound thunder = null;
        String[] names = new String[]{"AMBIENCE_THUNDER", "ENTITY_LIGHTNING_BOLT_THUNDER", "ENTITY_LIGHTNING_THUNDER"};
        for (String name : names) {
            try {
                thunder = Sound.valueOf(name);
            } catch (IllegalArgumentException ignored) { }
        }
        if (thunder == null) {
            throw new IllegalAccessException("COULD NOT FIND THUNDER SOUND");
        }
        return thunder;
    }

    public float getPitch(String sound) {
        if (sound.contains("NOTE_BASS")) {
            return 3;
        } else if (sound.contains("THUNDER")) {
            return 50;
        } else {
            return 1;
        }
    }

    public float getVolume(String sound) {
        if (sound.contains("NOTE_BASS")) {
            return 2;
        } else if (sound.contains("THUNDER")) {
            return 50;
        } else {
            return 1;
        }
    }

}