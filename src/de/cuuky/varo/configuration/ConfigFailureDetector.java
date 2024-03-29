package de.cuuky.varo.configuration;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.yaml.snakeyaml.scanner.ScannerException;

import de.cuuky.cfw.configuration.YamlConfigurationUtil;
import de.cuuky.varo.Main;

public final class ConfigFailureDetector {

	private static List<String> ignoreScan = Arrays.asList("logs", "presets", "legacy");

	private boolean failed;

	public ConfigFailureDetector() {
		detectConfig();
	}

	private void detectConfig() {
		File newFile = new File("plugins/Varo");
		if (newFile.listFiles() == null)
			newFile.mkdir();

		if (scanDirectory(newFile)) {
			System.out.println(Main.getConsolePrefix() + "Configurations scanned for mistakes - mistakes have been found");
			System.out.println(Main.getConsolePrefix() + "Plugin will get shut down.");

			this.failed = true;
		} else {
			System.out.println(Main.getConsolePrefix() + "Configurations scanned for mistakes successfully!");
		}
	}

	private boolean scanDirectory(File newFile) {
		for (File file : newFile.listFiles()) {
			if (file.isDirectory()) {
				if (ignoreScan.contains(file.getName()))
					continue;

				if (scanDirectory(file)) return true;
				else continue;
			}

			if (!file.getName().endsWith(".yml")) continue;

			try {
				YamlConfigurationUtil.loadConfiguration(file);
			} catch (NullPointerException e) {
				System.out.println(Main.getConsolePrefix() + "Odd config found, ignoring it");
			} catch (ScannerException e) {
				if (e.getMessage().contains("deserialize"))
					continue;

				System.err.println(Main.getConsolePrefix() + "Config failure detected!");
				System.err.println(Main.getConsolePrefix() + "File: " + file.getName());
				System.err.println(Main.getConsolePrefix() + "Usually the first information of the message gives you the location of the mistake. Just read the error and check the files.");
				System.err.println(Main.getConsolePrefix() + "Message: \n" + e.getMessage());
				return true;
			}
		}

		return false;
	}

	public boolean hasFailed() {
		return this.failed;
	}
}