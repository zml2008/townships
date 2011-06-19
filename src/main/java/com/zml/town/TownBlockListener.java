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

    @Override
    public void onSignChange(SignChangeEvent event) {
        if (event.getLine(0).equalsIgnoreCase("[NOTICE]")) {
            boolean exists = true;
            boolean canDo = true;
            String name = event.getLine(1);
            Player player = event.getPlayer();
            try {
                plugin.getText(event.getLine(1));
            } catch (Exception e) {
                exists = false;
                player.sendMessage(ChatColor.RED + "This note does not exist");
                event.setCancelled(true);
                plugin.dropSign(event.getBlock(), player);
            }
            if (exists) {
                if (plugin.isRestricted(event.getLine(1))) {
                    if (!plugin.permissionHandler.has(player,
                            "mech.sign.restricted" + name)) {
                        event.setCancelled(true);
                        plugin.dropSign(event.getBlock(), player);
                        canDo = false;
                    }
                } else {
                    if (!plugin.permissionHandler.has(player, "mech.sign.safe."
                            + name)) {
                        event.setCancelled(true);
                        plugin.dropSign(event.getBlock(), player);
                        canDo = false;
                    }
                }
                if (!canDo) {
                    player.sendMessage(ChatColor.RED
                            + "You don\'t have permission to place this sign");
                }
            }
        }
    }

    @Override
    public void onBlockCanBuild(BlockCanBuildEvent event) {
        if (event.getMaterial() == Material.TORCH) {
            event.setBuildable(true);
        }

    }

    @Override
    public void onBlockPhysics(BlockPhysicsEvent event) {
        if (event.getBlock().getTypeId() == 50) {
            event.setCancelled(true);
            return;
        }
    }

    @Override
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType() == Material.TORCH) {
            event.setCancelled(true);
            event.getBlock().setType(Material.TORCH);
        }
    }
}
