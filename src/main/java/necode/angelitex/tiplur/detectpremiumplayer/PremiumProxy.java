package necode.angelitex.tiplur.detectpremiumplayer;

import net.md_5.bungee.api.plugin.Plugin;

public final class PremiumProxy extends Plugin {
    private static PremiumProxy instance;

    private static ConfigManager config;
    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        config = new ConfigManager();
        config.init();

        getProxy().getPluginManager().registerListener(this, new JoinLicense());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public static PremiumProxy getInstance() {
        return instance;
    }

    public static ConfigManager getConfig() {
        return config;
    }
}
