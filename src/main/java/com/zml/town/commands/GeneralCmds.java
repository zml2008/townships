package com.zml.town.commands;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import com.sk89q.minecraft.util.commands.NestedCommand;
import com.zml.town.TownPlugin;

import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class GeneralCmds {

    @Command(aliases = {"townships"}, desc = "Townships plugin commands", flags = "", min = 1, max = 1)
    @NestedCommand({AdminCmds.class})
    public static void mech() throws CommandException {

    }

    @Command(aliases = {"nation"}, desc = "Nation management ", flags = "", min = 1, max = -1)
    @NestedCommand({NationCmds.class})
    public static void nation() throws CommandException {

    }

    @Command(aliases = {"plot"}, desc = "Plot management ", flags = "", min = 1, max = -1)
    @NestedCommand({PlotCmds.class})
    public static void plot() throws CommandException {

    }

    @Command(aliases = {"town"}, desc = "Town management ", flags = "", min = 1, max = -1)
    @NestedCommand({TownCmds.class})
    public static void town() throws CommandException {

    }
}
