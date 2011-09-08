package com.zml.economy;

import org.bukkit.event.Event;
import org.bukkit.event.server.*;

import org.bukkit.plugin.Plugin;

public class RegisterServerListener extends ServerListener {
    private Plugin plugin;
    private RegisterManager manager;

    public RegisterServerListener(RegisterManager manager, Plugin plugin) {
        this.plugin = plugin;
        this.manager = manager;
        register();
    }

    @Override
    public void onPluginDisable(PluginDisableEvent event) {
        manager.handleDisable(event.getPlugin());
    }

    @Override
    public void onPluginEnable(PluginEnableEvent event) {
        manager.handleEnable(plugin);
    }

    private void register() {
        plugin.getServer().getPluginManager().registerEvent(
                Event.Type.PLUGIN_DISABLE, this, Event.Priority.Normal, plugin);
        plugin.getServer().getPluginManager().registerEvent(
                Event.Type.PLUGIN_ENABLE, this, Event.Priority.Normal, plugin);
    }
}
