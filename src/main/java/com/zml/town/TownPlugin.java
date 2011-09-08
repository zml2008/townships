package com.zml.town;

import com.sk89q.regionbook.RegionManager;
import com.zml.economy.RegisterManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;

import java.util.logging.Logger;
import java.io.*;

import org.bukkit.util.config.Configuration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;

import com.sk89q.minecraft.util.commands.*;
import com.sk89q.bukkit.migration.*;
import com.zml.town.commands.*;

// Other plugins!
import com.sk89q.regionbook.bukkit.RegionBookPlugin;

/**
 * Plugin for mechanics tweaks on GoMinecraft.
 *
 * @author zml2008
 */
public class TownPlugin extends JavaPlugin {
    private final TownBlockListener blockListener = new TownBlockListener(this);
    private final TownPlayerListener playerListener = new TownPlayerListener(this);
    public PluginDescriptionFile pdf = getDescription();

    public Configuration config;
    private RegionBookPlugin rb;
    public RegisterManager economy;

    /**
     * Manager for commands. This automatically handles nested commands,
     * permissions checking, and a number of other fancy command things. We just
     * set it up and register commands against it.
     */
    private final CommandsManager<CommandSender> commands;

    /**
     * Logger for messages.
     */
    protected static final Logger logger = Logger
            .getLogger("Minecraft.Townships");

    /**
     * Processes queries for permissions information. The permissions manager is
     * from WorldEdit and it automatically handles looking up permissions
     * systems and picking the right one. Townships just needs to call the
     * permission methods.
     */
    private PermissionsResolverManager perms;

    public TownPlugin() {
        final TownPlugin plugin = this;
        commands = new CommandsManager<CommandSender>() {
            @Override
            public boolean hasPermission(CommandSender player, String perm) {
                return plugin.hasPermission(player, perm);
            }
        };

        // Register command classes
        commands.register(GeneralCmds.class);
        commands.register(AdminCmds.class);
    }

    public void onDisable() {
        logger.info(pdf.getName() + " version "
                + pdf.getVersion() + " is disabled!");
    }

    public void onEnable() {
        // Register events
        PluginManager pm = getServer().getPluginManager();
        // Events
        pm.registerEvent(Event.Type.PLAYER_MOVE, playerListener, Priority.High, this);
        pm.registerEvent(Event.Type.PLAYER_CHAT, playerListener, Priority.High, this);

        rb = (RegionBookPlugin) getServer().getPluginManager().getPlugin("RegionBook");

        // Permissions
        perms = new PermissionsResolverManager(this, "Townships", logger);
        perms.load();

        // Commands
        commands.register(GeneralCmds.class);

        getDataFolder().mkdirs();
        createDefaultConfiguration("config.yml");

        this.config = getConfiguration();
        this.config.load();

        economy = new RegisterManager(this, getDescription().getName(), logger);


        PluginDescriptionFile pdfFile = this.getDescription();
        logger.info(pdfFile.getName() + " version "
                + pdfFile.getVersion() + " is enabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label,
                             String[] args) {
        try {
            commands.execute(cmd.getName(), args, sender, this, sender);
        } catch (CommandPermissionsException e) {
            sender.sendMessage(ChatColor.RED + "You don't have permission.");
        } catch (MissingNestedCommandException e) {
            sender.sendMessage(ChatColor.RED + e.getUsage());
        } catch (CommandUsageException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
            sender.sendMessage(ChatColor.RED + e.getUsage());
        } catch (WrappedCommandException e) {
            if (e.getCause() instanceof NumberFormatException) {
                sender.sendMessage(ChatColor.RED
                        + "Number expected, string received instead.");
            } else {
                sender.sendMessage(ChatColor.RED
                        + "An error has occurred. See console.");
                e.printStackTrace();
            }
        } catch (CommandException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
        }

        return true;
    }

    /**
     * Check whether a player is in a group.
     *
     * @param player
     * @param group
     * @return
     */
    public boolean inGroup(Player player, String group) {
        try {
            return perms.inGroup(player.getName(), group);
        } catch (Throwable t) {
            t.printStackTrace();
            return false;
        }
    }

    /**
     * Get the groups of a player.
     *
     * @param player
     * @return
     */
    public String[] getGroups(Player player) {
        try {
            return perms.getGroups(player.getName());
        } catch (Throwable t) {
            t.printStackTrace();
            return new String[0];
        }
    }

    /**
     * Checks permissions.
     *
     * @param sender
     * @param perm
     * @return
     */
    public boolean hasPermission(CommandSender sender, String perm) {
        if (sender.isOp()) {
            if (sender instanceof Player) {
                if (((Player) sender).isOp()) {
                    return true;
                }
            } else {
                return true;
            }
        }

        // Invoke the permissions resolver
        if (sender instanceof Player) {
            return perms.hasPermission(((Player) sender).getName(), perm);
        }

        return false;
    }

    public boolean hasPermission(Player player, String perm) {
        if (player.isOp()) return true;
        return perms.hasPermission(player.getName(), perm);
    }

    /**
     * Checks permissions and throws an exception if permission is not met.
     *
     * @param sender
     * @param perm
     * @throws CommandPermissionsException
     */
    public void checkPermission(CommandSender sender, String perm)
            throws CommandPermissionsException {
        if (!hasPermission(sender, perm)) {
            throw new CommandPermissionsException();
        }
    }

    /**
     * Checks to see if the sender is a player, otherwise throw an exception.
     *
     * @param sender
     * @return
     * @throws CommandException
     */
    public Player checkPlayer(CommandSender sender) throws CommandException {
        if (sender instanceof Player) {
            return (Player) sender;
        } else {
            throw new CommandException("A player is expected.");
        }
    }

    public RegionManager getRegionMgr() {
        return rb.getGlobalRegionManager();
    }

    // Config loader. Lots of code, eh. "Borrowed" from CommandBook.
    protected void createDefaultConfiguration(String name) {
        File actual = new File(getDataFolder(), name);
        if (!actual.exists()) {

            InputStream input = this.getClass().getResourceAsStream(
                    "/defaults/" + name);
            if (input != null) {
                FileOutputStream output = null;

                try {
                    output = new FileOutputStream(actual);
                    byte[] buf = new byte[8192];
                    int length = 0;
                    while ((length = input.read(buf)) > 0) {
                        output.write(buf, 0, length);
                    }

                    logger.info(getDescription().getName()
                            + ": Default configuration file written: " + name);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (input != null)
                            input.close();
                    } catch (IOException e) {
                        logger.info("ERROR: File error");
                    }

                    try {
                        if (output != null)
                            output.close();
                    } catch (IOException e) {
                        logger.info("ERROR: File error");
                    }

                }
            }
        }

    }
}