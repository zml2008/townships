package com.zml.economy;

import com.LRFLEW.register.payment.Method;
import com.LRFLEW.register.payment.Methods;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

/**
 * @author zml2008
 */
public class RegisterManager {
    private Method method;
    private final String name;
    private Logger logger;

    public RegisterManager(Plugin plugin, String name, Logger logger) {
        this.name = name;
        this.logger = logger;
        new RegisterServerListener(this, plugin);
    }

    void setMethod(Method method) {
        this.method = method;
        if (method == null) {
            logger.info(name + ": Payment method was disabled. No longer accepting payments.");
        } else {
            logger.info(name + ": Payment method found (" + method.getName() + " version: " + method.getVersion() + ")");
        }
    }

    public Method getMethod() {
        return method;
    }

    void handleDisable(Plugin plugin) {
        if (Methods.hasMethod()) {
            if (Methods.checkDisabled(plugin)) {
               setMethod(null);
            }
        }
    }

    void handleEnable(Plugin plugin) {
        if (!Methods.hasMethod()) {
            if (Methods.setMethod(plugin)) {
                setMethod(Methods.getMethod());
            }
        }
    }


}
