package rlnt.joincommands;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import rlnt.joincommands.util.PluginLogger;

public final class JoinCommands extends JavaPlugin {

    private static JoinCommands instance;
    private PluginLogger logger = new PluginLogger(this);

    public static JoinCommands getInstance() {
        return instance;
    }

    @Override
    public PluginLogger getLogger() {
        return logger;
    }

    @Override
    public void onEnable() {
        logger.info(ChatColor.GREEN + "Plugin loading...");
        logger.info(ChatColor.GOLD + "developed by RLNT");
        logger.info(ChatColor.GREEN + "Plugin loaded and enabled");
    }

    @Override
    public void onDisable() {
        logger.info(ChatColor.GREEN + "Plugin disabled");
    }
}
