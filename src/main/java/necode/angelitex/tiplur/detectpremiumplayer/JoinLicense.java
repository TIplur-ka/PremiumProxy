package necode.angelitex.tiplur.detectpremiumplayer;

import com.sun.tools.javac.util.List;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class JoinLicense implements Listener {
    private TextComponent errormessage;
    private final ConfigManager config = PremiumProxy.getConfig();
    private final ArrayList<String> list = (ArrayList<String>) config.getStringList("Servers.Protect-Premium");
    private final Set<String> onlineModeServers = new HashSet<>(list);
    private final String Prefix = config.getString("Messages.Prefix") + "&f ";

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onConnect (ServerConnectEvent event) {
        errormessage = new TextComponent(ChatColor.translateAlternateColorCodes('&', Prefix + config.getString("Messages.NotPremium-JSON.Text")));
        errormessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',config.getString("Messages.NotPremium-JSON.Hover"))).create()));
        errormessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, config.getString("Messages.NotPremium-JSON.Command")));
        if (!this.onlineModeServers.contains(event.getTarget().getName())) return;
        if (event.getPlayer().getPendingConnection().isOnlineMode()) return;
        if (event.getReason() == ServerConnectEvent.Reason.JOIN_PROXY) {}
        else {
            event.setCancelled(true);
            if (config.getBoolean("Messages.Format-Text")) {
            event.getPlayer().sendMessage(errormessage);
            } else {
                String errormessage = config.getString("Messages.NotPremium-TEXT");
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Prefix + errormessage));
            }
        }
    }
}
