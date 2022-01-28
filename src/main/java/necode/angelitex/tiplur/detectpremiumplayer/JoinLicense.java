package necode.angelitex.tiplur.detectpremiumplayer;

import com.sun.tools.javac.util.List;
import net.md_5.bungee.api.*;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class JoinLicense implements Listener {
    private TextComponent errormessage;
    private final ConfigManager config = PremiumProxy.getConfig();
    private final ArrayList<String> list = (ArrayList<String>) config.getStringList("Servers.Protect-Premium");
    private final Set<String> onlineModeServers = new HashSet<>(list);
    private final String Prefix = config.getString("Messages.Prefix") + "&f ";
    private final Title title = ProxyServer.getInstance().createTitle();
    private final String TextTitle = config.getString("Messages.Title.Title");
    private final String TextSubTitle = config.getString("Messages.Title.SubTitle");
    private final String TextActionBar = config.getString("Messages.ActionBar.Text");


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onConnect (ServerConnectEvent event) {
        ProxiedPlayer player = event.getPlayer();

        errormessage = new TextComponent(ChatColor.translateAlternateColorCodes('&', Prefix + config.getString("Messages.NotPremium-JSON.Text")));
        errormessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',config.getString("Messages.NotPremium-JSON.Hover"))).create()));
        errormessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, config.getString("Messages.NotPremium-JSON.Command")));

        if (!this.onlineModeServers.contains(event.getTarget().getName())) return;
        if (player.getPendingConnection().isOnlineMode()) return;

        event.setCancelled(true);

        if (config.getBoolean("Messages.Format-Text")) {
            player.sendMessage(errormessage);
        } else {
            String errormessage = config.getString("Messages.NotPremium-TEXT");
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Prefix + errormessage));
        }

        if (config.getBoolean("Messages.Title.Enable")) {
            title.reset();
            title.title(new TextComponent(ChatColor.translateAlternateColorCodes('&',TextTitle)));
            title.subTitle(new TextComponent(ChatColor.translateAlternateColorCodes('&',TextSubTitle)));
            title.send(player);
        }
        if (config.getBoolean("Messages.ActionBar.Enable")) {
            player.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.translateAlternateColorCodes('&', TextActionBar)));
        }

    }
}
