package com.efreak1996.BukkitManager;

import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

public class BmAutosaveThread extends Thread {

	private static boolean run = true;
	private static BmConfiguration config;
	public static Date lastSave;
	public static boolean saveInProgress = false;
	private static BmIOManager io;
	private static Plugin plugin;

	public void initialize() {
		plugin = BmPlugin.getPlugin();
		config = new BmConfiguration();
		io = new BmIOManager();
		lastSave = new Date();
	}
	
	public void setRun(boolean run) {
		this.run = run;
	}
	
	public boolean getRun() {
		return run;
	}

	private void savePlayers() {
		io.sendConsole(io.translate("Autosave.Saving.Players"));
		Bukkit.getServer().savePlayers();
	}

	private int saveWorlds() {
		int i = 0;
		List<World> worlds = Bukkit.getServer().getWorlds();
		for (World world : worlds) {
			io.sendConsole(io.translate("Autosave.Saving.World").replaceAll("%world%", world.getName()));
			world.save();
			i++;
		}
		return i;
	}

	public void performSave() {
		if (saveInProgress) {
			io.sendConsoleWarning(io.translate("Autosave.SaveInProgress"));
			return;
		}
		if (config.getBoolean("Autosave.NoOffline")) {
			if (Bukkit.getServer().getOnlinePlayers().length == 0) {
				io.sendConsole(io.translate("Autosave.NoPlayer"));
				return;
			}
		}
		saveInProgress = true;
		savePlayers();
		io.sendConsole(io.translate("Autosave.Saved.Players"));
		int saved = 0;
		saved += saveWorlds();
		io.sendConsole(io.translate("Autosave.Saved.Worlds").replaceAll("%worlds%", String.valueOf(saved)));
		lastSave = new Date();
		saveInProgress = false;
	}
	
	public void run() {
		while (run) {
			if(config.getInt("Autosave.Interval") == 0) {
				try {
					Thread.sleep(5000);
				} catch(InterruptedException e) {
					if (config.getDebug()) e.printStackTrace();
				}
				continue;
			}

			for (int i = 0; i < config.getInt("Autosave.Interval"); i++) {
				try {
					if (!run) {
						/*if (config.Config.getBoolean("General.Debug")) {
							log.info(String.format("[%s] Graceful quit of AutoSaveThread", plugin.getDescription().getName()));
						}*/
					return;
					}
					/*boolean warn = false;
					for (int w : config.Config.getIntegerList("Autosave.Warntimes")) {
						if (w != 0 && w + i == config.Config.getInt("Autosave.Interval")) {
							warn = true;
						}
					}

					if (warn) {
						int timeLeft = config.Config.getInt("Autosave.Interval") - i;
						out.sendBroadcast("Autosave in " + timeLeft);
						out.sendConsole("Autosave in " + timeLeft);
					}*/
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					if (config.getDebug()) e.printStackTrace();
				}
			}
			if (config.getSaveMode().equalsIgnoreCase("SYNC")) {
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						performSave();
						lastSave = new Date();
					}
				});
			}else {
				Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
					public void run() {
						performSave();
						lastSave = new Date();
					}
				});
			}
		}
	}
}

