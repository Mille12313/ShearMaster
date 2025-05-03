package bloody.devmules.shearMaster;

import bloody.devmules.shearMaster.config.ConfigManager;
import bloody.devmules.shearMaster.listeners.ShearListener;
import org.bukkit.plugin.java.JavaPlugin;

public class ShearMaster extends JavaPlugin {

    private ConfigManager configManager;

    @Override
    public void onEnable() {
        // Config laden en aanmaken als deze niet bestaat
        saveDefaultConfig();
        configManager = new ConfigManager(this);

        // Event listener registreren
        getServer().getPluginManager().registerEvents(new ShearListener(this), this);

        // Command executor en tab-completer registreren
        getCommand("shearmaster").setExecutor(new ShearMasterCommand(this));
        getCommand("shearmaster").setTabCompleter(new ShearMasterTabCompleter(this));

        getLogger().info("ShearMaster plugin enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("ShearMaster plugin disabled.");
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}
