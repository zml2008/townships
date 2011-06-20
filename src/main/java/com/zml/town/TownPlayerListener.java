package com.zml.town;

import org.bukkit.event.player.PlayerListener;

/**
 * Handle events for player stuff
 *
 * @author zml2008
 */
public class TownPlayerListener extends PlayerListener {
    private final TownPlugin plugin;

    public TownPlayerListener(TownPlugin instance) {
        plugin = instance;
    }
}


