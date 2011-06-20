package com.zml.town.commands;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import com.zml.town.TownPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Commands for nation management.
 *
 * @author zml2008
 */
public class NationCmds {


    @Command(aliases = {"new"}, usage = "", desc = "Reload configs", flags = "", min = 0, max = 1)
    @CommandPermissions({"townships.admin.reload"})
    public static void add(CommandContext args, TownPlugin plugin,
                           CommandSender sender) throws CommandException {
    }
}
