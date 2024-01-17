package dev.orhidea.flightheightlimiter.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.google.common.collect.ImmutableSet;
import dev.orhidea.flightheightlimiter.FlightHeightLimiter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Set;

public class FlyLimiter extends PacketAdapter {
    private static final double maxY = 400;
    private Vector lastTicPos;
    public static Set<PacketType> FLYING = ImmutableSet.of(
            PacketType.Play.Client.FLYING, PacketType.Play.Client.POSITION, PacketType.Play.Client.POSITION_LOOK);


    public FlyLimiter() {
        super(FlightHeightLimiter.getInstance(), ListenerPriority.NORMAL, FLYING);
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {

        if (!event.getPacket().getBooleans().read(1)) return;

        double x = event.getPacket().getDoubles().read(0);
        double y = event.getPacket().getDoubles().read(1);
        double z = event.getPacket().getDoubles().read(2);

        Vector currentPos = new Vector(x, y, z);

        if (currentPos.getY() > maxY) {
            setBack(event.getPlayer());
        }

        lastTicPos = currentPos;
    }

    private void setBack(Player player) {
        Bukkit.getServer().getScheduler().runTask(plugin, new Runnable() {
            public void run() {
                player.teleport(new Location(player.getWorld(), lastTicPos.getX(), lastTicPos.getY() - 2, lastTicPos.getZ()));
            }
        });
    }
}
