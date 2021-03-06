package br.com.finalcraft.authmeaux.config.api;

import br.com.finalcraft.authmeaux.config.playerdata.AuthPlayerData;
import br.com.finalcraft.evernifecore.config.playerdata.PlayerController;
import br.com.finalcraft.evernifecore.config.playerdata.PlayerData;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class FCAuthMeAPI {

    public static AuthPlayerData getAuthPlayerData(PlayerData playerData){
        return AuthPlayerData.getOrCreateAuthPlayerData(playerData);
    }

    public static AuthPlayerData getAuthPlayerData(OfflinePlayer player){
        return getAuthPlayerData(player.getName());
    }

    public static AuthPlayerData getAuthPlayerData(String playerName){
        return getAuthPlayerData(PlayerController.getPlayerData(playerName));
    }

    public static Location getPlayerQuitLocation(Player player){
        try {
            PlayerData playerData = PlayerController.getPlayerData(player);
            if (playerData != null){ //If null, has never been online before
                AuthPlayerData authPlayerData = playerData.getOrCreatePDSection(AuthPlayerData.class);
                return authPlayerData.getQuitLocation();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
