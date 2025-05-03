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
<<<<<<< HEAD
        return config.getBoolean("shearable-animals." + animal + ".shearing.enabled", false);
=======
        return config.getBoolean("shearable-animals." + animal + ".shearable", false);
>>>>>>> origin/main
    }

    // Methode om te controleren of een dier sliceable is
    public boolean isSliceable(String animal) {
<<<<<<< HEAD
        return config.getBoolean("shearable-animals." + animal + ".slicing.enabled", false);
=======
        return config.getBoolean("shearable-animals." + animal + ".sliceable", false);
>>>>>>> origin/main
    }

    // Controle of een dier shearable kan zijn (dus dieren zoals pig blokkeren)
    public boolean canBeSheared(String animal) {
<<<<<<< HEAD
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
=======
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
>>>>>>> origin/main
    public boolean isAnimalConfigured(String animal) {
        return config.contains("shearable-animals." + animal);
    }

<<<<<<< HEAD
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
=======
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
>>>>>>> origin/main
}
