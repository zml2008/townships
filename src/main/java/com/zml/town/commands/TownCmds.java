package com.zml.town.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import com.sk89q.minecraft.util.commands.Command;
import com.zml.town.TownPlugin;

/**
 * Commands for sneak toggle.
 * 
 * @author zml2008
 */
public class TownCmds  {


    @Command(aliases = { "reload" }, usage = "", desc = "Reload configs", flags = "", min = 0, max = 1)
    @CommandPermissions({ "townships.admin.reload" })
    public static void give(CommandContext args, TownPlugin plugin,
            CommandSender sender) throws CommandException {
        plugin.config.load();
        sender.sendMessage(ChatColor.GREEN + "Config File reloaded");
    }
}
