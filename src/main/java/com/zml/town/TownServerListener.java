package com.zml.town;

import org.bukkit.event.server.*;

import com.nijikokun.register.payment.Methods;

public class TownServerListener extends ServerListener {
    private TownPlugin plugin;
    private Methods methods = null;

    public TownServerListener(TownPlugin plugin) {
        this.plugin = plugin;
        this.methods = new Methods();
    }

    @Override
    public void onPluginDisable(PluginDisableEvent event) {
        // Check to see if the plugin that's being disabled is the one we are using
        if (methods != null && methods.hasMethod()) {
            Boolean check = methods.checkDisabled(event.getPlugin());

            if (check) {
                plugin.method = null;
                System.out.println("[" + plugin.getDescription().getName() + "] Payment method was disabled. No longer accepting payments.");
            }
        }
    }

    @Override
    public void onPluginEnable(PluginEnableEvent event) {
        // Check to see if we need a payment method
        if (!methods.hasMethod()) {
            if (methods.setMethod(event.getPlugin())) {
                plugin.method = methods.getMethod();
                System.out.println("[" + plugin.getDescription().getName() + "] Payment method found (" + plugin.method.getName() + " version: " + plugin.method.getVersion() + ")");
            }
        }
    }
}
