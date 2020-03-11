package rlnt.joincommands.util;

import java.util.logging.LogRecord;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

public class PluginLogger extends Logger {

    public PluginLogger(Plugin plugin) {
        super(plugin.getDescription().getName(), null);
        setParent(plugin.getServer().getLogger());
    }

    @Override
    public void log(LogRecord record) {
        record.setMessage(ChatColor.translateAlternateColorCodes('&', record.getMessage()) + ChatColor.RESET);
        super.log(record);
    }
}
