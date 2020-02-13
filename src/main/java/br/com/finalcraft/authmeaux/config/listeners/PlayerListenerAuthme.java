package br.com.finalcraft.authmeaux.config.listeners;

import br.com.finalcraft.authmeaux.config.api.FCAuthMeAPI;
import br.com.finalcraft.authmeaux.config.playerdata.AuthPlayerData;
import br.com.finalcraft.evernifecore.ListenerHelper;
import fr.xephi.authme.AuthMe;
import fr.xephi.authme.events.LoginEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerListenerAuthme implements Listener {

    public static void registerIfPossible(JavaPlugin plugin){
        ListenerHelper.registerEvents(new PlayerListenerAuthme(plugin));
    }

    private AuthMe pluginInstance;

    public PlayerListenerAuthme(JavaPlugin plugin) {
        this.pluginInstance = (AuthMe) plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    void onPlayerLogin(LoginEvent event) {
        final Player player = event.getPlayer();

        AuthPlayerData authPlayerData = FCAuthMeAPI.getAuthPlayerData(player);

        if (authPlayerData.wasFlying()){
            if (!player.isFlying()) player.setFlying(true);
        }else {
            if (player.isFlying()) player.setFlying(false);
        }

        if (authPlayerData.wasAllowFlight()){
            if (!player.getAllowFlight()) player.setAllowFlight(true);
        }else {
            if (player.getAllowFlight()) player.setAllowFlight(false);
        }

        authPlayerData.setQuitLocation(null);

        //Do the check once again because this is minecraft!
        new BukkitRunnable(){
            @Override
            public void run() {
                if (player.isOnline()){
                    if (authPlayerData.wasFlying()){
                        if (!player.isFlying()) player.setFlying(true);
                    }else {
                        if (player.isFlying()) player.setFlying(false);
                    }
                    if (authPlayerData.wasAllowFlight()){
                        if (!player.getAllowFlight()) player.setAllowFlight(true);
                    }else {
                        if (player.getAllowFlight()) player.setAllowFlight(false);
                    }
                }
            }
        }.runTaskLater(pluginInstance, 2);
    }



}
