package bloody.devmules.shearMaster;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShearMasterCommand implements CommandExecutor {

    private ShearMaster plugin;

    public ShearMasterCommand(ShearMaster plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("shearmaster")) {
            if (args.length == 0) {
                player.sendMessage("Use /shearmaster reload, /shearmaster toggle <animal> <shear|slice>, or /shearmaster status.");
                return true;
            }

            if (args[0].equalsIgnoreCase("reload")) {
                if (!player.hasPermission("shearmaster.admin")) {
                    player.sendMessage("You do not have permission to use this command.");
                    return true;
                }
                // Config herladen en opnieuw initialiseren
                plugin.reloadConfig();
                plugin.getConfigManager().reloadConfig();
                player.sendMessage("ShearMaster configuration reloaded!");
                return true;
            }

            if (args[0].equalsIgnoreCase("toggle")) {
                if (!player.hasPermission("shearmaster.toggle")) {
                    player.sendMessage("You do not have permission to use this command.");
                    return true;
                }
                if (args.length < 3) {
                    player.sendMessage("Usage: /shearmaster toggle <animal> <shear|slice>");
                    return true;
                }
                String animal = args[1].toLowerCase();
                String type = args[2].toLowerCase();

                // Check if the animal is in the config
                if (!plugin.getConfigManager().isAnimalConfigured(animal)) {
                    player.sendMessage(ChatColor.RED + "Error: " + animal + " is not configured in the plugin.");
                    return true;
                }

                // Toggle shearable or sliceable
                if (type.equals("shear")) {
                    if (!plugin.getConfigManager().canBeSheared(animal)) {
                        player.sendMessage(ChatColor.RED + animal + " cannot be sheared.");
                    } else if (!plugin.getConfigManager().hasShearableOption(animal)) {
                        player.sendMessage(ChatColor.RED + animal + " cannot be toggled as shearable.");
                    } else {
                        plugin.getConfigManager().toggleShearable(animal);
                        player.sendMessage("Toggled shearable status for " + animal + ".");
                    }
                } else if (type.equals("slice")) {
                    if (!plugin.getConfigManager().hasSliceableOption(animal)) {
                        player.sendMessage(ChatColor.RED + animal + " cannot be toggled as sliceable.");
                    } else {
                        plugin.getConfigManager().toggleSliceable(animal);
                        player.sendMessage("Toggled sliceable status for " + animal + ".");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Error: Invalid type. Use shear or slice.");
                }
                return true;
            }

            if (args[0].equalsIgnoreCase("status")) {
                if (!player.hasPermission("shearmaster.admin")) {
                    player.sendMessage("You do not have permission to use this command.");
                    return true;
                }

                // Stijl en kleur toevoegen aan de status-output
                player.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "ShearMaster Status:");

                for (String animal : plugin.getConfigManager().getAnimals()) {
                    boolean shearable = plugin.getConfigManager().isShearable(animal);
                    boolean sliceable = plugin.getConfigManager().isSliceable(animal);
                    ChatColor shearableColor = shearable ? ChatColor.GREEN : ChatColor.RED;
                    ChatColor sliceableColor = sliceable ? ChatColor.GREEN : ChatColor.RED;

                    // Bericht voor elk dier
                    player.sendMessage(ChatColor.GOLD + animal + ": "
                            + ChatColor.WHITE + " Shearable: " + shearableColor + shearable
                            + ChatColor.WHITE + ", Sliceable: " + sliceableColor + sliceable);
                }
                return true;
            }
        }

        return false;
    }
}
