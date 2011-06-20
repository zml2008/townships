package com.zml.town;

import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.Material;

/**
 * Handle events for all block-related events
 *
 * @author zml2008
 */
public class TownBlockListener extends BlockListener {
    private final TownPlugin plugin;

    public TownBlockListener(final TownPlugin plugin) {
        this.plugin = plugin;
    }
}
