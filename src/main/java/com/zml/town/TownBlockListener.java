package com.zml.town;

import org.bukkit.event.block.BlockListener;

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
