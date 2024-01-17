package dev.orhidea.flightheightlimiter;

import com.comphenix.protocol.ProtocolLibrary;
import dev.orhidea.flightheightlimiter.listener.FlyLimiter;
import org.bukkit.plugin.java.JavaPlugin;

public final class FlightHeightLimiter extends JavaPlugin {

    private static FlightHeightLimiter instance;

    public static FlightHeightLimiter getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        ProtocolLibrary.getProtocolManager().addPacketListener(new FlyLimiter());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
