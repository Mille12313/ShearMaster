package bloody.devmules.shearMaster.config;

import bloody.devmules.shearMaster.ShearMaster;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Set;
import java.util.HashSet;

public class ConfigManager {

    private ShearMaster plugin;
    private FileConfiguration config;

    // Set of animals that cannot be sheared
    private static final Set<String> UNSHEARABLE_ANIMALS = new HashSet<String>() {{
        add("pig");
        add("wolf");
        add("cat");
        // Voeg hier andere dieren toe die niet geschoren mogen worden
    }};

    public ConfigManager(ShearMaster plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    public void reloadConfig() {
        plugin.reloadConfig();
        this.config = plugin.getConfig();
    }

    // Methode om te controleren of een dier shearable is
    public boolean isShearable(String animal) {
        return config.getBoolean("shearable-animals." + animal + ".shearing.enabled", false);
    }

    // Methode om te controleren of een dier sliceable is
    public boolean isSliceable(String animal) {
        return config.getBoolean("shearable-animals." + animal + ".slicing.enabled", false);
    }

    // Controle of een dier shearable kan zijn (dus dieren zoals pig blokkeren)
    public boolean canBeSheared(String animal) {
        return !UNSHEARABLE_ANIMALS.contains(animal);
    }

    // Toggle shearable status
    public void toggleShearable(String animal) {
        if (!canBeSheared(animal)) return;
        boolean current = config.getBoolean("shearable-animals." + animal + ".shearing.enabled", false);
        config.set("shearable-animals." + animal + ".shearing.enabled", !current);
        plugin.saveConfig();
    }

    // Toggle sliceable status
    public void toggleSliceable(String animal) {
        boolean current = config.getBoolean("shearable-animals." + animal + ".slicing.enabled", false);
        config.set("shearable-animals." + animal + ".slicing.enabled", !current);
        plugin.saveConfig();
    }

    // Check of dier geconfigureerd is
    public boolean isAnimalConfigured(String animal) {
        return config.contains("shearable-animals." + animal);
    }

    public boolean hasShearableOption(String animal) {
        return config.contains("shearable-animals." + animal + ".shearing.enabled");
    }

    public boolean hasSliceableOption(String animal) {
        return config.contains("shearable-animals." + animal + ".slicing.enabled");
    }

    public Set<String> getAnimals() {
        if (config.getConfigurationSection("shearable-animals") == null) {
            return new HashSet<>();
        }
        return config.getConfigurationSection("shearable-animals").getKeys(false);
    }

    // ✅ EXP-methodes toegevoegd BINNEN de class
    public int getShearExp(String animal) {
        return config.getInt("shearable-animals." + animal + ".shearing.exp", 1);
    }

    public int getSliceExp(String animal) {
        return config.getInt("shearable-animals." + animal + ".slicing.exp", 1);
    }
}
