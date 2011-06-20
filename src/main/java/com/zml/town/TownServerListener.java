package com.zml.town;

import org.bukkit.event.server.*;

import com.nijikokun.register.payment.Method;
import com.nijikokun.register.payment.Methods;

public class TownServerListener extends ServerListener {
    private TownPlugin plugin;
    private Methods Methods = null;

    public TownServerListener(TownPlugin plugin) {
        this.plugin = plugin;
        this.Methods = new Methods();
    }

    @Override
    public void onPluginDisable(PluginDisableEvent event) {
        // Check to see if the plugin thats being disabled is the one we are using
        if (this.Methods != null && this.Methods.hasMethod()) {
            Boolean check = this.Methods.checkDisabled(event.getPlugin());

            if (check) {
                this.plugin.Method = null;
                System.out.println("[" + plugin.pdf.getName() + "] Payment method was disabled. No longer accepting payments.");
            }
        }
    }

    @Override
    public void onPluginEnable(PluginEnableEvent event) {
        // Check to see if we need a payment method
        if (!this.Methods.hasMethod()) {
            if (this.Methods.setMethod(event.getPlugin())) {
                // You might want to make this a public variable inside your MAIN class public Method Method = null;
                // then reference it through this.plugin.Method so that way you can use it in the rest of your plugin ;)
                this.plugin.Method = this.Methods.getMethod();
                System.out.println("[" + plugin.pdf.getName() + "] Payment method found (" + this.plugin.Method.getName() + " version: " + this.plugin.Method.getVersion() + ")");
            }
        }
    }
}
