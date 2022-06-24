package de.cuuky.varo.clientadapter;

import java.util.ArrayList;
import java.util.logging.Level;

import de.cuuky.cfw.player.clientadapter.BoardUpdateHandler;
import de.cuuky.cfw.version.BukkitVersion;
import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.varo.Main;
import de.cuuky.varo.clientadapter.list.lists.ScoreboardBoardList;
import de.cuuky.varo.clientadapter.list.lists.TablistBoardList;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.stat.Rank;
import de.cuuky.varo.entity.team.VaroTeam;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class VaroBoardProvider extends BoardUpdateHandler<VaroPlayer> {

    private static ScoreboardBoardList scoreboard;
    private static TablistBoardList tablist;

    static {
        scoreboard = new ScoreboardBoardList();
        tablist = new TablistBoardList();
    }

    public VaroBoardProvider(VaroPlayer player) {
        super(player);
    }

    private ArrayList<String> replaceList(ArrayList<String> list) {
        ArrayList<String> newList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String line = list.get(i);
            line = Main.getLanguageManager().replaceMessage(line, player);
            newList.add(line);
        }

        return newList;
    }

    private ArrayList<String> replaceScoreboardList(ArrayList<String> list) {
        ArrayList<String> newList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String line = list.get(i);
            line = Main.getLanguageManager().replaceMessage(line, player);
            if (line.length() > 16) {
                newList.add(getColorcodeAt16Char(line)/*line.substring(0, 16) + getColorcodeAt16Char(line) + line.substring(16)*/);
            } else {
                newList.add(line);
            }
        }

        return newList;
    }

    private String getColorcodeAt16Char(String line) {
        ArrayList<String> parts = new ArrayList<>();
        parts.add(line.substring(0, 16));
        if (line.charAt(15) == '§') {
            String color = ChatColor.getLastColors(line.substring(0, 17));
            String part1 = parts.get(0);
            boolean addBlank = false;
            if (part1.charAt(15) == '§') {
                part1 = part1.substring(0, 15) + " ";
            }

            if (part1.charAt(14) == ' ' && part1.charAt(15) == ' ') {
                part1 = part1.substring(0, 14) + color;
                addBlank = true;
            }

            if (line.substring(17).startsWith(" ")) {
                parts.clear();
                parts.add(part1);
                if (addBlank) {
                    parts.add(" " + line.substring(18));
                } else {
                    parts.add(color + line.substring(18));
                }
            } else {
                parts.clear();
                parts.add(part1);
                if (addBlank) {
                    parts.add(" " + line.substring(17));
                } else {
                    parts.add(color + line.substring(17));
                }
            }

        } else {
            parts.add(ChatColor.getLastColors(parts.get(0)) + line.substring(16));
        }
        return parts.get(0) + parts.get(1);
    }

    @Override
    public ArrayList<String> getTablistHeader() {
        return replaceList(tablist.getHeaderLines());
    }

    @Override
    public ArrayList<String> getTablistFooter() {
        return replaceList(tablist.getFooterLines());
    }

    @Override
    public String getTablistName() {
        String listname;
        if (ConfigSetting.TABLIST_CHANGE_NAMES.getValueAsBoolean()) {
            int kills = player.getStats().getKills();
            ChatColor color = kills < 1 ? ChatColor.GREEN : kills < 3 ? ChatColor.DARK_GREEN : kills < 6 ? ChatColor.YELLOW : ChatColor.RED;
            if (player.getTeam() != null) {
                if (player.getRank() == null) {
                    listname = ConfigMessages.TABLIST_PLAYER_WITH_TEAM.getValue(null, player);
                } else {
                    listname = ConfigMessages.TABLIST_PLAYER_WITH_TEAM_RANK.getValue(null, player);
                }
            } else {
                if (player.getRank() == null) {
                    listname = ConfigMessages.TABLIST_PLAYER_WITHOUT_TEAM.getValue(null, player);
                } else {
                    listname = ConfigMessages.TABLIST_PLAYER_WITHOUT_TEAM_RANK.getValue(null, player);
                }
            }
            int maxlength = BukkitVersion.ONE_8.isHigherThan(VersionUtils.getVersion()) ? 16 : -1;
            if (maxlength > 0)
                if (listname.length() > maxlength)
                    listname = ConfigMessages.TABLIST_PLAYER_WITHOUT_TEAM_RANK.getValue(null, player);
            listname = listname + " " + color + kills;
        } else {
            listname = player.getName() + ChatColor.RESET;
        }
        return listname;
    }

    @Override
    public String getScoreboardTitle() {
        return Main.getLanguageManager().replaceMessage(scoreboard.getHeader(), player);
    }

    @Override
    public ArrayList<String> getScoreboardEntries() {
        return replaceScoreboardList(scoreboard.getScoreboardLines());
    }

    @Override
    public String getNametagName() {
        String name = player.getName();

        int teamsize = VaroTeam.getHighestNumber() + 1;
        int ranks = Rank.getHighestLocation() + 1;

        if (player.getTeam() != null)
            name = player.getTeam().getId() + name;
        else
            name = teamsize + name;

        if (player.getRank() != null)
            name = player.getRank().getTablistLocation() + name;
        else
            name = ranks + name;

        return name;
    }

    @Override
    public String getNametagPrefix() {
        String prefix = (player.getTeam() == null ? ConfigMessages.NAMETAG_NORMAL.getValue(null, player) : ConfigMessages.NAMETAG_TEAM_PREFIX.getValue(null, player));

        if (prefix.length() > 16)
            prefix = ConfigMessages.NAMETAG_NORMAL.getValue(null, player);

        return prefix;
    }

    @Override
    public String getNametagSuffix() {
        return String.valueOf(ConfigMessages.NAMETAG_SUFFIX.getValue(null, player));
    }

    @Override
    public boolean isNametagVisible() {
        return ConfigSetting.NAMETAGS_VISIBLE.getValueAsBoolean();
    }

    public static void update() {
        scoreboard.update();
        tablist.update();
    }
}