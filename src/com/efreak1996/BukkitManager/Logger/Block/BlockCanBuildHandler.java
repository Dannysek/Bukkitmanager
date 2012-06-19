package com.efreak1996.BukkitManager.Logger.Block;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.block.Block;

import com.efreak1996.BukkitManager.Logger.BmLoggerConfiguration;

public class BlockCanBuildHandler extends BmBlockHandler {

	public BlockCanBuildHandler(BlockCanBuildLogger arg1logger) {
		super(new File("Block" + File.separator + "BlockCanBuild.log"), arg1logger, BmLoggerConfiguration.get("Block.BlockCanBuild.File"), BmLoggerConfiguration.get("Block.BlockCanBuild.Database"));
	}
	
	public String logFile(HashMap<String, Object> values) {
		String msg = io.translate("Logger.Block.BlockCanBuild");
		return msg;
	}

	@Override
	public void logDb(HashMap<String, Object> values) {
		db.log("Log_BlockCanBuildEvent", "time, block, buildable, material, materialId", "'" + new Date().toGMTString() +  "', '" + (Block) values.get("Block") + "', '" + (Boolean) values.get("Buildable") + "', '" + (Material) values.get("Material") + "', '" + (Integer) values.get("MaterialId") + "'");		
	}
}