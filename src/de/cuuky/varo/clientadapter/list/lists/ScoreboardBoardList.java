package de.cuuky.varo.clientadapter.list.lists;

import java.io.IOException;
import java.util.ArrayList;

import de.cuuky.varo.clientadapter.list.BoardList;

public class ScoreboardBoardList extends BoardList {

    private String header;
    private ArrayList<String> scoreboardLines;

    public ScoreboardBoardList() {
        super("plugins/Varo/config/scoreboard.yml");
    }

    @Override
    protected void updateList() {
        this.scoreboardLines = new ArrayList<>();
        ArrayList<String> scoreboard = new ArrayList<>();
        configuration.options().header("Die Liste alle Placeholder findest du unter /varo placeholder!");

        scoreboard.add("&3Team: &e%team% &8(&e%teamID%&8)");
        scoreboard.add("%space%");
        scoreboard.add("&3Episode: &b%episodesPlayedPlus1%");
        scoreboard.add("&3Zeit: &b%time%");
        scoreboard.add("&3Kills: &b%kills%");
        scoreboard.add("&3Strikes: &b%strikes%");
        scoreboard.add("%space%");
        scoreboard.add("&cTOP Spieler:");
        scoreboard.add("&c1&8: &4%topplayer-1% &8- &4%topplayerkills-1%             &f");
        scoreboard.add("&c2&8: &4%topplayer-2% &8- &4%topplayerkills-2%             &f");
        scoreboard.add("&c3&8: &4%topplayer-3% &8- &4%topplayerkills-3%             &f");


        configuration.addDefault("header", "&7[%colorcode%%projectname%&7]");
        configuration.addDefault("scoreboard", scoreboard);

        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        scoreboardLines.addAll(configuration.getStringList("scoreboard"));

        this.header = configuration.getString("header");

        String space = "";
        for (int i = 0; i < scoreboardLines.size(); i++) {
            String line = scoreboardLines.get(i);
            if (line.equals("%space%")) {
                space += " ";
                scoreboardLines.set(i, space);
            }
        }
    }

    public String getHeader() {
        return this.header;
    }

    public ArrayList<String> getScoreboardLines() {
        return this.scoreboardLines;
    }
}