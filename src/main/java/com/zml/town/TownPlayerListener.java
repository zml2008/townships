package com.zml.town;

import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.block.Action;

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

    @Override
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();
            Material mat = block.getType();
            if ((mat == Material.WALL_SIGN) || (mat == Material.SIGN_POST)) {
                Sign sign = (Sign) block.getState();
                if (sign.getLine(0).equalsIgnoreCase("[NOTICE]")) {
                    String[] announce = plugin.getText(sign.getLine(1));
                    for (int i = 0; (i <= (announce.length - 1)); i++) {
                        player.sendMessage(announce[i]);
                    }
                }
            }
        }
    }
    
    @Override
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        BedPersist bed = plugin.getDatabase().find(BedPersist.class).where().ieq("playerName", player.getName()).findUnique();
        boolean perm = plugin.hasPermission(player, "mech.bed.respawn");
        if (bed != null && perm) {
            event.setRespawnLocation(bed.getLocation());
        } else {
            String msg;
            if (perm) {msg = "You do not have permission to respawn at beds";} else {msg = "You do not have a stored bed";}
            player.sendMessage(ChatColor.GRAY + msg);
        }
    }
    
    @Override
    public void onPlayerBedLeave(PlayerBedLeaveEvent event) {
        Player player = event.getPlayer();
        if (plugin.hasPermission(player, "mech.bed.store")) {
        BedPersist bed = plugin.getDatabase().find(BedPersist.class).where().ieq("playerName", player.getName()).findUnique();
        if (bed == null) {
            bed = new BedPersist();
            bed.setPlayer(player);
        }
        bed.setLocation(player.getLocation());
        plugin.getDatabase().save(bed);
        player.sendMessage(ChatColor.AQUA + "Your bed location saved.");
        } else {
            player.sendMessage(ChatColor.RED + "You can't set a respawn bed!");
        }
    }
}
