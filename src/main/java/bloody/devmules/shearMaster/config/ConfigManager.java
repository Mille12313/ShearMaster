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
        return config.getBoolean("shearable-animals." + animal + ".shearable", false);
    }

    // Methode om te controleren of een dier sliceable is
    public boolean isSliceable(String animal) {
        return config.getBoolean("shearable-animals." + animal + ".sliceable", false);
    }

    // Controle of een dier shearable kan zijn (dus dieren zoals pig blokkeren)
    public boolean canBeSheared(String animal) {
        return !UNSHEARABLE_ANIMALS.contains(animal);  // Controleer of het dier in de set van niet-shearable dieren zit
    }

    // Methode om de loot op te halen voor scheren
    public String getLoot(String animal) {
        return config.getString("shearable-animals." + animal + ".loot.material", "LEATHER");
    }

    // Methode om de hoeveelheid loot op te halen voor scheren
    public int getLootAmount(String animal) {
        return config.getInt("shearable-animals." + animal + ".loot.amount", 1);
    }

    // Methode om de loot op te halen voor slicen
    public String getSliceLoot(String animal) {
        return config.getString("shearable-animals." + animal + ".slice.loot.material", "PORKCHOP");
    }

    // Methode om de hoeveelheid loot op te halen voor slicen
    public int getSliceLootAmount(String animal) {
        return config.getInt("shearable-animals." + animal + ".slice.loot.amount", 1);
    }

    // Methode om het aantal EXP op te halen (standaard 1)
    public int getExpAmount(String animal) {
        return config.getInt("shearable-animals." + animal + ".exp", 1);
    }

    // Toggle shearable status van een dier
    public void toggleShearable(String animal) {
        // Controleer eerst of het dier shearable kan zijn
        if (!canBeSheared(animal)) {
            return;  // Blokkeer toggling als het dier niet shearable is
        }
        boolean currentStatus = config.getBoolean("shearable-animals." + animal + ".shearable", false);
        config.set("shearable-animals." + animal + ".shearable", !currentStatus);
        plugin.saveConfig();
    }

    // Toggle sliceable status van een dier
    public void toggleSliceable(String animal) {
        boolean currentStatus = config.getBoolean("shearable-animals." + animal + ".sliceable", false);
        config.set("shearable-animals." + animal + ".sliceable", !currentStatus);
        plugin.saveConfig();
    }

    // Methode om te controleren of een dier is geconfigureerd
    public boolean isAnimalConfigured(String animal) {
        return config.contains("shearable-animals." + animal);
    }

    // Methode om te controleren of een dier een shearable optie heeft
    public boolean hasShearableOption(String animal) {
        return config.contains("shearable-animals." + animal + ".shearable");
    }

    // Methode om te controleren of een dier een sliceable optie heeft
    public boolean hasSliceableOption(String animal) {
        return config.contains("shearable-animals." + animal + ".sliceable");
    }

    // Methode om alle geconfigureerde dieren te krijgen
    public Set<String> getAnimals() {
        return config.getConfigurationSection("shearable-animals").getKeys(false);
    }
}
