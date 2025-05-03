package bloody.devmules.shearMaster.listeners;

import bloody.devmules.shearMaster.ShearMaster;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.List;
import java.util.Map;

public class ShearListener implements Listener {

    private final ShearMaster plugin;

    public ShearListener(ShearMaster plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if (!(entity instanceof Ageable)) return;
        Ageable animal = (Ageable) entity;

        if (animal.getAge() < 0) return; // baby animal

        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() == Material.SHEARS) {
            shearAnimal(player, animal, item);
        } else if (isSword(item.getType())) {
            sliceAnimal(player, animal, item);
        }
    }

    private void shearAnimal(Player player, Ageable animal, ItemStack shears) {
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
        return null;
    }
}
