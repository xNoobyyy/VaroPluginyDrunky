package de.cuuky.varo.game.world.generators;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;

import de.cuuky.cfw.utils.BlockUtils;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.team.VaroTeam;
import de.cuuky.varo.spawns.Spawn;

public class SpawnGenerator {

    private final String blockId, sideBlockId;

    private SpawnGenerator(String blockId, String sideBlockId) {
        this.blockId = blockId;
        this.sideBlockId = sideBlockId;

        for (Spawn spawn : Spawn.getSpawnsClone())
            spawn.delete();
    }

    public SpawnGenerator(Location location, int radius, int amount, String blockId, String sideBlockId) {
        this(blockId, sideBlockId);

        int i = 0;
        for (Location loc : generateSpawns(location, radius, amount)) {
            i++;
            Location newLoc = loc.clone();

            new Spawn(i, setSpawnAt(newLoc));
        }
    }

    public SpawnGenerator(Location location, int radius, boolean withTeams, String blockId, String sideBlockId) {
        this(blockId, sideBlockId);

        ArrayList<Location> locations = generateSpawns(location, radius, VaroPlayer.getAlivePlayer().size());
        int i = 0;

        if (withTeams) {
            for (VaroTeam team : VaroTeam.getTeams()) {
                for (VaroPlayer player : team.getMember()) {
                    if (!player.getStats().isAlive() || i >= locations.size())
                        continue;

                    new Spawn(player, this.setSpawnAt(locations.get(i)));
                    i++;
                }
            }
        }

        for (VaroPlayer player : VaroPlayer.getAlivePlayer()) {
            if (Spawn.getSpawn(player) != null || i >= locations.size())
                continue;

            new Spawn(player, this.setSpawnAt(locations.get(i)));
            i++;
        }
    }

    private Location searchNext(Location newLoc, int add, boolean needsAir) {
        newLoc = newLoc.clone();
        while (BlockUtils.isAir(newLoc.clone().add(0, add, 0).getBlock()) != needsAir)
            newLoc = newLoc.add(0, add, 0);

        if (add > 0)
            newLoc.add(0, 1, 0);

        return newLoc;
    }

    private Location setSpawnAt(Location newLoc) {
        if (BlockUtils.isAir(newLoc.getBlock()))
            newLoc = this.searchNext(newLoc, -1, false);
        else
            newLoc = this.searchNext(newLoc, 1, true);

        newLoc.clone().add(0, -1, 0).getBlock().setType(Material.AIR);
        Materials xmat = blockId != null && !blockId.isEmpty() ? Materials.fromString(blockId) : Materials.STONE_BRICK_SLAB;
        BlockUtils.setBlockDelayed(Main.getInstance(), newLoc.clone().add(1, 0, 0).getBlock(), xmat);
        BlockUtils.setBlockDelayed(Main.getInstance(), newLoc.clone().add(0, 0, 1).getBlock(), xmat);
        BlockUtils.setBlockDelayed(Main.getInstance(), newLoc.clone().add(-1, 0, 0).getBlock(), xmat);
        BlockUtils.setBlockDelayed(Main.getInstance(), newLoc.clone().add(0, 0, -1).getBlock(), xmat);

        Materials mat = sideBlockId != null && !sideBlockId.isEmpty() ? Materials.fromString(sideBlockId) : Materials.GRASS_BLOCK;
        BlockUtils.setBlockDelayed(Main.getInstance(), newLoc.clone().add(0, -2, 0).getBlock(), mat);
        BlockUtils.setBlockDelayed(Main.getInstance(), newLoc.clone().add(1, -1, 0).getBlock(), mat);
        BlockUtils.setBlockDelayed(Main.getInstance(), newLoc.clone().add(0, -1, 1).getBlock(), mat);
        BlockUtils.setBlockDelayed(Main.getInstance(), newLoc.clone().add(-1, -1, 0).getBlock(), mat);
        BlockUtils.setBlockDelayed(Main.getInstance(), newLoc.clone().add(0, -1, -1).getBlock(), mat);

        return newLoc.getBlock().getLocation().add(0.5, -1, 0.5);
    }

    private ArrayList<Location> generateSpawns(Location middle, double radius, int amount) {
        double alpha = (2 * Math.PI) / amount;

        ArrayList<Location> locs = new ArrayList<>();

        for (int count = 0; count != amount; count++) {
            double beta = alpha * count;

            double x = radius * Math.cos(beta);
            double z = radius * Math.sin(beta);

            Location loc = middle.clone().add(x, 0, z);
            locs.add(loc);
        }

        return locs;
    }
}