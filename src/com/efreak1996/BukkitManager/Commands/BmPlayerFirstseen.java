package com.efreak1996.BukkitManager.Commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.efreak1996.BukkitManager.BmIOManager;
import com.efreak1996.BukkitManager.BmPermissions;

public class BmPlayerFirstseen {

	private static BmIOManager io;
	private static BmPermissions permHandler;

	public void initialize() {
		io = new BmIOManager();
		permHandler = new BmPermissions();
	}
	public void shutdown() {}
	
	public void cmd(CommandSender sender, String[] args, boolean prefixed) {
		if (prefixed) {
			if (args.length < 2) io.sendFewArgs(sender, "/bm player firstseen [player]");
			else if (args.length > 3) io.sendManyArgs(sender, "/bm player firstseen [player]");
			else {
				if (args.length == 2 && sender instanceof Player) {
					if (permHandler.has(sender, "bm.player.firstseen.your")) io.send(sender, io.translate("Command.Player.Firstseen.Your").replaceAll("%firstseen%", String.valueOf(((Player) sender).getFirstPlayed())));
				}else if (args.length == 3) {
					if (permHandler.has(sender, "bm.player.firstseen.other")) {
						OfflinePlayer player = Bukkit.getOfflinePlayer(args[2]);
						if (player != null) io.send(sender, io.translate("Command.Player.Firstseen.Other").replaceAll("%player%", player.getName()).replaceAll("%firstseen%", String.valueOf(player.getFirstPlayed())));
						else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == 2) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}
		}else {
			if (args.length < 1) io.sendFewArgs(sender, "/player firstseen [player]");
			else if (args.length > 2) io.sendManyArgs(sender, "/player firstseen [player]");
			else {
				if (args.length == 1 && sender instanceof Player) {
					if (permHandler.has(sender, "bm.player.firstseen.your")) io.send(sender, io.translate("Command.Player.Firstseen.Your").replaceAll("%firstseen%", String.valueOf(((Player) sender).getFirstPlayed())));
				}else if (args.length == 2) {
					if (permHandler.has(sender, "bm.player.firstseen.other")) {
						OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
						if (player != null) io.send(sender, io.translate("Command.Player.Firstseen.Other").replaceAll("%player%", player.getName()).replaceAll("%firstseen%", String.valueOf(player.getFirstPlayed())));
						else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == 1) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}
		}
	}
}
