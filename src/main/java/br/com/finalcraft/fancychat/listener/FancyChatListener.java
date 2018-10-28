package br.com.finalcraft.fancychat.listener;

import br.com.finalcraft.fancychat.config.fancychat.FancyChannel;
import br.com.finalcraft.fancychat.util.ChannelManager;
import br.com.finalcraft.fancychat.util.PublicMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class FancyChatListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent event){
		event.setCancelled(true);

		Player player = event.getPlayer();

		FancyChannel fancyChannel = ChannelManager.getPriorityChannel(player);

		PublicMessage.sendPublicMessage(player,fancyChannel,event.getMessage());
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		ChannelManager.playerJoined(e.getPlayer());
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		ChannelManager.playerLeaved(e.getPlayer());
	}

}
