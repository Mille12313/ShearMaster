package bloody.devmules.shearMaster.listeners;

import bloody.devmules.shearMaster.ShearMaster;
import org.bukkit.Material;
<<<<<<< HEAD
import org.bukkit.Sound;
=======
>>>>>>> origin/main
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

<<<<<<< HEAD
import java.util.List;
import java.util.Map;

public class ShearListener implements Listener {

    private final ShearMaster plugin;
=======
public class ShearListener implements Listener {

    private ShearMaster plugin;
>>>>>>> origin/main

    public ShearListener(ShearMaster plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

<<<<<<< HEAD
        if (!(entity instanceof Ageable)) return;
        Ageable animal = (Ageable) entity;

        if (animal.getAge() < 0) return; // baby animal

        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() == Material.SHEARS) {
            shearAnimal(player, animal, item);
        } else if (isSword(item.getType())) {
            sliceAnimal(player, animal, item);
=======
        // Controleren of het een dier is en of het een volwassene is
        if (!(entity instanceof Ageable)) return;
        Ageable animal = (Ageable) entity;

        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        // Stop de interactie als het dier een baby is
        if (animal.getAge() < 0) {
            return;
        }

        // Scheren met schaar
        if (itemInHand.getType() == Material.SHEARS) {
            shearAnimal(player, animal, itemInHand);
        }
        // Oogsten met een zwaard
        else if (isSword(itemInHand.getType())) {
            harvestMeat(player, animal, itemInHand);
>>>>>>> origin/main
        }
    }

    private void shearAnimal(Player player, Ageable animal, ItemStack shears) {
<<<<<<< HEAD
        String type = getAnimalType(animal);
        if (type == null || !plugin.getConfigManager().isShearable(type)) return;

        List<Map<?, ?>> lootList =
                plugin.getConfig().getMapList("shearable-animals." + type + ".shearing.loot");
        for (Map<?, ?> loot : lootList) {
            try {
                String matName = (String) loot.get("material");
                Material mat = Material.valueOf(matName.toUpperCase());
                Object amtObj = loot.get("amount");
                int amount = (amtObj instanceof Number ? ((Number) amtObj).intValue() : 1);
                animal.getWorld().dropItemNaturally(animal.getLocation(), new ItemStack(mat, amount));
            } catch (Exception ignored) { }
        }

        // Durability
        Damageable meta = (Damageable) shears.getItemMeta();
        meta.setDamage(meta.getDamage() + 1);
        shears.setItemMeta(meta);

        // EXP
        int exp = plugin.getConfigManager().getShearExp(type);
        animal.getWorld().spawn(animal.getLocation(), ExperienceOrb.class).setExperience(exp);

        // Sound
        animal.getWorld().playSound(animal.getLocation(), Sound.ENTITY_SHEEP_SHEAR, 1f, 1f);

        // Baby
        animal.setBaby();
    }

    private void sliceAnimal(Player player, Ageable animal, ItemStack sword) {
        String type = getAnimalType(animal);
        if (type == null || !plugin.getConfigManager().isSliceable(type)) return;

        List<Map<?, ?>> lootList =
                plugin.getConfig().getMapList("shearable-animals." + type + ".slicing.loot");
        for (Map<?, ?> loot : lootList) {
            try {
                String matName = (String) loot.get("material");
                Material mat = Material.valueOf(matName.toUpperCase());
                Object amtObj = loot.get("amount");
                int amount = (amtObj instanceof Number ? ((Number) amtObj).intValue() : 1);
                animal.getWorld().dropItemNaturally(animal.getLocation(), new ItemStack(mat, amount));
            } catch (Exception ignored) { }
        }

        // Durability
        Damageable meta = (Damageable) sword.getItemMeta();
        meta.setDamage(meta.getDamage() + 1);
        sword.setItemMeta(meta);

        // EXP
        int exp = plugin.getConfigManager().getSliceExp(type);
        animal.getWorld().spawn(animal.getLocation(), ExperienceOrb.class).setExperience(exp);

        // Sound
        animal.getWorld().playSound(animal.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT, 1f, 1f);

        // Baby
        animal.setBaby();
    }

    private boolean isSword(Material mat) {
        return mat.name().endsWith("_SWORD");
    }

    private String getAnimalType(Entity e) {
        if (e instanceof Cow) return "cow";
        if (e instanceof Chicken) return "chicken";
        if (e instanceof Horse) return "horse";
        if (e instanceof Rabbit) return "rabbit";
        if (e instanceof Llama) return "llama";
        if (e instanceof Strider) return "strider";
        if (e instanceof Pig) return "pig";
=======
        String animalType = getAnimalType(animal);
        if (animalType == null || !plugin.getConfigManager().isShearable(animalType)) return;

        // Geef loot voor het scheren
        Material lootMaterial;
        try {
            lootMaterial = Material.valueOf(plugin.getConfigManager().getLoot(animalType).toUpperCase());
        } catch (IllegalArgumentException e) {
            return;
        }

        int lootAmount = plugin.getConfigManager().getLootAmount(animalType);
        ItemStack loot = new ItemStack(lootMaterial, lootAmount);

        // Laat de items op de grond vallen
        animal.getWorld().dropItemNaturally(animal.getLocation(), loot);

        // Verlies van duurzaamheid voor de schaar
        Damageable shearsMeta = (Damageable) shears.getItemMeta();
        shearsMeta.setDamage(shearsMeta.getDamage() + 1); // Schaar verliest 1 duurzaamheid
        shears.setItemMeta(shearsMeta);

        // Laat de EXP orbs op de grond vallen
        int expAmount = plugin.getConfigManager().getExpAmount(animalType);
        animal.getWorld().spawn(animal.getLocation(), ExperienceOrb.class).setExperience(expAmount);

        // Verander het dier in een baby zonder bericht
        animal.setBaby();
    }

    private void harvestMeat(Player player, Ageable animal, ItemStack sword) {
        String animalType = getAnimalType(animal);
        if (animalType == null || !plugin.getConfigManager().isSliceable(animalType)) return;

        // Geef vlees op basis van het type dier
        Material meatMaterial;
        try {
            meatMaterial = Material.valueOf(plugin.getConfigManager().getSliceLoot(animalType).toUpperCase());
        } catch (IllegalArgumentException e) {
            return;
        }

        int meatAmount = plugin.getConfigManager().getSliceLootAmount(animalType);
        ItemStack meat = new ItemStack(meatMaterial, meatAmount);

        // Laat de items op de grond vallen
        animal.getWorld().dropItemNaturally(animal.getLocation(), meat);

        // Verlies van duurzaamheid voor het zwaard
        Damageable swordMeta = (Damageable) sword.getItemMeta();
        swordMeta.setDamage(swordMeta.getDamage() + 1); // Zwaard verliest 1 duurzaamheid
        sword.setItemMeta(swordMeta);

        // Laat de EXP orbs op de grond vallen
        int expAmount = plugin.getConfigManager().getExpAmount(animalType);
        animal.getWorld().spawn(animal.getLocation(), ExperienceOrb.class).setExperience(expAmount);

        // Verander het dier in een baby zonder bericht
        animal.setBaby();
    }

    // Methode om te controleren of een item een zwaard is
    private boolean isSword(Material material) {
        return material.name().endsWith("_SWORD");
    }

    // Methode om het dierstype te bepalen
    private String getAnimalType(Entity entity) {
        if (entity instanceof Cow) return "cow";
        if (entity instanceof Chicken) return "chicken";
        if (entity instanceof Horse) return "horse";
        if (entity instanceof Rabbit) return "rabbit";
        if (entity instanceof Llama) return "llama";
        if (entity instanceof Strider) return "strider";
        if (entity instanceof Pig) return "pig";
>>>>>>> origin/main
        return null;
    }
}
