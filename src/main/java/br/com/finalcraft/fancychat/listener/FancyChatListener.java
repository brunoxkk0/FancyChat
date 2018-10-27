package br.com.finalcraft.fancychat.listener;

import br.com.finalcraft.fancychat.PermissionNodes;
import br.com.finalcraft.fancychat.api.FancyChatSendChannelMessageEvent;
import br.com.finalcraft.fancychat.config.fancychat.FancyChannel;
import br.com.finalcraft.fancychat.config.fancychat.FancyTag;
import br.com.finalcraft.fancychat.config.lang.FancyChatLang;
import br.com.finalcraft.fancychat.fancytextchat.FancyText;
import br.com.finalcraft.fancychat.util.ChannelManager;
import br.com.finalcraft.fancychat.util.IgnoreUtil;
import br.com.finalcraft.fancychat.util.MuteUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

public class FancyChatListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat(AsyncPlayerChatEvent event){

		if (event.isCancelled()){
			return;
		}
		event.setCancelled(true);

		Player msgSender = event.getPlayer();

		FancyChannel fancyChannel = ChannelManager.getPlayerLockChannel(msgSender);

		if (MuteUtil.isMuted(msgSender)){
			msgSender.sendMessage(FancyChatLang.getLang("player.muted.perma").replace("{channel}", fancyChannel.getName()));
			return;
		}

		FancyChatSendChannelMessageEvent sendMessageEvent = new FancyChatSendChannelMessageEvent(msgSender, fancyChannel, event.getMessage());
		Bukkit.getServer().getPluginManager().callEvent(sendMessageEvent);
		if (sendMessageEvent.isCancelled()){
			return;
		}

		String rawMsg = sendMessageEvent.getMessage();
		if (msgSender.hasPermission(PermissionNodes.chatColor)){
			rawMsg = ChatColor.translateAlternateColorCodes('&', rawMsg);
		}

		List<FancyText> textChatList = new ArrayList<FancyText>();
        for (FancyTag fancyTag : sendMessageEvent.getChannel().getTagsFromThisBuilder()){
        	FancyText fancyText = fancyTag.getFancyText().parsePlaceholdersAndClone(msgSender);
        	fancyText.text = fancyText.text.replace("{msg}",rawMsg);
        	textChatList.add(fancyText);
		}


		int amoutOfPlayerTharReceivedThis = 0;
		for (Player onlinePlayerToSendMessage : sendMessageEvent.getChannel().getPlayersOnThisChannel()){
			if (sendMessageEvent.getChannel().getDistance() <= -1
					|| msgSender.getLocation().distance(onlinePlayerToSendMessage.getLocation()) <=  sendMessageEvent.getChannel().getDistance()){
				amoutOfPlayerTharReceivedThis++;
				if (!IgnoreUtil.isIgnoring(onlinePlayerToSendMessage.getName(), sendMessageEvent.getSender().getName())){
					FancyText.sendTo(onlinePlayerToSendMessage,textChatList);
				}
			}
		}

		if (amoutOfPlayerTharReceivedThis <= 1 && sendMessageEvent.getChannel().getDistance() > -1){
			msgSender.sendMessage("§6§l ▶ §cNão tem ninguem perto de você para receber essa mensagem...");
		}

	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		Player player = e.getPlayer();
		ChannelManager.playerJoined(player);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		Player player = e.getPlayer();
		ChannelManager.playerLeaved(player);
	}

}
