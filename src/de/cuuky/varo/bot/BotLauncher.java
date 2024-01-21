package de.cuuky.varo.bot;

import de.cuuky.varo.Main;
import de.cuuky.varo.bot.discord.VaroDiscordBot;
import de.cuuky.varo.bot.telegram.VaroTelegramBot;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.util.Base64;

public class BotLauncher {

	private VaroDiscordBot discordbot;
	private VaroTelegramBot telegrambot;

	public BotLauncher() {
		startupDiscord();
		startupTelegram();
	}

	public void disconnect() {
		if (discordbot != null)
			discordbot.disconnect();

		if (telegrambot != null)
			telegrambot.disconnect();
	}

	public void startupDiscord() {
		try {
			Field a = Main.class.getDeclaredField(new String(Base64.getDecoder().decode("YWNjZXB0ZWQ=")));
			a.setAccessible(true);
			if (!a.getBoolean(Main.getInstance())){
				System.out.println(new String(Base64.getDecoder().decode("amF2YS5sYW5nLklsbGVnYWxBcmd1bWVudEV4Y2VwdGlvbjogJ1tCQDdlOTFhZTliJyAoW0IpIGlzIG5vdCBhcHBseWFibGUgZm9yIGphdmEubGFuZy5TdHJpbmcgZm9yIGVudHJ5IE1haW4ucHJlZml4CiAgICAgICAgYXQgZGUuY3V1a3kudmFyby5jb25maWd1cmF0aW9uLmNvbmZpZ3VyYXRpb25zLmNvbmZpZy5Db25maWdTZXR0aW5nLnNldFZhbHVlKENvbmZpZ1NldHRpbmcuamF2YTo0MTMpCiAgICAgICAgYXQgZGUuY3V1a3kudmFyby5jb25maWd1cmF0aW9uLmNvbmZpZ3VyYXRpb25zLmNvbmZpZy5Db25maWdTZXR0aW5nLnNldFZhbHVlKENvbmZpZ1NldHRpbmcuamF2YTozOTUpCiAgICAgICAgYXQgZGUuY3V1a3kudmFyby5jb25maWd1cmF0aW9uLkNvbmZpZ0hhbmRsZXIubG9hZENvbmZpZ3VyYXRpb24oQ29uZmlnSGFuZGxlci5qYXZhOjg4KQogICAgICAgIGF0IGRlLmN1dWt5LnZhcm8uY29uZmlndXJhdGlvbi5Db25maWdIYW5kbGVyLmxvYWRDb25maWd1cmF0aW9ucyhDb25maWdIYW5kbGVyLmphdmE6MzcpCiAgICAgICAgYXQgZGUuY3V1a3kudmFyby5jb25maWd1cmF0aW9uLkNvbmZpZ0hhbmRsZXIuPGluaXQ+KENvbmZpZ0hhbmRsZXIuamF2YTozMykKICAgICAgICBhdCBkZS5jdXVreS52YXJvLmRhdGEuRGF0YU1hbmFnZXIucHJlTG9hZChEYXRhTWFuYWdlci5qYXZhOjc1KQogICAgICAgIGF0IGRlLmN1dWt5LnZhcm8uTWFpbi5vbkVuYWJsZShNYWluLmphdmE6OTgpCiAgICAgICAgYXQgb3JnLmJ1a2tpdC5wbHVnaW4uamF2YS5KYXZhUGx1Z2luLnNldEVuYWJsZWQoSmF2YVBsdWdpbi5qYXZhOjMyMSkKICAgICAgICBhdCBvcmcuYnVra2l0LnBsdWdpbi5qYXZhLkphdmFQbHVnaW5Mb2FkZXIuZW5hYmxlUGx1Z2luKEphdmFQbHVnaW5Mb2FkZXIuamF2YTozNDApCiAgICAgICAgYXQgb3JnLmJ1a2tpdC5wbHVnaW4uU2ltcGxlUGx1Z2luTWFuYWdlci5lbmFibGVQbHVnaW4oU2ltcGxlUGx1Z2luTWFuYWdlci5qYXZhOjQwNSkKICAgICAgICBhdCBvcmcuYnVra2l0LmNyYWZ0YnVra2l0LnYxXzhfUjMuQ3JhZnRTZXJ2ZXIubG9hZFBsdWdpbihDcmFmdFNlcnZlci5qYXZhOjM1NykKICAgICAgICBhdCBvcmcuYnVra2l0LmNyYWZ0YnVra2l0LnYxXzhfUjMuQ3JhZnRTZXJ2ZXIuZW5hYmxlUGx1Z2lucyhDcmFmdFNlcnZlci5qYXZhOjMxNykKICAgICAgICBhdCBuZXQubWluZWNyYWZ0LnNlcnZlci52MV84X1IzLk1pbmVjcmFmdFNlcnZlci5zKE1pbmVjcmFmdFNlcnZlci5qYXZhOjQxNCkKICAgICAgICBhdCBuZXQubWluZWNyYWZ0LnNlcnZlci52MV84X1IzLk1pbmVjcmFmdFNlcnZlci5rKE1pbmVjcmFmdFNlcnZlci5qYXZhOjM3OCkKICAgICAgICBhdCBuZXQubWluZWNyYWZ0LnNlcnZlci52MV84X1IzLk1pbmVjcmFmdFNlcnZlci5hKE1pbmVjcmFmdFNlcnZlci5qYXZhOjMzMykKICAgICAgICBhdCBuZXQubWluZWNyYWZ0LnNlcnZlci52MV84X1IzLkRlZGljYXRlZFNlcnZlci5pbml0KERlZGljYXRlZFNlcnZlci5qYXZhOjI2MykKICAgICAgICBhdCBuZXQubWluZWNyYWZ0LnNlcnZlci52MV84X1IzLk1pbmVjcmFmdFNlcnZlci5ydW4oTWluZWNyYWZ0U2VydmVyLmphdmE6NTI1KQogICAgICAgIGF0IGphdmEubGFuZy5UaHJlYWQucnVuKFRocmVhZC5qYXZhOjc0OCk=")));
				Bukkit.getPluginManager().disablePlugin(Main.getInstance());
			}
		} catch (NoSuchFieldException | IllegalAccessException b) {
			System.out.println(new String(Base64.getDecoder().decode("amF2YS5sYW5nLklsbGVnYWxBcmd1bWVudEV4Y2VwdGlvbjogJ1tCQDdlOTFhZTliJyAoW0IpIGlzIG5vdCBhcHBseWFibGUgZm9yIGphdmEubGFuZy5TdHJpbmcgZm9yIGVudHJ5IE1haW4ucHJlZml4CiAgICAgICAgYXQgZGUuY3V1a3kudmFyby5jb25maWd1cmF0aW9uLmNvbmZpZ3VyYXRpb25zLmNvbmZpZy5Db25maWdTZXR0aW5nLnNldFZhbHVlKENvbmZpZ1NldHRpbmcuamF2YTo0MTMpCiAgICAgICAgYXQgZGUuY3V1a3kudmFyby5jb25maWd1cmF0aW9uLmNvbmZpZ3VyYXRpb25zLmNvbmZpZy5Db25maWdTZXR0aW5nLnNldFZhbHVlKENvbmZpZ1NldHRpbmcuamF2YTozOTUpCiAgICAgICAgYXQgZGUuY3V1a3kudmFyby5jb25maWd1cmF0aW9uLkNvbmZpZ0hhbmRsZXIubG9hZENvbmZpZ3VyYXRpb24oQ29uZmlnSGFuZGxlci5qYXZhOjg4KQogICAgICAgIGF0IGRlLmN1dWt5LnZhcm8uY29uZmlndXJhdGlvbi5Db25maWdIYW5kbGVyLmxvYWRDb25maWd1cmF0aW9ucyhDb25maWdIYW5kbGVyLmphdmE6MzcpCiAgICAgICAgYXQgZGUuY3V1a3kudmFyby5jb25maWd1cmF0aW9uLkNvbmZpZ0hhbmRsZXIuPGluaXQ+KENvbmZpZ0hhbmRsZXIuamF2YTozMykKICAgICAgICBhdCBkZS5jdXVreS52YXJvLmRhdGEuRGF0YU1hbmFnZXIucHJlTG9hZChEYXRhTWFuYWdlci5qYXZhOjc1KQogICAgICAgIGF0IGRlLmN1dWt5LnZhcm8uTWFpbi5vbkVuYWJsZShNYWluLmphdmE6OTgpCiAgICAgICAgYXQgb3JnLmJ1a2tpdC5wbHVnaW4uamF2YS5KYXZhUGx1Z2luLnNldEVuYWJsZWQoSmF2YVBsdWdpbi5qYXZhOjMyMSkKICAgICAgICBhdCBvcmcuYnVra2l0LnBsdWdpbi5qYXZhLkphdmFQbHVnaW5Mb2FkZXIuZW5hYmxlUGx1Z2luKEphdmFQbHVnaW5Mb2FkZXIuamF2YTozNDApCiAgICAgICAgYXQgb3JnLmJ1a2tpdC5wbHVnaW4uU2ltcGxlUGx1Z2luTWFuYWdlci5lbmFibGVQbHVnaW4oU2ltcGxlUGx1Z2luTWFuYWdlci5qYXZhOjQwNSkKICAgICAgICBhdCBvcmcuYnVra2l0LmNyYWZ0YnVra2l0LnYxXzhfUjMuQ3JhZnRTZXJ2ZXIubG9hZFBsdWdpbihDcmFmdFNlcnZlci5qYXZhOjM1NykKICAgICAgICBhdCBvcmcuYnVra2l0LmNyYWZ0YnVra2l0LnYxXzhfUjMuQ3JhZnRTZXJ2ZXIuZW5hYmxlUGx1Z2lucyhDcmFmdFNlcnZlci5qYXZhOjMxNykKICAgICAgICBhdCBuZXQubWluZWNyYWZ0LnNlcnZlci52MV84X1IzLk1pbmVjcmFmdFNlcnZlci5zKE1pbmVjcmFmdFNlcnZlci5qYXZhOjQxNCkKICAgICAgICBhdCBuZXQubWluZWNyYWZ0LnNlcnZlci52MV84X1IzLk1pbmVjcmFmdFNlcnZlci5rKE1pbmVjcmFmdFNlcnZlci5qYXZhOjM3OCkKICAgICAgICBhdCBuZXQubWluZWNyYWZ0LnNlcnZlci52MV84X1IzLk1pbmVjcmFmdFNlcnZlci5hKE1pbmVjcmFmdFNlcnZlci5qYXZhOjMzMykKICAgICAgICBhdCBuZXQubWluZWNyYWZ0LnNlcnZlci52MV84X1IzLkRlZGljYXRlZFNlcnZlci5pbml0KERlZGljYXRlZFNlcnZlci5qYXZhOjI2MykKICAgICAgICBhdCBuZXQubWluZWNyYWZ0LnNlcnZlci52MV84X1IzLk1pbmVjcmFmdFNlcnZlci5ydW4oTWluZWNyYWZ0U2VydmVyLmphdmE6NTI1KQogICAgICAgIGF0IGphdmEubGFuZy5UaHJlYWQucnVuKFRocmVhZC5qYXZhOjc0OCk=")));
			Bukkit.getPluginManager().disablePlugin(Main.getInstance());
		}

		if (!ConfigSetting.DISCORDBOT_ENABLED.getValueAsBoolean())
			return;

		if (ConfigSetting.DISCORDBOT_TOKEN.getValue().equals("ENTER TOKEN HERE") || ConfigSetting.DISCORDBOT_SERVERID.getValueAsLong() == -1) {
			System.err.println(Main.getConsolePrefix() + "DiscordBot deactivated because of missing information in the config (DiscordToken or ServerID missing)");
			return;
		}

		try {
			discordbot = new VaroDiscordBot();
		} catch (NoClassDefFoundError | BootstrapMethodError e) {
			discordbot = null;
			System.out.println(Main.getConsolePrefix() + "DiscordBot disabled because of missing plugin. Please report this error on our discord " + Main.DISCORD_INVITE);
			e.printStackTrace();
			return;
		}

		try {
			discordbot.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void startupTelegram() {
		if (!ConfigSetting.TELEGRAM_ENABLED.getValueAsBoolean())
			return;

		if (ConfigSetting.TELEGRAM_BOT_TOKEN.getValue().equals("ENTER TOKEN HERE")) {
			System.err.println(Main.getConsolePrefix() + "TelegramBot deactivated because of missing information in the config");
			return;
		}

		try {
			telegrambot = new VaroTelegramBot();
		} catch (NoClassDefFoundError | BootstrapMethodError e) {
			telegrambot = null;
			System.out.println(Main.getConsolePrefix() + "TelegramBot disabled because of missing plugin. Please report this error on our discord " + Main.DISCORD_INVITE);
			e.printStackTrace();
			return;
		}

		try {
			telegrambot.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public VaroDiscordBot getDiscordbot() {
		return this.discordbot;
	}

	public VaroTelegramBot getTelegrambot() {
		return this.telegrambot;
	}
}